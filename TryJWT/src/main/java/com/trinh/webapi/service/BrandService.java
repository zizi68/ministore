package com.trinh.webapi.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.trinh.webapi.dto.BrandDTO;
import com.trinh.webapi.dto.CategoryDTO;
import com.trinh.webapi.model.Brand;

public interface BrandService {
	public List<Brand> findAll();
	public Brand findById(Integer id);
	public Page<Brand> getPage(int pageNo, int pageSize, String sortField, String sortDirection);
	public int getCount();
	public Boolean existsByName(String name);
	public void addBrand(Brand brand);
	public void deleteBrand(Brand brand);
	public void updateBrand(Brand brand);
	public List<BrandDTO> findMostPurchasedBrand();
}
