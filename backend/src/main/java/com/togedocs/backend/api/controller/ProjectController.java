package com.togedocs.backend.api.controller;

import com.togedocs.backend.api.dto.ProjectRequest;
import com.togedocs.backend.api.dto.ProjectResponse;
import com.togedocs.backend.api.exception.IdNotFoundException;
import com.togedocs.backend.api.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.security.Principal;

@RestController
@RequestMapping("/api/project")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;


    @PostMapping
    public ResponseEntity<?> createProject(@RequestBody ProjectRequest.CreateProjectRequest request, Principal principal) {
        ProjectResponse.Id response;
        String providerId = principal.getName();
        response = projectService.createProject(request, providerId);
        return ResponseEntity.status(200).body(response);
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<?> deleteProject(@PathVariable Long projectId, Principal principal) {
        ProjectResponse.Id response;
        String providerId = principal.getName();
        try {
            response = projectService.deleteProject(projectId, providerId);
        } catch (IdNotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Unexpected exception");
        }
        return ResponseEntity.status(204).body(response);
    }

    @DeleteMapping("/{projectId}/leave")
    public ResponseEntity<?> leaveProject(@PathVariable Long projectId, Principal principal){
        ProjectResponse.Id response;
        String providerId = principal.getName();
        try {
            response = projectService.leaveProject(projectId, providerId);
        } catch (IdNotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Unexpected exception");
        }
        return ResponseEntity.status(204).body(response);
    }

    @PostMapping("/join")
    public ResponseEntity<?> joinProject(@RequestBody ProjectRequest.JoinProjectRequest request, Principal principal) {
        ProjectResponse.ProjectUser response;
        String providerId = principal.getName();

        try {
            response = projectService.joinProject(request, providerId);
        } catch (IdNotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Unexpected exception");
        }
        return ResponseEntity.status(200).body(response);
    }

    @GetMapping("/{projectId}/members")
    public ResponseEntity<?> getMemberManageInfo(@PathVariable Long projectId, Principal principal) {
        ProjectResponse.MemberManageInfo response;
        String providerId = principal.getName();
        try {
            response = projectService.getMemberManagerInfo(projectId, providerId);
        } catch (IdNotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Unexpected exception");
        }
        return ResponseEntity.status(200).body(response);
    }

    @Transactional
    @DeleteMapping("/{projectId}/member/{userId}")
    public ResponseEntity<?> removeMember(@PathVariable Long projectId, @PathVariable Long userId, Principal principal) {
        ProjectResponse.MemberManageInfo response;
        String providerId = principal.getName();
        try {
            response = projectService.removeMember(projectId, userId, providerId);
        } catch (IdNotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Unexpected exception");
        }
        return ResponseEntity.status(200).body(response);
    }

    @Transactional
    @PatchMapping("/{projectId}/member")
    public ResponseEntity<?> updateMemberRole(@PathVariable Long projectId, @RequestBody ProjectRequest.UpdateMemberRoleRequest request, Principal principal) {
        ProjectResponse.MemberManageInfo response;
        String providerId = principal.getName();
        try {
            response = projectService.updateMemberRole(projectId, request, providerId);
        } catch (IdNotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Unexpected exception");
        }
        return ResponseEntity.status(200).body(response);
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<?> getProjectByCode(@PathVariable String code, Principal principal){
        ProjectResponse.Project response;
        try{
            response = projectService.getProjectByCode(code);
        } catch (IdNotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Unexpected exception");
        }
        return ResponseEntity.status(200).body(response);
    }

}
