package com.opencryptotrade.templatebuilder.service;

import com.opencryptotrade.templatebuilder.dto.FolderDTO;
import com.opencryptotrade.templatebuilder.entity.Folder;

import java.util.List;

public interface FolderService {

    /**
     * Create new folder.
     *
     * @param name
     * @return Created folder
     */
    FolderDTO create(String name);

    /**
     * Update folder.
     *
     * @return Updated folder
     */
    FolderDTO update(FolderDTO folder);

    /**
     * Delete folder from system.
     *
     * @param folderId
     * @return Result of operation.
     */
    boolean delete(Long folderId);

    /**
     * Get all folders from DB.
     *
     * @return All folders.
     */
    List<FolderDTO> getAllFolders();
}
