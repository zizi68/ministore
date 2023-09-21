package com.trinh.webapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.trinh.webapi.model.District;
import com.trinh.webapi.model.Province;


@Repository
public interface DistrictRepository extends JpaRepository<District, Integer>{
	public List<District> findByProvince(Province province); 
	
	@Query(value = "select * \r\n"
			+ "from district \r\n"
			+ "where district.province_id = ?1", nativeQuery = true)
	public List<District> findAllDistrictByIdProvince(String id);
}
