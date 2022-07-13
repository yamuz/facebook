package com.akvelon.facebook.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 24.05.2022
 * 19. Servlet Application
 *
 * @author Sidikov Marsel (Akvelon)
 * @version v1.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
public class FileInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String originalFileName;
    private String storageFileName;
    private Long size;
    private String mimeType;
    private String description;
}
