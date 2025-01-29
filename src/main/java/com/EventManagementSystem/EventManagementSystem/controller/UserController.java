package com.EventManagementSystem.EventManagementSystem.controller;


import com.EventManagementSystem.EventManagementSystem.dto.AuthRequest;
import com.EventManagementSystem.EventManagementSystem.dto.UserDTO;
import com.EventManagementSystem.EventManagementSystem.model.IdentifyUser;
import com.EventManagementSystem.EventManagementSystem.service.EmailSenderService;
import com.EventManagementSystem.EventManagementSystem.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {


    private final UserService userService;
    private final EmailSenderService emailSenderService;
    public UserController(UserService userService, EmailSenderService emailSenderService) {
        this.userService = userService;
        this.emailSenderService = emailSenderService;
    }

//    @PostMapping("/login")
//    public String login(@RequestBody AuthRequest authRequest){
//        return userService.verify(authRequest);
//    }



//    // API to create a new user
//    @PostMapping("/register-user")
//    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO) {
//        UserDTO createdUser = userService.createUser(userDTO);
//        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
//    }

    // API to get all users
    @GetMapping("/get-all-users")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    // API to delete a user
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



    //Send email Api
    @GetMapping("/sendEmail")
    public String sendEmail() {
        String subject = "Test subject";
        String body = "An event is created";
        emailSenderService.sendEmail(subject, body);
        return "Email sent successfully to all users";
    }
    
   

    @PostMapping("/{userId}/uploadImages")
    public ResponseEntity<?> uploadImages(@PathVariable Long userId,
                                          @RequestParam("citizenshipImage") MultipartFile citizenshipFile,
                                          @RequestParam("userImage") MultipartFile userFile) {
        try {
            String response = userService.uploadImageForIdentifyUser(userId, citizenshipFile, userFile);
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", response));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", e.getMessage()));
        }
    }


    @GetMapping("/{userId}/downloadImage/{imageType}")
    public ResponseEntity<?> downloadImage(@PathVariable Long userId,
                                           @PathVariable String imageType) {
        try {
            // Fetch the image path for the given userId and imageType
            String filePath = userService.getImagePathForIdentifyUser(userId, imageType);

            // Check if the file exists
            File imageFile = new File(filePath);
            if (!imageFile.exists()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Image file not found"));
            }

            // Read the image data
            byte[] imageData = Files.readAllBytes(imageFile.toPath());

            // Determine the file type dynamically based on the image file extension
            String fileType = Files.probeContentType(imageFile.toPath());

            // Return the image in the HTTP response
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.parseMediaType(fileType)) // Set the dynamic content type
                    .body(imageData);
        } catch (Exception e) {
            // Handle exceptions by returning a 500 error with the exception message
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", e.getMessage()));
        }
    }




}
