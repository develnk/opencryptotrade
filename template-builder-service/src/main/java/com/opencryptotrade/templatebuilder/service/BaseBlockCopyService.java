package com.opencryptotrade.templatebuilder.service;

import com.opencryptotrade.templatebuilder.entity.BaseBlockCopy;
import org.bson.types.ObjectId;

public interface BaseBlockCopyService {

    BaseBlockCopy save(BaseBlockCopy baseBlockCopy);

    void delete(BaseBlockCopy baseBlockCopy);

    void deleteById(ObjectId id);

    BaseBlockCopy findById(ObjectId id);
}
