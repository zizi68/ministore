package com.trinh.webapi.admin;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trinh.webapi.Exception.AppUtils;
import com.trinh.webapi.Exception.NotFoundException;
import com.trinh.webapi.model.Brand;
import com.trinh.webapi.model.Cart;
import com.trinh.webapi.model.Import;
import com.trinh.webapi.model.ImportDetail;
import com.trinh.webapi.model.Order;
import com.trinh.webapi.model.OrderDetail;
import com.trinh.webapi.model.OrderStatus;
import com.trinh.webapi.model.Product;
import com.trinh.webapi.model.User;
import com.trinh.webapi.service.BrandService;
import com.trinh.webapi.service.ImportDetailService;
import com.trinh.webapi.service.ImportService;
import com.trinh.webapi.service.ProductService;
import com.trinh.webapi.service.PromotionService;
import com.trinh.webapi.service.UserService;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/admin/import")
public class ManageImportController {
	
	@Autowired
	ImportService importService;
	
	@Autowired
	ImportDetailService importDetailService;	
	
	@Autowired
	PromotionService promotionService;
	
	@Autowired
	ProductService productService;
	
	@Autowired
	UserService userService;
	
	@GetMapping
	public ResponseEntity<?> getAllImport() {
		List<Import> imports = importService.findAllByOrderByDateDesc();
		return ResponseEntity.ok(imports);
	}
	
	@GetMapping(value = { "/{id}" })
	public ResponseEntity<?> getImportById(@PathVariable("id") Integer id) {
		
		Import imports = null;
		imports = importService.findById(id);
		if (imports == null)
			return AppUtils.returnJS(HttpStatus.BAD_REQUEST, "Import is unavaiable", null);
		return ResponseEntity.ok(imports);
	}
	
	@GetMapping(value = { "/search/{startDate}/{finishDate}" })
	public ResponseEntity<?> searchImport(@PathVariable("startDate") String startDate, @PathVariable("finishDate") String finishDate) {
		return ResponseEntity.ok(importService.searchImport(promotionService.convertStringToDate(startDate),
				promotionService.convertStringToDate(finishDate)));
	}
	
	@GetMapping(value = "/import-detail")
	public ResponseEntity<?> getImportDetailByImportId(@RequestParam("importId") Integer importId) {
		List<ImportDetail> list = null;
		try {
			list = importService.findImportDetailByImportId(importId);
		} catch (NotFoundException e) {
			return AppUtils.returnJS(HttpStatus.BAD_REQUEST, "Import is unavaiable", null);
		}
		return ResponseEntity.ok(list);
	}
	
	@PostMapping
	public ResponseEntity<?> insertImportByUserId(@RequestParam("userId") Integer userId, @RequestParam("totalPrice") Float totalPrice, @RequestBody List<ImportDetail> list) {
		User user = userService.findById(userId);
		if (user == null) {
			return AppUtils.returnJS(HttpStatus.BAD_REQUEST, "User not found!", null);
		}
		
		Import imports = new Import();
		imports.setDate(new Date());
		imports.setUser(user);
		imports.setTotalPrice(totalPrice);
		importService.addImport(imports);		
		
		Product product = null;

		for(ImportDetail s : list) {
			s.setImports(imports);
			product = productService.findById(s.getProduct().getId());
			product.setQuantity(product.getQuantity() + s.getQuantity());
			productService.updateProduct(product);
		}
		importDetailService.saveListImportDetail(list);
		
		return AppUtils.returnJS(HttpStatus.OK, "Save import successfully!", null);
	}
}
