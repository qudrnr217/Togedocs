package com.togedocs.backend.api.controller;

import com.togedocs.backend.api.dto.ProjectRequest;
import com.togedocs.backend.api.dto.ProjectResponse;
import com.togedocs.backend.api.service.ProjectService;
import com.togedocs.backend.common.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/project")
public class ProjectController {
    private final ProjectService projectService;

    @PostMapping
    public ResponseEntity<String> createProject(@RequestBody ProjectRequest.CreateProjectRequest request, Principal principal) {
        String providerId = principal.getName();
        projectService.createProject(request, providerId);
        return ResponseEntity.status(HttpStatus.CREATED).body("성공적으로 프로젝트를 생성했습니다!");
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long projectId, Principal principal) {
        String providerId = principal.getName();
        projectService.deleteProject(projectId, providerId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/{projectId}/leave")
    public ResponseEntity<Void> leaveProject(@PathVariable Long projectId, Principal principal) {
        String providerId = principal.getName();
        projectService.leaveProject(projectId, providerId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/join")
    public ResponseEntity<String> joinProject(@RequestBody ProjectRequest.JoinProjectRequest request, Principal principal) {
        String providerId = principal.getName();
        projectService.joinProject(request, providerId);
        return ResponseEntity.status(HttpStatus.CREATED).body("성공적으로 프로젝트에 참가했습니다!");
    }

    @GetMapping("/{projectId}/members")
    public ResponseEntity<ProjectResponse.MemberManageInfo> getMemberManageInfo(@PathVariable Long projectId, Principal principal) {
        ProjectResponse.MemberManageInfo response;
        String providerId = principal.getName();
        response = projectService.getMemberManagerInfo(projectId, providerId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{projectId}/member/{userId}")
    public ResponseEntity<Void> removeMember(@PathVariable Long projectId, @PathVariable Long userId, Principal principal) {
        String providerId = principal.getName();
        projectService.removeMember(projectId, userId, providerId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("/{projectId}/member")
    public ResponseEntity<ProjectResponse.MemberManageInfo> updateMemberRole(@PathVariable Long projectId, @RequestBody ProjectRequest.UpdateMemberRoleRequest request, Principal principal) {
        String providerId = principal.getName();
        ProjectResponse.MemberManageInfo response = projectService.updateMemberRole(projectId, request, providerId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<ProjectResponse.Project> getProjectByCode(@PathVariable String code, Principal principal) {
        ProjectResponse.Project response = projectService.getProjectByCode(code);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
