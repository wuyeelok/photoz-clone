package com.wu.kenneth.photozclone;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PhotozController {
    @GetMapping("/")
    public String hello() {
        return "Hello World! Getting started!";
    }
}
