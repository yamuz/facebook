package com.akvelon.facebook.service.impl;

import com.akvelon.facebook.dto.PostDto;
import com.akvelon.facebook.dto.PostNewDto;
import com.akvelon.facebook.entity.FileInfo;
import com.akvelon.facebook.entity.Post;
import com.akvelon.facebook.entity.UserGroup;
import com.akvelon.facebook.exception.EntityNotFoundException;
import com.akvelon.facebook.repository.FilesRepository;
import com.akvelon.facebook.repository.PostRepository;
import com.akvelon.facebook.repository.UserGroupRepository;
import com.akvelon.facebook.repository.UserRepository;
import com.akvelon.facebook.service.interfaces.PostService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    private final String storagePath;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final FilesRepository filesRepository;

    private final UserGroupRepository userGroupRepository;


    public PostServiceImpl(PostRepository postRepository, UserRepository userRepository,
                           FilesRepository filesRepository, @Value("${app.fileStorage.path}") String storagePath,
                           UserGroupRepository userGroupRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.filesRepository = filesRepository;
        this.storagePath = storagePath;
        this.userGroupRepository = userGroupRepository;
    }



    @CachePut(value = "postService", key = "#result.id", unless = "#result == null")
    @Transactional
    @Override
    public PostDto save(MultipartFile file, String postText, String ownerEmail) throws IOException {
        FileInfo fileInfo = FileInfo.builder()
                .description("Post mediafile. File owner:" + ownerEmail)
                .mimeType(file.getContentType())
                .size(file.getSize())
                .originalFileName(file.getOriginalFilename())
                .storageFileName(UUID.randomUUID().toString())
                .build();
        FileInfo savedFileInfo = filesRepository.save(fileInfo);

        Post post = Post.builder()
                .postedDate(Date.from(Instant.now()))
                .postText(postText)
                .owner(userRepository.findByEmail(ownerEmail)
                        .orElseThrow(() -> new EntityNotFoundException("user not found by email:" + ownerEmail)))
                .fileInfo(savedFileInfo)
                .userGroup(null)
                .build();

        Post postSaved = postRepository.save(post);
        try {
            Files.copy(file.getInputStream(), Paths.get(storagePath + fileInfo.getStorageFileName()));
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        return PostDto.from(postSaved);
    }

    @Transactional
    @Override
    public PostDto saveToGroup(MultipartFile file, String postText, String ownerEmail, Long userGroupId) throws IOException {
        FileInfo fileInfo = FileInfo.builder()
                .description("Post mediafile. File owner:" + ownerEmail)
                .mimeType(file.getContentType())
                .size(file.getSize())
                .originalFileName(file.getOriginalFilename())
                .storageFileName(UUID.randomUUID().toString())
                .build();
        FileInfo savedFileInfo = filesRepository.save(fileInfo);

        Post post = Post.builder()
                .postedDate(Date.from(Instant.now()))
                .postText(postText)
                .owner(userRepository.findByEmail(ownerEmail)
                        .orElseThrow(() -> new EntityNotFoundException("user not found by email:" + ownerEmail)))
                .fileInfo(savedFileInfo)
                .userGroup(userGroupRepository.findById(userGroupId).orElseThrow())
                .build();

        Post postSaved = postRepository.save(post);
        try {
            Files.copy(file.getInputStream(), Paths.get(storagePath + fileInfo.getStorageFileName()));
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        return PostDto.from(postSaved);
    }

    @Override
    public List<PostDto> findAll() {
        return postRepository.findAll().stream().map(PostDto::from).collect(Collectors.toList());
    }

    @Cacheable(value = "postService", key = "#postId")
    @Override
    public PostDto findById(Long id) {
        return PostDto.from(postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("post not found by id")));
    }

    @Override
    public PostDto update(PostNewDto postDto) {
        // TODO implement post update
        return null;
    }

    @Override
    public List<PostDto> findAllByUser(String ownerEmail) {
        return postRepository.findAllByOwner(userRepository.findByEmail(ownerEmail).orElseThrow(
                        ()-> new EntityNotFoundException("User not found, email:" + ownerEmail)
                )).stream().map(PostDto::from).collect(Collectors.toList());
    }

    @Override
    @CacheEvict(value = "postsService", key = "#postId")
    public void deleteById(Long postId) {
        postRepository.deleteById(postId);
    }

    @Override
    public List<PostDto> findFriendsPostsByUser(String ownerEmail) {
        return postRepository.findFriendsPostsByEmail(ownerEmail).stream()
                .map(PostDto::from).collect(Collectors.toList());
    }

    @Override
    public List<PostDto> findPostsByUserGroup(Long userGroupId) {
        return postRepository.findAllByUserGroupOrderByPostedDateDesc(userGroupRepository.findById(userGroupId)
                .orElseThrow( ()-> new EntityNotFoundException("User group not found, Id:" + userGroupId)))
                .stream().map(PostDto::from).collect(Collectors.toList());
    }
}
