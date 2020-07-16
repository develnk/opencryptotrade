package com.opencryptotrade.templatebuilder.service;

import com.opencryptotrade.templatebuilder.dto.FolderDTO;
import com.opencryptotrade.templatebuilder.entity.Folder;
import org.bson.types.ObjectId;

import java.util.List;

public interface FolderService {

    /**
     * Create new folder.
     *
     * @return Created folder
     */
    FolderDTO create(FolderDTO folderDTO);

    /**
     * Update folder.
     *
     * @return Updated folder
     */
    FolderDTO update(FolderDTO folder);

    /**
     * Delete folder from system.
     *
     * @param id
     *   Folder id.
     * @return Result of operation.
     */
    boolean delete(ObjectId id);

    /**
     * Get all folders from DB.
     *
     * @return All folders.
     */
    List<FolderDTO> getAllFolders();

    /**
     * Find folder by id.
     *
     * @param id
     *   Folder id.
     * @return Found folder.
     */
    Folder findFolderById(ObjectId id);

    /**
     * Find folder by name
     *
     * @param name
     *   Folder name.
     * @return Found folder.
     */
    Folder findFolderByName(String name);

    /**
     * Return default folder if necessary will be create.
     *
     * @return Default folder.
     */
    Folder getDefaultFolder();

}
