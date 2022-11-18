package com.togedocs.backend.api.service;

import com.mongodb.client.result.DeleteResult;
import com.togedocs.backend.api.dto.ProjectRequest;
import com.togedocs.backend.api.dto.ProjectResponse;
import com.togedocs.backend.api.dto.UserDto;
import com.togedocs.backend.api.exception.IdNotFoundException;
import com.togedocs.backend.domain.entity.*;
import com.togedocs.backend.domain.repository.ProjectRepository;
import com.togedocs.backend.domain.repository.ProjectUserRepository;
import com.togedocs.backend.domain.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@AllArgsConstructor
public class ProjectService {
    private final String APIDOCS = "apidocs";
    private final String APILOGS = "apilogs";
    private final int DEFAULT_WIDTH = 100;
    private UserRepository userRepository;
    private ProjectRepository projectRepository;
    private ProjectUserRepository projectUserRepository;
    private MongoTemplate mongoTemplate;

    public Project findById(Long projectId) throws IdNotFoundException {
        return projectRepository.findById(projectId)
                .orElseThrow(() -> new IdNotFoundException("projectId"));
    }

    public ProjectUser findProjectUser(Long projectId, Long userId) throws IdNotFoundException {
        return projectUserRepository.findByProjectIdAndUserId(projectId, userId)
                .orElseThrow(() -> new IdNotFoundException("projectId and userId"));
    }

    public ProjectResponse.Id createProject(ProjectRequest.CreateProjectRequest projectRequest, String loginUserProviderId) {
        // 1. code 생성하기
        String code = UUID.randomUUID().toString();

        // 2. project insert
        Project project = Project.builder()
                .imgNo(projectRequest.getImgNo())
                .code(code)
                .build();
        projectRepository.save(project);

        // 3-1. mongodb에 apidocs insert
        // projectId, title, desc, row : [], cols : [REQUIRED, PAYLOAD인 cols], data : {}
        List<ColDto> cols = new ArrayList<>();
        cols.add(new ColDto("one", "Name", "text", DEFAULT_WIDTH, ColCategory.REQUIRED));
        cols.add(new ColDto("two", "Method", "text", DEFAULT_WIDTH, ColCategory.REQUIRED));
        cols.add(new ColDto("three", "URL", "text", DEFAULT_WIDTH, ColCategory.REQUIRED));
        cols.add(new ColDto("d-one", "Query Params", "text", 1, ColCategory.PAYLOAD));
        cols.add(new ColDto("d-two", "Request Body", "text", 1, ColCategory.PAYLOAD));
        cols.add(new ColDto("d-three", "Response Body", "text", 1, ColCategory.PAYLOAD));

        Apidocs apidocs = Apidocs.builder()
                .projectId(project.getId())
                .title(projectRequest.getTitle())
                .desc(projectRequest.getDesc())
                .rows(new ArrayList<String>())
                .cols(cols)
                .data(new HashMap<String, Map<String, String>>())
                .build();

        mongoTemplate.insert(apidocs, APIDOCS);

        // 3-2. mongodb에 apilogs insert

        Apilogs apilogs = Apilogs.builder()
                .projectId(project.getId())
                .log(new HashMap<String, List<LogDto>>())
                .build();

        mongoTemplate.insert(apilogs,APILOGS);

        // 4. project_user insert
        // 생성한 사람한테 admin 권한 부여
        // 사용자 정보 꺼내기
        User user = userRepository.findByProviderId(loginUserProviderId);

        ProjectUser projectUser = ProjectUser.builder()
                .project(project)
                .user(user)
                .role(ProjectUserRole.ADMIN)
                .build();
        projectUserRepository.save(projectUser);

        return ProjectResponse.Id.build(project.getId());
    }


    @Transactional
    public ProjectResponse.Id deleteProject(Long projectId, String loginUserProviderId) throws IdNotFoundException {
        // 1. user 확인 (admin)
        User user = userRepository.findByProviderId(loginUserProviderId);
        ProjectUser loginProjectUser = findProjectUser(projectId, user.getId());
        if (loginProjectUser.getRole() != ProjectUserRole.ADMIN) {
            throw new AccessDeniedException("Login User is not ADMIN");
        }

        // 2. RDB에서 삭제 (Project, project user 테이블에서 삭제)
        Project project = findById(projectId);
        projectUserRepository.deleteByProjectId(projectId);
        projectRepository.deleteById(projectId);

        // 3. mongoDB에서 삭제
        Query query = new Query().addCriteria(Criteria.where("projectId").is(projectId));
        DeleteResult deleteResult = mongoTemplate.remove(query, APIDOCS);

        if (deleteResult.getDeletedCount() == 0) throw new IdNotFoundException("projectId");
        return ProjectResponse.Id.build(projectId);
    }

