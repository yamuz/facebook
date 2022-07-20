package com.akvelon.facebook.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.FileNotFoundException;

@Tags(value = @Tag(name="Получение файлов"))
public interface FilesApi {
    @Operation(summary = "Получение файла по имени (storage file name)")
    @GetMapping("/api/file/{storageFileName}")
    ResponseEntity<InputStreamResource> getFileStream(@PathVariable("storageFileName") String fileStorageName) throws FileNotFoundException;
}
