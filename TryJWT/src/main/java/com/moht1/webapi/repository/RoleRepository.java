package com.moht1.webapi.repository;

import java.util.Optional;

import com.moht1.webapi.model.ERole;
import com.moht1.webapi.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
	Optional<Role> findByName(ERole name);
}