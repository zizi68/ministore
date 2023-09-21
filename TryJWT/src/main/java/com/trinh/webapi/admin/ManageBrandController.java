package com.trinh.webapi.admin;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trinh.webapi.Exception.AppUtils;
import com.trinh.webapi.dto.BrandDTO;
import com.trinh.webapi.dto.CategoryDTO;
import com.trinh.webapi.model.Brand;
import com.trinh.webapi.service.BrandService;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/admin/brand")
public class ManageBrandController {
	
	@Autowired
	BrandService brandService;
	
	@GetMapping(value = "/most-purchased")
	public ResponseEntity<List<BrandDTO>> getMostPurchasedCategory() {
		List<BrandDTO> list = brandService.findMostPurchasedBrand();
		return ResponseEntity.ok(list);
	}
	
	@PostMapping
	public ResponseEntity<?> postBrand(@Valid @RequestBody Brand brand, BindingResult bindingResult) {
		if (brandService.existsByName(brand.getName())) {
			return AppUtils.returnJS(HttpStatus.BAD_REQUEST, "Brand name is already taken!", null);
		}
		if (bindingResult.hasErrors())
			return AppUtils.returnJS(HttpStatus.BAD_REQUEST, bindingResult.getAllErrors().get(0).getDefaultMessage(), null);
		
		brandService.addBrand(brand);
		
		return AppUtils.returnJS(HttpStatus.OK, "Add brand successfully!", brand);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> deleteBrand(@PathVariable("id") Integer id) {
		Brand brand = null;

		brand = brandService.findById(id);
		
		if(brand == null) {
			return AppUtils.returnJS(HttpStatus.BAD_REQUEST, "Brand is unavaiable", null);
		}
		
		if(brand.getProducts().size() > 0)
			return AppUtils.returnJS(HttpStatus.BAD_REQUEST, "Brand is in use", null);

		brandService.deleteBrand(brand);
		return AppUtils.returnJS(HttpStatus.OK, "Remove brand successfully!", null);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<?> putBrand(@PathVariable("id") Integer id, @Valid @RequestBody Brand newbrand, BindingResult bindingResult) {
		Brand oldBrand = null;
		oldBrand = brandService.findById(id);
		
		if(oldBrand == null)
			return AppUtils.returnJS(HttpStatus.BAD_REQUEST, "Brand is unavaiable", null);
		
		if (brandService.existsByName(newbrand.getName()) && !newbrand.getName().equals(oldBrand.getName())) {
			return AppUtils.returnJS(HttpStatus.BAD_REQUEST, "Brand name is already taken!", null);
		}
		if (bindingResult.hasErrors())
			return AppUtils.returnJS(HttpStatus.BAD_REQUEST, bindingResult.getAllErrors().get(0).getDefaultMessage(), null);
		
		newbrand.setId(id);
		brandService.updateBrand(newbrand);
		return AppUtils.returnJS(HttpStatus.OK, "Update brand successfully!", newbrand);
	}
}
