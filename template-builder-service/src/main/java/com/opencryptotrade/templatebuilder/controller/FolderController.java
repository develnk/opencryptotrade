package com.opencryptotrade.templatebuilder.controller;

import com.opencryptotrade.templatebuilder.dto.FolderDTO;
import com.opencryptotrade.templatebuilder.service.FolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/folder")
public class FolderController {

    public static final String ROLE_ADMIN = "ROLE_ADMIN";

    @Autowired
    FolderService folderService;

    @PreAuthorize("#oauth2.hasScope('ui')")
    @Secured({ROLE_ADMIN})
    @RequestMapping(path = "/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public FolderDTO create(@Valid @RequestBody String name) {
        return folderService.create(name);
    }

    @PreAuthorize("#oauth2.hasScope('ui')")
    @Secured({ROLE_ADMIN})
    @RequestMapping(path = "/", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public FolderDTO update(@Valid @RequestBody FolderDTO folder) {
        return folderService.update(folder);
    }

    @PreAuthorize("#oauth2.hasScope('ui')")
    @Secured({ROLE_ADMIN})
    @RequestMapping(path = "/", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public boolean delete(@Valid @RequestBody FolderDTO folder) {
        return folderService.delete(folder.getId());
    }

    @PreAuthorize("#oauth2.hasScope('ui')")
    @RequestMapping(path = "/", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<FolderDTO> get() {
        return folderService.getAllFolders();
    }

}
