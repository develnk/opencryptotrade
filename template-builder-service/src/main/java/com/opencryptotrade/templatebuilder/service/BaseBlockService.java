package com.opencryptotrade.templatebuilder.service;

import com.opencryptotrade.templatebuilder.dto.BaseBlockDTO;
import com.opencryptotrade.templatebuilder.entity.BaseBlock;
import com.opencryptotrade.templatebuilder.exception.BaseBlockInTemplate;
import org.bson.types.ObjectId;

import java.util.List;

public interface BaseBlockService {

    /**
     * Save new base block to DB.
     *
     * @return Created base block.
     */
    BaseBlockDTO create(BaseBlockDTO baseBlockDTO);

    /**
     * Update existing base block.
     *
     * @return Updated base block.
     */
    BaseBlockDTO update(BaseBlockDTO baseBlockDTO);

    /**
     * Delete base block from DB.
     *
     * @return Result of operation.
     */
    boolean delete(BaseBlockDTO baseBlockDTO) throws BaseBlockInTemplate;

    /**
     * Get list of base blocks by type.
     *
     * @return The list of base blocks by type.
     */
    List<BaseBlockDTO> getBaseBlocksType(String type);

    /**
     * Get all list of base blocks.
     *
     * @return All base blocks.
     */
    List<BaseBlockDTO> getAllBaseBlocks();

    /**
     * Find Base Block by id.
     *
     * @param id
     * @return
     */
    BaseBlock findById(ObjectId id);

}
