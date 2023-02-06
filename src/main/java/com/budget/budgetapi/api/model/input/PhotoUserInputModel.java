package com.budget.budgetapi.api.model.input;

import javax.validation.constraints.NotNull;

import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import com.budget.budgetapi.core.validation.FileContentType;
import com.budget.budgetapi.core.validation.FileSize;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PhotoUserInputModel {

    @NotNull
    @FileContentType(allowed = { MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE })
    @FileSize(max = "500KB")
    private MultipartFile file;
}
