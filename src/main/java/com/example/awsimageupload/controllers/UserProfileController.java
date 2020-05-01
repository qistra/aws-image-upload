package com.example.awsimageupload.controllers;

import com.example.awsimageupload.profile.UserProfile;
import com.example.awsimageupload.services.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/user-profile")
@CrossOrigin("*")
public class UserProfileController {

    private final UserProfileService userProfileService;

    @Autowired
    public UserProfileController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @GetMapping
    public List<UserProfile> getUserProfiles() {
        return userProfileService.getUserProfiles();
    }

    @PostMapping(
            path = "/{userId}/image/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public void uploadUserProfileImage(@PathVariable("userId") UUID userId,
                                       @RequestParam("file") MultipartFile file) {
        userProfileService.uploadUserProfileImage(userId, file);
    }

    @GetMapping("/{userId}/image/download")
    public byte[] downloadUserProfileImage(@PathVariable("userId") UUID userId) {
        return userProfileService.downloadUserProfileImage(userId);
    }
}
