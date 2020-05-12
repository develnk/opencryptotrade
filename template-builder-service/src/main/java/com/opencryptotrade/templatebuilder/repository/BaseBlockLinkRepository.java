package com.opencryptotrade.templatebuilder.repository;

import com.opencryptotrade.templatebuilder.entity.BaseBlockLink;
import org.bson.types.ObjectId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BaseBlockLinkRepository extends CrudRepository<BaseBlockLink, ObjectId> {
}
