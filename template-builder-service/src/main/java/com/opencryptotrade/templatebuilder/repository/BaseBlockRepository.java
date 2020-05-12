package com.opencryptotrade.templatebuilder.repository;

import com.opencryptotrade.templatebuilder.entity.BaseBlock;
import com.opencryptotrade.templatebuilder.enums.BaseBlockType;
import org.bson.types.ObjectId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BaseBlockRepository extends CrudRepository<BaseBlock, ObjectId> {

    List<BaseBlock> findByType(BaseBlockType type);

}
