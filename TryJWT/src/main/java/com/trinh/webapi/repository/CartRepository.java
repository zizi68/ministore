package com.trinh.webapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.trinh.webapi.model.Cart;
import com.trinh.webapi.model.Product;
import com.trinh.webapi.model.User;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer>{
  
	@Query(value = "SELECT * FROM Cart WHERE user_id = :userId AND quantity > 0 order by id desc", 
			nativeQuery = true)
	public List<Cart> findByUserId(@Param("userId") Integer userId);
	
	public Cart findByUserAndProduct(User user, Product product);
  
	public List<Cart> findByUser(User user);
	
	public List<Cart> findAllByProduct(Product product);
	
	public void deleteByUser(User user);


	public List<Cart> findAllByUser(User user);
}
