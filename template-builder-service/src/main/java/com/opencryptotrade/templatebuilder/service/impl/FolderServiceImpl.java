package com.opencryptotrade.templatebuilder.service.impl;

import com.opencryptotrade.templatebuilder.entity.Folder;
import com.opencryptotrade.templatebuilder.repository.FolderRepository;
import com.opencryptotrade.templatebuilder.service.FolderService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FolderServiceImpl implements FolderService {

    private final FolderRepository folderRepository;

    public FolderServiceImpl(FolderRepository folderRepository) {
        this.folderRepository = folderRepository;
    }

    @Override
    public Folder create(String name) {
        Folder newFolder = new Folder();
        newFolder.setName(name);
        return folderRepository.save(newFolder);
    }

    @Override
    public Folder update(Long folderId, String name) {
        Folder folder = folderRepository.findById(folderId).orElseThrow();
        folder.setName(name);
        folderRepository.save(folder);
        return folder;
    }

    @Override
    public boolean delete(Long folderId) {
        Folder folder = folderRepository.findById(folderId).orElseThrow();
        // @TODO need check to exist templates in folder.
        folderRepository.delete(folder);
        return true;
    }

    @Override
    public List<Folder> getAllFolders() {
        List<Folder> folders = new ArrayList<>();
        folderRepository.findAll().forEach(folders::add);
        return folders;
    }

}
