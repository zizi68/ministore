package com.moht1.webapi.repository;

import com.moht1.webapi.model.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProvinceRepository extends JpaRepository<Province, Integer> {

}
