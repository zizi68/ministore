package com.moht1.webapi.repository;

import java.util.Date;
import java.util.List;

import com.moht1.webapi.model.Order;
import com.moht1.webapi.model.OrderStatus;
import com.moht1.webapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer>{
	public List<Order> findByStatusOrderByDateDesc(OrderStatus status);

	public List<Order> findByUser(User user);

	@Query("select sum(o.totalPrice) from Order o where DATE(o.date) = :date")
	public Long countItem(@Param("date") Date date);
	public long countByStatus(OrderStatus statusId);
	
	public List<Order> findByUserAndStatusOrderByDateDesc(User user, OrderStatus orderStatus);
	public List<Order> findByShipperAndStatusOrderByDateDesc(User user, OrderStatus orderStatus);
	public List<Order> findByUserOrderByDateDesc(User user);

	@Query("select sum(o.totalPrice) from Order o where YEAR(o.date) = :year and ((o.status.id in (4, 7, 8, 9) and o.paymentType = 'off') or (o.status.id != 5 and o.paymentType like '%MOMO%'))")
	public Long sumOrderByYear(Integer year);
	
	@Query("select sum(o.totalPrice) from Order o where YEAR(o.date) = :year and MONTH(o.date) = :month and ((o.status.id in (4, 7, 8, 9) and o.paymentType = 'off') or (o.status.id != 5 and o.paymentType like '%MOMO%'))")
	public Long sumOrderByYearAndMonth(Integer year, Integer month);
	
	@Query("select sum(o.totalPrice) from Order o where DATE(o.date) = :date and ((o.status.id in (4, 7, 8, 9) and o.paymentType = 'off') or (o.status.id != 5 and o.paymentType like '%MOMO%'))")
	public Long sumOrderByDate(@Param("date") Date date);
}
