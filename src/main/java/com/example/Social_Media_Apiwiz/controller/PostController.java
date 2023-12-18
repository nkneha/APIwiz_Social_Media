package com.example.Social_Media_Apiwiz.controller;

import com.example.Social_Media_Apiwiz.model.dto.PostInput;
import com.example.Social_Media_Apiwiz.model.dto.PostOutput;
import com.example.Social_Media_Apiwiz.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/post")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping("/new")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<String> createPost(Authentication authentication,@RequestBody PostInput postInput) {
        String username = authentication.getName();
        return  postService.createPost(postInput,username);
    }

    @GetMapping("/of/{userId}")
    @PreAuthorize("hasAuthority('ROLE_USER,ROLE_ADMIN')")
    public ResponseEntity<List<PostOutput>> getPostsByUserId(Authentication authentication, @PathVariable Long userId) {

        String username =authentication.getName();

        return  postService.getPostsByUserId(userId,username);
    }

    @DeleteMapping("/{postId}")
    @PreAuthorize("hasAuthority('ROLE_USER,ROLE_ADMIN')")
    public ResponseEntity<String> deletePost(Authentication authentication,@PathVariable Long postId) {
        String username = authentication.getName();
        return  postService.deletePost(postId,username);
    }

    @PostMapping("/reShare/{postId}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<String> reSharePost(Authentication authentication, @PathVariable Long postId) {
        String username = authentication.getName();
        return postService.reSharePost(postId,username);
    }




}
