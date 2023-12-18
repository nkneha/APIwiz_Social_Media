package com.example.Social_Media_Apiwiz.repository;

import com.example.Social_Media_Apiwiz.model.Follow;
import com.example.Social_Media_Apiwiz.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowRepo extends JpaRepository<Follow,Long> {
    List<Follow> findByCurrentUserAndCurrentUserFollower(User targetUser, User follower);
}
