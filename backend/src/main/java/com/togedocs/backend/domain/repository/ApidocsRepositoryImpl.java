package com.togedocs.backend.domain.repository;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.togedocs.backend.api.dto.ApidocsRequest;
import com.togedocs.backend.api.dto.ApidocsResponse;
import com.togedocs.backend.api.dto.ProjectRequest;
import com.togedocs.backend.common.exception.BusinessException;
import com.togedocs.backend.common.exception.ErrorCode;
import com.togedocs.backend.domain.entity.Apidocs;
import com.togedocs.backend.domain.entity.ColCategory;
import com.togedocs.backend.domain.entity.ColDto;
import com.togedocs.backend.domain.entity.Project;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@RequiredArgsConstructor
public class ApidocsRepositoryImpl implements ApidocsRepository {

    private final MongoTemplate mongoTemplate;
    private final String APIDOCS = "apidocs";
    private final String PROJECT_ID = "projectId";
    private final String ROWS = "rows";
    private final String COLS = "cols";
    private final String DATA = "data";
    private final int DEFAULT_WIDTH = 100;
    private final ColCategory DEFAULT_CATEGORY = ColCategory.ADDED;

    public boolean existsByProjectId(Long projectId) {
        Query query = new Query().addCriteria(Criteria.where(PROJECT_ID).is(projectId));
        return mongoTemplate.exists(query, APIDOCS);
    }

    @Override
    public void createApidocs(ProjectRequest.CreateProjectRequest request, Long projectId) {
        List<ColDto> cols = new ArrayList<>();
        cols.add(new ColDto("one", "Name", "text", DEFAULT_WIDTH, ColCategory.REQUIRED));
        cols.add(new ColDto("two", "Method", "text", DEFAULT_WIDTH, ColCategory.REQUIRED));
        cols.add(new ColDto("three", "URL", "text", DEFAULT_WIDTH, ColCategory.REQUIRED));
        cols.add(new ColDto("d-one", "Query Params", "text", 1, ColCategory.PAYLOAD));
        cols.add(new ColDto("d-two", "Request Body", "text", 1, ColCategory.PAYLOAD));
        cols.add(new ColDto("d-three", "Response Body", "text", 1, ColCategory.PAYLOAD));

        Apidocs apidocs = Apidocs.builder()
                .projectId(projectId)
                .title(request.getTitle())
                .desc(request.getDesc())
                .rows(new ArrayList<String>())
                .cols(cols)
                .data(new HashMap<String, Map<String, String>>())
                .build();

        mongoTemplate.insert(apidocs, APIDOCS);
    }

    @Override
    public void deleteApidocs(Long projectId){
        Query query = new Query().addCriteria(Criteria.where(PROJECT_ID).is(projectId));
        mongoTemplate.remove(query, APIDOCS);
    }

    @Override
    public boolean addRow(Long projectId) {

        Query query = new Query().addCriteria(Criteria.where(PROJECT_ID).is(projectId));
        Update update = new Update();
        String rowId = UUID.randomUUID().toString();
        update.push(ROWS, rowId);
        update.set(DATA + "." + rowId, new Document());
        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, APIDOCS);

