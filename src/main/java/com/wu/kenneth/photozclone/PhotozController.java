package com.wu.kenneth.photozclone;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class PhotozController {

    private final PhotozService photozService;

    @Autowired
    public PhotozController(PhotozService photozService) {
        this.photozService = photozService;
    }

    @GetMapping("/")
    public String hello() {
        return "Hello World! Getting started!";
    }

    @GetMapping("/photoz")
    public Collection<Photo> get() {
        return photozService.get();
    }

    @GetMapping("/photoz/{id}")
    public Photo get(@PathVariable String id) {

        Photo photo = photozService.get(id);
        if (photo == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return photo;
    }

    @DeleteMapping("/photoz/{id}")
    public void delete(@PathVariable String id) {

        Photo photo = photozService.remove(id);
        if (photo == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/photoz")
    public Photo create(@RequestPart("data") MultipartFile file) throws IOException {
        Set<String> existingFiles = photozService.get().stream()
                .map(Photo::getFileName)
                .collect(Collectors.toSet());


        if (existingFiles.contains(file.getOriginalFilename())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Photo already exist! Delete photo and try again!");
        }

        return photozService.save(file.getOriginalFilename(), file.getContentType(), file.getBytes());
    }

    @PutMapping("/photoz")
    public Photo replace(@RequestBody @Valid Photo photo) {
        Set<String> existingFiles = photozService.get().stream()
                .map(Photo::getFileName)
                .collect(Collectors.toSet());
        if (!existingFiles.contains(photo.getFileName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Photo you wanted to update does not exist in record!");
        }


        return photozService.get().stream()
                .filter(p -> p.getFileName().equals(photo.getFileName()))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
    }


}
