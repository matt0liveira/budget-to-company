package com.budget.budgetapi.core.storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.budget.budgetapi.core.storage.StorageProperties.StorageType;
import com.budget.budgetapi.domain.service.PhotoStorageService;
import com.budget.budgetapi.infrasctruture.service.storage.LocalPhotoStorageService;
import com.budget.budgetapi.infrasctruture.service.storage.S3PhotoStorageService;

@Configuration
public class StorageConfig {

    @Autowired
    private StorageProperties storageProperties;

    @Bean
    @ConditionalOnProperty(name = "budget.storage.type", havingValue = "s3")
    public AmazonS3 amazonS3() {
        var credentials = new BasicAWSCredentials(
                storageProperties.getS3().getAccessKeyId(),
                storageProperties.getS3().getAccessKeySecret());

        return AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(storageProperties.getS3().getRegion())
                .build();
    }

    @Bean
    public PhotoStorageService photoStorageService() {
        if (storageProperties.getType().equals(StorageType.LOCAL)) {
            return new LocalPhotoStorageService();
        } else {
            return new S3PhotoStorageService();
        }
    }
}
