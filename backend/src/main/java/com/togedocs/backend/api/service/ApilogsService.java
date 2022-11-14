package com.togedocs.backend.api.service;

import com.mongodb.client.result.UpdateResult;
import com.togedocs.backend.api.dto.ApilogsDto;
import com.togedocs.backend.api.exception.IdNotFoundException;
import com.togedocs.backend.domain.entity.LogDto;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ApilogsService {

    private MongoTemplate mongoTemplate;

    private final String APILOGS = "apilogs";

        public ApilogsDto.LogDto addLog(Long projectId, String rowId, ApilogsDto.LogDto request) throws IdNotFoundException {
            Query query = new Query().addCriteria(Criteria.where("projectId").is(projectId));
            Update update = new Update();
            LogDto  logDto = LogDto.build(request.getUserId(),request.getLogTime(),request.getStatusCode(),request.getUrl(),request.getPathVariable(),request.getQueryParams(),request.getRequestBody(),request.getResponseBody());
            update.push("log."+rowId, logDto);
            UpdateResult updateResult = mongoTemplate.updateFirst(query,update,APILOGS);

            if(updateResult.getMatchedCount()==0) throw new IdNotFoundException("projectId");
            return request;
        }
}
