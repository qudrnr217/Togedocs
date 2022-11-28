package com.togedocs.backend.api.service;

import com.mongodb.client.result.DeleteResult;
import com.togedocs.backend.api.dto.ProjectRequest;
import com.togedocs.backend.api.dto.ProjectResponse;
import com.togedocs.backend.api.dto.UserDto;
import com.togedocs.backend.common.exception.BusinessException;
import com.togedocs.backend.common.exception.ErrorCode;
import com.togedocs.backend.domain.entity.*;
import com.togedocs.backend.domain.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final UserService userService;
    private final ProjectRepository projectRepository;
    private final ProjectUserRepository projectUserRepository;
    private final ApidocsRepository apidocsRepository;
    private final ApilogsRepository apilogsRepository;

    public Project findById(Long projectId) throws BusinessException {
        return projectRepository.findById(projectId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PROJECT_NOT_FOUND));
    }

    public ProjectUser findProjectUser(Long projectId, Long userId) {
        return projectUserRepository.findByProjectIdAndUserId(projectId, userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PROJECT_USER_FORBIDDEN));
    }

    public void createProject(ProjectRequest.CreateProjectRequest request, String loginUserProviderId) {
        String code = UUID.randomUUID().toString();
        Project project = Project.builder()
                .imgNo(request.getImgNo())
                .code(code)
                .build();
        projectRepository.save(project);
        apidocsRepository.createApidocs(request, project.getId());
        apilogsRepository.createApilogs(project);

        User user = userService.findUserByUuid(loginUserProviderId);
        ProjectUser projectUser = ProjectUser.builder()
                .project(project)
                .user(user)
                .role(ProjectUserRole.ADMIN)
                .build();
        projectUserRepository.save(projectUser);
    }

    @Transactional
    public void deleteProject(Long projectId, String loginUserProviderId) {
        User user = userService.findUserByUuid(loginUserProviderId);
        ProjectUser loginProjectUser = findProjectUser(projectId, user.getId());
        if (loginProjectUser.getRole() != ProjectUserRole.ADMIN) {
            throw new BusinessException(ErrorCode.USER_NOT_ADMIN);
        }

        findById(projectId);
        projectUserRepository.deleteByProjectId(projectId);
        projectRepository.deleteById(projectId);

        apidocsRepository.deleteApidocs(projectId);
        apilogsRepository.deleteApilogs(projectId);
    }

    @Transactional
    public void leaveProject(Long projectId, String loginUserProviderId) {
        User user = userService.findUserByUuid(loginUserProviderId);
        ProjectUser loginProjectUser = findProjectUser(projectId, user.getId());
        projectUserRepository.deleteById(loginProjectUser.getId());
    }

    public void joinProject(ProjectRequest.JoinProjectRequest request, String loginUserProviderId) {
        Project project = projectRepository.findByCode(request.getCode())
                .orElseThrow(() -> new BusinessException(ErrorCode.PROJECT_NOT_FOUND));
        User user = userService.findUserByUuid(loginUserProviderId);
        ProjectUser projectUser = ProjectUser.builder()
                .project(project)
                .user(user)
                .role(ProjectUserRole.MEMBER)
                .build();
        projectUserRepository.save(projectUser);
    }


    public ProjectResponse.MemberManageInfo getMemberManagerInfo(Long projectId, String loginUserProviderId) {
        User user = userService.findUserByUuid(loginUserProviderId);
        findProjectUser(projectId, user.getId());

        List<UserDto> members = projectUserRepository.getMembers(projectId);
        String code = findById(projectId).getCode();
        return ProjectResponse.MemberManageInfo.build(projectId, members, code);
    }

    public void removeMember(Long projectId, Long userId, String loginUserProviderId) {
        User user = userService.findUserByUuid(loginUserProviderId);
        ProjectUser loginProjectUser = findProjectUser(projectId, user.getId());
        if (loginProjectUser.getRole() != ProjectUserRole.ADMIN) {
            throw new BusinessException(ErrorCode.USER_NOT_ADMIN);
        }

        ProjectUser projectUser = findProjectUser(projectId, userId);
        projectUserRepository.deleteById(projectUser.getId());
    }


    @Transactional
    public ProjectResponse.MemberManageInfo updateMemberRole(Long projectId, ProjectRequest.UpdateMemberRoleRequest request, String loginUserProviderId) {
        User user = userService.findUserByUuid(loginUserProviderId);
        ProjectUser loginProjectUser = findProjectUser(projectId, user.getId());
        if (loginProjectUser.getRole() != ProjectUserRole.ADMIN) {
            throw new BusinessException(ErrorCode.USER_NOT_ADMIN);
        }

        Long updatedResult = projectUserRepository.updateMemberRole(projectId, request);
        if (updatedResult == 0) {
            throw new BusinessException(ErrorCode.PROJECT_USER_NOT_FOUND);
        }
        return getMemberManagerInfo(projectId, loginUserProviderId);
    }

    public ProjectResponse.Project getProjectByCode(String code) throws BusinessException {
        Project project = projectRepository.findByCode(code)
                .orElseThrow(() -> new BusinessException(ErrorCode.PROJECT_NOT_FOUND));
        List<String> names = projectUserRepository.getMemberNames(project.getId());
        Apidocs apidocs = apidocsRepository.getDocs(project.getId());

        return ProjectResponse.Project.build(project.getId(), apidocs.getTitle(), apidocs.getDesc(), project.getImgNo(), names);
    }
}
