package com.example.Social_Media_Apiwiz.repository;

import com.example.Social_Media_Apiwiz.model.Friendship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendshipRepo extends JpaRepository<Friendship,Long> {
}