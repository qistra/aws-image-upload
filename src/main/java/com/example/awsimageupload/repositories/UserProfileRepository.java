package com.example.awsimageupload.repositories;

import com.example.awsimageupload.aws.bucket.BucketName;
import com.example.awsimageupload.aws.filestore.FileStore;
import com.example.awsimageupload.datastore.FakeUserProfileDataStore;
import com.example.awsimageupload.profile.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Repository
public class UserProfileRepository {

    private final FakeUserProfileDataStore fakeUserProfileDataStore;
    private final FileStore fileStore;

    @Autowired
    public UserProfileRepository(FakeUserProfileDataStore fakeUserProfileDataStore, FileStore fileStore) {
        this.fakeUserProfileDataStore = fakeUserProfileDataStore;
        this.fileStore = fileStore;
    }

    public List<UserProfile> getUserProfiles() {
        return fakeUserProfileDataStore.getUserProfiles();
    }

    public void uploadUserProfileImage(UserProfile userProfile, MultipartFile file) {
        String filePath = String.format("%s/%s", BucketName.PROFILE_IMAGE, userProfile.getUserId());
        String fileName = String.format("%s-%s", file.getName(), UUID.randomUUID());
        Map<String, String> metadata = new HashMap<>();
        metadata.put("Content-Type", file.getContentType());
        metadata.put("Content-Length", String.valueOf(file.getSize()));
        try {
            fileStore.save(filePath, fileName, Optional.of(metadata), file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
