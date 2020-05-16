package com.opencryptotrade.templatebuilder.service;

import com.opencryptotrade.templatebuilder.dto.EmailTemplateDTO;
import com.opencryptotrade.templatebuilder.entity.EmailTemplate;
import org.bson.types.ObjectId;

import java.util.List;

public interface EmailTemplateService {

    EmailTemplateDTO create(EmailTemplateDTO template);

    EmailTemplateDTO update(EmailTemplateDTO template);

    List<EmailTemplateDTO> getEmailTemplates();

    boolean delete(ObjectId id);

    void save(EmailTemplate template);

    EmailTemplate findById(ObjectId id);

}
