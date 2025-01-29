package com.EventManagementSystem.EventManagementSystem.service.serviceImpl;

import com.EventManagementSystem.EventManagementSystem.dto.AuthRequest;
import com.EventManagementSystem.EventManagementSystem.dto.UserDTO;
import com.EventManagementSystem.EventManagementSystem.exception.UserNotFoundException;
import com.EventManagementSystem.EventManagementSystem.mapper.UserMapper;
import com.EventManagementSystem.EventManagementSystem.model.IdentifyUser;
import com.EventManagementSystem.EventManagementSystem.model.User;
import com.EventManagementSystem.EventManagementSystem.repository.IdentifyRepository;
import com.EventManagementSystem.EventManagementSystem.repository.UserRepository;
import com.EventManagementSystem.EventManagementSystem.service.UserService;



import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationManager authManager;
    private final UserRepository userRepository;
    private final IdentifyRepository identifyRepository;
    
    public UserServiceImpl(UserRepository userRepository, AuthenticationManager authManager, IdentifyRepository identifyRepository) {
        this.userRepository = userRepository;
        this.authManager = authManager;
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
      this.identifyRepository = identifyRepository;
    }

    @Override
    public void createUser(UserDTO userDTO) {
        User user = UserMapper.INSTANCE.convertDtoToEntity(userDTO);
        user.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
        user.setRole("ROLE_USER");
		//User savedUser = userRepository.save(user);
        userRepository.save(user);
    }

    @Override
    public List<UserDTO> getAllUsers() {

        List<User> users = userRepository.findAll();

        return users.stream().map(UserMapper.INSTANCE::convertEntityToDto).collect(Collectors.toList());
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    private static final String FOLDER_PATH = "D:\\projectimage\\";

    public String uploadImageForIdentifyUser(Long userId, MultipartFile citizenshipFile, MultipartFile userFile) throws IOException {
        // Ensure the directory exists
        File directory = new File(FOLDER_PATH);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // Fetch User entity
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found for userId: " + userId));

        // Create a new IdentifyUser entity
        IdentifyUser identifyUser = new IdentifyUser();
        identifyUser.setUser(user); // Set the User entity for the IdentifyUser

        try {
            // Handle citizenship image
            String citizenshipFileName = UUID.randomUUID() + "_" + Paths.get(citizenshipFile.getOriginalFilename()).getFileName().toString();
            String citizenshipFilePath = FOLDER_PATH + citizenshipFileName;
            citizenshipFile.transferTo(new File(citizenshipFilePath));
            identifyUser.setCitizenshipImage(citizenshipFilePath); // Set citizenship image path

            // Handle user image
            String userFileName = UUID.randomUUID() + "_" + Paths.get(userFile.getOriginalFilename()).getFileName().toString();
            String userFilePath = FOLDER_PATH + userFileName;
            userFile.transferTo(new File(userFilePath));
            identifyUser.setUserImage(userFilePath); // Set user image path

            // Save the newly created IdentifyUser entity
            identifyRepository.save(identifyUser);

        } catch (IOException e) {
            throw new RuntimeException("Error saving images for IdentifyUser with ID: " + userId, e);
        }

        return "Images uploaded successfully for IdentifyUser with ID: " + userId;
    }

    public byte[] downloadImageForIdentifyUser(Long userId, String imageType) throws IOException {
        // Check if the user exists in the User table
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found for userId: " + userId));

        // Fetch IdentifyUser entity linked with the existing userId
        IdentifyUser identifyUser = (IdentifyUser) identifyRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("IdentifyUser not found for userId: " + userId));

        String filePath = null;

        // Determine the file path based on the image type
        if ("citizenship".equalsIgnoreCase(imageType)) {
            filePath = identifyUser.getCitizenshipImage();
        } else if ("user".equalsIgnoreCase(imageType)) {
            filePath = identifyUser.getUserImage();
        } else {
            throw new IllegalArgumentException("Invalid image type: " + imageType);
        }

        // Ensure file path is not null or empty
        if (filePath == null || filePath.isEmpty()) {
            throw new RuntimeException("No image found for type: " + imageType + " for IdentifyUser with ID: " + userId);
        }

        // Check if the file exists
        File imageFile = new File(filePath);
        if (!imageFile.exists()) {
            throw new RuntimeException("Image file does not exist at path: " + filePath);
        }

        // Read and return the file data
        try {
            return Files.readAllBytes(imageFile.toPath());
        } catch (IOException e) {
            throw new RuntimeException("Error reading image file for IdentifyUser with ID: " + userId + " and type: " + imageType, e);
        }
    }

    public String getImagePathForIdentifyUser(Long userId, String imageType) {
        // Check if the user exists in the User table
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found for userId: " + userId));

        // Fetch IdentifyUser entity linked with the existing userId
        IdentifyUser identifyUser = (IdentifyUser) identifyRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("IdentifyUser not found for userId: " + userId));

        // Determine the file path based on the image type
        if ("citizenship".equalsIgnoreCase(imageType)) {
            return identifyUser.getCitizenshipImage();
        } else if ("user".equalsIgnoreCase(imageType)) {
            return identifyUser.getUserImage();
        } else {
            throw new IllegalArgumentException("Invalid image type: " + imageType);
        }
    }

	@Override
	public String getUserEmailByUsername(String userName) {
	    // Use your repository to find the user by username and return their email
	    User user = userRepository.findByUserName(userName);
	    return user.getEmail(); // Assuming 'getEmail()' retrieves the email from the user object
	}


}
