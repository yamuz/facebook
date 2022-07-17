package com.akvelon.facebook.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Builder
@Data
public class PostDtoPage {
    List<PostDto> posts;
}
