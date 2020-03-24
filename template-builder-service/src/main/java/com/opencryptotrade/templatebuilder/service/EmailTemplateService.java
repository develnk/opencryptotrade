package com.opencryptotrade.templatebuilder.service;

import com.opencryptotrade.templatebuilder.dto.EmailTemplateDTO;
import com.opencryptotrade.templatebuilder.entity.EmailTemplate;

public interface EmailTemplateService {

    EmailTemplateDTO create(EmailTemplateDTO template);

    EmailTemplateDTO update(EmailTemplateDTO template);

    boolean delete(Long id);

}
