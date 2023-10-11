package com.moht1.webapi.repository;

import java.util.List;

import com.moht1.webapi.model.District;
import com.moht1.webapi.model.Ward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface WardRepository extends JpaRepository<Ward, Integer>{
	public List<Ward> findByDistrict(District district);
	
	@Query(value = "select * \r\n"
			+ "from ward \r\n"
			+ "where ward.district_id = ?1", nativeQuery = true)
	public List<Ward> findAllVillageByIdDistrict(String id);
}