        return updateResult.getMatchedCount() != 0;
    }


    @Override
    public boolean addCol(Long projectId, ApidocsRequest.AddColRequest request) {

        Query query = new Query().addCriteria(Criteria.where(PROJECT_ID).is(projectId));
        Update update = new Update();
        String colId = UUID.randomUUID().toString();
        ColDto col = ColDto.build(colId, request.getName(), request.getType(), DEFAULT_WIDTH, DEFAULT_CATEGORY);
        update.push(COLS).atPosition(-3).value(col);
        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, APIDOCS);

        return updateResult.getMatchedCount() != 0;
    }

    @Override
    public boolean moveRow(Long projectId, ApidocsRequest.MoveItemRequest request) {

        Query query = new Query().addCriteria(Criteria.where(PROJECT_ID).is(projectId));
        Update update = new Update();
        update.pull(ROWS, request.getFromId());
        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, APIDOCS);

        if (updateResult.getModifiedCount() == 0) return false;

        update = new Update();
        update.push(ROWS).atPosition(request.getToIndex()).value(request.getFromId());
        mongoTemplate.updateFirst(query, update, APIDOCS);

        return true;
    }

    @Override
    public boolean moveCol(Long projectId, ApidocsRequest.MoveItemRequest request) {

        Query query = new Query().addCriteria(Criteria.where(PROJECT_ID).is(projectId));
        Apidocs apidocs = mongoTemplate.findOne(query, Apidocs.class);
        Update update = new Update();
        // col을 배열에서 제거
        update.pull(COLS, Query.query(Criteria.where("uuid").is(request.getFromId())));
        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, APIDOCS);

        // 여기에 colid 못찾는 exception 처리
        if (updateResult.getModifiedCount() == 0) return false;

        List<ColDto> colDtos = apidocs.getCols();
        ColDto targetCol = new ColDto();
        for (ColDto colDto : colDtos) {
            if (colDto.getUuid().equals(request.getFromId())) {
                targetCol = colDto;
            }
        }
        update = new Update();
        // col을 배열의 특정 위치에 추가
        update.push(COLS).atPosition(request.getToIndex()).value(targetCol);
        mongoTemplate.updateFirst(query, update, APIDOCS);

        return true;
    }

    @Override
    public boolean deleteRow(Long projectId, String rowId) {

        Query query = new Query().addCriteria(Criteria.where(PROJECT_ID).is(projectId));
        Update update = new Update();
        update.pull(ROWS, rowId);
        update.unset(DATA + "." + rowId);
        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, APIDOCS);

        if (updateResult.getModifiedCount() == 0) return false;
        return true;
    }

    @Override
    public boolean deleteCol(Long projectId, String colId) {

        Query query = new Query().addCriteria(Criteria.where(PROJECT_ID).is(projectId));
        Update update = new Update();
        // #1. cols에서 uuid가 colId인 colDto 제거
        update.pull(COLS, Query.query(Criteria.where("uuid").is(colId)));
        // rows 배열 조회
        List<String> rows = mongoTemplate.findDistinct(query, ROWS, APIDOCS, String.class);
        for (String rowId : rows) {
            // #2. rows 배열의 rowId를 순회하며 colId쌍 제거
            update.unset(DATA + "." + rowId + "." + colId);
        }
        // #1, #2 실행
        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, APIDOCS);

        if (updateResult.getModifiedCount() == 0) return false;
        return true;
    }

    @Override
    public boolean updateCell(Long projectId, ApidocsRequest.UpdateCellRequest request) {

        Query query = new Query().addCriteria(Criteria.where(PROJECT_ID).is(projectId));

        Apidocs apidocs = mongoTemplate.findOne(query, Apidocs.class);
        List<String> rowIds = apidocs.getRows();
        boolean exceptionFlag = true;
        for (String rowId : rowIds) {
            if (rowId.equals(request.getRowId())) exceptionFlag = false;
        }
        if (exceptionFlag) return false;
        List<ColDto> colDtos = apidocs.getCols();
        exceptionFlag = true;
        for (ColDto colDto : colDtos) {
            if (colDto.getUuid().equals(request.getColId())) exceptionFlag = false;
        }
        if (exceptionFlag) return false;

        Update update = new Update();
        update.set(DATA + "." + request.getRowId() + "." + request.getColId(), request.getContent());
        mongoTemplate.updateFirst(query, update, APIDOCS);

        return true;
    }

    @Override
    public Apidocs getDocs(Long projectId) {

        Query query = new Query().addCriteria(Criteria.where(PROJECT_ID).is(projectId));
        Apidocs apidocs = mongoTemplate.findOne(query, Apidocs.class, APIDOCS);

        return apidocs;
    }

    @Override
    public boolean updateProjectInfo(Long projectId, ApidocsRequest.UpdateProjectInfoRequest request) {

        Query query = new Query().addCriteria(Criteria.where(PROJECT_ID).is(projectId));
        Update update = new Update();
        update.set("title", request.getTitle());
        update.set("desc", request.getDesc());
        update.set("baseUrl", request.getBaseUrl());
        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, APIDOCS);

        if (updateResult.getMatchedCount() == 0) return false;
        return true;
    }

    @Override
    public boolean updateCol(Long projectId, String colId, ApidocsRequest.UpdateColRequest request) {

        Query query = new Query().addCriteria(Criteria.where(PROJECT_ID).is(projectId));
        Update update = new Update();
        update.pull(COLS, Query.query(Criteria.where("uuid").is(colId)));
        Apidocs apidocs = mongoTemplate.findAndModify(query, update, Apidocs.class);

        if (apidocs == null)
            return false; // pjt not found exception, but already checked in service before this method is called

        boolean exceptionFlag = true;
        List<ColDto> colDtos = apidocs.getCols();
        for (ColDto colDto : colDtos) {
            if (colDto.getUuid().equals(colId)) exceptionFlag = false;
        }
        if (exceptionFlag) return false; // col not found exception

        int size = colDtos.size();
        int targetIndex = 0;
        for (int i = 0; i < size; i++) {
            if (colDtos.get(i).getUuid().equals(colId)) {
                targetIndex = i;
            }
        }
        ColDto updatedCol = ColDto.build(colId, request.getName(), request.getType(), request.getWidth(), colDtos.get(targetIndex).getCategory());
        update = new Update();
        update.push(COLS).atPosition(targetIndex).value(updatedCol);
        mongoTemplate.updateFirst(query, update, APIDOCS);

        return true;
    }
}
