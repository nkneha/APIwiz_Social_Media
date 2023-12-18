package com.example.Social_Media_Apiwiz.service;

import com.example.Social_Media_Apiwiz.model.Follow;
import com.example.Social_Media_Apiwiz.model.User;
import com.example.Social_Media_Apiwiz.repository.FollowRepo;
import com.example.Social_Media_Apiwiz.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FollowService {
    @Autowired
    FollowRepo followRepository;
    @Autowired
    private UserRepo userRepository;


    public ResponseEntity<String> createFollow(String myUserName, String username) {
        Optional<User> followUser = userRepository.findByUserName(username);

        if (isFollowing(myUserName, userRepository.findByUserName(username).get().getId())) {
            return new ResponseEntity<>("your are already following the user", HttpStatus.BAD_REQUEST);
        }

        Follow follow = Follow.builder()
                .currentUser(userRepository.findByUserName(username).get())
                .currentUserFollower(userRepository.findByUserName(myUserName).get())
                .build();
        followRepository.save(follow);

        return new ResponseEntity<>("You are successfully following " + username, HttpStatus.OK);

    }

    public ResponseEntity<String> unFollow(String myUserName, String username) {


        if (unfollowmethod(myUserName, username))
            return new ResponseEntity<>("user successfully unfollowed", HttpStatus.OK);

        if (isFollowing(myUserName, userRepository.findByUserName(username).get().getId())) {
            if (unfollowmethod(myUserName, username))
                return new ResponseEntity<>("user successfully unfollowed", HttpStatus.OK);
        }

        return new ResponseEntity<>("You are not following the user", HttpStatus.BAD_REQUEST);

    }

    private boolean unfollowmethod(String myUserName, String username) {
        Long followId;
        for (Follow follow : followRepository.findAll()) {

            if (follow.getCurrentUserFollower().getUserName().equals(myUserName)
                    && follow.getCurrentUser().getUserName().equals(username)) {
                followId = follow.getFollowId();
                followRepository.deleteById(followId);

                return true;

            }
        }
        return false;
    }


    public boolean isFollowing(String username, Long userId) {

        Optional<User> user = userRepository.findById(userId);

        for (Follow follow : followRepository.findAll()) {

            if (follow.getCurrentUserFollower().getUserName().equals(username)
                    && follow.getCurrentUser().equals(user.get())) {
                return true;
            }
        }

        return false;
    }
}