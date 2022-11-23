package com.togedocs.backend.domain.repository;

import com.mongodb.client.result.UpdateResult;
import com.togedocs.backend.api.dto.ApidocsRequest;
import com.togedocs.backend.api.dto.ApidocsResponse;
import com.togedocs.backend.common.exception.BusinessException;
import com.togedocs.backend.common.exception.ErrorCode;
import com.togedocs.backend.domain.entity.Apidocs;
import com.togedocs.backend.domain.entity.ColCategory;
import com.togedocs.backend.domain.entity.ColDto;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class ApidocsRepositoryImpl implements ApidocsRepository {

    private final MongoTemplate mongoTemplate;
    private final String APIDOCS = "apidocs";
    private final int DEFAULT_WIDTH = 100;
    private final ColCategory DEFAULT_CATEGORY = ColCategory.ADDED;

    @Override
    public ApidocsResponse.Ids addRow(Long projectId) throws BusinessException {

        Query query = new Query().addCriteria(Criteria.where("projectId").is(projectId));
        Update update = new Update();
        String rowId = UUID.randomUUID().toString();
        update.push("rows", rowId);
        update.set("data." + rowId, new Document());
        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, APIDOCS);

        if (updateResult.getMatchedCount() == 0) throw new BusinessException(ErrorCode.PROJECT_NOT_FOUND);
        return ApidocsResponse.Ids.build(projectId, rowId, null);
    }

    @Override
    public ApidocsResponse.Ids addCol(Long projectId, ApidocsRequest.AddColRequest request) throws BusinessException {

        Query query = new Query().addCriteria(Criteria.where("projectId").is(projectId));
        Update update = new Update();
        String colId = UUID.randomUUID().toString();
        ColDto col = ColDto.build(colId, request.getName(), request.getType(), DEFAULT_WIDTH, DEFAULT_CATEGORY);
        update.push("cols").atPosition(-3).value(col);
        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, APIDOCS);

        if (updateResult.getMatchedCount() == 0) throw new BusinessException(ErrorCode.PROJECT_NOT_FOUND);
        return ApidocsResponse.Ids.build(projectId, null, colId);
    }

    @Override
    public ApidocsResponse.Ids moveRow(Long projectId, ApidocsRequest.MoveItemRequest request) throws BusinessException {

        Query query = new Query().addCriteria(Criteria.where("projectId").is(projectId));
        Update update = new Update();
        update.pull("rows", request.getFromId()); // rowId를 배열에서 제거
        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, APIDOCS);

        if (updateResult.getMatchedCount() == 0) throw new BusinessException(ErrorCode.PROJECT_NOT_FOUND);

        update = new Update();
        update.push("rows").atPosition(request.getToIndex()).value(request.getFromId()); // rowId를 배열의 특정 위치에 추가
        mongoTemplate.updateFirst(query, update, APIDOCS);

        return ApidocsResponse.Ids.build(projectId, request.getFromId(), null);
    }

    @Override
    public ApidocsResponse.Ids moveCol(Long projectId, ApidocsRequest.MoveItemRequest request) throws BusinessException {

        Query query = new Query().addCriteria(Criteria.where("projectId").is(projectId));
        Update update = new Update();
        // col을 배열에서 제거
        update.pull("cols", Query.query(Criteria.where("uuid").is(request.getFromId())));
        Apidocs apidocs = mongoTemplate.findAndModify(query, update, Apidocs.class);

        if (apidocs == null) throw new BusinessException(ErrorCode.PROJECT_NOT_FOUND);

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

    @Override
    public ApidocsResponse.Ids deleteRow(Long projectId, String rowId) throws BusinessException {

        Query query = new Query().addCriteria(Criteria.where("projectId").is(projectId));
        Update update = new Update();
        update.pull("rows", rowId);
        update.unset("data." + rowId);
        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, APIDOCS);

        if (updateResult.getMatchedCount() == 0) throw new BusinessException(ErrorCode.PROJECT_NOT_FOUND);
        if (updateResult.getModifiedCount() == 0) throw new BusinessException(ErrorCode.ROW_NOT_FOUND);
        return ApidocsResponse.Ids.build(projectId, rowId, null);
    }

    @Override
    public ApidocsResponse.Ids deleteCol(Long projectId, String colId) throws BusinessException {

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
        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, APIDOCS);

        if (updateResult.getMatchedCount() == 0) throw new BusinessException(ErrorCode.PROJECT_NOT_FOUND);
        if (updateResult.getModifiedCount() == 0) throw new BusinessException(ErrorCode.COL_NOT_FOUND);
        return ApidocsResponse.Ids.build(projectId, null, colId);
    }

    @Override
    public ApidocsResponse.Ids updateCell(Long projectId, ApidocsRequest.UpdateCellRequest request) throws BusinessException {

        Query query = new Query().addCriteria(Criteria.where("projectId").is(projectId));
        Update update = new Update();
        update.set("data." + request.getRowId() + "." + request.getColId(), request.getContent());
        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, APIDOCS);

        if (updateResult.getMatchedCount() == 0) throw new BusinessException(ErrorCode.PROJECT_NOT_FOUND);
        return ApidocsResponse.Ids.build(projectId, request.getRowId(), request.getColId());
    }

    @Override
    public ApidocsResponse.Apidocs getDocs(Long projectId) throws BusinessException {

        Query query = new Query().addCriteria(Criteria.where("projectId").is(projectId));
        Apidocs apidocs = mongoTemplate.findOne(query, Apidocs.class, APIDOCS);

        if (apidocs == null) throw new BusinessException(ErrorCode.PROJECT_NOT_FOUND);
        return ApidocsResponse.Apidocs.build(apidocs);
    }

    @Override
    public ApidocsResponse.ProjectInfo updateProjectInfo(Long projectId, ApidocsRequest.UpdateProjectInfoRequest request) throws BusinessException {

        Query query = new Query().addCriteria(Criteria.where("projectId").is(projectId));
        Update update = new Update();
        update.set("title", request.getTitle());
        update.set("desc", request.getDesc());
        update.set("baseUrl", request.getBaseUrl());
        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, APIDOCS);

        if (updateResult.getMatchedCount() == 0) throw new BusinessException(ErrorCode.PROJECT_NOT_FOUND);
        return ApidocsResponse.ProjectInfo.build(projectId, request.getTitle(), request.getDesc(), request.getBaseUrl());
    }

    @Override
    public ApidocsResponse.Ids updateCol(Long projectId, String colId, ApidocsRequest.UpdateColRequest request) throws BusinessException {

        Query query = new Query().addCriteria(Criteria.where("projectId").is(projectId));
        Update update = new Update();
        update.pull("cols", Query.query(Criteria.where("uuid").is(colId)));
        Apidocs apidocs = mongoTemplate.findAndModify(query, update, Apidocs.class);

        if (apidocs == null) throw new BusinessException(ErrorCode.PROJECT_NOT_FOUND);
        boolean exceptionFlag = false;

        List<ColDto> colDtos = apidocs.getCols();

        for (ColDto colDto : colDtos) {
            if (colDto.getUuid().equals(colId)) exceptionFlag = true;
        }
        if (exceptionFlag) throw new BusinessException(ErrorCode.COL_NOT_FOUND);

        int size = colDtos.size();
        int targetIndex = 0;
        for (int i = 0; i < size; i++) {
            if (colDtos.get(i).getUuid().equals(colId)) {
                targetIndex = i;
            }
        }
        ColDto updatedCol = ColDto.build(colId, request.getName(), request.getType(), request.getWidth(), colDtos.get(targetIndex).getCategory());
        update = new Update();
        update.push("cols").atPosition(targetIndex).value(updatedCol);
        mongoTemplate.updateFirst(query, update, APIDOCS);

        return ApidocsResponse.Ids.build(projectId, null, colId);
    }
}
