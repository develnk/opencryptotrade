package com.opencryptotrade.templatebuilder;

import com.opencryptotrade.templatebuilder.dto.FolderDTO;
import com.opencryptotrade.templatebuilder.entity.Folder;
import com.opencryptotrade.templatebuilder.exception.FolderAlreadyExist;
import com.opencryptotrade.templatebuilder.repository.FolderRepository;
import com.opencryptotrade.templatebuilder.service.impl.FolderServiceImpl;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;

@ExtendWith(SpringExtension.class)
public class FolderServiceImplIntegrationTest {

    private FolderServiceImpl folderService;

    @Mock
    private FolderRepository folderRepository;

    @Mock
    private ModelMapper modelMapper;

    Folder folder;

    Folder newFolder;

    Folder defaultFolder;

    private final ObjectId folderId = ObjectId.get();

    private final ObjectId newFolderId = ObjectId.get();

    private final ObjectId defaultFolderId = ObjectId.get();

    private final String folderName = "Test";

    private final String defaultFolderName = "Default";

    private final String newFolderName = "New";

    @BeforeEach
    public void setUp() {
        folder = new Folder();
        folder.setId(folderId);
        folder.setName(folderName);
        Optional<Folder> optionalFolder = Optional.of(folder);

        newFolder = new Folder();
        newFolder.setName(newFolderName);
        newFolder.setId(newFolderId);
        Optional<Folder> optionalNewFolder = Optional.of(newFolder);

        defaultFolder = new Folder();
        defaultFolder.setName(defaultFolderName);
        defaultFolder.setId(defaultFolderId);
        Optional<Folder> optionalDefaultFolder = Optional.of(defaultFolder);

        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setAmbiguityIgnored(true);

        // findByName
        doAnswer(invocation -> folder).when(folderRepository).findByName(folderName);
        doAnswer(invocation -> defaultFolder).when(folderRepository).findByName(defaultFolderName);
        doAnswer(invocation -> newFolder).when(folderRepository).findByName(newFolderName);

        // findById
        doAnswer(invocation -> optionalFolder).when(folderRepository).findById(folderId);
        doAnswer(invocation -> optionalNewFolder).when(folderRepository).findById(newFolderId);
        doAnswer(invocation -> optionalDefaultFolder).when(folderRepository).findById(defaultFolderId);

        // save
        doAnswer(invocation -> newFolder).when(folderRepository).save(any());

        // findAll
        List<Folder> folderList = new ArrayList<>();
        folderList.add(folder);
        folderList.add(defaultFolder);
        folderList.add(newFolder);
        doAnswer(invocation -> folderList).when(folderRepository).findAll();

        folderService = new FolderServiceImpl(folderRepository, modelMapper);
    }

    @Test
    public void whenValidId_thenFolderShouldBeFound() {
        Folder found = folderService.findFolderById(folderId);
        assertEquals(found.getName(), folderName);
    }

    @Test
    public void whenNotValidId_thenDefaultFolderShouldBeReturn() {
        Folder found = folderService.findFolderById(ObjectId.get());
        assertEquals(found.getName(), defaultFolderName);
    }

    @Test
    public void whenFolderCreate_thenFolderShouldBeSaved() {
        FolderDTO savedFolder = folderService.create(new FolderDTO());
        assertEquals(savedFolder.getName(), newFolder.getName());
    }

    @Test
    public void whenFolderCreateExist_thenReturnException() {
        doAnswer(invocation -> newFolder).when(folderRepository).findByName("New");
        FolderDTO folderDTO = new FolderDTO();
        folderDTO.setName("New");
        FolderAlreadyExist exception = assertThrows(FolderAlreadyExist.class, () -> folderService.create(folderDTO));
        assertEquals("Folder already exist", exception.getMessage());
    }

    @Test
    public void whenFolderUpdate_thenFolderShouldBeUpdated() {
        Folder updatedFolder = new Folder();
        updatedFolder.setId(folderId);
        updatedFolder.setName("Updated");

        doAnswer(invocation -> updatedFolder).when(folderRepository).save(any());

        FolderDTO folderDTO = new FolderDTO();
        folderDTO.setId(folderId.toString());
        folderDTO.setName("Updated");
        FolderDTO result = folderService.update(folderDTO);
        assertEquals(folderDTO.getName(), result.getName());
    }

    @Test
    public void whenNotExistFolderUpdate_thenReturnException() {
        FolderDTO folderDTO = new FolderDTO();
        folderDTO.setId(ObjectId.get().toString());
        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> folderService.update(folderDTO));
        assertEquals("Folder not exist", exception.getMessage());
    }

    @Test
    public void whenFolderDelete_thenReturnTrue() {
        boolean result = folderService.delete(folderId);
        assertTrue(result);
    }

    @Test
    public void whenFolderDeleteUnknown_thenReturnException() {
        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> folderService.delete(new ObjectId()));
        assertEquals("Folder not exist", exception.getMessage());
    }

    @Test
    public void whenGetAllFolders_thenReturnAllFolders() {
        List<FolderDTO> folders = folderService.getAllFolders();
        assertEquals(3, folders.size());
    }

}
