package com.togedocs.backend.api.service;

import com.togedocs.backend.api.dto.ApilogsRequest;
import com.togedocs.backend.api.dto.ApilogsResponse;
import com.togedocs.backend.api.exception.IdNotFoundException;
import com.togedocs.backend.domain.repository.ApilogsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApilogsService {
    private final ApilogsRepository apilogsRepository;

    public ApilogsResponse.Logs getLogs(Long projectId, String rowId) {
        return apilogsRepository.getLogs(projectId, rowId);
    }

    public ApilogsResponse.LogIdsAndTime addLog(Long projectId, String rowId, ApilogsRequest.AddLogRequest request) throws IdNotFoundException {
        return apilogsRepository.addLog(projectId, rowId, request);
    }
}
