package com.moht1.webapi.service;

import com.moht1.webapi.dto.CategoryDTO;
import com.moht1.webapi.model.Category;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CategoryService {
    public List<Category> findAll();

    public Category findById(Integer id);

    public Category getById(Integer id);

    public Page<Category> getPage(int pageNo, int pageSize, String sortField, String sortDirection);

    public int getCount();

    public Boolean existsByName(String name);

    public Category addCategory(Category category);

    public void deleteCategory(Category category);

    public Category updateCategory(Category category);

    public List<CategoryDTO> findMostPurchasedCategory();
}
