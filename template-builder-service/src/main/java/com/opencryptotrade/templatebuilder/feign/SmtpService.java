package com.opencryptotrade.templatebuilder.feign;

import com.opencryptotrade.templatebuilder.dto.TriggerDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "smtp-service")
public interface SmtpService {

    @RequestMapping(method = RequestMethod.POST, value = "/all_triggers", consumes = MediaType.APPLICATION_JSON_VALUE)
    List<TriggerDTO> triggerList();
}
