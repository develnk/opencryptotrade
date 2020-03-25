package com.opencryptotrade.templatebuilder.service;

import com.opencryptotrade.templatebuilder.dto.EmailTemplateDTO;

public interface EmailTemplateService {

    EmailTemplateDTO create(EmailTemplateDTO template);

    EmailTemplateDTO update(EmailTemplateDTO template);

    boolean delete(Long id);

}
