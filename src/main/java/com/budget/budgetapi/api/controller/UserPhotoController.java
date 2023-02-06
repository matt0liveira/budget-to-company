package com.budget.budgetapi.api.controller;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.budget.budgetapi.api.assembler.photoUserAssembler.PhotoUserModelAssembler;
import com.budget.budgetapi.api.model.PhotoUserModel;
import com.budget.budgetapi.api.model.input.PhotoUserInputModel;
import com.budget.budgetapi.api.openapi.controlller.UserPhotoControllerOpenApi;
import com.budget.budgetapi.core.security.CheckSecurity;
import com.budget.budgetapi.domain.exception.EntityNotfoundException;
import com.budget.budgetapi.domain.model.PhotoUser;
import com.budget.budgetapi.domain.model.User;
import com.budget.budgetapi.domain.service.GalleryPhotoUserService;
import com.budget.budgetapi.domain.service.PhotoStorageService;
import com.budget.budgetapi.domain.service.PhotoStorageService.RetrievedPhoto;
import com.budget.budgetapi.domain.service.UserService;

@RestController
@RequestMapping("/users/{userId}/photo")
public class UserPhotoController implements UserPhotoControllerOpenApi {

    @Autowired
    private PhotoStorageService photoStorageService;

    @Autowired
    private GalleryPhotoUserService galleryPhotoUser;

    @Autowired
    private UserService userService;

    @Autowired
    private PhotoUserModelAssembler photoUserModelAssembler;

    @CheckSecurity.UsersProfilesPermissions.CanChangeUser
    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PhotoUserModel> toUpdate(@PathVariable Long userId,
            @Valid PhotoUserInputModel photoUserInput, @RequestPart(required = true) MultipartFile file)
            throws IOException {
        User user = userService.findOrFail(userId);

        PhotoUser photo = new PhotoUser();
        photo.setUser(user);
        photo.setContentType(file.getContentType());
        photo.setSize(file.getSize());
        photo.setFileName(file.getOriginalFilename());

        PhotoUser phosaved = galleryPhotoUser.save(photo, file.getInputStream());

        return ResponseEntity
                .ok()
                .body(photoUserModelAssembler.toModel(phosaved));
    }

    @CheckSecurity.UsersProfilesPermissions.CanConsultUser
    @GetMapping(produces = { MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> toFind(@PathVariable Long userId,
            @RequestHeader(name = "accept") String acceptHeader) throws HttpMediaTypeNotAcceptableException {
        if (acceptHeader.equals(MediaType.APPLICATION_JSON_VALUE)) {
            return ResponseEntity
                    .ok()
                    .body(photoUserModelAssembler.toModel(galleryPhotoUser.findOrFail(userId)));
        }

        try {

            PhotoUser photo = galleryPhotoUser.findOrFail(userId);
            MediaType mediaTypePhoto = MediaType.parseMediaType(photo.getContentType());
            List<MediaType> mediaTypeAccepts = MediaType.parseMediaTypes(acceptHeader);

            verifyCompatibilityMediaType(mediaTypePhoto, mediaTypeAccepts);

            RetrievedPhoto retrievedPhoto = photoStorageService.retrieve(photo.getFileName());

            if (retrievedPhoto.hasUrl()) {
                return ResponseEntity
                        .status(HttpStatus.FOUND)
                        .header(HttpHeaders.LOCATION, retrievedPhoto.getUrl())
                        .build();
            } else {
                return ResponseEntity
                        .ok()
                        .contentType(mediaTypePhoto)
                        .body(new InputStreamResource(retrievedPhoto.getInputStream()));
            }
        } catch (EntityNotfoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @CheckSecurity.UsersProfilesPermissions.CanChangeUser
    @DeleteMapping
    public ResponseEntity<Void> remove(@PathVariable Long userId) {
        galleryPhotoUser.remove(userId);

        return ResponseEntity.noContent().build();
    }

    private void verifyCompatibilityMediaType(MediaType mediaTypePhoto, List<MediaType> mediaTypeAccepts)
            throws HttpMediaTypeNotAcceptableException {
        boolean compatible = mediaTypeAccepts
                .stream()
                .anyMatch(mediaTypeAccept -> mediaTypeAccept.isCompatibleWith(mediaTypePhoto));

        if (!compatible) {
            throw new HttpMediaTypeNotAcceptableException(mediaTypeAccepts);
        }
    }
}