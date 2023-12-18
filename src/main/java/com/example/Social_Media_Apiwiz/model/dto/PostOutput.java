package com.example.Social_Media_Apiwiz.model.dto;

import com.example.Social_Media_Apiwiz.model.enums.PostType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor

public class PostOutput {

    private Long id;
    private String content;
    private PostType postType;
    private String postUrl;
    private LocalDateTime createDate;
    private String postOwnerName;
}
