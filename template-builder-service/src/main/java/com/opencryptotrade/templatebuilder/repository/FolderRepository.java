package com.opencryptotrade.templatebuilder.repository;

import com.opencryptotrade.templatebuilder.entity.Folder;
import org.bson.types.ObjectId;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FolderRepository extends CrudRepository<Folder, ObjectId> {

    Folder findByName(String name);

}
