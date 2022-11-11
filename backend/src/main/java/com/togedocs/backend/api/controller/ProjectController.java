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

@RestController
@RequestMapping("/api/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @PostMapping
    public ResponseEntity<?> createProject(@RequestBody ProjectRequest.CreateProjectRequest projectRequest){
        ProjectResponse.Id response;
        response = projectService.createProject(projectRequest);
        return ResponseEntity.status(200).body(response);
    }


}
