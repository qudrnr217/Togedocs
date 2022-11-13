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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@AllArgsConstructor
public class ProjectService {
    private final String APIDOCS = "apidocs";
    private final int DEFAULT_WIDTH = 100;
    private UserRepository userRepository;
    private ProjectRepository projectRepository;
    private ProjectUserRepository projectUserRepository;
    private MongoTemplate mongoTemplate;

    public Project findById(Long projectId) throws IdNotFoundException {
        return projectRepository.findById(projectId)
                .orElseThrow(() -> new IdNotFoundException("projectId"));
    }

    public ProjectResponse.Id createProject(ProjectRequest.CreateProjectRequest projectRequest) {
        // TODO 1. code 생성하기
        String code = UUID.randomUUID().toString();

        // 2. project insert
        Project project = Project.builder()
                .imgNo(projectRequest.getImgNo())
                .code(code)
                .build();
        projectRepository.save(project);

        // 3. mongodb에 collection insert
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

        // 4. project_user insert
        // 생성한 사람한테 admin 권한 부여
        // TODO 사용자 정보 꺼내기
        User user = userRepository.findById(1L).get();

        ProjectUser projectUser = ProjectUser.builder()
                .project(project)
                .user(user)
                .role(ProjectUserRole.ADMIN)
                .build();
        projectUserRepository.save(projectUser);

        return ProjectResponse.Id.build(project.getId());
    }


    @Transactional
    public ProjectResponse.Id deleteProject(Long projectId) throws IdNotFoundException {
        // TODO 1. user가 매니저인지 확인하기

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


    public ProjectResponse.ProjectUser joinProject(ProjectRequest.JoinProjectRequest request) throws IdNotFoundException {
        // 1. project에서 code가 동일한 레코드 찾기
        Project project = projectRepository.findByCode(request.getCode()).orElseThrow(() -> new IdNotFoundException("project code"));

        // TODO 2. user 정보 꺼내기
        User user = userRepository.findById(2L).get();

        // 3. Project user insert하기 (기본 role은 member)
        ProjectUser projectUser = ProjectUser.builder()
                .project(project)
                .user(user)
                .role(ProjectUserRole.MEMBER)
                .build();
        projectUserRepository.save(projectUser);

        return ProjectResponse.ProjectUser.build(project.getId(), user.getId(), projectUser.getRole());
    }


    public ProjectResponse.MemberManageInfo getMemberManagerInfo(Long projectId) throws IdNotFoundException {
        // TODO 1. user 확인 (프로젝트 멤버인지)

        // 2. project 테이블에서 code를 꺼내기
        String code = findById(projectId).getCode();
        // 3. project user 테이블에서 user와 role을 꺼내 list로 만들기
        List<UserDto> members = projectUserRepository.getMembers(projectId);
        return ProjectResponse.MemberManageInfo.build(projectId, members, code);
    }

    public ProjectResponse.MemberManageInfo removeMember(Long projectId, Long userId) throws IdNotFoundException {
        // TODO 1. 매니저인지 확인

        // TODO 2. 삭제 후 매니저가 1명 이상 남아있어야 함

        // 3. project user 삭제
        ProjectUser projectUser = projectUserRepository.findByProjectIdAndUserId(projectId, userId)
                .orElseThrow(() -> new IdNotFoundException("projectId and userId"));
        projectUserRepository.deleteById(projectUser.getId());

        return null;
    }


    @Transactional
    public ProjectResponse.MemberManageInfo updateMemberRole(Long projectId, ProjectRequest.UpdateMemberRoleRequest request) throws IdNotFoundException {
        // TODO 1. 매니저인지 확인

        // 2. project user role 업데이트
        // TODO 고려할 점 : 다른 매니저를 강등할 수 있는지? 혹은 MEMBER -> ADMIN만 가능?
        Long updatedResult = projectUserRepository.updateMemberRole(projectId, request);
        if (updatedResult == 0) throw new IdNotFoundException("projectId and userId");

        return null;
    }
}
