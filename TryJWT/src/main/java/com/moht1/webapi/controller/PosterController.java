package com.moht1.webapi.controller;

import com.moht1.webapi.model.Poster;
import com.moht1.webapi.serviceImpl.PosterServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/posters")
public class PosterController {

    @Autowired
    private PosterServiceImpl posterService;

    @GetMapping("")
    public ResponseEntity<List<Poster>> getListPoster() {
        List<Poster> list = posterService.getListPoster();
        if (list == null) {
            list = new ArrayList<Poster>();
        }
        return ResponseEntity.ok(list);
    }

    @RequestMapping(value = "/{imageName}", method = RequestMethod.GET,
            produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<?> getImage(@PathVariable("imageName") String imageName) throws IOException {

        try {
            ClassPathResource imgFile = new ClassPathResource("images/posters/" + imageName);
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(new InputStreamResource(imgFile.getInputStream()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Image not found!");
        }
    }
}
