package com.example.awsimageupload.bucket;

public enum BucketName {

    PROFILE_IMAGE("aws-image-upload-123456");

    private final String bucketName;

    BucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getBucketName() {
        return this.bucketName;
    }
}
