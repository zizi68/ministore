package com.moht1.webapi.service;

import java.util.List;

import com.moht1.webapi.dto.BrandDTO;
import com.moht1.webapi.model.Brand;
import org.springframework.data.domain.Page;

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
