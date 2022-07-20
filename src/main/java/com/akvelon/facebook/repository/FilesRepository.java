package com.akvelon.facebook.repository;

import com.akvelon.facebook.entity.FileInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FilesRepository extends JpaRepository<FileInfo, Long> {


    Optional<FileInfo> findByStorageFileName(String storageFileName);
}
