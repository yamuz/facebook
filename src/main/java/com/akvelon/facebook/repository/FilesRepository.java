package com.akvelon.facebook.repository;

import com.akvelon.facebook.entity.FileInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilesRepository extends JpaRepository<FileInfo, Long> {
}
