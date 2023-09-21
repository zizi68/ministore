package com.trinh.webapi.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trinh.webapi.Exception.AppUtils;
import com.trinh.webapi.dto.FullProduct;
import com.trinh.webapi.dto.ProductOutput;
import com.trinh.webapi.model.Product;
import com.trinh.webapi.service.BrandService;
import com.trinh.webapi.service.CategoryService;
import com.trinh.webapi.service.PriceHistoryService;
import com.trinh.webapi.service.ProductService;
import com.trinh.webapi.service.PromotionService;

import io.swagger.annotations.ApiOperation;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/product")
public class ProductController {

	@Autowired
	ProductService productService;

	@Autowired
	CategoryService categoryService;

	@Autowired
	BrandService brandService;
	
	@Autowired
	PriceHistoryService priceHistoryService;
	
	@Autowired
	PromotionService promotionService;

//	@GetMapping(value = { "/{pageNo}", "/" })
//	public ResponseEntity<ProductOutput> getProductInOnePage(@PathVariable(required = false) Optional<Integer> pageNo) {
//		int pageNoOffical = 1;
//		if (pageNo.isPresent())
//			pageNoOffical = pageNo.get();
//		String sortDir = "desc";
//		String sortField = "productId";
//		int pageSize = 20;
//		List<Product> products = productService.findPaginated(pageNoOffical, pageSize, sortField, sortDir).getContent();
//
//		ProductOutput output = new ProductOutput();
//		output.setPage(pageNoOffical);
//		output.setTotalPage((int) Math.ceil((double) (productService.totalItem()) / pageSize));
//		output.setListResult(products);
//		return ResponseEntity.ok(output);
//	}

	@GetMapping(value = { "/{id}" })
	public ResponseEntity<?> getProductById(@PathVariable("id") Integer id) {
		Product product = null;
		
		product = productService.findById(id);
		
		if(product == null) {
			return AppUtils.returnJS(HttpStatus.BAD_REQUEST, "Product is unavaiable", product);
		}
		
		FullProduct fullProduct = new FullProduct(product, priceHistoryService.getLatestPrice(product), promotionService.getCurrentPromotionByProduct(product));
		return ResponseEntity.ok(fullProduct);
	}
	
	@GetMapping(value = { "/relate/{id}" })
	public ResponseEntity<?> getRelatedProducts(@PathVariable("id") Integer id) {
		Product product = null;
		
		product = productService.findById(id);
		
		if(product == null) {
			return AppUtils.returnJS(HttpStatus.BAD_REQUEST, "Product is unavaiable", null);
		}
		
		List<Product> products = productService.getAllByCategory(product.getCategory().getId());
		
		List<FullProduct> fullProducts = new ArrayList<>();
		for(Product p : products) 
			fullProducts.add(new FullProduct(p, priceHistoryService.getLatestPrice(p), promotionService.getCurrentPromotionByProduct(p)));

		return ResponseEntity.ok(fullProducts);
	}

	@RequestMapping(value = "image/{imageName}", method = RequestMethod.GET, produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_GIF_VALUE})
	public ResponseEntity<?> getImage(@PathVariable("imageName") String imageName) throws IOException {

		try {
			ClassPathResource imgFile = new ClassPathResource("images/products/" + imageName);
			return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG)
					.body(new InputStreamResource(imgFile.getInputStream()));
		} catch (Exception e) {
			return AppUtils.returnJS(HttpStatus.BAD_REQUEST, "Image not found", null);
		}
	}

	@GetMapping("")
	public ResponseEntity<ProductOutput> findProducts(
			@RequestParam(value = "pageNo", required = false) Optional<Integer> pPageNo,
			@RequestParam(value = "pageSize", required = false) Optional<Integer> pPageSize,
			@RequestParam(value = "sortField", required = false) Optional<String> pSortField,
			@RequestParam(value = "sortDirection", required = false) Optional<String> pSortDir,
			@RequestParam(value = "categoryId", required = false) Optional<Integer> pCategoryId) {
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
		List<Product> products = new ArrayList<Product>();
		if (pCategoryId.isPresent()) {
			Integer categoryId = pCategoryId.get();
			totalPage = (int) Math.ceil((double) (productService.getCountByCategoryId(categoryId)) / pageSize);
			products = productService.getAllByCategoryId(categoryId, pageNo, pageSize, sortField, sortDirection);
		} else {
			totalPage = (int) Math.ceil((double) (productService.getCount()) / pageSize);
			products = productService.getAllByStatus(true, pageNo, pageSize, sortField, sortDirection);
		}

		ProductOutput output = new ProductOutput();
		output.setPage(pageNo);
		output.setTotalPage(totalPage);
		
		List<FullProduct> fullProducts = new ArrayList<>();
		for(Product p : products) {
			if(p.getQuantity() > p.getSoldQuantity())
				fullProducts.add(new FullProduct(p, priceHistoryService.getLatestPrice(p), promotionService.getCurrentPromotionByProduct(p)));
		}

		output.setListResult(fullProducts);
		return ResponseEntity.ok(output);
	}
	
	@ApiOperation(value="Lấy tất cả danh sách sản phẩm")
    @GetMapping("/all")
    public ResponseEntity<List<FullProduct>> getAllProduct(){
		List<Product> products = productService.getAllProduct();
		
		List<FullProduct> fullProducts = new ArrayList<>();
		for(Product p : products) 
			fullProducts.add(new FullProduct(p, priceHistoryService.getLatestPrice(p), promotionService.getCurrentPromotionByProduct(p)));

        return ResponseEntity.ok(fullProducts);
    }
	
	@GetMapping("/search")
    public ResponseEntity<List<FullProduct>> searchProduct(@RequestParam("keyword") String keyword){
		List<Product> products = productService.search(keyword);
		
		List<FullProduct> fullProducts = new ArrayList<>();
		for(Product p : products) 
			fullProducts.add(new FullProduct(p, priceHistoryService.getLatestPrice(p), promotionService.getCurrentPromotionByProduct(p)));

        return ResponseEntity.ok(fullProducts);
    }
}
