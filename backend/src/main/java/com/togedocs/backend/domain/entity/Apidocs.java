package com.togedocs.backend.domain.entity;

import lombok.Getter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;

@Document(collection = "apidocs")
@Getter
public class Apidocs {

    @Id
    private ObjectId id;

    private Long projectId;
    private List<String> rows;
    private List<ColDto> cols;
    private Map<String, Map<String, String>> data;

}
