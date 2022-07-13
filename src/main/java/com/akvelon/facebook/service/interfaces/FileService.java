package com.akvelon.facebook.service.interfaces;



import com.akvelon.facebook.dto.FileDto;

import java.io.OutputStream;


public interface FileService {

    void upload(FileDto file);

    void writeFileToStream(String storageFileName, OutputStream outputStream);

    FileDto getFileInformation(String storageFileName);
}
