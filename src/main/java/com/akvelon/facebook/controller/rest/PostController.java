package com.akvelon.facebook.controller.rest;

import com.akvelon.facebook.controller.api.PostsApi;
import com.akvelon.facebook.dto.PostDtoPage;
import com.akvelon.facebook.service.interfaces.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/posts")
@RestController
public class PostController implements PostsApi {

    private final PostService postService;
    @Override
    @PostMapping("/save2")
    public ResponseEntity<PostDtoPage> save(@RequestPart("mediaStream") MultipartFile mediaStream,
                                       @RequestParam("postText") String postText,
                                       @RequestParam("ownerEmail") String ownerEmail) throws IOException{

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(PostDtoPage
                        .builder()
                        .posts(List.of(postService.save(mediaStream, postText, ownerEmail )))
                        .build());
    }


    @GetMapping("/{postId}")
    public ResponseEntity<PostDtoPage> getPost(@PathVariable Long postId ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(PostDtoPage
                        .builder()
                        .posts(List.of(postService.findById(postId)))
                        .build());
    }

    @GetMapping("/{ownerEmail}/all")
    public ResponseEntity<PostDtoPage> getPostOfUser(@PathVariable String ownerEmail ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(PostDtoPage
                        .builder()
                        .posts(postService.findAllByUser(ownerEmail))
                        .build());
    }

    @Override
    public ResponseEntity<String> deletePost(@PathVariable Long postId) {
        postService.deleteById(postId);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Response code 200. Post Deleted successfully.");
    }

    @Override
    public ResponseEntity<PostDtoPage> getPostOfFriends(@PathVariable String ownerEmail) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(PostDtoPage
                        .builder()
                        .posts(postService.findFriendsPostsByUser(ownerEmail))
                        .build());
    }

}
