package com.togedocs.backend.domain.repository;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.togedocs.backend.api.dto.ApilogsRequest;
import com.togedocs.backend.api.dto.ApilogsResponse;
import com.togedocs.backend.domain.entity.Apilogs;
import com.togedocs.backend.domain.entity.LogDto;
import com.togedocs.backend.domain.entity.Project;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ApilogsRepositoryImpl implements ApilogsRepository {
    private final MongoTemplate mongoTemplate;
    private final String APILOGS = "apilogs";
    private final String PROJECT_ID = "projectId";

    @Override
    public void createApilogs(Project project) {
        Apilogs apilogs = Apilogs.builder().projectId(project.getId()).log(new HashMap<String, List<LogDto>>()).build();
        mongoTemplate.insert(apilogs, APILOGS);
    }

    @Override
    public void deleteApilogs(Long projectId) {
        Query query = new Query().addCriteria(Criteria.where(PROJECT_ID).is(projectId));
        mongoTemplate.remove(query, APILOGS);
    }

    public boolean existsByProjectId(Long projectId) {
        Query query = new Query().addCriteria(Criteria.where(PROJECT_ID).is(projectId));
        return mongoTemplate.exists(query, APILOGS);
    }

    @Override
    public List<LogDto> getLogs(Long projectId, String rowId) {

        Query query = new Query().addCriteria(Criteria.where(PROJECT_ID).is(projectId));
        List<LogDto> logDtos = mongoTemplate.findDistinct(query, "log." + rowId, APILOGS, LogDto.class);

        return logDtos;
    }

    @Override
    public boolean addLog(Long projectId, String rowId, ApilogsRequest.AddLogRequest request) {

        Query query = new Query().addCriteria(Criteria.where(PROJECT_ID).is(projectId));
        Update update = new Update();
        String logTime = ZonedDateTime.now(ZoneId.of("Asia/Seoul")).toString();
        LogDto logDto = LogDto.build(logTime, request.getUserName(), request.getMethod(), request.getUrl(), request.getRequestBody(), request.getStatusCode(), request.getResponseBody());
        update.push("log." + rowId, logDto);
        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, APILOGS);

        return updateResult.getMatchedCount() != 0;
    }
}
