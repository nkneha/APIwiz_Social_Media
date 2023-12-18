package com.example.Social_Media_Apiwiz.controller;

import com.example.Social_Media_Apiwiz.model.User;
import com.example.Social_Media_Apiwiz.model.dto.UserInput;
import com.example.Social_Media_Apiwiz.model.dto.UserUpdateInput;
import com.example.Social_Media_Apiwiz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/new")
    public ResponseEntity<String> createUser(@RequestBody UserInput userInput) {
        return userService.createUser(userInput);
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<User> getUserById(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PutMapping("/update")
    @PreAuthorize("hasAuthority('ROLE_USER')")

    public ResponseEntity<String> updateUser(Authentication authentication, @RequestBody UserUpdateInput updateInput) {
        // Assuming user IDs match between path variable and request body
        String username= authentication.getName();

        return userService.updateUser(username,updateInput);
    }

    @DeleteMapping("/remove/{userId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN,ROLE_USER')")
    public ResponseEntity<String> deleteUser(Authentication authentication,@PathVariable Long userId) {
        String username = authentication.getName();
        return  userService.deleteUser(userId,username);

    }

    @GetMapping("/activate/{userId}")
    public ResponseEntity<String> activate (@PathVariable Long userId) {
        return userService.Activate(userId);

    }

}
