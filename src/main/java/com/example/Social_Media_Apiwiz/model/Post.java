package com.example.Social_Media_Apiwiz.model;

import com.example.Social_Media_Apiwiz.model.enums.PostType;
import com.example.Social_Media_Apiwiz.model.enums.Privacy;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content; // Caption
    private PostType postType;
    private String postUrl; // text, URL of the image , video (you can use S3 for actual storage)
    private Privacy privacy; // "public" or "private"
    private Long shareCount=0L;

    @CreationTimestamp
    private LocalDateTime createDate; // creation time stamp

    @ManyToOne
    @JoinColumn(name = "post_user_id")
    private User user;

}

