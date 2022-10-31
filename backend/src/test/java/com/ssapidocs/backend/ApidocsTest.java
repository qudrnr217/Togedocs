package com.ssapidocs.backend;

import com.ssapidocs.backend.domain.entity.Apidocs;
import com.ssapidocs.backend.domain.entity.ColDto;
import com.ssapidocs.backend.domain.repository.ApidocsRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Map;

@SpringBootTest
@ActiveProfiles("local")
public class ApidocsTest {

    @Autowired
    private ApidocsRepository apidocsRepository;

    @Test
    public void printDB(){
        Apidocs result = apidocsRepository.findByProjectId(1L);
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

}
