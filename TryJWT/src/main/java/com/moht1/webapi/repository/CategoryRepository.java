package com.moht1.webapi.repository;

import com.moht1.webapi.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer>{
	public Boolean existsByName(String name);

	
}