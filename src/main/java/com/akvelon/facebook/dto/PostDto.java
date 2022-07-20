package com.akvelon.facebook.dto;

import com.akvelon.facebook.entity.FileInfo;
import com.akvelon.facebook.entity.Post;
import lombok.Builder;
import lombok.Data;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;

@Data
@Builder
public class PostDto {
    private Long id;
    private String postedDate;
    private String postText;
    private String fileStorageName;
    private String ownerEmail;

    public static PostDto from(Post post) {
        return PostDto.builder()
                .id(post.getId())
                .postedDate(post.getPostedDate().toString())
                .ownerEmail(post.getOwner().getEmail())
                .postText(post.getPostText())
                .fileStorageName(post.getFileInfo().getStorageFileName())
                .build();
    }

}
