package com.riyasewana.api.controller;

import com.riyasewana.api.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin(origins = "*")
public class TempController {

    @Autowired
    private FileService service;

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        service.uploadFile(file, "TestFile");
        return "File Uploaded Successfully";
    }

    @GetMapping("/download/{file}")
    public ResponseEntity<ByteArrayResource> download(@PathVariable String file) {
        byte[] data = service.downloadFile(file);
        ByteArrayResource resource = new ByteArrayResource(data);
        return ResponseEntity.ok().contentLength(data.length).header("Content-type", "application/octet-stream")
                .header("Content-disposition", "attachment; filename=\""+file+"\"").body(resource);
    }

    @DeleteMapping("/delete/{file}")
    public void delete(@PathVariable String file) {
        service.deleteFile(file);
    }

    @GetMapping("/filenames")
    public ResponseEntity<?> getFileNames() {
        return new ResponseEntity<>(service.getFileNames(), HttpStatus.OK);
    }

}
