package com.togedocs.backend.api.service;

import com.togedocs.backend.api.dto.ApidocsRequest;
import com.togedocs.backend.api.dto.ApidocsResponse;
import com.togedocs.backend.domain.entity.Apidocs;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ApidocsService {

    private MongoTemplate mongoTemplate;

    public void addRow(Long projectId) {

    }

    public void addCol(Long projectId, ApidocsRequest.AddColRequest request) {

    }

    public void moveRow(Long projectId, ApidocsRequest.MoveItemRequest request) {

    }

    public void moveCol(Long projectId, ApidocsRequest.MoveItemRequest request) {

    }

    public ApidocsResponse.Ids deleteRow(Long projectId, String rowId) {
        Query query = new Query().addCriteria(Criteria.where("projectId").is(projectId));
        Update update = new Update();
        update.pull("rows", rowId);
        update.unset("data." + rowId);
        mongoTemplate.updateFirst(query, update, "apidocs");

        return ApidocsResponse.Ids.build(projectId, rowId, null);
    }

    public ApidocsResponse.Ids deleteCol(Long projectId, String colId) {
        Query query = new Query().addCriteria(Criteria.where("projectId").is(projectId));
        Update update = new Update();
        // #1. cols에서 uuid가 colId인 colDto 제거
        update.pull("cols", Query.query(Criteria.where("uuid").is(colId)));

        // rows 배열 조회
        List<String> rows = mongoTemplate.findDistinct(query, "rows", "apidocs", String.class);
        for (String rowId : rows) {
            // #2. rows 배열의 rowId를 순회하며 colId쌍 제거
            update.unset("data." + rowId + "." + colId);
        }
        // #1, #2 실행
        mongoTemplate.updateFirst(query,update,"apidocs");

        return ApidocsResponse.Ids.build(projectId, null, colId);
    }

    public void updateCell(Long projectId, ApidocsRequest.UpdateCellRequest request) {
        Query query = new Query().addCriteria(Criteria.where("projectId").is(projectId));
        Update update = new Update();
        update.set("data." + request.getRowId() + "." + request.getColId(), request.getContent());
        mongoTemplate.updateFirst(query, update, "apidocs");
    }

    public ApidocsResponse.Apidocs getDocs(Long projectId) {
        Query query = new Query().addCriteria(Criteria.where("projectId").is(projectId));
        Apidocs apidocs = mongoTemplate.findOne(query, Apidocs.class, "apidocs");
        return ApidocsResponse.Apidocs.build(apidocs);
    }

}
