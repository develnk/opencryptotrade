package com.opencryptotrade.templatebuilder.service;

import com.opencryptotrade.templatebuilder.entity.Folder;

import java.util.List;

public interface FolderService {

    /**
     * Create new folder.
     *
     * @param name
     * @return Created folder
     */
    Folder create(String name);

    /**
     * Update folder.
     *
     * @param name
     * @return Updated folder
     */
    Folder update(Long folderId, String name);

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
    List<Folder> getAllFolders();
}
