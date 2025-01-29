package com.EventManagementSystem.EventManagementSystem.service;

import com.EventManagementSystem.EventManagementSystem.dto.AuthRequest;
import com.EventManagementSystem.EventManagementSystem.dto.UserDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public interface UserService {
	
    void createUser(UserDTO userDTO);

    List<UserDTO> getAllUsers();

    void deleteUser(Long id);


    String uploadImageForIdentifyUser(Long userId, MultipartFile citizenshipFile, MultipartFile userFile) throws IOException;


    byte[] downloadImageForIdentifyUser(Long userId, String imageType) throws IOException;

    String getImagePathForIdentifyUser(Long userId, String imageType);

	String getUserEmailByUsername(String loggedInUsername);

}
