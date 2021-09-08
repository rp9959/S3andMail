package com.s3mail.s3.controller;


import com.s3mail.s3.service.EmailService;
import com.s3mail.s3.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file")
public class StorageController {

    @Autowired
    private StorageService service;
    
    @Autowired
    private EmailService emailservice;

    @PostMapping("/uploadandmail/{username}")
    public ResponseEntity<String> uploadFile(@RequestParam(value = "file") MultipartFile file,@PathVariable String username)
    {
    	String url = service.uploadFile(file,username);
    	
    	emailservice.sendmail(username,url);
    	
    	
        return new ResponseEntity<>( HttpStatus.OK);
    }
    



    @GetMapping("/download/{fileName}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable String fileName) {
        byte[] data = service.downloadFile(fileName);
        ByteArrayResource resource = new ByteArrayResource(data);
        return ResponseEntity
                .ok()
                .contentLength(data.length)
                .header("Content-type", "application/octet-stream")
                .header("Content-disposition", "attachment; filename=\"" + fileName + "\"")
                .body(resource);
    }

    @DeleteMapping("/delete/{fileName}")
    public ResponseEntity<String> deleteFile(@PathVariable String fileName) {
        return new ResponseEntity<>(service.deleteFile(fileName), HttpStatus.OK);
    }
}
