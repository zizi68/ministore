package com.trinh.webapi.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.trinh.webapi.model.Role;
import com.trinh.webapi.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	public Optional<User> findByUsername(String username);

	public Optional<User> findById(Integer id);

	public Integer countByStatus(Boolean status);

	public Boolean existsByUsername(String username);

	public Boolean existsByEmail(String email);

	public List<User> findAllByStatus(boolean status, Pageable pageable);

	public List<User> findAllByStatus(boolean status);
	
	public List<User> findByRolesInAndStatus(Collection<Role> role, Boolean status);

	public Boolean existsByPhone(String phone);

	@Query(value = "select u.* from users u where u.email = :email and u.username NOT LIKE :username ", nativeQuery = true)
	public List<User> verifyDuplicateEmail(@Param("email") String email, @Param("username") String username);

	@Query(value = "select u.* from users u where u.phone = :phone and u.username NOT LIKE :username ", nativeQuery = true)
	public List<User> verifyDuplicatePhone(@Param("phone") String phone, @Param("username") String username);

	@Query(value = "SELECT COUNT(id) FROM orders WHERE user_id = :userId GROUP BY user_id", nativeQuery = true)
	public int getNumberOrderById(@Param("userId") Integer userId);
}
