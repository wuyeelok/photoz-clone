package com.wu.kenneth.photozclone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DownloadController {

    private final PhotozService photozService;

    @Autowired
    public DownloadController(PhotozService photozService) {
        this.photozService = photozService;
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> download(@PathVariable String id) {
        byte[] data = new byte[0];
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(data, headers, HttpStatus.OK);
    }
}
