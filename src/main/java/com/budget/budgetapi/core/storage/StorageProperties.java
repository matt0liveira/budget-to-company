package com.budget.budgetapi.core.storage;

import java.nio.file.Path;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.amazonaws.regions.Regions;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Component
@ConfigurationProperties("budget.storage")
public class StorageProperties {

    private Local local = new Local();
    private S3 s3 = new S3();
    private StorageType type = StorageType.LOCAL;

    public enum StorageType {
        LOCAL, S3
    }

    @Setter
    @Getter
    public class Local {
        private Path directoryPhotos;
    }

    @Setter
    @Getter
    public class S3 {
        private String AccessKeyId;
        private String AccessKeySecret;
        private String bucket;
        private Regions region;
        private String directoryPhotos;
    }
}
