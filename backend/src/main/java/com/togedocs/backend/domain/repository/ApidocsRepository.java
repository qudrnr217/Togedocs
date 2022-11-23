package com.togedocs.backend.domain.repository;


import com.togedocs.backend.api.dto.ApidocsRequest;
import com.togedocs.backend.api.dto.ApidocsResponse;
import com.togedocs.backend.common.exception.BusinessException;

public interface ApidocsRepository {

    ApidocsResponse.Ids addRow(Long projectId) throws BusinessException;

    ApidocsResponse.Ids addCol(Long projectId, ApidocsRequest.AddColRequest request) throws BusinessException;

    ApidocsResponse.Ids moveRow(Long projectId, ApidocsRequest.MoveItemRequest request) throws BusinessException;

    ApidocsResponse.Ids moveCol(Long projectId, ApidocsRequest.MoveItemRequest request) throws BusinessException;

    ApidocsResponse.Ids deleteRow(Long projectId, String rowId) throws BusinessException;

    ApidocsResponse.Ids deleteCol(Long projectId, String colId) throws BusinessException;

    ApidocsResponse.Ids updateCell(Long projectId, ApidocsRequest.UpdateCellRequest request) throws BusinessException;

    ApidocsResponse.Apidocs getDocs(Long projectId) throws BusinessException;

    ApidocsResponse.ProjectInfo updateProjectInfo(Long projectId, ApidocsRequest.UpdateProjectInfoRequest request) throws BusinessException;

    ApidocsResponse.Ids updateCol(Long projectId, String colId, ApidocsRequest.UpdateColRequest request) throws BusinessException;
}
