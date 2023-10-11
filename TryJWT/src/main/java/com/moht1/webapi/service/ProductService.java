package com.moht1.webapi.service;

import java.util.List;

import com.moht1.webapi.model.ImportDetail;
import com.moht1.webapi.model.Product;
import org.springframework.data.domain.Page;


public interface ProductService {
	public Page<Product> getAll(int pageNo, int pageSize, String sortField, String sortDirection);
	public List<Product> getAllByCategoryId(Integer categoryId, int pageNo, int pageSize, String sortField, String sortDirection);
	public List<Product> getAllByCategory(Integer categoryId);
	public List<Product> getAllByStatus(boolean status, int pageNo, int pageSize, String sortField, String sortDirection);
	public int getCount();
	public int getCountByCategoryId(Integer categoryId);
	public Product findById(Integer productId);
	public Boolean existsByName(String name);
	public void addProduct(Product product);
	public void deleteProduct(Product product);
	public void updateProduct(Product product);
	public List<Product> getAllProduct();
	
	public Product getProductById(Integer id);
	public void deleteById(Integer id);
	
	public List<Product> search(String keyword);
	
	public List<ImportDetail> getImportPrice(Integer id);
}
