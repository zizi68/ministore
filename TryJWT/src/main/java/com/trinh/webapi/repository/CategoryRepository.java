package com.trinh.webapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.trinh.webapi.model.Category;
import com.trinh.webapi.model.Product;
@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer>{
	public Boolean existsByName(String name);

	
}