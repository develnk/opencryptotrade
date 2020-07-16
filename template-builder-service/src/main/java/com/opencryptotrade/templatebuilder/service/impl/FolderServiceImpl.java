package com.opencryptotrade.templatebuilder.service.impl;

import com.opencryptotrade.templatebuilder.dto.FolderDTO;
import com.opencryptotrade.templatebuilder.entity.Folder;
import com.opencryptotrade.templatebuilder.exception.FolderAlreadyExist;
import com.opencryptotrade.templatebuilder.repository.FolderRepository;
import com.opencryptotrade.templatebuilder.service.FolderService;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class FolderServiceImpl implements FolderService {

    private final FolderRepository folderRepository;

    private final ModelMapper modelMapper;

    private final String folderNotExist = "Folder not exist";

    public FolderServiceImpl(FolderRepository folderRepository, ModelMapper modelMapper) {
        this.folderRepository = folderRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public FolderDTO create(FolderDTO folderDTO) {
        Folder folder = folderRepository.findByName(folderDTO.getName());
        if (folder != null) {
            throw new FolderAlreadyExist("Folder already exist");
        }

        Folder savedFolder = saveNewFolder(folderDTO.getName());
        return modelMapper.map(savedFolder, FolderDTO.class);

    }

    @Override
    public FolderDTO update(FolderDTO folderDTO) {
        Folder folder = folderRepository.findById(new ObjectId(folderDTO.getId())).orElseThrow(() -> new NoSuchElementException(folderNotExist));
        folder.setName(folderDTO.getName());
        Folder savedFolder = folderRepository.save(folder);
        return modelMapper.map(savedFolder, FolderDTO.class);
    }

    @Override
    public boolean delete(ObjectId id) {
        Folder folder = folderRepository.findById(id).orElseThrow(() -> new NoSuchElementException(folderNotExist));
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
        folders.sort(Comparator.comparing(FolderDTO::getName));
        folders.stream().filter(f -> f.getName().equals("Default")).findFirst().ifPresentOrElse(f -> {
            folders.remove(f);
            folders.add(0, f);
        }, () -> {
            // Create default folder and add to first position in ArrayList.
            Folder newFolder = this.saveNewFolder("Default");
            FolderDTO newFolderDto = modelMapper.map(newFolder, FolderDTO.class);
            folders.add(0, newFolderDto);
        });
        return folders;
    }

    @Override
    public Folder findFolderById(ObjectId id) {
        return folderRepository.findById(id).orElseGet(() -> folderRepository.findByName("Default"));
    }

    @Override
    public Folder findFolderByName(String name) {
        return folderRepository.findByName(name);
    }

    @Override
    public Folder getDefaultFolder() {
        AtomicReference<Folder> defaultFolder = new AtomicReference<>();
        Optional.of(findFolderByName("Default")).ifPresentOrElse(
            defaultFolder::set,
            () -> defaultFolder.set(this.saveNewFolder("Default"))
        );
        return defaultFolder.get();
    }

    private Folder saveNewFolder(String name) {
        Folder newFolder = new Folder();
        newFolder.setName(name);
        return folderRepository.save(newFolder);
    }

}
