package com.moht1.webapi.admin;

import com.moht1.webapi.Exception.AppUtils;
import com.moht1.webapi.model.Poster;
import com.moht1.webapi.service.PosterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/admin/poster")
public class ManagePosterController {

    @Autowired
    private PosterService posterService;

    @PostMapping
    public ResponseEntity<?> postPoster(@Valid @RequestBody Poster poster) {
        poster = posterService.savePoster(poster);
        return AppUtils.returnJS(HttpStatus.OK, "Add poster successfully!", poster);
    }

    @PutMapping(value = "")
    public ResponseEntity<?> putPoster(@Valid @RequestBody Poster poster) {
        poster = posterService.savePoster(poster);
        return AppUtils.returnJS(HttpStatus.OK, "Update poster successfully!", poster);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable("id") Integer id) {
        Poster poster = posterService.getPosterById(id);
        if (poster == null)
            return AppUtils.returnJS(HttpStatus.BAD_REQUEST, "Poster not found!", null);

        posterService.deletePoster(id);

        return AppUtils.returnJS(HttpStatus.OK, "Remove poster successfully!", null);

    }
}
