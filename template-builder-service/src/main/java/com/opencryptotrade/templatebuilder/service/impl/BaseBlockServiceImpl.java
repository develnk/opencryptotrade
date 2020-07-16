package com.opencryptotrade.templatebuilder.service.impl;

import com.opencryptotrade.templatebuilder.exception.BaseBlockInTemplate;
import org.springframework.stereotype.Service;
import org.springframework.context.annotation.Lazy;
import com.opencryptotrade.templatebuilder.dto.BaseBlockDTO;
import com.opencryptotrade.templatebuilder.entity.BaseBlock;
import com.opencryptotrade.templatebuilder.enums.BaseBlockType;
import com.opencryptotrade.templatebuilder.repository.BaseBlockRepository;
import com.opencryptotrade.templatebuilder.service.BaseBlockService;
import com.opencryptotrade.templatebuilder.service.EmailTemplateService;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;

import java.util.*;

@Service
public class BaseBlockServiceImpl implements BaseBlockService {

    private final BaseBlockRepository baseBlockRepository;

    private final EmailTemplateService emailTemplateService;

    private final ModelMapper modelMapper;

    public BaseBlockServiceImpl(BaseBlockRepository baseBlockRepository, @Lazy EmailTemplateService emailTemplateService, ModelMapper modelMapper) {
        this.baseBlockRepository = baseBlockRepository;
        this.emailTemplateService = emailTemplateService;
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
    public boolean delete(BaseBlockDTO baseBlockDTO) throws BaseBlockInTemplate {
        BaseBlock baseBlock = baseBlockRepository.findById(new ObjectId(baseBlockDTO.getId())).orElseThrow();
        boolean check = emailTemplateService.checkExistBaseBlockInTemplates(baseBlock);
        if (check) {
            throw new BaseBlockInTemplate("Base block found in templates");
        }
        else {
            baseBlockRepository.delete(baseBlock);
        }
        return true;
    }

    @Override
    public List<BaseBlockDTO> getBaseBlocksType(String type) {
        List<BaseBlockDTO> result = new ArrayList<>();
        List<BaseBlock> blocks = baseBlockRepository.findByType(BaseBlockType.valueOf(type));
        blocks.forEach(baseBlock -> result.add(modelMapper.map(baseBlock, BaseBlockDTO.class)));
        return result;
    }

    @Override
    public List<BaseBlockDTO> getAllBaseBlocks() {
        List<BaseBlockDTO> blocks = new ArrayList<>();
        baseBlockRepository.findAll().forEach(baseBlock -> blocks.add(modelMapper.map(baseBlock, BaseBlockDTO.class)));
        Collections.sort(blocks);
        return blocks;
    }

    @Override
    public BaseBlock findById(ObjectId id) {
        return baseBlockRepository.findById(id).orElseThrow();
    }

}
