package com.budget.budgetapi.infrasctruture.service.storage;

import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.budget.budgetapi.core.storage.StorageProperties;
import com.budget.budgetapi.domain.service.PhotoStorageService;

public class S3PhotoStorageService implements PhotoStorageService {

    @Autowired
    private AmazonS3 amazonS3;

    @Autowired
    private StorageProperties storageProperties;

    @Override
    public void storage(NewPhoto newPhoto) {
        try {
            String filePath = getFilePath(newPhoto.getFileName());
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(newPhoto.getContentType());

            var putObjectRequest = new PutObjectRequest(
                    storageProperties.getS3().getBucket(),
                    filePath,
                    newPhoto.getInputStream(),
                    objectMetadata).withCannedAcl(CannedAccessControlList.PublicRead);

            amazonS3.putObject(putObjectRequest);
        } catch (Exception e) {
            throw new StorageException("Não foi possível armazenar o arquivo.", e);
        }
    }

    @Override
    public void remove(String fileName) {
        var deleteObjectRequest = new DeleteObjectRequest(storageProperties.getS3().getBucket(), getFilePath(fileName));

        amazonS3.deleteObject(deleteObjectRequest);
    }

    @Override
    public RetrievedPhoto retrieve(String fileName) {
        URL url = amazonS3.getUrl(storageProperties.getS3().getBucket(), getFilePath(fileName));

        return RetrievedPhoto
                .builder()
                .url(url.toString())
                .build();
    }

    private String getFilePath(String fileName) {
        return String.format("%s/%s", storageProperties.getS3().getDirectoryPhotos(), fileName);
    }

}
