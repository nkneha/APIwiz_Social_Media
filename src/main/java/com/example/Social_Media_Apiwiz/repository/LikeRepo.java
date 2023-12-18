package com.example.Social_Media_Apiwiz.repository;

import com.example.Social_Media_Apiwiz.model.Like;
import com.example.Social_Media_Apiwiz.model.Post;
import com.example.Social_Media_Apiwiz.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikeRepo extends JpaRepository<Like,Long> {

    List<Like> findByInstaPostAndLiker(Post instaPost, User liker);

    List<Like> findByInstaPost(Post validPost);
}
