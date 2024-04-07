package com.wu.kenneth.photozclone.service;

import com.wu.kenneth.photozclone.model.Photo;
import com.wu.kenneth.photozclone.repository.PhotozRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class PhotozService {
    private final PhotozRepository photozRepository;

    public PhotozService(PhotozRepository photozRepository) {
        this.photozRepository = photozRepository;
    }

    public Iterable<Photo> get() {
        return photozRepository.findAll();
    }

    public Photo get(Integer id) {
        return photozRepository.findById(id).orElse(null);
    }

    public void remove(Integer id) {
        photozRepository.deleteById(id);
    }

    public Photo save(String fileName, String contentType, byte[] data) {
        Photo photo = new Photo();
        // photo.setId(this.getNewId());
        photo.setFileName(fileName);
        photo.setContentType(contentType);
        photo.setData(data);

        photozRepository.save(photo);
        return photo;
    }

}




