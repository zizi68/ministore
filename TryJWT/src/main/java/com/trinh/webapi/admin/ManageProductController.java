package com.trinh.webapi.admin;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.trinh.webapi.Exception.AppUtils;
import com.trinh.webapi.dto.FullProduct;
import com.trinh.webapi.dto.ProductOutput;
import com.trinh.webapi.model.Brand;
import com.trinh.webapi.model.Category;
import com.trinh.webapi.model.ImportDetail;
import com.trinh.webapi.model.PriceHistory;
import com.trinh.webapi.model.Product;
import com.trinh.webapi.model.User;
import com.trinh.webapi.service.BrandService;
import com.trinh.webapi.service.CategoryService;
import com.trinh.webapi.service.PriceHistoryService;
import com.trinh.webapi.service.ProductService;
import com.trinh.webapi.service.PromotionService;
import com.trinh.webapi.service.UserService;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/admin/product")
public class ManageProductController {
	
	@Autowired
	ProductService productService;
	
	@Autowired
	UserService userService;

	@Autowired
	CategoryService categoryService;
	
	@Autowired
	PromotionService promotionService;

	@Autowired
	BrandService brandService;
	
	@Autowired
	PriceHistoryService priceHistoryService;
	
	@GetMapping("/lasted-import-price/{id}")
	public ResponseEntity<?> getLastedImportPrice(@PathVariable("id") Integer id){
		List<ImportDetail> details = productService.getImportPrice(id);
		if(details.size() != 0)
			return AppUtils.returnJS(HttpStatus.OK, details.get(0).getPrice() + "", null);
		else
			return AppUtils.returnJS(HttpStatus.OK, "0.0", null);
	}
	
	@GetMapping(value = { "import-history/{productId}" })
	public ResponseEntity<?> getImportHistory(@PathVariable("productId") Integer productId) {
		
		return ResponseEntity.ok(productService.getImportPrice(productId));
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
			fullProducts.add(new FullProduct(p, priceHistoryService.getLatestPrice(p), promotionService.getCurrentPromotionByProduct(p)));
		}

