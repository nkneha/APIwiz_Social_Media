package com.example.Social_Media_Apiwiz.repository;

import com.example.Social_Media_Apiwiz.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepo extends JpaRepository<Comment,Long> {


}