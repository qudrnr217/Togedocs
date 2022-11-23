package com.togedocs.backend.api.service;

import com.togedocs.backend.api.dto.ApilogsRequest;
import com.togedocs.backend.api.dto.ApilogsResponse;
import com.togedocs.backend.common.exception.BusinessException;
import com.togedocs.backend.common.exception.ErrorCode;
import com.togedocs.backend.domain.repository.ApilogsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApilogsService {
    private final ApilogsRepository apilogsRepository;

    public ApilogsResponse.Logs getLogs(Long projectId, String rowId) {
        if (!apilogsRepository.existsByProjectId(projectId)) {
            throw new BusinessException(ErrorCode.PROJECT_NOT_FOUND);
        }
        return ApilogsResponse.Logs.build(apilogsRepository.getLogs(projectId, rowId));
    }

    public void addLog(Long projectId, String rowId, ApilogsRequest.AddLogRequest request) {
        boolean result = apilogsRepository.addLog(projectId, rowId, request);
        if (!result) {
            throw new BusinessException(ErrorCode.PROJECT_NOT_FOUND);
        }
    }
}
