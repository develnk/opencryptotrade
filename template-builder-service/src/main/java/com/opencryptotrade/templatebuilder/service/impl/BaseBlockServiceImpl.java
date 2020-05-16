package com.opencryptotrade.templatebuilder.service.impl;

import com.opencryptotrade.templatebuilder.dto.BaseBlockDTO;
import com.opencryptotrade.templatebuilder.entity.BaseBlock;
import com.opencryptotrade.templatebuilder.enums.BaseBlockType;
import com.opencryptotrade.templatebuilder.repository.BaseBlockRepository;
import com.opencryptotrade.templatebuilder.service.BaseBlockService;
import org.bson.types.ObjectId;
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
    public BaseBlockDTO create(BaseBlockDTO baseBlockDTO) {
        BaseBlock baseBlock = new BaseBlock();
        baseBlock.setType(baseBlockDTO.getType());
        baseBlock.setHtml(baseBlockDTO.getHtml());
        BaseBlock savedBaseBlock = baseBlockRepository.save(baseBlock);
        return modelMapper.map(savedBaseBlock, BaseBlockDTO.class);
    }

    @Override
    public BaseBlockDTO update(BaseBlockDTO baseBlockDTO) {
        BaseBlock baseBlock = baseBlockRepository.findById(new ObjectId(baseBlockDTO.getId())).orElseThrow();
        baseBlock.setType(baseBlockDTO.getType());
        baseBlock.setHtml(baseBlockDTO.getHtml());
        BaseBlock updatedBaseBlock = baseBlockRepository.save(baseBlock);
        return modelMapper.map(updatedBaseBlock, BaseBlockDTO.class);
    }

    @Override
    public boolean delete(BaseBlockDTO baseBlockDTO) {
        BaseBlock baseBlock = baseBlockRepository.findById(new ObjectId(baseBlockDTO.getId())).orElseThrow();
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

    @Override
    public BaseBlock findById(ObjectId id) {
        return baseBlockRepository.findById(id).orElseThrow();
    }

}
