package com.opencryptotrade.templatebuilder;

import com.opencryptotrade.templatebuilder.dto.BaseBlockDTO;
import com.opencryptotrade.templatebuilder.entity.BaseBlock;
import com.opencryptotrade.templatebuilder.enums.BaseBlockType;
import com.opencryptotrade.templatebuilder.exception.BaseBlockInTemplate;
import com.opencryptotrade.templatebuilder.repository.BaseBlockRepository;
import com.opencryptotrade.templatebuilder.service.EmailTemplateService;
import com.opencryptotrade.templatebuilder.service.impl.BaseBlockServiceImpl;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;

@ExtendWith(SpringExtension.class)
public class BaseBlockServiceImplTest {

    private BaseBlockServiceImpl baseBlockService;

    @Mock
    private BaseBlockRepository baseBlockRepository;

    @Mock
    private EmailTemplateService emailTemplateService;

    @Mock
    private ModelMapper modelMapper;

    private BaseBlock baseBlockHeader;

    private BaseBlock baseBlockBody;

    private BaseBlock newBaseBlockHeader;

    private BaseBlock newBaseBlockBody;

    private final ObjectId baseBlockHeaderId = ObjectId.get();

    private final ObjectId baseBlockBodyId = ObjectId.get();

    private final ObjectId newBaseBlockHeaderId = ObjectId.get();

    private final ObjectId newBaseBlockBodyId = ObjectId.get();

    private final String baseBlockHeaderHtml = "Base Header HTML";

    private final String baseBlockBodyHtml = "Base Body HTML";

    private final String newBaseBlockHeaderHtml = "New Base Header HTML";

    private final String newBaseBlockBodyHtml = "New Base Body HTML";

    @BeforeEach
    public void setUp() {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setAmbiguityIgnored(true);

        baseBlockHeader = new BaseBlock();
        baseBlockHeader.setId(baseBlockHeaderId);
        baseBlockHeader.setHtml(baseBlockHeaderHtml);
        baseBlockHeader.setType(BaseBlockType.HEADER);
        Optional<BaseBlock> optionalBaseBlockHeader = Optional.of(baseBlockHeader);

        baseBlockBody = new BaseBlock();
        baseBlockBody.setId(baseBlockBodyId);
        baseBlockBody.setHtml(baseBlockBodyHtml);
        baseBlockBody.setType(BaseBlockType.BODY);
        Optional<BaseBlock> optionalBaseBlockBody = Optional.of(baseBlockBody);

        newBaseBlockHeader = new BaseBlock();
        newBaseBlockHeader.setId(newBaseBlockHeaderId);
        newBaseBlockHeader.setHtml(newBaseBlockHeaderHtml);
        newBaseBlockHeader.setType(BaseBlockType.HEADER);
        Optional<BaseBlock> optionalNewBaseBlockHeader = Optional.of(newBaseBlockHeader);

        newBaseBlockBody = new BaseBlock();
        newBaseBlockBody.setId(newBaseBlockBodyId);
        newBaseBlockBody.setHtml(newBaseBlockBodyHtml);
        newBaseBlockBody.setType(BaseBlockType.BODY);
        Optional<BaseBlock> optionalNewBaseBlockBody = Optional.of(newBaseBlockBody);

        // findById
        doAnswer(invocation -> optionalBaseBlockHeader).when(baseBlockRepository).findById(baseBlockHeaderId);
        doAnswer(invocation -> optionalBaseBlockBody).when(baseBlockRepository).findById(baseBlockBodyId);
        doAnswer(invocation -> optionalNewBaseBlockHeader).when(baseBlockRepository).findById(newBaseBlockHeaderId);
        doAnswer(invocation -> optionalNewBaseBlockBody).when(baseBlockRepository).findById(newBaseBlockBodyId);

        // findAll
        List<BaseBlock> baseBlockList = new ArrayList<>();
        baseBlockList.add(baseBlockHeader);
        baseBlockList.add(baseBlockBody);
        baseBlockList.add(newBaseBlockHeader);
        baseBlockList.add(newBaseBlockBody);
        doAnswer(invocation -> baseBlockList).when(baseBlockRepository).findAll();

        // save
        doAnswer(invocation -> newBaseBlockHeader).when(baseBlockRepository).save(any());

        baseBlockService = new BaseBlockServiceImpl(baseBlockRepository, emailTemplateService, modelMapper);
    }

