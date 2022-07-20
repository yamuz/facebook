package com.akvelon.facebook.service.impl;

import com.akvelon.facebook.dto.FileDto;
import com.akvelon.facebook.entity.FileInfo;
import com.akvelon.facebook.repository.FilesRepository;
import com.akvelon.facebook.service.interfaces.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final FilesRepository filesRepository;

    @Override
    public void upload(FileDto file) {

        //filesRepository.save(file);
    }

    @Override
    public void writeFileToStream(String storageFileName, OutputStream outputStream) {

    }

    @Override
    public FileDto getFileInformation(String storageFileName) throws FileNotFoundException {
        FileInfo fileInfo = filesRepository.findByStorageFileName(storageFileName).orElseThrow();
        return FileDto.builder()
                .fileName(fileInfo.getOriginalFileName())
                .storageFileName(fileInfo.getStorageFileName())
                .mimeType(fileInfo.getMimeType())
                .size(fileInfo.getSize().intValue())
                .fileStream(new FileOutputStream(fileInfo.getStorageFileName()))
                .build();
    }
}
