package com.opencryptotrade.templatebuilder.controller;

import com.opencryptotrade.templatebuilder.dto.TriggerDTO;
import com.opencryptotrade.templatebuilder.feign.SmtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class TemplateBuilderController {

	@Autowired
	private SmtpService smtpService;

	@PreAuthorize("#oauth2.hasScope('ui')")
	@RequestMapping(path = "/triggers", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public List<TriggerDTO> getSmtpTriggers() {
		return smtpService.triggerList();
	}


}
