package com.wu.kenneth.photozclone;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PhotozService {
    private Map<String, Photo> db = new HashMap<>() {{
        put("1", new Photo("1", "hello.jpg"));
    }};

    public Collection<Photo> get() {
        return db.values();
    }

    public Photo get(String id) {
        return db.get(id);
    }

    public Photo remove(String id) {
        return db.remove(id);
    }

    public Photo save(String fileName, byte[] data) {
        Photo photo = new Photo();
        photo.setId(this.getNewId());
        photo.setFileName(fileName);
        photo.setData(data);

        db.put(photo.getId(), photo);
        return photo;
    }

    private Set<String> getIds() {
        return db.keySet();
    }

    private String getNewId() {
        String newId;

        do {
            newId = UUID.randomUUID().toString();
        } while (this.getIds().contains(newId));

        return newId;

//        int currentMaxID = db.values().stream()
//                .map(Photo::getId)
//                .mapToInt(Integer::valueOf)
//                .max().orElse(0);
//        return String.valueOf(currentMaxID + 1);
    }
}




