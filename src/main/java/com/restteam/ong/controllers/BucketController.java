package com.restteam.ong.controllers;

import com.restteam.ong.controllers.dto.FileUploadResponse;
import com.restteam.ong.services.AmazonS3ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import java.io.FileNotFoundException;

@RestController
@RequestMapping("/storage/")
public class BucketController {

    private AmazonS3ClientService amazonClient;

    @Autowired
    BucketController(AmazonS3ClientService amazonClient) {
        this.amazonClient = amazonClient;
    }

    @PostMapping("/uploadFile")
    public ResponseEntity<?> uploadFile(@RequestPart(value = "file") MultipartFile file) {
        try{
            String fileUrl = this.amazonClient.uploadFile(file);
            return ResponseEntity.ok(new FileUploadResponse(fileUrl));
        }catch (FileNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }

    @DeleteMapping("/deleteFile")
    public ResponseEntity<?> deleteFile(@RequestPart(value = "url") String fileUrl) {
        try {
            return ResponseEntity.ok(this.amazonClient.deleteFileFromS3Bucket(fileUrl));
        } catch (FileNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}