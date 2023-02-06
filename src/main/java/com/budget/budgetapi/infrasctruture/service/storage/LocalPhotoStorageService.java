package com.budget.budgetapi.infrasctruture.service.storage;

import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;

import com.budget.budgetapi.core.storage.StorageProperties;
import com.budget.budgetapi.domain.service.PhotoStorageService;

public class LocalPhotoStorageService implements PhotoStorageService {

    @Autowired
    private StorageProperties storageProperties;

    @Override
    public void storage(NewPhoto newPhoto) {
        try {
            Path filePath = getFilePath(newPhoto.getFileName());

            FileCopyUtils.copy(newPhoto.getInputStream(), Files.newOutputStream(filePath));
        } catch (Exception e) {
            throw new StorageException("Não foi possível armazenar o arquivo", e);
        }
    }

    @Override
    public void remove(String fileName) {
        Path filePath = getFilePath(fileName);

        try {
            Files.deleteIfExists(filePath);
        } catch (Exception e) {
            throw new StorageException("Não foi possível excluir o arquivo", e);
        }

    }

    @Override
    public RetrievedPhoto retrieve(String fileName) {
        try {
            Path filePath = getFilePath(fileName);

            RetrievedPhoto retrievedPhoto = RetrievedPhoto
                    .builder()
                    .inputStream(Files.newInputStream(filePath))
                    .build();

            return retrievedPhoto;
        } catch (Exception e) {
            throw new StorageException("Não foi possível recuperar o arquivo", e);
        }
    }

    private Path getFilePath(String fileName) {
        return storageProperties
                .getLocal()
                .getDirectoryPhotos()
                .resolve(Path.of(fileName));
    }

}