		output.setListResult(fullProducts);
		return ResponseEntity.ok(output);
	}
	
	@PostMapping(value = "/{price}")
	public ResponseEntity<?> postProduct(Principal principal, @PathVariable("price") Float price, @Valid @RequestBody Product product, BindingResult bindingResult) {
		if (productService.existsByName(product.getName())) {
			return AppUtils.returnJS(HttpStatus.BAD_REQUEST, "Product name is already taken!", null);
		}
		
		if (bindingResult.hasErrors())
			return AppUtils.returnJS(HttpStatus.BAD_REQUEST, bindingResult.getAllErrors().get(0).getDefaultMessage(), null);
		
		if(price <= 0.0)
			return AppUtils.returnJS(HttpStatus.BAD_REQUEST, "Price should be greater than 0!", null); 

		productService.addProduct(product);
		
		PriceHistory history = new PriceHistory();
		history.setPrice(price);
		history.setProduct(product);
		history.setUser(userService.getUserByUsername(principal.getName()));
		history.setDate(new Date());
		priceHistoryService.addPriceHistory(history);
		
		return AppUtils.returnJS(HttpStatus.OK, "Add product successfully!", product);
	}
	
	@PostMapping(value = "/test")
	public ResponseEntity<?> postTestProduct(@Valid @RequestBody Product product, @RequestParam("file") MultipartFile file, BindingResult bindingResult) {
		if (productService.existsByName(product.getName())) {
			return AppUtils.returnJS(HttpStatus.BAD_REQUEST, "Product name is already taken!", null);
		}
		
		if (bindingResult.hasErrors())
			return AppUtils.returnJS(HttpStatus.BAD_REQUEST, bindingResult.getAllErrors().get(0).getDefaultMessage(), null);
		
//		
//		Product newProduct = product;
//		newProduct.setBrand(brandService.findById(product.getBrand().getBrandId()));
//		newProduct.setCategory(categoryService.findById(product.getCategory().getCategoryId()));
//		productService.addProduct(newProduct);
		
		System.out.println(file.getOriginalFilename());
		return AppUtils.returnJS(HttpStatus.OK, "Add product successfully!", null);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> deleteProduct(@PathVariable("id") Integer id) {
		Product product = productService.findById(id);
		if(product == null) {
			return AppUtils.returnJS(HttpStatus.NOT_FOUND, "Product is unavaiable", null);
		}
		
		int size;
		size = product.getCarts().size();
		if (size > 0)
			return AppUtils.returnJS(HttpStatus.BAD_REQUEST, 
					String.format("Cannot delete this product! This product relate to %s carts", size), null);
		
		size = product.getOrderDetails().size();
		if (size > 0)
			return AppUtils.returnJS(HttpStatus.BAD_REQUEST, 
					String.format("Cannot delete this product! This product relate to %s order details", size), null);
		
//		size = product.getFeedBacks().size();
//		if (size > 0)
//			return AppUtils.returnJS(HttpStatus.BAD_REQUEST, 
//					String.format("Cannot delete this product! This product relate to %s feedbacks", size), null);
////		
//		if(product.getCategory() != null) {
//			Category category = categoryService.findById(product.getCategory().getId());
//			category.getProducts().remove(product);
//			categoryService.updateCategory(category);
//		}
//		if(product.getBrand() != null) {
//			Brand brand = brandService.findById(product.getBrand().getId());
//			brand.getProducts().remove(product);
//			brandService.updateBrand(brand);
//		}
//		
//		productService.deleteById(id);
		product.setStatus(false);
		productService.updateProduct(product);
		return AppUtils.returnJS(HttpStatus.OK, "Move product to Deleted Product List successfully!", null);
	}

	@PutMapping(value = "/{id}/{price}")
	public ResponseEntity<?> putProduct(Principal principal, @PathVariable("id") Integer id, @PathVariable("price") Float price, 
			@Valid @RequestBody Product newProduct,
			BindingResult bindingResult) {
		Product oldProduct = null;
		
		oldProduct = productService.findById(id);
		
		if(oldProduct == null) {
			return AppUtils.returnJS(HttpStatus.BAD_REQUEST, "Product is unavaiable", oldProduct);
		}
		
		if (productService.existsByName(newProduct.getName())
				&& !newProduct.getName().equals(oldProduct.getName())) {
			return AppUtils.returnJS(HttpStatus.BAD_REQUEST, "Product name is already taken!", null);
		}
		if (bindingResult.hasErrors())
			return AppUtils.returnJS(HttpStatus.BAD_REQUEST, bindingResult.getAllErrors().get(0).getDefaultMessage(), null);
		
		if(price <= 0.0)
			return AppUtils.returnJS(HttpStatus.BAD_REQUEST, "Price should be greater than 0!", null); 

		productService.updateProduct(newProduct);
		
		System.out.println(priceHistoryService.getLatestPrice(newProduct));
		System.out.println(price);
		if(Float.compare(price, priceHistoryService.getLatestPrice(newProduct)) != 0) {
			PriceHistory history = new PriceHistory();
			history.setPrice(price);
			history.setProduct(newProduct);
			history.setUser(userService.getUserByUsername(principal.getName()));
			history.setDate(new Date());
			priceHistoryService.addPriceHistory(history);
		}
			
		return AppUtils.returnJS(HttpStatus.OK, "Update product successfully!", newProduct);
	}
	
	@GetMapping(value = { "price-history/{productId}" })
	public ResponseEntity<?> getPriceHistory(@PathVariable("productId") Integer productId) {
		Product product = null;
		
		product = productService.findById(productId);
		
		if(product == null) {
			return AppUtils.returnJS(HttpStatus.BAD_REQUEST, "Product is unavaiable", product);
		}
		return ResponseEntity.ok(priceHistoryService.findByProduct(product));
	}
	
	@GetMapping(value = { "price-history/{option}/{productId}" })
	public ResponseEntity<?> getPriceHistoryByPrice(@PathVariable("option") String option, @PathVariable("productId") Integer productId) {
		Product product = null;
		
		product = productService.findById(productId);
		
		if(product == null) {
			return AppUtils.returnJS(HttpStatus.BAD_REQUEST, "Product is unavaiable", product);
		}
		return ResponseEntity.ok(priceHistoryService.findByProductOrderByPrice(product, option));
	}
	
	@PostMapping(value = { "price-history" })
	public ResponseEntity<?> postPriceHistory(@Valid @RequestBody PriceHistory priceHistory, BindingResult bindingResult) {
		
		if (bindingResult.hasErrors())
			return AppUtils.returnJS(HttpStatus.BAD_REQUEST, bindingResult.getAllErrors().get(0).getDefaultMessage(), null);
		
		Product product = null;
		
		product = productService.findById(priceHistory.getProduct().getId());
		
		if(product == null) {
			return AppUtils.returnJS(HttpStatus.BAD_REQUEST, "Product is unavaiable", product);
		}
		
		User user = null;
		
		user = userService.findById(priceHistory.getUser().getId());
		
		if(user == null) {
			return AppUtils.returnJS(HttpStatus.BAD_REQUEST, "User is unavaiable", product);
		}
		
		priceHistory.setDate(new Date());
		priceHistory.setUser(user);
		priceHistory.setProduct(product);
		
		priceHistoryService.addPriceHistory(priceHistory);

		return AppUtils.returnJS(HttpStatus.OK, "Update price successfully!", product);
	}
}
