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
    @Value("${spring.data.mongodb.username}")
    private String user;
    @Value("${spring.data.mongodb.password}")
    private String password;
    @Value("${spring.data.mongodb.port}")
    private String port;
    @Value("${spring.data.mongodb.authentication-database}")
    private String authSource;

    @Bean
    public MongoClient mongo() throws Exception {
        String mongoClientURI = "mongodb://" + user + ":" + password + "@" + host + ":" + port + "/" + dbName + "?authSource=" + authSource;
        final ConnectionString connectionString = new ConnectionString(mongoClientURI);
        final MongoClientSettings mongoClientSettings = MongoClientSettings.builder().applyConnectionString(connectionString).build();
        return MongoClients.create(mongoClientSettings);
    }

    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        return new MongoTemplate(mongo(), dbName);
    }
}
