package com.wu.kenneth.photozclone;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
    public Photo create(@RequestBody @Valid Photo photo) {
        Set<String> existingFiles = db.values().stream()
                .map(Photo::getFileName)
                .collect(Collectors.toSet());
        if (existingFiles.contains(photo.getFileName())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Photo already exist! Delete photo and try again!");
        }

        String newId = this.getNewId();
        photo.setId(newId);
        db.put(newId, photo);
        return photo;
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
