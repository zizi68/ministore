package com.moht1.webapi.repository;

import com.moht1.webapi.model.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Integer> {
    public Boolean existsByName(String name);
}