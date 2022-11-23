package com.togedocs.backend;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("prod")
class BackendApplicationTests {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    public void contextLoads() {
        Long projectId = 47l;
        Query query = new Query().addCriteria(Criteria.where("projectId").is(projectId));
        System.out.println(mongoTemplate.exists(query, "apilogs"));
    }

}
