package com.opencryptotrade.templatebuilder.service.impl;

import com.opencryptotrade.templatebuilder.dto.FolderDTO;
import com.opencryptotrade.templatebuilder.entity.Folder;
import com.opencryptotrade.templatebuilder.exception.FolderAlreadyExist;
import com.opencryptotrade.templatebuilder.repository.FolderRepository;
import com.opencryptotrade.templatebuilder.service.FolderService;
import feign.FeignException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class FolderServiceImpl implements FolderService {

    private final FolderRepository folderRepository;

    final ModelMapper modelMapper;

    public FolderServiceImpl(FolderRepository folderRepository, ModelMapper modelMapper) {
        this.folderRepository = folderRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public FolderDTO create(FolderDTO folderDTO) {
        Folder folder = folderRepository.findByName(folderDTO.getName());
        if (folder != null) {
            throw new FolderAlreadyExist("Folder already exist.");
        }

        return saveNewFolder(folderDTO.getName());
    }

    @Override
    public FolderDTO update(FolderDTO folderDTO) {
        Folder folder = folderRepository.findNewById(folderDTO.getId());
        if (folder == null) {
            throw new NoSuchElementException("No value present");
        }
        folder.setName(folderDTO.getName());
        folderRepository.save(folder);
        return modelMapper.map(folder, FolderDTO.class);
    }

    @Override
    public boolean delete(Long folderId) {
        Folder folder = folderRepository.findNewById(folderId);
        if (folder == null) {
            throw new NoSuchElementException("No value present");
        }
        // @TODO need check to exist templates in folder.
        folderRepository.delete(folder);
        return true;
    }

    @Override
    public List<FolderDTO> getAllFolders() {
        List<FolderDTO> folders = new ArrayList<>();
        folderRepository.findAll().forEach(folder -> {
            folders.add(modelMapper.map(folder, FolderDTO.class));
        });
        return folders;
    }

    private FolderDTO saveNewFolder(String name) {
        Folder newFolder = new Folder();
        newFolder.setName(name);
        Folder savedFolder = folderRepository.save(newFolder);
        return modelMapper.map(savedFolder, FolderDTO.class);
    }

}
