package com.togedocs.backend.domain.entity;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;

// TODO 어노테이션 수정해야함.
@Document(collection = "apidocs")
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Apidocs {

    @Id
    private ObjectId id;

    private Long projectId;
    private String title;
    private String desc;
    private String baseUrl;
    private List<String> rows;
    private List<ColDto> cols;
    private Map<String, Map<String, String>> data;

}
