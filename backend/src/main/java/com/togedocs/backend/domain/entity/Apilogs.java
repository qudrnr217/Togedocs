package com.togedocs.backend.domain.entity;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Document(collection = "apilogs")
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Apilogs {

    @Id
    private ObjectId id;

    private Long projectId;
    // String: rowId, List: rowId에 해당하는 log의 배열
    private Map<String, List<LogDto>> log;
}
