package com.togedocs.backend.domain.repository;

import com.togedocs.backend.api.dto.ApilogsRequest;
import com.togedocs.backend.api.dto.ApilogsResponse;

public interface ApilogsRepository {

    boolean existsByProjectId(Long projectId);

    ApilogsResponse.Logs getLogs(Long projectId, String rowId);

    boolean addLog(Long projectId, String rowId, ApilogsRequest.AddLogRequest request);
}