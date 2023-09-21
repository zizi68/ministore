package com.trinh.webapi.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.trinh.webapi.model.Import;
import com.trinh.webapi.model.Order;
import com.trinh.webapi.model.OrderStatus;
import com.trinh.webapi.model.User;

@Repository
public interface ImportRepository extends JpaRepository<Import, Integer>{

	public List<Import> findByUser(User user);
	
	public List<Import> findAllByOrderByDateDesc();

	public long countByDate(Date date);
	
	@Query("select sum(o.totalPrice) from Import o where YEAR(o.date) = :year")
	public Long sumImportByYear(Integer year);
	
	@Query("select sum(o.totalPrice) from Import o where YEAR(o.date) = :year and MONTH(o.date) = :month")
	public Long sumImportByYearAndMonth(Integer year, Integer month);
	
	@Query("select sum(o.totalPrice) from Import o where DATE(o.date) = :date")
	public Long sumImportByDate(@Param("date") Date date);
	
	public List<Import> findByDateBetweenOrderByIdDesc(Date date1, Date date2);
}
