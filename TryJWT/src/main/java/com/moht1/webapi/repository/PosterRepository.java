package com.moht1.webapi.repository;

import com.moht1.webapi.model.Poster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PosterRepository extends JpaRepository<Poster, Integer> {

}
