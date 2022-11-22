package com.togedocs.backend.domain.repository;

import com.mongodb.client.result.UpdateResult;
import com.togedocs.backend.api.dto.ApilogsRequest;
import com.togedocs.backend.api.dto.ApilogsResponse;
import com.togedocs.backend.api.exception.IdNotFoundException;
import com.togedocs.backend.domain.entity.LogDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ApilogsRepositoryImpl implements ApilogsRepository {
    private final MongoTemplate mongoTemplate;
    private final String APILOGS = "apilogs";

    @Override
    public ApilogsResponse.Logs getLogs(Long projectId, String rowId) {

        Query query = new Query().addCriteria(Criteria.where("projectId").is(projectId));
        List<LogDto> logDtos = mongoTemplate.findDistinct(query, "log." + rowId, APILOGS, LogDto.class);

        // exception 발생 x :
        // projectId가 잘못됨 + rowId가 잘못됨 + 둘다 맞지만 log가 0개
        // 위 세 개의 상황 모두 log size = 0 으로 response가 반환되기 때문에 구분할 수 없음. (인 것 같음...)

        return ApilogsResponse.Logs.build(logDtos);
    }

    @Override
    public ApilogsResponse.LogIdsAndTime addLog(Long projectId, String rowId, ApilogsRequest.AddLogRequest request) throws IdNotFoundException {

        Query query = new Query().addCriteria(Criteria.where("projectId").is(projectId));
        Update update = new Update();
        String logTime = ZonedDateTime.now(ZoneId.of("Asia/Seoul")).toString();
        LogDto logDto = LogDto.build(logTime, request.getUserName(), request.getMethod(), request.getUrl(), request.getRequestBody(), request.getStatusCode(), request.getResponseBody());
        update.push("log." + rowId, logDto);
        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, APILOGS);

        if (updateResult.getMatchedCount() == 0) throw new IdNotFoundException("projectId");
        return ApilogsResponse.LogIdsAndTime.build(projectId, rowId, logTime);
    }
}
