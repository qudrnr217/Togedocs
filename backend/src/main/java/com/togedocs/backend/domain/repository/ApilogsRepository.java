package com.togedocs.backend.domain.repository;

import com.togedocs.backend.api.dto.ApilogsRequest;
import com.togedocs.backend.domain.entity.LogDto;

import java.util.List;
import com.togedocs.backend.domain.entity.Project;

public interface ApilogsRepository {

    void createApilogs(Project project);
    void deleteApilogs(Long projectId);
    boolean existsByProjectId(Long projectId);

    List<LogDto> getLogs(Long projectId, String rowId);

    boolean addLog(Long projectId, String rowId, ApilogsRequest.AddLogRequest request);
}