    @Transactional
    public ProjectResponse.Id leaveProject(Long projectId, String loginUserProviderId) throws IdNotFoundException {
        // 1. project_user에서 제거
        User user = userRepository.findByProviderId(loginUserProviderId);
        ProjectUser loginProjectUser = findProjectUser(projectId, user.getId());
        projectUserRepository.deleteById(loginProjectUser.getId());

        return ProjectResponse.Id.build(projectId);
    }

    public ProjectResponse.ProjectUser joinProject(ProjectRequest.JoinProjectRequest request, String loginUserProviderId) throws IdNotFoundException {
        // 1. project에서 code가 동일한 레코드 찾기
        Project project = projectRepository.findByCode(request.getCode())
                .orElseThrow(() -> new IdNotFoundException("project code"));

        // 2. user 정보 꺼내기
        User user = userRepository.findByProviderId(loginUserProviderId);

        // 3. Project user insert하기 (기본 role은 member)
        ProjectUser projectUser = ProjectUser.builder()
                .project(project)
                .user(user)
                .role(ProjectUserRole.MEMBER)
                .build();
        projectUserRepository.save(projectUser);
        return ProjectResponse.ProjectUser.build(project.getId(), user.getId(), projectUser.getRole());
    }


    public ProjectResponse.MemberManageInfo getMemberManagerInfo(Long projectId, String loginUserProviderId) throws IdNotFoundException {
        // 1. user 확인 (member or admin)
        User user = userRepository.findByProviderId(loginUserProviderId);
        ProjectUser loginProjectUser = findProjectUser(projectId, user.getId());

        // 2. project 테이블에서 code를 꺼내기
        String code = findById(projectId).getCode();

        // 3. project user 테이블에서 user와 role을 꺼내 list로 만들기
        List<UserDto> members = projectUserRepository.getMembers(projectId);
        return ProjectResponse.MemberManageInfo.build(projectId, members, code);
    }

    public ProjectResponse.MemberManageInfo removeMember(Long projectId, Long userId, String loginUserProviderId) throws IdNotFoundException {
        // 1. user 확인 (admin)
        User user = userRepository.findByProviderId(loginUserProviderId);
        ProjectUser loginProjectUser = findProjectUser(projectId, user.getId());
        if (loginProjectUser.getRole() != ProjectUserRole.ADMIN) {
            throw new AccessDeniedException("Login User is not ADMIN");
        }

        // 2. project user 삭제
        ProjectUser projectUser = findProjectUser(projectId, userId);
        projectUserRepository.deleteById(projectUser.getId());
        return getMemberManagerInfo(projectId, loginUserProviderId);
    }


    @Transactional
    public ProjectResponse.MemberManageInfo updateMemberRole(Long projectId, ProjectRequest.UpdateMemberRoleRequest request, String loginUserProviderId) throws IdNotFoundException {
        // 1. user 확인 (admin)
        User user = userRepository.findByProviderId(loginUserProviderId);
        ProjectUser loginProjectUser = findProjectUser(projectId, user.getId());
        if (loginProjectUser.getRole() != ProjectUserRole.ADMIN) {
            throw new AccessDeniedException("Login User is not ADMIN");
        }

        // 2. project user role 업데이트
        Long updatedResult = projectUserRepository.updateMemberRole(projectId, request);
        if (updatedResult == 0) {
            throw new IdNotFoundException("projectId and userId");
        }
        return getMemberManagerInfo(projectId, loginUserProviderId);
    }

    public ProjectResponse.Project getProjectByCode(String code) throws IdNotFoundException {
        // 1. project에서 code가 동일한 레코드 찾기
        Project project = projectRepository.findByCode(code)
                .orElseThrow(() -> new IdNotFoundException("project code"));

        // 2. member 이름 리스트
        List<String> names = projectUserRepository.getMemberNames(project.getId());

        // 3. title, desc, imgNo
        Query query = new Query().addCriteria(Criteria.where("projectId").is(project.getId()));
        Apidocs apidocs = mongoTemplate.findOne(query, Apidocs.class, APIDOCS);

        return ProjectResponse.Project.build(project.getId(), apidocs.getTitle(), apidocs.getDesc(), project.getImgNo(), names);
    }
}
