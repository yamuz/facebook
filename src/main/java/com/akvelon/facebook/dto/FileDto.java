package com.akvelon.facebook.dto;

import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.InputStream;


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
    private String mimeType;
    private String description;
    @NotNull
    private InputStream fileStream;
}
