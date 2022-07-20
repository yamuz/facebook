package com.akvelon.facebook.controller.rest;

import com.akvelon.facebook.controller.api.FilesApi;
import com.akvelon.facebook.dto.FileDto;
import com.akvelon.facebook.service.interfaces.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.FileInputStream;
import java.io.FileNotFoundException;


@Controller
public class FilesController implements FilesApi {

    private final FileService fileService;
    private final String storagePath;

    public FilesController(FileService fileService, @Value("${app.fileStorage.path}") String storagePath) {
        this.fileService = fileService;
        this.storagePath = storagePath;
    }

    @Override
    @GetMapping("/api/file/{storageFileName}")
    public ResponseEntity<InputStreamResource> getFileStream(@PathVariable("storageFileName") String storageFileName) throws FileNotFoundException {

        FileDto fileDto = fileService.getFileInformation(storageFileName);

        HttpHeaders respHeaders = new HttpHeaders();
        respHeaders.setContentType(MediaType.parseMediaType(fileDto.getMimeType()));
        respHeaders.setContentLength(fileDto.getSize());
        respHeaders.setContentDispositionFormData("attachment", fileDto.getFileName());

        InputStreamResource isr = new InputStreamResource(new FileInputStream(storagePath + fileDto.getStorageFileName()));
        return new ResponseEntity<>(isr, respHeaders, HttpStatus.OK);

    }
}
