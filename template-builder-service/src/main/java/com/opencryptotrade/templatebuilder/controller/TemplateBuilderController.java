package com.opencryptotrade.templatebuilder.controller;

import com.opencryptotrade.templatebuilder.dto.EmailTemplateDTO;
import com.opencryptotrade.templatebuilder.dto.TriggerDTO;
import com.opencryptotrade.templatebuilder.feign.SmtpService;
import com.opencryptotrade.templatebuilder.service.EmailTemplateService;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class TemplateBuilderController {

	private final SmtpService smtpService;

	private final EmailTemplateService emailTemplateService;

	public TemplateBuilderController(SmtpService smtpService, EmailTemplateService emailTemplateService) {
		this.smtpService = smtpService;
		this.emailTemplateService = emailTemplateService;
	}

	@PreAuthorize("#oauth2.hasScope('ui')")
	@RequestMapping(path = "/triggers", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public List<TriggerDTO> getSmtpTriggers() {
		return smtpService.triggerList();
	}

	@PreAuthorize("#oauth2.hasScope('ui')")
	@RequestMapping(path = "/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public EmailTemplateDTO create(@Valid @RequestBody EmailTemplateDTO templateDTO) {
		return emailTemplateService.create(templateDTO);
	}

	@PreAuthorize("#oauth2.hasScope('ui')")
	@RequestMapping(path = "/", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public EmailTemplateDTO update(@Valid @RequestBody EmailTemplateDTO templateDTO) {
		return emailTemplateService.update(templateDTO);
	}

	@PreAuthorize("#oauth2.hasScope('ui')")
	@RequestMapping(path = "/", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public boolean delete(@Valid @RequestBody Long id) {
		return emailTemplateService.delete(id);
	}

}
