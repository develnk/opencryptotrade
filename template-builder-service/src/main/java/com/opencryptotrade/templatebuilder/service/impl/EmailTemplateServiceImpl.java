package com.opencryptotrade.templatebuilder.service.impl;

import com.opencryptotrade.templatebuilder.dto.BaseBlockCopyDTO;
import com.opencryptotrade.templatebuilder.dto.BaseBlockLinkDTO;
import com.opencryptotrade.templatebuilder.dto.EmailTemplateDTO;
import com.opencryptotrade.templatebuilder.entity.BaseBlockCopy;
import com.opencryptotrade.templatebuilder.entity.BaseBlockLink;
import com.opencryptotrade.templatebuilder.entity.EmailTemplate;
import com.opencryptotrade.templatebuilder.entity.Folder;
import com.opencryptotrade.templatebuilder.repository.BaseBlockLinkRepository;
import com.opencryptotrade.templatebuilder.repository.BaseBlockRepository;
import com.opencryptotrade.templatebuilder.repository.EmailTemplateRepository;
import com.opencryptotrade.templatebuilder.repository.FolderRepository;
import com.opencryptotrade.templatebuilder.service.BaseBlockLinkService;
import com.opencryptotrade.templatebuilder.service.EmailTemplateService;
import org.bson.types.ObjectId;
import org.modelmapper.AbstractConverter;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class EmailTemplateServiceImpl implements EmailTemplateService {

    final EmailTemplateRepository templateRepository;

    final BaseBlockLinkService baseBlockLinkService;

    final FolderRepository folderRepository;

    final BaseBlockRepository baseBlockRepository;

    final BaseBlockLinkRepository baseBlockLinkRepository;

    final ModelMapper modelMapper;

    public EmailTemplateServiceImpl(EmailTemplateRepository templateRepository, BaseBlockLinkService baseBlockLinkService, FolderRepository folderRepository, BaseBlockRepository baseBlockRepository, BaseBlockLinkRepository baseBlockLinkRepository, ModelMapper modelMapper) {
        this.templateRepository = templateRepository;
        this.baseBlockLinkService = baseBlockLinkService;
        this.folderRepository = folderRepository;
        this.baseBlockRepository = baseBlockRepository;
        this.baseBlockLinkRepository = baseBlockLinkRepository;
        this.modelMapper = modelMapper;
        // @TODO Move to separately Bean
        this.modelMapper.addConverter(new AbstractConverter<ObjectId, String>() {
            @Override
            protected String convert(ObjectId source) {
                return source == null ? null : source.toString();
            }
        });
        this.modelMapper.addConverter(new AbstractConverter<Folder, String>() {
            @Override
            protected String convert(Folder source) {
                return source == null ? null : source.getId().toString();
            }
        });
    }

    @Override
    public EmailTemplateDTO create(EmailTemplateDTO templateDTO) {
        EmailTemplate template = new EmailTemplate();
        Set<BaseBlockLink> baseBlockLinks = templateDTO.getBaseBlockLinks().stream().map(baseBlockLinkService::addNewBaseBlockLink).collect(Collectors.toSet());
        template.setBaseBlockLinks(baseBlockLinks);
        template.setName(templateDTO.getName());
        template.setSubject(templateDTO.getSubject());
        // @TODO Check to trigger exist.
        template.setTrigger(templateDTO.getTrigger());
        return saveTemplate(template, templateDTO);
    }

    @Override
    public EmailTemplateDTO update(EmailTemplateDTO templateDTO) {
        EmailTemplate template = templateRepository.findById(new ObjectId(templateDTO.getId())).orElseThrow();
        template.setTrigger(templateDTO.getTrigger());
        template.setSubject(templateDTO.getSubject());
        template.setName(templateDTO.getName());
        Set<BaseBlockLink> baseBlockLinks = templateDTO.getBaseBlockLinks().stream().map(baseBlockLinkService::updateBaseBlockLink).collect(Collectors.toSet());
        template.getBaseBlockLinks().clear();
        template.getBaseBlockLinks().addAll(baseBlockLinks);
        return saveTemplate(template, templateDTO);
    }

    @Override
    public List<EmailTemplateDTO> getEmailTemplates() {
        List<EmailTemplateDTO> emailTemplates = new LinkedList<>();
        templateRepository.findAll().forEach(e -> emailTemplates.add(modelMapper.map(e, EmailTemplateDTO.class)));
        return emailTemplates;
    }

    @Override
    public boolean delete(ObjectId id) {
        EmailTemplate template = templateRepository.findById(id).orElseThrow();
        templateRepository.delete(template);
        return true;
    }


    private EmailTemplateDTO saveTemplate(EmailTemplate template, EmailTemplateDTO templateDTO) {
        // Find folder by name or load default folder.
        Folder folder = folderRepository.findById(new ObjectId(templateDTO.getFolder())).orElseGet(() -> folderRepository.findByName("Default"));
        template.setFolder(folder);
        // Save BaseBlockLinks separately
        template.setBaseBlockLinks(template.getBaseBlockLinks().stream().map(baseBlockLinkService::save).collect(Collectors.toSet()));
        EmailTemplate savedTemplate = templateRepository.save(template);
        return modelMapper.map(savedTemplate, EmailTemplateDTO.class);
    }

}
