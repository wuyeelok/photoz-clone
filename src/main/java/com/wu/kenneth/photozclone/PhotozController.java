package com.wu.kenneth.photozclone;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

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

    @PostMapping("/photoz/{fileName}")
    public Photo newPhoto(@PathVariable(value = "fileName") String fName) {
        String newId = this.getNewId();
        Photo photo = new Photo(newId, fName);
        db.put(newId, photo);
        return photo;
    }

    private String getNewId() {
        int currentMaxID = db.values().stream()
                .map(Photo::getId)
                .mapToInt(Integer::valueOf)
                .max().orElse(0);
        return String.valueOf(currentMaxID + 1);
    }
}
