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
public class BaseBlockController {

    @Autowired
    BaseBlockService baseBlockService;

    @PreAuthorize("#oauth2.hasScope('ui')")
    @RequestMapping(path = "/base_block", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public BaseBlockDTO create(@Valid @RequestBody BaseBlockDTO baseBlockDTO) {
        return baseBlockService.create(baseBlockDTO);
    }

    @PreAuthorize("#oauth2.hasScope('ui')")
    @RequestMapping(path = "/base_block", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public BaseBlockDTO update(@Valid @RequestBody BaseBlockDTO baseBlockDTO) {
        return baseBlockService.update(baseBlockDTO);
    }

    @PreAuthorize("#oauth2.hasScope('ui')")
    @RequestMapping(path = "/base_block", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public boolean delete(@Valid @RequestBody BaseBlockDTO baseBlockDTO) {
        return baseBlockService.delete(baseBlockDTO);
    }

    @PreAuthorize("#oauth2.hasScope('ui')")
    @RequestMapping(path = "/base_block", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<BaseBlockDTO> get() {
        return baseBlockService.getAllBaseBlocks();
    }

    @PreAuthorize("#oauth2.hasScope('ui')")
    @RequestMapping(path = "/base_block/blocksType", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<BaseBlockDTO> getBlocksType(@Valid @RequestParam String type) {
        return baseBlockService.getBaseBlocksType(type);
    }

}
