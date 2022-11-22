package com.togedocs.backend.api.service;

import com.togedocs.backend.api.dto.ApidocsRequest;
import com.togedocs.backend.api.dto.ApidocsResponse;
import com.togedocs.backend.api.exception.IdNotFoundException;
import com.togedocs.backend.domain.repository.ApidocsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApidocsService {
    private final ApidocsRepository apidocsRepository;

    public ApidocsResponse.Ids addRow(Long projectId) throws IdNotFoundException {
        return apidocsRepository.addRow(projectId);
    }

    public ApidocsResponse.Ids addCol(Long projectId, ApidocsRequest.AddColRequest request) throws IdNotFoundException {
        return apidocsRepository.addCol(projectId, request);
    }

    public ApidocsResponse.Ids moveRow(Long projectId, ApidocsRequest.MoveItemRequest request) throws IdNotFoundException {
        return apidocsRepository.moveRow(projectId, request);
    }

    public ApidocsResponse.Ids moveCol(Long projectId, ApidocsRequest.MoveItemRequest request) throws IdNotFoundException {
        return apidocsRepository.moveCol(projectId, request);
    }

    public ApidocsResponse.Ids deleteRow(Long projectId, String rowId) throws IdNotFoundException {
        return apidocsRepository.deleteRow(projectId, rowId);
    }

    public ApidocsResponse.Ids deleteCol(Long projectId, String colId) throws IdNotFoundException {
        return apidocsRepository.deleteCol(projectId, colId);
    }

    public ApidocsResponse.Ids updateCell(Long projectId, ApidocsRequest.UpdateCellRequest request) throws IdNotFoundException {
        return apidocsRepository.updateCell(projectId, request);
    }

    public ApidocsResponse.Apidocs getDocs(Long projectId) throws IdNotFoundException {
        return apidocsRepository.getDocs(projectId);
    }

    public ApidocsResponse.ProjectInfo updateProjectInfo(Long projectId, ApidocsRequest.UpdateProjectInfoRequest request) throws IdNotFoundException {
        return apidocsRepository.updateProjectInfo(projectId, request);
    }

    public ApidocsResponse.Ids updateCol(Long projectId, String colId, ApidocsRequest.UpdateColRequest request) throws IdNotFoundException {
        return apidocsRepository.updateCol(projectId, colId, request);
    }

}
