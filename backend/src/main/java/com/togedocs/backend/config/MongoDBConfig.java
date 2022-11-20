package com.togedocs.backend.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.core.convert.*;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

@Configuration
public class MongoDBConfig {
    @Value("${spring.data.mongodb.uri}")
    private String mongodb_uri;

    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        MongoClient mongoClient = MongoClients.create(mongodb_uri);
        MongoDatabaseFactory factory = new SimpleMongoClientDatabaseFactory(mongoClient, "togedocs");
        MappingMongoConverter converter = new MappingMongoConverter(new DefaultDbRefResolver(factory), new MongoMappingContext());
        converter.setTypeMapper(new DefaultMongoTypeMapper(null));
        return new MongoTemplate(factory, converter);
    }
}
