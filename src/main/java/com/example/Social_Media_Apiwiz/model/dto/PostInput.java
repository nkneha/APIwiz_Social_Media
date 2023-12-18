package com.example.Social_Media_Apiwiz.model.dto;

import com.example.Social_Media_Apiwiz.model.enums.PostType;
import com.example.Social_Media_Apiwiz.model.enums.Privacy;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostInput {

    private String content;
    private PostType postType;
    private String postUrl;
    private Privacy privacy; // "public" or "private"


}