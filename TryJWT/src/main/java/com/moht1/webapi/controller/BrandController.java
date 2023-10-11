package com.moht1.webapi.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.moht1.webapi.dto.BrandOutput;
import com.moht1.webapi.model.Brand;
import com.moht1.webapi.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.moht1.webapi.Exception.AppUtils;

@RestController
@RequestMapping("/api/brand")
public class BrandController {
	
	@Autowired
    BrandService brandService;
	
	@GetMapping(value = "/all")
	public ResponseEntity<List<Brand>> getAllBrands() {
		List<Brand> list = brandService.findAll();
		return ResponseEntity.ok(list);
	}
	
	@GetMapping("")
	public ResponseEntity<BrandOutput> findBrands(
			@RequestParam(value = "pageNo", required = false) Optional<Integer> pPageNo, 
			@RequestParam(value = "pageSize", required = false) Optional<Integer> pPageSize,
			@RequestParam(value = "sortField", required = false) Optional<String> pSortField, 
			@RequestParam(value = "sortDirection", required = false) Optional<String> pSortDir) 
	{
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
		List<Brand> brands = new ArrayList<Brand>();
		
		totalPage = (int) Math.ceil((double) (brandService.getCount()) / pageSize);
		brands = brandService.getPage(pageNo, pageSize, sortField, sortDirection).getContent();
		
		
		BrandOutput output = new BrandOutput();
		output.setPage(pageNo);
		output.setTotalPage(totalPage);
		output.setListResult(brands);
		return ResponseEntity.ok(output);
	}
	
	@GetMapping(value = { "/{id}" })
	public ResponseEntity<?> getBrandById(@PathVariable("id") Integer id) {
		Brand brand = null;

		brand = brandService.findById(id);
		
		if(brand == null) {
			return AppUtils.returnJS(HttpStatus.BAD_REQUEST, "Brand is unavaiable", null);
		}
		return ResponseEntity.ok(brand);
	}
}
