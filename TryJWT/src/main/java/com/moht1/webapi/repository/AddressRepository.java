package com.moht1.webapi.repository;

import java.util.List;

import com.moht1.webapi.model.Address;
import com.moht1.webapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer>{
	@Query(value = "SELECT * FROM province\r\n"
			+ "where province_id = :id", nativeQuery = true)
	public Address findByAddressId(@Param("id") Integer id);
	
	public List<Address> findByUser(User user);
	
//	List<Address> findBySetUsers_Id(Long id);
}
