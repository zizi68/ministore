package com.moht1.webapi.admin;

import com.moht1.webapi.Exception.AppUtils;
import com.moht1.webapi.dto.CategoryDTO;
import com.moht1.webapi.model.Category;
import com.moht1.webapi.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/admin/category")
public class ManageCategoryController {

    @Autowired
    CategoryService categoryService;

    @GetMapping(value = "/most-purchased")
    public ResponseEntity<List<CategoryDTO>> getMostPurchasedCategory() {
        List<CategoryDTO> list = categoryService.findMostPurchasedCategory();
        return ResponseEntity.ok(list);
    }

    @PostMapping
    public ResponseEntity<?> postCategory(@Valid @RequestBody Category category, BindingResult bindingResult) {
        if (categoryService.existsByName(category.getName())) {
            return AppUtils.returnJS(HttpStatus.BAD_REQUEST, "Category name is already taken!", null);
        }
        if (bindingResult.hasErrors()) {
            return AppUtils.returnJS(HttpStatus.BAD_REQUEST, bindingResult.getAllErrors().get(0).getDefaultMessage(), null);
        }
        category = categoryService.addCategory(category);
        return AppUtils.returnJS(HttpStatus.OK, "Add category successfully!", category);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> putCategory(@PathVariable("id") Integer id, @Valid @RequestBody Category newCategory, BindingResult bindingResult) {
        Category oldCategory = null;

        oldCategory = categoryService.findById(id);
        if (oldCategory == null)
            return AppUtils.returnJS(HttpStatus.BAD_REQUEST, "Category is unavaiable", null);

        if (categoryService.existsByName(newCategory.getName()) && !newCategory.getName().equals(oldCategory.getName())) {
            return AppUtils.returnJS(HttpStatus.BAD_REQUEST, "Category name is already taken!", null);
        }
        if (bindingResult.hasErrors()) {
            return AppUtils.returnJS(HttpStatus.BAD_REQUEST, bindingResult.getAllErrors().get(0).getDefaultMessage(), null);
        }

        newCategory.setId(id);
        Category category = categoryService.updateCategory(newCategory);
        return AppUtils.returnJS(HttpStatus.OK, "Update category successfully!", category);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable("id") Integer id) {
        Category category = null;

        category = categoryService.findById(id);

        if (category == null)
            return AppUtils.returnJS(HttpStatus.BAD_REQUEST, "Category is unavaiable", null);

        if (category.getProducts().size() > 0)
            return AppUtils.returnJS(HttpStatus.BAD_REQUEST, "Category is in use", null);

        categoryService.deleteCategory(category);


        return AppUtils.returnJS(HttpStatus.OK, "Remove category successfully!", null);

    }
}
