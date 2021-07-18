package com.riyasewana.api.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.utils.IoUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.riyasewana.api.common.util.AWSUtil.getS3Client;
import static com.riyasewana.api.common.constant.AWS.*;

@Log4j2
@Service
public class FileService {
    public boolean uploadFile(MultipartFile mFile, String fileName) {
        File file = convertMultipartFileToFile(mFile);
        getS3Client().putObject(PutObjectRequest.builder().bucket(IMAGE_BUCKET).key(fileName).build(), RequestBody.fromFile(file));
        file.delete();
        return true;
    }

    public byte[] downloadFile(String fileName) {
        GetObjectRequest getObjectRequest = null;
        getObjectRequest = GetObjectRequest.builder().bucket(IMAGE_BUCKET).key(fileName).build();
        ResponseInputStream inputStream = getS3Client().getObject(getObjectRequest);
        try {
            byte[] content = IoUtils.toByteArray(inputStream);
            return content;
        } catch (IOException e) {
            log.error("Error while converting to ByteArray: ", e);
            e.printStackTrace();
        }
        return null;
    }

    public String deleteFile(String fileName) {
        DeleteObjectRequest deleteObjectRequest = null;
        deleteObjectRequest = DeleteObjectRequest.builder().bucket(IMAGE_BUCKET).key(fileName).build();
        DeleteObjectResponse deleteObjectResponse = getS3Client().deleteObject(deleteObjectRequest);
        return fileName + " deleted";
    }

    public List<String> getFileNames() {
        List<String> fileNames = new ArrayList<>();
        List<S3Object> s3Objects = new ArrayList<>();
        ListObjectsV2Request request = null;
        request = ListObjectsV2Request.builder().bucket(IMAGE_BUCKET).build();
        ListObjectsV2Response result = getS3Client().listObjectsV2(request);
        s3Objects = result.contents();
        for (S3Object object : s3Objects) {
            fileNames.add(object.key());
        }
        return fileNames;
    }

    private File convertMultipartFileToFile(MultipartFile mFile) {
        File convertedFile = new File(mFile.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convertedFile)){
            fos.write(mFile.getBytes());
        } catch (IOException e) {
            log.error("Error converting MultipartFile to File: ", e);
        }
        return convertedFile;
    }
}
