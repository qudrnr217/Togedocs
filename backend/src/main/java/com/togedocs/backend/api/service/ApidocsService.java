package com.togedocs.backend.api.service;

import com.togedocs.backend.api.dto.ApidocsRequest;
import com.togedocs.backend.api.dto.ApidocsResponse;
import com.togedocs.backend.domain.entity.Apidocs;
import com.togedocs.backend.domain.repository.ApidocsRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ApidocsService {
    private final ApidocsRepository apidocsRepository;

    public void addRow(Long projectId){

    }

    public void addCol(Long projectId, ApidocsRequest.AddColRequest request){

    }

    public void moveRow(Long projectId, ApidocsRequest.MoveItemRequest request){

    }

    public void moveCol(Long projectId, ApidocsRequest.MoveItemRequest request){

    }

    public ApidocsResponse.Ids deleteRow(Long projectId, String rowId){
        Query query = new Query().addCriteria(Criteria.where("projectId").is(projectId));
        Update update = new Update();

        update.pull("rows",rowId);
//        update.set("data."+)
        Apidocs apidocs = apidocsRepository.findByProjectId(projectId);

        return ApidocsResponse.Ids.build(projectId,rowId,null);
    }

    public void deleteCol(Long projectId, String colId){

    }

    public void updateCell(Long projectId, ApidocsRequest.UpdateCellRequest request){

    }

    public ApidocsResponse.Apidocs getDocs(Long projectId){
        Apidocs apidocs = apidocsRepository.findByProjectId(projectId);
        return ApidocsResponse.Apidocs.build(apidocs);
    }

}
