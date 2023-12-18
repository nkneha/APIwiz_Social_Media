package com.example.Social_Media_Apiwiz.repository;

import com.example.Social_Media_Apiwiz.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepo extends JpaRepository<Post,Long> {
    List<Post> findByUserId(Long userId);

    List<Post> findFeedByUserId(Long userId);
}
