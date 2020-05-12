package com.opencryptotrade.smtpservice.service;

import com.opencryptotrade.smtpservice.dto.TriggerDTO;
import org.springframework.stereotype.Service;
import com.opencryptotrade.smtpservice.enums.Trigger;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Service
public class TriggerService {

    public List<TriggerDTO> getAll() {
        List<TriggerDTO> triggers = new LinkedList<>();
        Arrays.stream(Trigger.values()).forEach((tr) -> triggers.add(new TriggerDTO(tr.name(), tr.getValue(), tr.getId())));
        return triggers;
    }

}
