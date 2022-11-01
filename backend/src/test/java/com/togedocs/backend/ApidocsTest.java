package com.togedocs.backend;

import com.mongodb.client.MongoClient;
import com.togedocs.backend.domain.entity.Apidocs;
import com.togedocs.backend.domain.entity.ColDto;
import com.togedocs.backend.domain.repository.ApidocsRepository;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
@ActiveProfiles("local")
public class ApidocsTest {

    @Autowired
    private ApidocsRepository apidocsRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    public void printDB(){
        Apidocs result = apidocsRepository.findByProjectId(2L);
        System.out.println(result.getRows());
        System.out.println(result.getCols());
        for (String row: result.getRows()) {
            Map<String, String> apiDto = result.getData().get(row);
            for(ColDto col : result.getCols()){
                System.out.print(apiDto.get(col.getUuid()) + "\t");
            }
            System.out.println();
        }
    }


    @Test
    public void testTemplate(){
        List<String> rows = new ArrayList<>();
        rows.add("first2");
        rows.add("second2");
        List<ColDto> cols = new ArrayList<>();
        cols.add(new ColDto("one2", "url", "text"));
        cols.add(new ColDto("two2", "method", "text"));
        Map<String, Map<String, String>> data = new HashMap<>();
        Map<String, String> row1 = new HashMap<>();
        row1.put("one2", "/login");
        row1.put("two2", "post");
        Map<String, String> row2 = new HashMap<>();
        row2.put("one2", "/");
        row2.put("two2", "get");
        data.put("first2", row1);
        data.put("second2", row2);
        Apidocs apidocs = new Apidocs(new ObjectId("635f773e6f2df42ec88ef693"), 2L, rows, cols, data);
        mongoTemplate.insert(apidocs, "apidocs");
    }

    @Test
    public void test2(){
        Query query = new Query().addCriteria(Criteria.where("projectId").is(2L));
        Update update = new Update();
        // 행 추가
        String rowId = "third2";
        update.push("rows", rowId);
        update.set("data.third2", new Document());

        mongoTemplate.updateFirst(query, update, "apidocs");
    }
}