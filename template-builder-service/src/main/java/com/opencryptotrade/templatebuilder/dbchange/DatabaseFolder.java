package com.opencryptotrade.templatebuilder.dbchange;

import com.github.mongobee.changeset.ChangeLog;
import com.github.mongobee.changeset.ChangeSet;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import com.opencryptotrade.templatebuilder.entity.Folder;

@ChangeLog
public class DatabaseFolder {

    @ChangeSet(order = "001", id = "defaultFolderExist", author = "Admin", runAlways = true)
    public void defaultFolder(MongoDatabase db) {
        MongoCollection<Document> folderCollection = db.getCollection("folder");
        long count = folderCollection.countDocuments(Filters.eq("name", "Default"));
        if (count == 0) {
            folderCollection.insertOne(new Document("name", "Default").append("_class", Folder.class.getCanonicalName()));
        }
    }

}