    @Test
    public void whenCreateBaseBlock_thenBaseBlockShouldBeCreated() {
        BaseBlockDTO baseBlockDTO = baseBlockService.create(new BaseBlockDTO());
        assertEquals(baseBlockDTO.getId(), newBaseBlockHeader.getId().toString());
    }

    @Test
    public void whenUpdateBaseBlock_thenBaseBlockShouldBeUpdated() {
        BaseBlock baseBlock = new BaseBlock();
        baseBlock.setId(ObjectId.get());
        baseBlock.setType(BaseBlockType.BODY);
        baseBlock.setHtml("Test");
        doAnswer(invocation -> baseBlock).when(baseBlockRepository).save(any());

        BaseBlockDTO baseBlockDTO = new BaseBlockDTO();
        baseBlockDTO.setId(baseBlockHeaderId.toString());

        BaseBlockDTO result = baseBlockService.update(baseBlockDTO);
        assertEquals(baseBlock.getHtml(), result.getHtml());
        assertEquals(baseBlock.getType(), result.getType());
    }

    @Test
    public void whenUpdateBaseBlockWithUnknownId_thenReturnException() {
        BaseBlockDTO baseBlockDTO = new BaseBlockDTO();
        baseBlockDTO.setId(ObjectId.get().toString());

        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> baseBlockService.update(baseBlockDTO));
        assertEquals("No value present", exception.getMessage());
    }

    @Test
    public void whenDeleteBaseBlock_thenBaseBlockShouldBeDeleted() throws BaseBlockInTemplate {
        BaseBlockDTO baseBlockDTO = new BaseBlockDTO();
        baseBlockDTO.setId(baseBlockHeaderId.toString());
        boolean result = baseBlockService.delete(baseBlockDTO);
        assertTrue(result);
    }

    @Test
    public void whenDeleteBaseBlockWithUnknownId_thenReturnException() {
        BaseBlockDTO baseBlockDTO = new BaseBlockDTO();
        baseBlockDTO.setId(ObjectId.get().toString());
        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> baseBlockService.delete(baseBlockDTO));
        assertEquals("No value present", exception.getMessage());
    }

    @Test
    public void whenFindAllBaseBlocksByType_thenAllBaseBlocksShouldBeFound() {
        List<BaseBlock> list = new ArrayList<>();
        BaseBlock baseBlock1 = new BaseBlock();
        baseBlock1.setType(BaseBlockType.BODY);
        list.add(baseBlock1);
        BaseBlock baseBlock2 = new BaseBlock();
        baseBlock1.setType(BaseBlockType.BODY);
        list.add(baseBlock2);
        BaseBlock baseBlock3 = new BaseBlock();
        baseBlock1.setType(BaseBlockType.BODY);
        list.add(baseBlock3);

        doAnswer(invocation -> list).when(baseBlockRepository).findByType(BaseBlockType.valueOf("BODY"));
        List<BaseBlockDTO> result = baseBlockService.getBaseBlocksType("BODY");
        assertEquals(3, result.size());

        List<BaseBlockDTO> result2 = baseBlockService.getBaseBlocksType("HEADER");
        assertEquals(0, result2.size());
    }


    @Test
    public void whenNeedAllBaseBlocks_thenReturnAllBaseBlocks() {
        List<BaseBlockDTO> result = baseBlockService.getAllBaseBlocks();
        assertEquals(4, result.size());
    }

    @Test
    public void whenFindByIdBaseBlock_thenBaseBlockShouldBeFound() {
        BaseBlock result = baseBlockService.findById(baseBlockHeaderId);
        assertEquals(BaseBlockType.HEADER, result.getType());
        assertNotEquals(BaseBlockType.FOOTER, result.getType());
        assertNotEquals(BaseBlockType.BODY, result.getType());
    }

    @Test
    public void whenFindByUnknownId_thenReturnException() {
        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> baseBlockService.findById(ObjectId.get()));
        assertEquals("No value present", exception.getMessage());
    }

}
