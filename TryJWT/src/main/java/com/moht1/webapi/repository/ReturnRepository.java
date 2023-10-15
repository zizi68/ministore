package com.moht1.webapi.repository;

import com.moht1.webapi.model.Order;
import com.moht1.webapi.model.Return;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ReturnRepository extends JpaRepository<Return, Integer> {
    public List<Return> findByIsApprovedOrderByDateDesc(boolean isApproved);

    public List<Return> findAllByOrderByIdDesc();

    public List<Return> findByOrder(Order order);

    public long countByIsApproved(boolean isApproved);

    public List<Return> findByOrderOrderByDateDesc(Order order);

    @Query("select sum(o.totalPrice) from Return o where YEAR(o.date) = :year and isApproved = 1")
    public Long sumReturnsByYear(Integer year);

    @Query("select sum(o.totalPrice) from Return o where YEAR(o.date) = :year and MONTH(o.date) = :month and isApproved = 1")
    public Long sumReturnsByYearAndMonth(Integer year, Integer month);

    @Query("select sum(o.totalPrice) from Return o where DATE(o.date) = :date and isApproved = 1")
    public Long sumReturnsByDate(@Param("date") Date date);
}
