package com.opencryptotrade.templatebuilder.service.impl;

import com.opencryptotrade.templatebuilder.entity.BaseBlockCopy;
import com.opencryptotrade.templatebuilder.repository.BaseBlockCopyRepository;
import com.opencryptotrade.templatebuilder.service.BaseBlockCopyService;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

@Service
public class BaseBlockCopyServiceImpl implements BaseBlockCopyService {

    final BaseBlockCopyRepository baseBlockCopyRepository;

    public BaseBlockCopyServiceImpl(BaseBlockCopyRepository baseBlockCopyRepository) {
        this.baseBlockCopyRepository = baseBlockCopyRepository;
    }

    @Override
    public BaseBlockCopy save(BaseBlockCopy baseBlockCopy) {
        return baseBlockCopyRepository.save(baseBlockCopy);
    }

    @Override
    public void delete(BaseBlockCopy baseBlockCopy) {
        baseBlockCopyRepository.delete(baseBlockCopy);
    }

    @Override
    public void deleteById(ObjectId id) {
        BaseBlockCopy baseBlockCopy = findById(id);
        delete(baseBlockCopy);
    }

    @Override
    public BaseBlockCopy findById(ObjectId id) {
        return baseBlockCopyRepository.findById(id).orElseThrow();
    }

}
