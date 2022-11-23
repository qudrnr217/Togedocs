package com.togedocs.backend.domain.repository;

import com.togedocs.backend.api.dto.ApilogsRequest;
import com.togedocs.backend.domain.entity.LogDto;

import java.util.List;

public interface ApilogsRepository {

    boolean existsByProjectId(Long projectId);

    List<LogDto> getLogs(Long projectId, String rowId);

    boolean addLog(Long projectId, String rowId, ApilogsRequest.AddLogRequest request);
}