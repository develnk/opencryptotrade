package com.opencryptotrade.templatebuilder.repository;

import com.opencryptotrade.templatebuilder.entity.Folder;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FolderRepository extends CrudRepository<Folder, Long> {

    Folder findByName(String name);

}
