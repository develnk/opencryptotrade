package com.opencryptotrade.templatebuilder.service.impl;

import com.opencryptotrade.templatebuilder.dto.EmailTemplateDTO;
import com.opencryptotrade.templatebuilder.entity.EmailTemplate;
import com.opencryptotrade.templatebuilder.entity.Folder;
import com.opencryptotrade.templatebuilder.repository.EmailTemplateRepository;
import com.opencryptotrade.templatebuilder.repository.FolderRepository;
import com.opencryptotrade.templatebuilder.service.EmailTemplateService;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import java.util.Optional;

@Service
public class EmailTemplateServiceImpl implements EmailTemplateService {

    final EmailTemplateRepository templateRepository;

    final FolderRepository folderRepository;

    final ModelMapper modelMapper;

    public EmailTemplateServiceImpl(EmailTemplateRepository templateRepository, FolderRepository folderRepository, ModelMapper modelMapper) {
        this.templateRepository = templateRepository;
        this.folderRepository = folderRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public EmailTemplateDTO create(EmailTemplateDTO templateDTO) {
        EmailTemplate template = new EmailTemplate();
        template.setBody(templateDTO.getBody());
        template.setName(templateDTO.getName());
        template.setSubject(templateDTO.getSubject());
        // @TODO Check to trigger exist.
        template.setTrigger(templateDTO.getTrigger());
        // Find folder by name or load default folder.
        return saveTemplate(templateDTO, template);
    }

    @Override
    public EmailTemplateDTO update(EmailTemplateDTO templateDTO) {
        EmailTemplate template = templateRepository.findById(templateDTO.getId()).orElseThrow();
        template.setTrigger(templateDTO.getTrigger());
        template.setSubject(templateDTO.getSubject());
        template.setName(templateDTO.getName());
        template.setBody(templateDTO.getBody());
        return saveTemplate(templateDTO, template);
    }

    @Override
    public boolean delete(Long id) {
        EmailTemplate template = templateRepository.findById(id).orElseThrow();
        templateRepository.delete(template);
        return true;
    }

    private EmailTemplateDTO saveTemplate(EmailTemplateDTO templateDTO, EmailTemplate template) {
        Optional<Folder> folder = Optional.of(folderRepository.findByName(templateDTO.getName()));
        template.setFolder(folder.orElseGet(() -> folderRepository.findByName("Default")));
        EmailTemplate savedTemplate = templateRepository.save(template);
        EmailTemplateDTO result = modelMapper.map(savedTemplate, EmailTemplateDTO.class);
        result.setFolder(savedTemplate.getFolder().getId());
        return result;
    }

}
