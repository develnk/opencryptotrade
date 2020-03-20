package com.opencryptotrade.templatebuilder.repository;

import com.opencryptotrade.templatebuilder.entity.BaseBlock;
import com.opencryptotrade.templatebuilder.enums.BaseBlockType;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BaseBlockRepository extends CrudRepository<BaseBlock, Long> {

    List<BaseBlock> findByType(BaseBlockType type);

}
