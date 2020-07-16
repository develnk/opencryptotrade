package com.opencryptotrade.templatebuilder.components;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;

import com.opencryptotrade.templatebuilder.entity.Folder;


@Component
public class DefaultFolderControl implements ApplicationListener<ApplicationReadyEvent> {

    @Value("${spring.data.mongodb.database}")
    private String database;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        ConfigurableApplicationContext context = event.getApplicationContext();
        MongoClient client = (MongoClient) context.getBean("mongoClient");
        MongoDatabase db = client.getDatabase(database);
        MongoCollection<Document> folderCollection = db.getCollection("folder");
        long count = folderCollection.countDocuments(Filters.eq("name", "Default"));
        if (count == 0) {
            folderCollection.insertOne(new Document("name", "Default").append("_class", Folder.class.getCanonicalName()));
            System.out.println("Created default folder.");
        }
    }
}
