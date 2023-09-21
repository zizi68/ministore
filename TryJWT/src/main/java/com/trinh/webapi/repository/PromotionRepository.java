package com.trinh.webapi.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trinh.webapi.model.Import;
import com.trinh.webapi.model.Order;
import com.trinh.webapi.model.OrderStatus;
import com.trinh.webapi.model.Promotion;
import com.trinh.webapi.model.User;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Integer>{

	public List<Promotion> findByUser(User user);
	
	public List<Promotion> findAllByOrderByIdDesc();
	
	public List<Promotion> findByFinishDateAfter(Date now);
	
	public List<Promotion> findByFinishDateBeforeOrderByIdDesc(Date now);
	
	public List<Promotion> findByStartDateAfterOrderByIdDesc(Date now);
	
	public List<Promotion> findByStartDateBeforeOrderByIdDesc(Date now);
	
	public List<Promotion> findByStartDateBeforeAndFinishDateAfterOrderByIdDesc(Date now1, Date now2);
	
	public List<Promotion> findByStartDateAfterAndFinishDateBeforeOrderByIdDesc(Date now1, Date now2);
	
	public List<Promotion> findByStartDateLessThanEqualAndFinishDateGreaterThanEqualOrderByIdDesc(Date now1, Date now2);
	
	public List<Promotion> findByStartDateGreaterThanEqualAndFinishDateLessThanEqualOrderByIdDesc(Date now1, Date now2);
}
