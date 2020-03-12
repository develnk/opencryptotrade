package com.opencryptotrade.templatebuilder.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/")
public class BuilderController {

	@Autowired
	RestTemplate restTemplate;

	@RequestMapping(path = "/current", method = RequestMethod.GET)
	public String getCurrentAccount() {
		return "Test";
	}

}
