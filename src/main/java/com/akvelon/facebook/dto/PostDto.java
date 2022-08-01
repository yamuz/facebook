package com.akvelon.facebook.dto;

import com.akvelon.facebook.entity.Post;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Data
@Builder
public class PostDto {
    private Long id;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalDateTime postedDate;
    private String postText;
    private String fileStorageName;
    private String ownerEmail;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Long userGroupId;

    public static PostDto from(Post post) {
        return PostDto.builder()
                .id(post.getId())
                .postedDate(post.getPostedDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime())
                .ownerEmail(post.getOwner().getEmail())
                .postText(post.getPostText())
                .fileStorageName(post.getFileInfo().getStorageFileName())
                .userGroupId(post.getUserGroup() == null ? null : post.getUserGroup().getId())
                .build();
    }

}
