package com.riyasewana.api.controller;

import com.riyasewana.api.model.Services;
import com.riyasewana.api.service.VehicleWorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;

import com.riyasewana.api.common.constant.VehicleServices;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class VehicleServiceController {

    private final VehicleWorkerService vehicleWorkerService;

    @Autowired
    public VehicleServiceController(VehicleWorkerService vehicleWorkerService){
        this.vehicleWorkerService = vehicleWorkerService;
    }

    @PostMapping("/services")
    public ResponseEntity<?> insertUser(@RequestParam("title") String title,
                                        @RequestParam("subtitle") String subTitle,
                                        @RequestParam("contactNo") String contactNo,
                                        @RequestParam("description") String description,
                                        @RequestParam("location") String location,
                                        @RequestParam("provider") String serviceProvider,
                                        @RequestParam("image1")MultipartFile image1,
                                        @RequestParam("image2")MultipartFile image2
                                        )
    {
        Services services = new Services(title,subTitle,contactNo,location,description,serviceProvider,image1,image2,LocalDateTime.now(),VehicleServices.SERVICE_STATUS);
        return new ResponseEntity<>(vehicleWorkerService.insertService(services), HttpStatus.CREATED);
    }

    @GetMapping("/services")
    public ResponseEntity<?> getAllServices() {
        return new ResponseEntity<>(vehicleWorkerService.getServices(), HttpStatus.OK);
    }
}