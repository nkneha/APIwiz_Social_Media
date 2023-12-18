package com.example.Social_Media_Apiwiz.controller;

import com.example.Social_Media_Apiwiz.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/new/{postId}")
    public ResponseEntity<String> newComment (Authentication authentication, @PathVariable Long postId,@RequestParam("comment") String comment){
        String username = authentication.getName();
        return commentService.addComment(username,postId,comment);
    }
    @DeleteMapping("/remove/{postId}/{commentId}")
    public ResponseEntity<String> removeComment (Authentication authentication, @PathVariable Long postId,@PathVariable Long commentId){
        String username = authentication.getName();
        return commentService.removeComment(username,postId,commentId);
    }


}
