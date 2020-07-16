package com.opencryptotrade.templatebuilder.controller;

import com.opencryptotrade.templatebuilder.dto.BaseBlockDTO;
import com.opencryptotrade.templatebuilder.dto.BaseBlockLinkDTO;
import com.opencryptotrade.templatebuilder.exception.BaseBlockInTemplate;
import com.opencryptotrade.templatebuilder.service.BaseBlockLinkService;
import com.opencryptotrade.templatebuilder.service.BaseBlockService;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class BaseBlockController {

    final BaseBlockService baseBlockService;

    final BaseBlockLinkService baseBlockLinkService;

    public BaseBlockController(BaseBlockService baseBlockService, BaseBlockLinkService baseBlockLinkService) {
        this.baseBlockService = baseBlockService;
        this.baseBlockLinkService = baseBlockLinkService;
    }

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
    public boolean delete(@Valid @RequestBody BaseBlockDTO baseBlockDTO) throws BaseBlockInTemplate {
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

    @PreAuthorize("#oauth2.hasScope('ui')")
    @RequestMapping(path = "/add_base_block_links", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public BaseBlockLinkDTO addBaseBlockLink(@Valid @RequestBody BaseBlockLinkDTO baseBlockLinkDTO) {
        // Need Template id and block to add to template.
        return baseBlockLinkService.addToTemplate(baseBlockLinkDTO);
    }

    @PreAuthorize("#oauth2.hasScope('ui')")
    @RequestMapping(path = "/delete_base_block_links", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public boolean deleteBaseBlockLink(@Valid @RequestBody BaseBlockLinkDTO baseBlockLinkDTO) {
        // Need Template id.
        return baseBlockLinkService.delete(baseBlockLinkDTO);
    }

}
