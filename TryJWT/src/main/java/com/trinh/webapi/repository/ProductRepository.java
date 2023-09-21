package com.trinh.webapi.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.trinh.webapi.dto.FullProduct;
import com.trinh.webapi.model.Category;
import com.trinh.webapi.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
	public Boolean existsByName(String name);
	public List<Product> findAllByCategory(Category category, Pageable pageable);
	public List<Product> findAllByStatus(boolean status, Pageable pageable);
	public List<Product> findAllByStatusOrderByIdDesc(boolean status);
	public int countByCategory(Category category);
	public List<Product> findAllByCategory(Category category);
	@Query(value = "SELECT * FROM Product where name like %:keyword%", nativeQuery = true)
	public List<Product> search(@Param("keyword") String keyword);
	
	@Query(value = "SELECT sum(sold_quantity) "
			+ "FROM Product "
			+ "Where category_id = :categoryId", nativeQuery = true)
	public Integer sumSoldQuantityByCategory(Integer categoryId);
	
	@Query(value = "SELECT sum(sold_quantity) "
			+ "FROM Product "
			+ "Where brand_id = :brandId", nativeQuery = true)
	public Integer sumSoldQuantityByBrand(Integer brandId);
}