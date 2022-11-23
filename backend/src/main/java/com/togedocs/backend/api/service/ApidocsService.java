package com.togedocs.backend.api.service;

import com.togedocs.backend.api.dto.ApidocsRequest;
import com.togedocs.backend.api.dto.ApidocsResponse;
import com.togedocs.backend.common.exception.BusinessException;
import com.togedocs.backend.common.exception.ErrorCode;
import com.togedocs.backend.domain.entity.Apidocs;
import com.togedocs.backend.domain.repository.ApidocsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApidocsService {
    private final ApidocsRepository apidocsRepository;

    public void addRow(Long projectId) {
        boolean result = apidocsRepository.addRow(projectId);
        if (!result) {
            throw new BusinessException(ErrorCode.PROJECT_NOT_FOUND);
        }
    }

    public void addCol(Long projectId, ApidocsRequest.AddColRequest request) {
        boolean result = apidocsRepository.addCol(projectId, request);
        if (!result) {
            throw new BusinessException(ErrorCode.PROJECT_NOT_FOUND);
        }
    }

    public void moveRow(Long projectId, ApidocsRequest.MoveItemRequest request) {
        if (!apidocsRepository.existsByProjectId(projectId)) {
            throw new BusinessException(ErrorCode.PROJECT_NOT_FOUND);
        }
        boolean result = apidocsRepository.moveRow(projectId, request);
        if (!result) {
            throw new BusinessException(ErrorCode.ROW_NOT_FOUND);
        }
    }

    public void moveCol(Long projectId, ApidocsRequest.MoveItemRequest request) {
        if (!apidocsRepository.existsByProjectId(projectId)) {
            throw new BusinessException(ErrorCode.PROJECT_NOT_FOUND);
        }
        boolean result = apidocsRepository.moveCol(projectId, request);
        if (!result) {
            throw new BusinessException(ErrorCode.COL_NOT_FOUND);
        }
    }

    public void deleteRow(Long projectId, String rowId) {
        if (!apidocsRepository.existsByProjectId(projectId)) {
            throw new BusinessException(ErrorCode.PROJECT_NOT_FOUND);
        }
        boolean result = apidocsRepository.deleteRow(projectId, rowId);
        if (!result) {
            throw new BusinessException(ErrorCode.ROW_NOT_FOUND);
        }
    }

    public void deleteCol(Long projectId, String colId) {
        if (!apidocsRepository.existsByProjectId(projectId)) {
            throw new BusinessException(ErrorCode.PROJECT_NOT_FOUND);
        }
        boolean result = apidocsRepository.deleteCol(projectId, colId);
        if (!result) {
            throw new BusinessException(ErrorCode.COL_NOT_FOUND);
        }
    }

    public void updateCell(Long projectId, ApidocsRequest.UpdateCellRequest request) {
        if (!apidocsRepository.existsByProjectId(projectId)) {
            throw new BusinessException(ErrorCode.PROJECT_NOT_FOUND);
        }
        boolean result = apidocsRepository.updateCell(projectId, request);
        if (!result) {
            throw new BusinessException(ErrorCode.CELL_NOT_FOUND);
        }
    }

    public ApidocsResponse.Apidocs getDocs(Long projectId) {
        Apidocs apidocs = apidocsRepository.getDocs(projectId);
        if (apidocs == null) {
            throw new BusinessException(ErrorCode.PROJECT_NOT_FOUND);
        }
        return ApidocsResponse.Apidocs.build(apidocs);
    }

    public void updateProjectInfo(Long projectId, ApidocsRequest.UpdateProjectInfoRequest request) {
        boolean result = apidocsRepository.updateProjectInfo(projectId, request);
        if (!result) {
            throw new BusinessException(ErrorCode.PROJECT_NOT_FOUND);
        }
    }

    public void updateCol(Long projectId, String colId, ApidocsRequest.UpdateColRequest request) {
        if (!apidocsRepository.existsByProjectId(projectId)) {
            throw new BusinessException(ErrorCode.PROJECT_NOT_FOUND);
        }
        boolean result = apidocsRepository.updateCol(projectId, colId, request);
        if (!result) {
            throw new BusinessException(ErrorCode.COL_NOT_FOUND);
        }
    }

}
