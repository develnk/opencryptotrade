package com.opencryptotrade.templatebuilder.repository;

import com.opencryptotrade.templatebuilder.entity.Folder;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FolderRepository extends CrudRepository<Folder, Long> {

    Folder findByName(String name);

    @Query("select f from Folder f where f.id = ?1")
    Folder findNewById(Long id);

}
