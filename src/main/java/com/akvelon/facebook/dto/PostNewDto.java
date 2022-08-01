package com.akvelon.facebook.dto;

import com.akvelon.facebook.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostNewDto {
    private Long id;
    private String postText;
    private MultipartFile mediaStream;
    private String ownerEmail;

    public static PostNewDto from(Post post){
        return PostNewDto.builder()
                .id(post.getId())
                .ownerEmail(post.getOwner().getEmail())
                .build();
    }
}
