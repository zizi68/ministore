package com.trinh.webapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trinh.webapi.model.Brand;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Integer>{
	public Boolean existsByName(String name);
}