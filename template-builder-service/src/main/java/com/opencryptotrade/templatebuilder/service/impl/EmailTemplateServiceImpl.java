package com.opencryptotrade.templatebuilder.service.impl;

import com.opencryptotrade.templatebuilder.dto.EmailTemplateDTO;
import com.opencryptotrade.templatebuilder.entity.BaseBlockLink;
import com.opencryptotrade.templatebuilder.entity.EmailTemplate;
import com.opencryptotrade.templatebuilder.entity.Folder;
import com.opencryptotrade.templatebuilder.repository.EmailTemplateRepository;
import com.opencryptotrade.templatebuilder.service.BaseBlockLinkService;
import com.opencryptotrade.templatebuilder.service.EmailTemplateService;
import com.opencryptotrade.templatebuilder.service.FolderService;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class EmailTemplateServiceImpl implements EmailTemplateService {

    final EmailTemplateRepository emailTemplateRepository;

    final BaseBlockLinkService baseBlockLinkService;

    final FolderService folderService;

    final ModelMapper modelMapper;

    public EmailTemplateServiceImpl(EmailTemplateRepository emailTemplateRepository, BaseBlockLinkService baseBlockLinkService, FolderService folderService, ModelMapper modelMapper) {
        this.emailTemplateRepository = emailTemplateRepository;
        this.baseBlockLinkService = baseBlockLinkService;
        this.folderService = folderService;
        this.modelMapper = modelMapper;
    }

    @Override
    public EmailTemplateDTO create(EmailTemplateDTO templateDTO) {
        EmailTemplate template = new EmailTemplate();
        List<BaseBlockLink> baseBlockLinks = templateDTO.getBaseBlockLinks().stream().map(baseBlockLinkService::addNewBaseBlockLink).collect(Collectors.toList());
        template.setBaseBlockLinks(baseBlockLinks);
        template.setName(templateDTO.getName());
        template.setSubject(templateDTO.getSubject());
        // @TODO Check to trigger exist.
        template.setTrigger(templateDTO.getTrigger());
        return saveTemplate(template, templateDTO);
    }

    @Override
    public EmailTemplateDTO update(EmailTemplateDTO templateDTO) {
        EmailTemplate template = emailTemplateRepository.findById(new ObjectId(templateDTO.getId())).orElseThrow();
        template.setTrigger(templateDTO.getTrigger());
        template.setSubject(templateDTO.getSubject());
        template.setName(templateDTO.getName());
        List<BaseBlockLink> baseBlockLinks = templateDTO.getBaseBlockLinks().stream().map(baseBlockLinkService::updateBaseBlockLink).collect(Collectors.toList());
        template.getBaseBlockLinks().clear();
        template.getBaseBlockLinks().addAll(baseBlockLinks);
        return saveTemplate(template, templateDTO);
    }

    @Override
    public List<EmailTemplateDTO> getEmailTemplates() {
        List<EmailTemplateDTO> emailTemplates = new LinkedList<>();
        emailTemplateRepository.findAll().forEach(e -> {
            EmailTemplateDTO template = modelMapper.map(e, EmailTemplateDTO.class);
            Collections.sort(template.getBaseBlockLinks());
            emailTemplates.add(template);
        });
        return emailTemplates;
    }

    @Override
    public boolean delete(ObjectId id) {
        EmailTemplate template = emailTemplateRepository.findById(id).orElseThrow();
        emailTemplateRepository.delete(template);
        return true;
    }

    @Override
    public void save(EmailTemplate template) {
        emailTemplateRepository.save(template);
    }

    @Override
    public EmailTemplate findById(ObjectId id) {
        return emailTemplateRepository.findById(id).orElseThrow();
    }

    private EmailTemplateDTO saveTemplate(EmailTemplate template, EmailTemplateDTO templateDTO) {
        // Find folder by name or load default folder.
        Folder folder = folderService.findFolderById(new ObjectId(templateDTO.getFolder()));
        template.setFolder(folder);
        // Save BaseBlockLinks separately
        template.setBaseBlockLinks(template.getBaseBlockLinks().stream().map(baseBlockLinkService::save).collect(Collectors.toList()));
        EmailTemplate savedTemplate = emailTemplateRepository.save(template);
        return modelMapper.map(savedTemplate, EmailTemplateDTO.class);
    }

}
