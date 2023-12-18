package com.example.Social_Media_Apiwiz.service;

import com.example.Social_Media_Apiwiz.model.Comment;
import com.example.Social_Media_Apiwiz.model.Post;
import com.example.Social_Media_Apiwiz.model.User;
import com.example.Social_Media_Apiwiz.model.enums.Privacy;
import com.example.Social_Media_Apiwiz.repository.CommentRepo;
import com.example.Social_Media_Apiwiz.repository.PostRepo;
import com.example.Social_Media_Apiwiz.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentService {
    @Autowired
    CommentRepo commentRepository;
    @Autowired
    private PostRepo postRepository;
    @Autowired
    private FollowService followService;
    @Autowired
    private UserRepo userRepository;


    public ResponseEntity<String> addComment(String username, Long postId, String comment) {
        Optional<User> user = userRepository.findByUserName(username);
        Optional<Post> post = postRepository.findById(postId);

        if(post.isEmpty()){
            return  new ResponseEntity<>("Post does not exist", HttpStatus.BAD_REQUEST);
        }
        Privacy postPrivacy = post.get().getPrivacy();
        if(postPrivacy.equals(Privacy.Public)){
            Comment newComment = Comment.builder()
                    .commentBody(comment)
                    .commenter(user.get())
                    .instaPost(post.get())
                    .build();
            commentRepository.save(newComment);
            return  new ResponseEntity<>("You have commented the post", HttpStatus.OK);
        }
        else if(followService.isFollowing(username, userRepository.findByUserName(username).get().getId())){
            Comment newComment = Comment.builder()
                    .commentBody(comment)
                    .commenter(user.get())
                    .instaPost(post.get())
                    .build();
            commentRepository.save(newComment);

            return  new ResponseEntity<>("You have commented the post", HttpStatus.OK);
        }
        return  new ResponseEntity<>("Invalid credentials",HttpStatus.BAD_REQUEST);



    }

    public ResponseEntity<String> removeComment(String username, Long postId, Long commentId) {
        Optional<User> user = userRepository.findByUserName(username);
        Optional<Post> post = postRepository.findById(postId);
        Optional<Comment> comment = commentRepository.findById(commentId);

        if(post.isEmpty()){
            return  new ResponseEntity<>("Post does not exist", HttpStatus.BAD_REQUEST);
        }
        if(comment.isEmpty()){
            return  new ResponseEntity<>("Comment does not exist", HttpStatus.BAD_REQUEST);
        }
        // post owner remove comment
        if(post.get().getUser().getUserName().equals(username)){
            commentRepository.deleteById(commentId);
            return  new ResponseEntity<>("Comment Removed successfully",HttpStatus.OK);
        }

        // commenter removing comment
        if(comment.get().getCommenter().getUserName().equals(username)){
            commentRepository.deleteById(commentId);
            return  new ResponseEntity<>("Comment Removed successfully",HttpStatus.OK);
        }

        return new ResponseEntity<>("only post owner or commenter can remove comment",HttpStatus.BAD_REQUEST);



    }

    public ResponseEntity<String> countComment(String username, Long postId) {
        Optional<User> user = userRepository.findByUserName(username);
        Optional<Post> post = postRepository.findById(postId);
        if (post.isEmpty()) {
            return new ResponseEntity<>("Post does not exist", HttpStatus.BAD_REQUEST);
        }
        Privacy postPrivacy = post.get().getPrivacy();
        int count = 0;

        if (postPrivacy.equals(Privacy.Public)) {
            for (Comment comment : commentRepository.findAll()) {
                if (comment.getInstaPost().equals(post.get())) {
                    count++;
                }
            }
            return new ResponseEntity<>("Post have" + count + "Comments", HttpStatus.OK);
        }

        if (followService.isFollowing(username, post.get().getUser().getId())) {
            for (Comment comment : commentRepository.findAll()) {
                if (comment.getInstaPost().equals(post.get())) {
                    count++;
                }
            }
            return new ResponseEntity<>("Post have" + count + "comment", HttpStatus.OK);
        }

        return new ResponseEntity<>("Post is private You cannot see the comment", HttpStatus.BAD_REQUEST);



    }
}