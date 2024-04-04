package com.wu.kenneth.photozclone;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class PhotozController {

    private Map<String, Photo> db = new HashMap<>() {{
        put("1", new Photo("1", "hello.jpg"));
    }};

    @GetMapping("/")
    public String hello() {
        return "Hello World! Getting started!";
    }

    @GetMapping("/photoz")
    public Collection<Photo> get() {
        return db.values();
    }

    @GetMapping("/photoz/{id}")
    public Photo get(@PathVariable String id) {

        Photo photo = db.get(id);
        if (photo == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return photo;
    }

    @DeleteMapping("/photoz/{id}")
    public void delete(@PathVariable String id) {

        Photo photo = db.remove(id);
        if (photo == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/photoz")
    public Photo create(@RequestPart("data") MultipartFile file) throws IOException {
        Set<String> existingFiles = db.values().stream()
                .map(Photo::getFileName)
                .collect(Collectors.toSet());
        Photo photo = new Photo();
        photo.setId(this.getNewId());
        photo.setFileName(file.getOriginalFilename());
        photo.setData(file.getBytes());

        if (existingFiles.contains(photo.getFileName())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Photo already exist! Delete photo and try again!");
        }


        db.put(photo.getId(), photo);
        return photo;
    }

    @PutMapping("/photoz")
    public Photo replace(@RequestBody @Valid Photo photo) {
        Set<String> existingFiles = db.values().stream()
                .map(Photo::getFileName)
                .collect(Collectors.toSet());
        if (!existingFiles.contains(photo.getFileName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Photo you wanted to update does not exist in record!");
        }


        return db.values().stream()
                .filter(p -> p.getFileName().equals(photo.getFileName()))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
    }

    private String getNewId() {
        String newId;

        do {
            newId = UUID.randomUUID().toString();
        } while (this.db.containsKey(newId));

        return newId;

//        int currentMaxID = db.values().stream()
//                .map(Photo::getId)
//                .mapToInt(Integer::valueOf)
//                .max().orElse(0);
//        return String.valueOf(currentMaxID + 1);
    }
}
