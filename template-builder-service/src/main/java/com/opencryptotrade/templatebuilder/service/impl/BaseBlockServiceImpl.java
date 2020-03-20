package com.opencryptotrade.templatebuilder.service.impl;

import com.opencryptotrade.templatebuilder.entity.BaseBlock;
import com.opencryptotrade.templatebuilder.enums.BaseBlockType;
import com.opencryptotrade.templatebuilder.repository.BaseBlockRepository;
import com.opencryptotrade.templatebuilder.service.BaseBlockService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BaseBlockServiceImpl implements BaseBlockService {

    private final BaseBlockRepository baseBlockRepository;

    public BaseBlockServiceImpl(BaseBlockRepository baseBlockRepository) {
        this.baseBlockRepository = baseBlockRepository;
    }

    @Override
    public BaseBlock create(BaseBlockType type, String html) {
        BaseBlock baseBlock = new BaseBlock();
        baseBlock.setType(type);
        baseBlock.setHtml(html);
        return baseBlockRepository.save(baseBlock);
    }

    @Override
    public BaseBlock update(Long id, BaseBlockType type, String html) {
        BaseBlock baseBlock = baseBlockRepository.findById(id).orElseThrow();
        baseBlock.setType(type);
        baseBlock.setHtml(html);
        baseBlockRepository.save(baseBlock);
        return baseBlock;
    }

    @Override
    public boolean delete(Long id) {
        BaseBlock baseBlock = baseBlockRepository.findById(id).orElseThrow();
        // @TODO need check to use base block in templates.
        baseBlockRepository.delete(baseBlock);
        return true;
    }

    @Override
    public List<BaseBlock> getBaseBlocksType(BaseBlockType type) {
        return baseBlockRepository.findByType(type);
    }

    @Override
    public List<BaseBlock> getAllBaseBlocks() {
        List<BaseBlock> blocks = new ArrayList<>();
        baseBlockRepository.findAll().forEach(blocks::add);
        return blocks;
    }
}
