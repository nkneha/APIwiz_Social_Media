package com.example.Social_Media_Apiwiz.controller;

import com.example.Social_Media_Apiwiz.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/like")
public class LikeController {

    @Autowired
    private LikeService likeService;

    @PostMapping("/new/{postId}")
    public ResponseEntity<String> newLike (Authentication authentication, @PathVariable Long postId){
        String username = authentication.getName();
        return likeService.addLike(username,postId);
    }
    @DeleteMapping("/remove/{postId}")
    public ResponseEntity<String> removeLike (Authentication authentication, @PathVariable Long postId){
        String username = authentication.getName();
        return likeService.removeLike(username,postId);
    }



}
