package com.akvelon.facebook.service.interfaces;

import com.akvelon.facebook.dto.PostDto;
import com.akvelon.facebook.dto.PostNewDto;
import com.akvelon.facebook.entity.UserGroup;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PostService {

    PostDto save(MultipartFile file, String postText, String ownerEmail) throws IOException;

    PostDto saveToGroup(MultipartFile file, String postText, String ownerEmail, Long groupId) throws IOException;

    List<PostDto> findAll();

    PostDto findById(Long id);

    PostDto update(PostNewDto postDto);

    List<PostDto> findAllByUser(String ownerEmail);

    void deleteById(Long postId);

    List<PostDto> findFriendsPostsByUser(String ownerEmail);

    List<PostDto> findPostsByUserGroup(Long userGroupId);

}
