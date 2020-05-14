package com.opencryptotrade.templatebuilder.service.impl;

import com.opencryptotrade.templatebuilder.dto.BaseBlockLinkDTO;
import com.opencryptotrade.templatebuilder.entity.BaseBlockCopy;
import com.opencryptotrade.templatebuilder.entity.BaseBlockLink;
import com.opencryptotrade.templatebuilder.entity.EmailTemplate;
import com.opencryptotrade.templatebuilder.repository.BaseBlockCopyRepository;
import com.opencryptotrade.templatebuilder.repository.BaseBlockLinkRepository;
import com.opencryptotrade.templatebuilder.repository.BaseBlockRepository;
import com.opencryptotrade.templatebuilder.repository.EmailTemplateRepository;
import com.opencryptotrade.templatebuilder.service.BaseBlockLinkService;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Service;

@Service
public class BaseBlockLinkServiceImpl implements BaseBlockLinkService {

    final BaseBlockRepository baseBlockRepository;

    final BaseBlockCopyRepository baseBlockCopyRepository;

    final BaseBlockLinkRepository baseBlockLinkRepository;

    final EmailTemplateRepository emailTemplateRepository;

    final ModelMapper modelMapper;

    public BaseBlockLinkServiceImpl(BaseBlockRepository baseBlockRepository, BaseBlockCopyRepository baseBlockCopyRepository, BaseBlockLinkRepository baseBlockLinkRepository, EmailTemplateRepository emailTemplateRepository, ModelMapper modelMapper) {
        this.baseBlockRepository = baseBlockRepository;
        this.baseBlockCopyRepository = baseBlockCopyRepository;
        this.baseBlockLinkRepository = baseBlockLinkRepository;
        this.emailTemplateRepository = emailTemplateRepository;
        this.modelMapper = modelMapper;
        this.modelMapper.addMappings(new PropertyMap<BaseBlockLink, BaseBlockLinkDTO>() {
            @Override
            protected void configure() {
                map().setHtml(source.getBaseBlockCopy().getHtml());
            }
        });
    }

    @Override
    public BaseBlockLinkDTO addToTemplate(BaseBlockLinkDTO baseBlockLinkDTO) {
        BaseBlockLink baseBlockLink = addNewBaseBlockLink(baseBlockLinkDTO);
        BaseBlockLink savedBaseBlockLink = save(baseBlockLink);
        EmailTemplate template = emailTemplateRepository.findById(new ObjectId(baseBlockLinkDTO.getTemplateId())).orElseThrow();
        template.addBaseBlockLink(savedBaseBlockLink);
        emailTemplateRepository.save(template);
        // @TODO Add custom exception.
        BaseBlockLink insertedBaseBlockLink = template.getBaseBlockLinks().stream().filter(b -> (b.equalsFull(baseBlockLink))).findFirst().orElseThrow();
        return modelMapper.map(insertedBaseBlockLink, BaseBlockLinkDTO.class);
    }

    @Override
    public boolean update(BaseBlockLinkDTO baseBlockLinkDTO) {
        BaseBlockLink baseBlockLink = updateBaseBlockLink(baseBlockLinkDTO);
        EmailTemplate template = emailTemplateRepository.findById(new ObjectId(baseBlockLinkDTO.getTemplateId())).orElseThrow();
        BaseBlockLink baseBlockLinkToUpdate = template.getBaseBlockLinks().stream().filter(b -> (b.equals(baseBlockLink))).findFirst().orElseThrow();
        if (!baseBlockLinkDTO.isEditFlag()) {
            // Need delete link to BaseBlockCopy manually.
            if (baseBlockLinkDTO.getBaseBlockCopyId() != null) {
                deleteBaseBlockCopy(new ObjectId(baseBlockLinkDTO.getBaseBlockCopyId()));
            }
            baseBlockLinkToUpdate.setBaseBlockCopy(null);
        }
        else {
            baseBlockLinkToUpdate.setEditFlag(baseBlockLink.isEditFlag());
            if (baseBlockLinkToUpdate.getBaseBlockCopy() != null) {
                baseBlockLinkToUpdate.getBaseBlockCopy().setHtml(baseBlockLinkDTO.getHtml());
            }
            else {
                BaseBlockCopy baseBlockCopy = new BaseBlockCopy();
                baseBlockCopy.setHtml(baseBlockLinkDTO.getHtml());
                baseBlockLinkToUpdate.setBaseBlockCopy(baseBlockCopy);
            }
        }
        emailTemplateRepository.save(template);
        return true;
    }

