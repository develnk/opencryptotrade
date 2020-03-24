package com.opencryptotrade.templatebuilder.controller;

import com.opencryptotrade.templatebuilder.dto.BaseBlockDTO;
import com.opencryptotrade.templatebuilder.service.BaseBlockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/base_block")
public class BaseBlockController {

    @Autowired
    BaseBlockService baseBlockService;

    @PreAuthorize("#oauth2.hasScope('ui')")
    @RequestMapping(path = "/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public BaseBlockDTO create(@Valid @RequestBody String type, @Valid @RequestBody String html) {
        return baseBlockService.create(type, html);
    }

    @PreAuthorize("#oauth2.hasScope('ui')")
    @RequestMapping(path = "/", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public BaseBlockDTO update(@Valid @RequestBody Long id, @Valid @RequestBody String type, @Valid @RequestBody String html) {
        return baseBlockService.update(id, type, html);
    }

    @PreAuthorize("#oauth2.hasScope('ui')")
    @RequestMapping(path = "/", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public boolean delete(@Valid @RequestBody Long id) {
        return baseBlockService.delete(id);
    }

    @PreAuthorize("#oauth2.hasScope('ui')")
    @RequestMapping(path = "/", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<BaseBlockDTO> get() {
        return baseBlockService.getAllBaseBlocks();
    }

    @PreAuthorize("#oauth2.hasScope('ui')")
    @RequestMapping(path = "/blocksType", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<BaseBlockDTO> getBlocksType(@Valid @RequestParam String type) {
        return baseBlockService.getBaseBlocksType(type);
    }

}
