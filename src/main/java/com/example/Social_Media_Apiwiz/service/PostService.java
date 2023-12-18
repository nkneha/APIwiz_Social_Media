package com.example.Social_Media_Apiwiz.service;

import com.example.Social_Media_Apiwiz.model.Post;
import com.example.Social_Media_Apiwiz.model.User;
import com.example.Social_Media_Apiwiz.model.dto.PostInput;
import com.example.Social_Media_Apiwiz.model.dto.PostOutput;
import com.example.Social_Media_Apiwiz.model.enums.Privacy;
import com.example.Social_Media_Apiwiz.repository.FollowRepo;
import com.example.Social_Media_Apiwiz.repository.PostRepo;
import com.example.Social_Media_Apiwiz.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PostService {
    @Autowired
    private PostRepo postRepository;

    @Autowired
    private UserRepo userRepository;

    @Autowired
    private FollowRepo followRepository;
    @Autowired
    private FollowService followService;


    public ResponseEntity<String> createPost(PostInput postInput, String username) {
        //  logic for creating a post
        Post post = Post.builder()
                .postUrl(postInput.getPostUrl())
                .postType(postInput.getPostType())
                .content(postInput.getContent())
                .privacy(postInput.getPrivacy())
                .shareCount(0L)
                .user(userRepository.findByUserName(username).get())
                .build();
        postRepository.save(post);
        return new ResponseEntity<>("Post Created successfully", HttpStatus.OK);
    }


    public ResponseEntity<List<PostOutput>> getPostsByUserId(Long userId, String username) {
        // Implement your logic for getting posts by user ID
        ArrayList<PostOutput> newList = new ArrayList<PostOutput>();
        Optional<User> user = userRepository.findByUserName(username);


        String userRole = user.get().getRole();

        if (userRole.equals("ROLE_ADMIN")) {
            return getListResponseEntity(userId, newList);
        }
        if(Objects.equals(userId, user.get().getId())){
            return getListResponseEntity(userId, newList);
        }
//        if(userPrivacy.equals(Privacy.Public)){
//            return getListResponseEntity(userId, newList);
//
//        }
//        if(userPrivacy.equals(Privacy.Private)){
//            if(followService.isFollowing(username,userId)){
//                return getListResponseEntity(userId, newList);
//            }
//            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
//        }
        if(followService.isFollowing(username, userId)){
            return getListResponseEntity(userId, newList);
        }

        for(Post post : postRepository.findAll()){
            if(post.getPrivacy().equals(Privacy.Public)){
                PostOutput postOutput= PostOutput.builder()
                        .id(post.getId())
                        .content(post.getContent())
                        .postType(post.getPostType())
                        .postUrl(post.getPostUrl())
                        .createDate(post.getCreateDate())
                        . postOwnerName(post.getUser().getUserName())
                        .build();
                newList.add(postOutput);
            }
        }
        return new ResponseEntity<>(newList,HttpStatus.OK);

    }

    private ResponseEntity<List<PostOutput>> getListResponseEntity(Long userId, ArrayList<PostOutput> newList) {

        for(Post post: postRepository.findByUserId(userId)){
            PostOutput postOutput= PostOutput.builder()
                    .id(post.getId())
                    .content(post.getContent())
                    .postType(post.getPostType())
                    .postUrl(post.getPostUrl())
                    .createDate(post.getCreateDate())
                    . postOwnerName(post.getUser().getUserName())
                    .build();
            newList.add(postOutput);

        }

        return new ResponseEntity<>(newList, HttpStatus.OK);
    }


    public ResponseEntity<String> deletePost(Long postId, String username) {
        if(postRepository.existsById(postId)) {
            Optional<User> user = userRepository.findByUserName(username);
            Long postOwnerId = postRepository.findById(postId).get().getUser().getId();
            if(postRepository.findById(postId).isEmpty()){
                return  new ResponseEntity<>("Post does not exist", HttpStatus.BAD_REQUEST);
            }
            String userRole = user.get().getRole();
            if (userRole.equals("ROLE_ADMIN")) {
                postRepository.deleteById(postId);
                return new ResponseEntity<>("Post is Successfully delete by admin " + user.get().getUserName(), HttpStatus.OK);
            }
            if (Objects.equals(postOwnerId, user.get().getId())) {
                postRepository.deleteById(postId);
                return new ResponseEntity<>("Post is Successfully delete by user " + user.get().getUserName(), HttpStatus.OK);
            }
            return new ResponseEntity<>("Post can only be delete by post owner or admin ", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Post not available with post id "+ postId, HttpStatus.BAD_REQUEST);
    }


    public ResponseEntity<String> reSharePost(Long postId, String username) {
        if(postRepository.existsById(postId) && userRepository.findByUserName(username).isPresent()){
            Optional<User> user= userRepository.findByUserName(username);
            Optional<Post> postInput =postRepository.findById(postId);
            if(postRepository.findById(postId).isEmpty()){
                return  new ResponseEntity<>("Post does not exist", HttpStatus.BAD_REQUEST);
            }
            Privacy postPrivacy =postInput.get().getPrivacy();

            if(postPrivacy.equals(Privacy.Public)){
                Long count =postInput.get().getShareCount();
                postInput.get().setShareCount(count+1L);
                postRepository.save(postInput.get());

                Post newPost = Post.builder()
                        .postUrl(postInput.get().getPostUrl())
                        .postType(postInput.get().getPostType())
                        .content(postInput.get().getContent())
                        .shareCount(0L)
                        .user(user.get())
                        .build();

                return new ResponseEntity<>("Post Re-Share Successfully",HttpStatus.OK);
            }
            else if(followService.isFollowing(username,user.get().getId())){

                Long count =postInput.get().getShareCount();
                postInput.get().setShareCount(count+1L);
                postRepository.save(postInput.get());

                Post newPost = Post.builder()
                        .postUrl(postInput.get().getPostUrl())
                        .postType(postInput.get().getPostType())
                        .shareCount(0L)
                        .content(postInput.get().getContent())
                        .user(user.get())
                        .build();

                return new ResponseEntity<>("Post Re-Share Successfully",HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("Failed to re share the post is private",HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> reShareCount(Long postId, String username) {
        if(postRepository.findById(postId).isEmpty()){
            return  new ResponseEntity<>("Post does not exist", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Post Re-Share count are : "+ postRepository.findById(postId).get().getShareCount(),HttpStatus.OK);

    }
}
