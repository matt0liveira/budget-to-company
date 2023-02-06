package com.budget.budgetapi.domain.service;

import java.io.InputStream;
import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

public interface PhotoStorageService {

    void storage(NewPhoto newPhoto);

    void remove(String fileName);

    RetrievedPhoto retrieve(String fileName);

    default String generateFileName(String fileName) {
        String localDateTimeFormatted = LocalDateTime.now().toString();
        localDateTimeFormatted = localDateTimeFormatted.replace(":", "");
        localDateTimeFormatted = localDateTimeFormatted.replace("-", "");
        localDateTimeFormatted = localDateTimeFormatted.replace(".", "");

        return localDateTimeFormatted + "_" + fileName;
    }

    default void toUpdate(String fileNameOld, NewPhoto newPhoto) {
        this.storage(newPhoto);

        if (fileNameOld != null) {
            this.remove(fileNameOld);
        }
    }

    @Builder
    @Getter
    class NewPhoto {
        private String fileName;
        private String contentType;
        private InputStream inputStream;
    }

    @Builder
    @Getter
    public class RetrievedPhoto {
        private String url;
        private InputStream inputStream;

        public boolean hasUrl() {
            return url != null;
        }

        public boolean hasInputStream() {
            return inputStream != null;
        }
    }
}
