package com.akvelon.facebook.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.OutputStream;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileDto {
    @NotEmpty
    private String fileName;
    @Max(value = 50000)
    private Integer size;
    @NotEmpty
    private String storageFileName;
    @NotEmpty
    private String mimeType;
    private String description;
    @NotNull
    private OutputStream fileStream;
}
