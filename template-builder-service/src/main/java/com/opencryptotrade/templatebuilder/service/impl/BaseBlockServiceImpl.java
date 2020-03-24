package com.opencryptotrade.templatebuilder.service.impl;

import com.opencryptotrade.templatebuilder.dto.BaseBlockDTO;
import com.opencryptotrade.templatebuilder.entity.BaseBlock;
import com.opencryptotrade.templatebuilder.enums.BaseBlockType;
import com.opencryptotrade.templatebuilder.repository.BaseBlockRepository;
import com.opencryptotrade.templatebuilder.service.BaseBlockService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class BaseBlockServiceImpl implements BaseBlockService {

    private final BaseBlockRepository baseBlockRepository;

    final ModelMapper modelMapper;

    public BaseBlockServiceImpl(BaseBlockRepository baseBlockRepository, ModelMapper modelMapper) {
        this.baseBlockRepository = baseBlockRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public BaseBlockDTO create(String type, String html) {
        BaseBlock baseBlock = new BaseBlock();
        baseBlock.setType(BaseBlockType.valueOf(type));
        baseBlock.setHtml(html);
        BaseBlock savedBaseBlock = baseBlockRepository.save(baseBlock);
        return modelMapper.map(savedBaseBlock, BaseBlockDTO.class);
    }

    @Override
    public BaseBlockDTO update(Long id, String type, String html) {
        // @TODO convert to DTO object
        BaseBlock baseBlock = baseBlockRepository.findById(id).orElseThrow();
        baseBlock.setType(BaseBlockType.valueOf(type));
        baseBlock.setHtml(html);
        BaseBlock updatedBaseBlock = baseBlockRepository.save(baseBlock);
        return modelMapper.map(updatedBaseBlock, BaseBlockDTO.class);
    }

    @Override
    public boolean delete(Long id) {
        BaseBlock baseBlock = baseBlockRepository.findById(id).orElseThrow();
        // @TODO need check to use base block in templates.
        baseBlockRepository.delete(baseBlock);
        return true;
    }

    @Override
    public List<BaseBlockDTO> getBaseBlocksType(String type) {
        List<BaseBlockDTO> result = new LinkedList<>();
        List<BaseBlock> blocks = baseBlockRepository.findByType(BaseBlockType.valueOf(type));
        blocks.forEach(baseBlock -> result.add(modelMapper.map(baseBlock, BaseBlockDTO.class)));
        return result;
    }

    @Override
    public List<BaseBlockDTO> getAllBaseBlocks() {
        List<BaseBlockDTO> blocks = new ArrayList<>();
        baseBlockRepository.findAll().forEach(baseBlock -> blocks.add(modelMapper.map(baseBlock, BaseBlockDTO.class)));
        return blocks;
    }

}
