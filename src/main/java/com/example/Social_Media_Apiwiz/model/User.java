package com.example.Social_Media_Apiwiz.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

    @Entity
    @Builder
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class User {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String name;
        @Column(unique = true)
        private String userName;
        @Column(unique = true)
        private String email;
        private String profileImage;
        private String password;
        private String role;
        private String bio;
        private boolean isAccountActivate = false;

    }

