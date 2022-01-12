package com.piotrm.wodbot.config;

import com.mongodb.MongoCredential;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

@Configuration
public class SimpleMongoConfig {

    @Value("${spring.data.mongodb.host}")
    private String host;
    @Value("${spring.data.mongodb.database}")
    private String dbName;

    @Bean
    public MongoClient mongo() throws Exception {
        final ConnectionString connectionString = new ConnectionString("mongodb://" + host + ":27017/" + dbName);
        final MongoClientSettings mongoClientSettings = MongoClientSettings.builder().applyConnectionString(connectionString).build();
        return MongoClients.create(mongoClientSettings);
    }

    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        return new MongoTemplate(mongo(), "test");
    }

}
