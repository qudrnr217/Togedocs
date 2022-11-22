package com.togedocs.backend.domain.repository;

import com.togedocs.backend.api.dto.ApilogsRequest;
import com.togedocs.backend.api.dto.ApilogsResponse;
import com.togedocs.backend.api.exception.IdNotFoundException;

public interface ApilogsRepository {
    public ApilogsResponse.Logs getLogs(Long projectId, String rowId);

    public ApilogsResponse.LogIdsAndTime addLog(Long projectId, String rowId, ApilogsRequest.AddLogRequest request) throws IdNotFoundException;
}