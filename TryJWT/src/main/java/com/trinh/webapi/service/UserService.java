package com.trinh.webapi.service;

import java.util.List;

import com.trinh.webapi.model.ERole;
import com.trinh.webapi.model.User;

public interface UserService {
	public Boolean existsByUsername(String username);

	public int getCount();

	public int getCountByStatus(Boolean status);

	public List<User> getAllByStatus(boolean status, int pageNo, int pageSize, String sortField, String sortDirection);

	public User findById(Integer userId);

	public User getUserByUsername(String username);

	Boolean existsByEmail(String email);

	Boolean existsByPhone(String phone);

	void updateProfile(User user);

	public int deleteUser1(Integer userId);

	public List<User> getAllUserByStatus(boolean status);
	
	public List<User> getAllUserByRole(String role);

	public void addUser(User user);

	public void saveUser(User user);

	public boolean checkExistEmailInfo(String email, String username);

	public boolean checkExistPhoneInfo(String phone, String username);

	public int deleteUser(User user);

	public int getNumberOrderById(Integer userId);
}
