package com.trinh.webapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trinh.webapi.model.Poster;

@Repository
public interface PosterRepository extends JpaRepository<Poster, Integer>{

}
