package com.moht1.webapi.controller;

import com.moht1.webapi.Exception.AppUtils;
import com.moht1.webapi.dto.CategoryOutput;
import com.moht1.webapi.model.Category;
import com.moht1.webapi.service.CategoryService;
import com.moht1.webapi.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @GetMapping(value = "/all")
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> list = categoryService.findAll();
        return ResponseEntity.ok(list);
    }

    @RequestMapping(value = "image/{imageName}", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<?> getImage(@PathVariable("imageName") String imageName) throws IOException {

        try {
            ClassPathResource imgFile = new ClassPathResource("images/categories/" + imageName);
            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG)
                    .body(new InputStreamResource(imgFile.getInputStream()));
        } catch (Exception e) {
            return AppUtils.returnJS(HttpStatus.BAD_REQUEST, "Image not found", null);
        }
    }

    @GetMapping
    public ResponseEntity<CategoryOutput> findCategories(
            @RequestParam(value = "pageNo", required = false) Optional<Integer> pPageNo,
            @RequestParam(value = "pageSize", required = false) Optional<Integer> pPageSize,
            @RequestParam(value = "sortField", required = false) Optional<String> pSortField,
            @RequestParam(value = "sortDirection", required = false) Optional<String> pSortDir) {
        int pageNo = 1;
        int pageSize = 10;
        String sortField = "id";
        String sortDirection = "ASC";
        if (pPageNo.isPresent()) {
            pageNo = pPageNo.get();
        }
        if (pPageSize.isPresent()) {
            pageSize = pPageSize.get();
        }
        if (pSortField.isPresent()) {
            sortField = pSortField.get();
        }
        if (pSortDir.isPresent()) {
            sortDirection = pSortDir.get();
        }

        int totalPage;
        List<Category> categories = new ArrayList<Category>();

        totalPage = (int) Math.ceil((double) (categoryService.getCount()) / pageSize);
        categories = categoryService.getPage(pageNo, pageSize, sortField, sortDirection).getContent();

        CategoryOutput output = new CategoryOutput();
        output.setPage(pageNo);
        output.setTotalPage(totalPage);
        output.setListResult(categories);
        return ResponseEntity.ok(output);
    }

    @GetMapping(value = {"/{id}"})
    public ResponseEntity<?> getCategoryById(@PathVariable("id") Integer id) {
        Category category = null;

        category = categoryService.findById(id);

        if (category == null)
            return AppUtils.returnJS(HttpStatus.BAD_REQUEST, Constants.CATEGORY_404.getMessage(), null);

        return ResponseEntity.ok(category);
    }
}
