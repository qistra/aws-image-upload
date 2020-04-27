package com.example.awsimageupload.datastore;

import com.example.awsimageupload.profile.UserProfile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class FakeUserProfileDataStore {
    private static final List<UserProfile> USER_PROFILES = new ArrayList<>();

    static {
        USER_PROFILES.add(new UserProfile(UUID.fromString("102c52c8-88e8-4bc0-b2a3-ff9b14fa836e"),
                "jess.smith", null));
        USER_PROFILES.add(new UserProfile(UUID.fromString("7754d3db-08b9-452e-92d0-2268e95d6be6"),
                "eric.napret", null));
    }

    public List<UserProfile> getUserProfiles() {
        return USER_PROFILES;
    }
}
