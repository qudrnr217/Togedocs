package com.togedocs.backend.domain.repository;


import com.togedocs.backend.api.dto.ApidocsRequest;
import com.togedocs.backend.api.dto.ApidocsResponse;
import com.togedocs.backend.api.exception.IdNotFoundException;

public interface ApidocsRepository {

    ApidocsResponse.Ids addRow(Long projectId) throws IdNotFoundException;

    ApidocsResponse.Ids addCol(Long projectId, ApidocsRequest.AddColRequest request) throws IdNotFoundException;

    ApidocsResponse.Ids moveRow(Long projectId, ApidocsRequest.MoveItemRequest request) throws IdNotFoundException;

    ApidocsResponse.Ids moveCol(Long projectId, ApidocsRequest.MoveItemRequest request) throws IdNotFoundException;

    ApidocsResponse.Ids deleteRow(Long projectId, String rowId) throws IdNotFoundException;

    ApidocsResponse.Ids deleteCol(Long projectId, String colId) throws IdNotFoundException;

    ApidocsResponse.Ids updateCell(Long projectId, ApidocsRequest.UpdateCellRequest request) throws IdNotFoundException;

    ApidocsResponse.Apidocs getDocs(Long projectId) throws IdNotFoundException;

    ApidocsResponse.ProjectInfo updateProjectInfo(Long projectId, ApidocsRequest.UpdateProjectInfoRequest request) throws IdNotFoundException;

    ApidocsResponse.Ids updateCol(Long projectId, String colId, ApidocsRequest.UpdateColRequest request) throws IdNotFoundException;
}
