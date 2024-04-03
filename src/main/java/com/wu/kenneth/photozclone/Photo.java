package com.wu.kenneth.photozclone;

import jakarta.validation.constraints.NotEmpty;

public class Photo {
    private String id;

    @NotEmpty(message = "fileName can't be empty or null!")
    private String fileName;

    // Raw data


    public Photo() {
    }

    public Photo(String id, String fileName) {
        this.id = id;
        this.fileName = fileName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
