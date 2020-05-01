package com.example.awsimageupload.services;

import com.example.awsimageupload.profile.UserProfile;
import com.example.awsimageupload.repositories.UserProfileRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;

    @Autowired
    public UserProfileService(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    public List<UserProfile> getUserProfiles() {
        return userProfileRepository.getUserProfiles();
    }

    public void uploadUserProfileImage(UUID userId, MultipartFile file) {

        if (file.isEmpty()) {
            throw new IllegalStateException("Cannot upload an empty file " + file.getName());
        }

        if (!file.getContentType().startsWith("image/")) {
            throw new IllegalStateException("File must be an image");
        }

        // check if user exists in DB
        UserProfile user = getUserProfileOrThrow(userId);

        userProfileRepository.uploadUserProfileImage(user, file);
    }

    public byte[] downloadUserProfileImage(UUID userId) {
        UserProfile user = getUserProfileOrThrow(userId);
        return userProfileRepository.downloadUserProfileImage(user);
    }

    private UserProfile getUserProfileOrThrow(UUID userId) {
        return getUserProfiles()
                .stream()
                .filter(userProfile -> userProfile.getUserId().equals(userId))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(String.format("User profile %s not found", userId)));

    }
}
