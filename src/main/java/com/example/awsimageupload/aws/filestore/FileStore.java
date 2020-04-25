package com.example.awsimageupload.aws.filestore;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Map;
import java.util.Optional;

@Service
public class FileStore {

    private final AmazonS3 amazonS3;

    @Autowired
    public FileStore(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    public void save(String filePath,
                     String fileName,
                     Optional<Map<String, String>> optionalMetadata,
                     InputStream inputStream) {

        ObjectMetadata metadata = new ObjectMetadata();

        optionalMetadata.ifPresent(map -> {
            if(!map.isEmpty()) {
                map.forEach(metadata::addUserMetadata);
            }
        });

        try{
            amazonS3.putObject(filePath, fileName, inputStream, metadata);
        }catch(AmazonServiceException e) {
            throw new IllegalStateException("Failed to store file to S3. ", e);
        }
    }
}
