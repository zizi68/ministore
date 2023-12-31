package com.moht1.webapi.repository;

import com.moht1.webapi.model.PriceHistory;
import com.moht1.webapi.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PriceHistoryRepository extends JpaRepository<PriceHistory, Integer> {
    public List<PriceHistory> findByProductOrderByDateDesc(Product product);

    public List<PriceHistory> findByProductOrderByPriceDesc(Product product);

    public List<PriceHistory> findByProductOrderByPriceAsc(Product product);
}