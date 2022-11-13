package com.togedocs.backend.api.controller;

import com.togedocs.backend.api.dto.ApidocsResponse;
import com.togedocs.backend.api.dto.ProjectDto;
import com.togedocs.backend.api.dto.ProjectRequest;
import com.togedocs.backend.api.dto.ProjectResponse;
import com.togedocs.backend.api.exception.IdNotFoundException;
import com.togedocs.backend.api.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;

@RestController
@RequestMapping("/api/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @PostMapping
    public ResponseEntity<?> createProject(@RequestBody ProjectRequest.CreateProjectRequest request){
        ProjectResponse.Id response;
        response = projectService.createProject(request);
        return ResponseEntity.status(200).body(response);
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<?> deleteProject(@PathVariable Long projectId){
        ProjectResponse.Id response;
        try {
            response = projectService.deleteProject(projectId);
        } catch (IdNotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e);
        }
        return ResponseEntity.status(204).body(response);
    }

    @PostMapping("/join")
    public ResponseEntity<?> joinProject(@RequestBody ProjectRequest.JoinProjectRequest request){
        ProjectResponse.ProjectUser response;
        try {
            response = projectService.joinProject(request);
        } catch (IdNotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e);
        }
        return ResponseEntity.status(200).body(response);
    }

    @GetMapping("/{projectId}/members")
    public ResponseEntity<?> getMemberManageInfo(@PathVariable Long projectId){
        ProjectResponse.MemberManageInfo response;
        try {
            response = projectService.getMemberManagerInfo(projectId);
        } catch (IdNotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e);
        }
        return ResponseEntity.status(200).body(response);
    }

    @DeleteMapping("/{projectId}/member/{userId}")
    public ResponseEntity<?> removeMember(@PathVariable Long projectId, @PathVariable Long userId){
        ProjectResponse.MemberManageInfo response;
        try {
            response = projectService.removeMember(projectId, userId);
        } catch (IdNotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e);
        }
        return ResponseEntity.status(204).body(response);
    }

    @PatchMapping("/{projectId}/member")
    public ResponseEntity<?> updateMemberRole(@PathVariable Long projectId, @RequestBody ProjectRequest.UpdateMemberRoleRequest request){
        ProjectResponse.MemberManageInfo response;
        try {
            response = projectService.updateMemberRole(projectId, request);
        } catch (IdNotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e);
        }
        return ResponseEntity.status(200).body(response);
    }
}
