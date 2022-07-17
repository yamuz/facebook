package com.akvelon.facebook.dto;

import com.akvelon.facebook.entity.Post;
import com.akvelon.facebook.entity.User;
import lombok.Builder;
import lombok.Data;

import java.io.InputStream;
import java.util.Date;

@Data
@Builder
public class PostNewDto {
    private Long id;

    private Date postedDate;
    private String postText;
    private InputStream mediaStream;
    private String ownerEmail;

    public static PostNewDto from(Post post){
        return PostNewDto.builder()
                .id(post.getId())
                .postedDate(post.getPostedDate())
                .ownerEmail(post.getOwner().getEmail())
                .build();
    }
}
