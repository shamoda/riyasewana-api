package com.riyasewana.api.model;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Document
public class Services {

    private String title;
    private String subTitle;
    private String contactNo;
    private String location;
    private String description;
    private String serviceProvider;
    private MultipartFile image1;
    private MultipartFile image2;
    private LocalDateTime currentTime;
    private String status;
}
