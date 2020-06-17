package com.opencryptotrade.templatebuilder.controller;

import com.opencryptotrade.templatebuilder.dto.FolderDTO;
import com.opencryptotrade.templatebuilder.service.FolderService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class FolderController {

    public static final String ROLE_ADMIN = "ROLE_ADMIN";

    @Autowired
    FolderService folderService;

    @PreAuthorize("#oauth2.hasScope('ui')")
    @Secured({ROLE_ADMIN})
    @RequestMapping(path = "/folder", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public FolderDTO create(@Valid @RequestBody FolderDTO folder) {
        return folderService.create(folder);
    }

    @PreAuthorize("#oauth2.hasScope('ui')")
    @Secured({ROLE_ADMIN})
    @RequestMapping(path = "/folder", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public FolderDTO update(@Valid @RequestBody FolderDTO folder) {
        return folderService.update(folder);
    }

    @PreAuthorize("#oauth2.hasScope('ui')")
    @Secured({ROLE_ADMIN})
    @DeleteMapping(value = "/folder/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> delete(@PathVariable String id) {
        boolean result =  folderService.delete(new ObjectId(id));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PreAuthorize("#oauth2.hasScope('ui')")
    @RequestMapping(path = "/folder", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<FolderDTO> get() {
        return folderService.getAllFolders();
    }

}