    @Override
    public boolean delete(BaseBlockLinkDTO baseBlockLinkDTO) {
        EmailTemplate template = emailTemplateRepository.findById(new ObjectId(baseBlockLinkDTO.getTemplateId())).orElseThrow();
        BaseBlockLink baseBlockLink = template.getBaseBlockLinks().stream().filter(b -> b.getId().equals(new ObjectId(baseBlockLinkDTO.getId()))).findFirst().orElseThrow();
        // Need delete BaseBlockLink manually.
        deleteBaseBlockLink(baseBlockLink);
        template.removeBaseBlockLink(baseBlockLink);
        emailTemplateRepository.save(template);
        return true;
    }

    public BaseBlockLink addNewBaseBlockLink(BaseBlockLinkDTO baseBlockLinkDTO) {
        BaseBlockLink baseBlockLink = new BaseBlockLink();
        baseBlockLink.setWeight(baseBlockLinkDTO.getWeight());
        baseBlockLink.setEditFlag(baseBlockLinkDTO.isEditFlag());
        baseBlockLink.setBaseBlock(baseBlockRepository.findById(new ObjectId(baseBlockLinkDTO.getBaseBlockId())).orElseThrow());

        if (baseBlockLinkDTO.isEditFlag()) {
            BaseBlockCopy baseBlockCopy = new BaseBlockCopy();
            baseBlockCopy.setHtml(baseBlockLinkDTO.getHtml());
            baseBlockLink.setBaseBlockCopy(baseBlockCopy);
        }

        return baseBlockLink;
    }

    public BaseBlockLink updateBaseBlockLink(BaseBlockLinkDTO baseBlockLinkDTO) {
        BaseBlockLink baseBlockLink = baseBlockLinkRepository.findById(new ObjectId(baseBlockLinkDTO.getId())).orElseThrow();
        baseBlockLink.setWeight(baseBlockLinkDTO.getWeight());
        baseBlockLink.setEditFlag(baseBlockLinkDTO.isEditFlag());
        baseBlockLink.setBaseBlock(baseBlockRepository.findById(new ObjectId(baseBlockLinkDTO.getBaseBlockId())).orElseThrow());

        if (baseBlockLinkDTO.isEditFlag()) {
            BaseBlockCopy baseBlockCopy = new BaseBlockCopy();
            if (baseBlockLinkDTO.getBaseBlockCopyId() != null) {
                baseBlockCopy = baseBlockCopyRepository.findById(new ObjectId(baseBlockLinkDTO.getBaseBlockCopyId())).orElseThrow();
            }
            if (!baseBlockLinkDTO.getHtml().equals(baseBlockCopy.getHtml())) {
                baseBlockCopy.setHtml(baseBlockLinkDTO.getHtml());
                baseBlockLink.setBaseBlockCopy(baseBlockCopy);
            }
        }

        return baseBlockLink;
    }

    @Override
    public BaseBlockCopy saveBaseBlockCopy(BaseBlockCopy baseBlockCopy) {
        return baseBlockCopyRepository.save(baseBlockCopy);
    }

    @Override
    public BaseBlockLink save(BaseBlockLink baseBlockLink) {
        if (baseBlockLink.getBaseBlockCopy() != null) {
            saveBaseBlockCopy(baseBlockLink.getBaseBlockCopy());
        }

        return baseBlockLinkRepository.save(baseBlockLink);
    }

    private void deleteBaseBlockLink(BaseBlockLink baseBlockLink) {
        if (baseBlockLink.getBaseBlockCopy() != null) {
            deleteBaseBlockCopy(baseBlockLink.getBaseBlockCopy().getId());
        }

        baseBlockLinkRepository.delete(baseBlockLink);
    }

    private void deleteBaseBlockCopy(ObjectId baseBlockCopyId) {
        BaseBlockCopy baseBlockCopy = baseBlockCopyRepository.findById(baseBlockCopyId).orElseThrow();
        baseBlockCopyRepository.delete(baseBlockCopy);
    }

}
