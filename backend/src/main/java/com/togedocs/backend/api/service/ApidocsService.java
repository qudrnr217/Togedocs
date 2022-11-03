package com.togedocs.backend.api.service;

import com.togedocs.backend.api.dto.ApidocsRequest;
import com.togedocs.backend.api.dto.ApidocsResponse;
import com.togedocs.backend.domain.entity.Apidocs;
import com.togedocs.backend.domain.entity.ColDto;
import lombok.AllArgsConstructor;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.UUID;

import java.util.List;

@Service
@AllArgsConstructor
public class ApidocsService {

    private MongoTemplate mongoTemplate;

    private final String APIDOCS = "apidocs";

    private final int DEFAULT_WIDTH = 100;

    public ApidocsResponse.Ids addRow(Long projectId) {
        Query query = new Query().addCriteria(Criteria.where("projectId").is(projectId));
        Update update = new Update();
        String rowId = UUID.randomUUID().toString();
        update.push("rows", rowId);
        update.set("data." + rowId, new Document());
        mongoTemplate.updateFirst(query, update, APIDOCS);

        return ApidocsResponse.Ids.build(projectId, rowId, null);
    }

    public ApidocsResponse.Ids addCol(Long projectId, ApidocsRequest.AddColRequest request) {
        Query query = new Query().addCriteria(Criteria.where("projectId").is(projectId));
        Update update = new Update();
        String colId = UUID.randomUUID().toString();
        ColDto col = ColDto.build(colId, request.getName(), request.getType(), DEFAULT_WIDTH);
        update.push("cols", col);
        mongoTemplate.updateFirst(query, update, APIDOCS);

        return ApidocsResponse.Ids.build(projectId, null, colId);
    }

    public ApidocsResponse.Ids moveRow(Long projectId, ApidocsRequest.MoveItemRequest request) {
        Query query = new Query().addCriteria(Criteria.where("projectId").is(projectId));
        Update update = new Update();
        // rowId를 배열에서 제거
        update.pull("rows", request.getFromId());
        mongoTemplate.updateFirst(query, update, APIDOCS);
        // rowId를 배열의 특정 위치에 추가
        update = new Update();
        update.push("rows").atPosition(request.getToIndex()).value(request.getFromId());
        mongoTemplate.updateFirst(query, update, APIDOCS);

        return ApidocsResponse.Ids.build(projectId, request.getFromId(), null);
    }

    public ApidocsResponse.Ids moveCol(Long projectId, ApidocsRequest.MoveItemRequest request) {
        Query query = new Query().addCriteria(Criteria.where("projectId").is(projectId));
        Update update = new Update();
        // col을 배열에서 제거
        update.pull("cols", Query.query(Criteria.where("uuid").is(request.getFromId())));
        Apidocs apidocs = mongoTemplate.findAndModify(query, update, Apidocs.class);
        List<ColDto> colDtos = apidocs.getCols();
        ColDto targetCol = new ColDto();
        for (ColDto colDto : colDtos) {
            if (colDto.getUuid().equals(request.getFromId())) {
                targetCol = colDto;
            }
        }
        update = new Update();
        // col을 배열의 특정 위치에 추가
        update.push("cols").atPosition(request.getToIndex()).value(targetCol);
        mongoTemplate.updateFirst(query, update, APIDOCS);

        return ApidocsResponse.Ids.build(projectId, null, request.getFromId());
    }

    public ApidocsResponse.Ids deleteRow(Long projectId, String rowId) {
        Query query = new Query().addCriteria(Criteria.where("projectId").is(projectId));
        Update update = new Update();
        update.pull("rows", rowId);
        update.unset("data." + rowId);
        mongoTemplate.updateFirst(query, update, APIDOCS);

        return ApidocsResponse.Ids.build(projectId, rowId, null);
    }

    public ApidocsResponse.Ids deleteCol(Long projectId, String colId) {
        Query query = new Query().addCriteria(Criteria.where("projectId").is(projectId));
        Update update = new Update();
        // #1. cols에서 uuid가 colId인 colDto 제거
        update.pull("cols", Query.query(Criteria.where("uuid").is(colId)));

        // rows 배열 조회
        List<String> rows = mongoTemplate.findDistinct(query, "rows", APIDOCS, String.class);
        for (String rowId : rows) {
            // #2. rows 배열의 rowId를 순회하며 colId쌍 제거
            update.unset("data." + rowId + "." + colId);
        }
        // #1, #2 실행
        mongoTemplate.updateFirst(query, update, APIDOCS);

        return ApidocsResponse.Ids.build(projectId, null, colId);
    }

    public ApidocsResponse.Ids updateCell(Long projectId, ApidocsRequest.UpdateCellRequest request) {
        Query query = new Query().addCriteria(Criteria.where("projectId").is(projectId));
        Update update = new Update();
        update.set("data." + request.getRowId() + "." + request.getColId(), request.getContent());
        mongoTemplate.updateFirst(query, update, APIDOCS);
        return ApidocsResponse.Ids.build(projectId, request.getRowId(), request.getColId());
    }

    public ApidocsResponse.Apidocs getDocs(Long projectId) {
        Query query = new Query().addCriteria(Criteria.where("projectId").is(projectId));
        Apidocs apidocs = mongoTemplate.findOne(query, Apidocs.class, APIDOCS);
        return ApidocsResponse.Apidocs.build(apidocs);
    }

    public ApidocsResponse.ProjectInfo updateProjectInfo(Long projectId, ApidocsRequest.UpdateProjectInfoRequest request) {
        Query query = new Query().addCriteria(Criteria.where("projectId").is(projectId));
        Update update = new Update();
        update.set("title", request.getTitle());
        update.set("desc", request.getDesc());
        mongoTemplate.updateFirst(query, update, APIDOCS);
        return ApidocsResponse.ProjectInfo.build(projectId, request.getTitle(), request.getDesc());
    }

    public ApidocsResponse.Ids updateCol(Long projectId, String colId, ApidocsRequest.UpdateColRequest request) {
        Query query = new Query().addCriteria(Criteria.where("projectId").is(projectId));
        Update update = new Update();
        update.pull("cols", Query.query(Criteria.where("uuid").is(colId)));
        Apidocs apidocs = mongoTemplate.findAndModify(query, update, Apidocs.class);
        List<ColDto> colDtos = apidocs.getCols();
        int size = colDtos.size();
        int targetIndex = 0;
        for (int i = 0; i < size; i++) {
            if (colDtos.get(i).getUuid().equals(colId)) {
                targetIndex = i;
            }
        }
        ColDto updatedCol = ColDto.build(colId, request.getName(), request.getType(), request.getWidth());
        update = new Update();
        update.push("cols").atPosition(targetIndex).value(updatedCol);
        mongoTemplate.updateFirst(query, update, APIDOCS);
        return ApidocsResponse.Ids.build(projectId, null, colId);
    }

}
