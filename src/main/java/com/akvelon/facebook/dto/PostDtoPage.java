package com.akvelon.facebook.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class PostDtoPage {
    List<PostDto> posts;
}
