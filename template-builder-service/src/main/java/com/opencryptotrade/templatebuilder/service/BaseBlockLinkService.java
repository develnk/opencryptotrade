package com.opencryptotrade.templatebuilder.service;

import com.opencryptotrade.templatebuilder.dto.BaseBlockCopyDTO;
import com.opencryptotrade.templatebuilder.dto.BaseBlockLinkDTO;
import com.opencryptotrade.templatebuilder.entity.BaseBlockCopy;
import com.opencryptotrade.templatebuilder.entity.BaseBlockLink;

public interface BaseBlockLinkService {

    BaseBlockLinkDTO addToTemplate(BaseBlockLinkDTO baseBlockLinkDTO);

    boolean update(BaseBlockLinkDTO baseBlockLinkDTO);

    boolean delete(BaseBlockLinkDTO baseBlockLinkDTO);

    BaseBlockLink addNewBaseBlockLink(BaseBlockLinkDTO baseBlockLinkDTO);

    BaseBlockLink updateBaseBlockLink(BaseBlockLinkDTO baseBlockLinkDTO);

    BaseBlockCopy saveBaseBlockCopy(BaseBlockCopy baseBlockCopyDTO);

    BaseBlockLink save(BaseBlockLink baseBlockLinkDTO);

}
