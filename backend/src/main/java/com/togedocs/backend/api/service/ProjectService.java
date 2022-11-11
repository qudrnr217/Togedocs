package com.togedocs.backend.api.service;

import com.togedocs.backend.api.dto.ProjectRequest;
import com.togedocs.backend.api.dto.ProjectResponse;
import com.togedocs.backend.domain.entity.Project;
import com.togedocs.backend.domain.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;
    public ProjectResponse.Id createProject(ProjectRequest.CreateProjectRequest projectRequest) {
        // 1. code 생성해서 따로 update 시켜줘야함
        String code = null;
        // 2. RDB에 project 레코드를 넣으면서 imgNo를 넣을거야
        Project project = Project.builder()
                .imgNo(projectRequest.getImgNo())
                .code(code)
                .build();

        projectRepository.save(project);
        // 3. mongodb에 projectId, title, desc,
        // row : [], cols : [REQUIRED, PAYLOAD인 cols], data : {}

    }

}
