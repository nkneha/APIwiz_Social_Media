package com.example.Social_Media_Apiwiz.controller;

import com.example.Social_Media_Apiwiz.service.CommentService;
import com.example.Social_Media_Apiwiz.service.LikeService;
import com.example.Social_Media_Apiwiz.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/analytics")
public class UserAnalyticsController {

    @Autowired
    private LikeService likeService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private PostService postService;

    @GetMapping("/likeCount/{postId}")
    public ResponseEntity<String> countLike (Authentication authentication, @PathVariable Long postId){
        String username = authentication.getName();
        return likeService.countLike(username,postId);
    }

    @GetMapping("/reShareCount/{postId}")
    public ResponseEntity<String> reShareCount(Authentication authentication, @PathVariable Long postId) {
        String username = authentication.getName();
        return postService.reShareCount(postId,username);
    }

    @GetMapping("/commentCount/{postId}")
    public ResponseEntity<String> countComment (Authentication authentication, @PathVariable Long postId){
        String username = authentication.getName();
        return commentService.countComment(username,postId);
    }

}
