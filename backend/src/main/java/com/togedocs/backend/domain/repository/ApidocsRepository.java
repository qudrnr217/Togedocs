package com.togedocs.backend.domain.repository;

import com.togedocs.backend.domain.entity.Apidocs;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApidocsRepository extends MongoRepository<Apidocs, ObjectId> {
    public Apidocs findByProjectId(Long id);
}
