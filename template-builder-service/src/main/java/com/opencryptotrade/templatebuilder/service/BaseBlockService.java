package com.opencryptotrade.templatebuilder.service;

import com.opencryptotrade.templatebuilder.dto.BaseBlockDTO;
import com.opencryptotrade.templatebuilder.entity.BaseBlock;
import com.opencryptotrade.templatebuilder.enums.BaseBlockType;

import java.util.List;

public interface BaseBlockService {

    /**
     * Save new base block to DB.
     *
     * @param type enum.
     * @param html Full html text of block's.
     * @return Created base block.
     */
    BaseBlockDTO create(String type, String html);

    /**
     * Update existing base block.
     *
     * @param id Unique identifier block.
     * @param type enum.
     * @param html Full html text of block's.
     * @return Updated base block.
     */
    BaseBlockDTO update(Long id, String type, String html);

    /**
     * Delete base block from DB.
     *
     * @param id Unique identifier block.
     * @return Result of operation.
     */
    boolean delete(Long id);

    /**
     * Get list of base blocks by type.
     *
     * @param type enum.
     * @return The list of base blocks by type.
     */
    List<BaseBlockDTO> getBaseBlocksType(String type);

    /**
     * Get all list of base blocks.
     *
     * @return All base blocks.
     */
    List<BaseBlockDTO> getAllBaseBlocks();

}
