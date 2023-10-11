package com.moht1.webapi.service;

import java.util.List;

import com.moht1.webapi.model.Address;

public interface AddressService {
	public List<Address> findAllByUserId(Integer userId);

	public Address addAddress(Address address);

	public Address updateAddress(Address address);

	public Address findById(Integer id);

	public void deleteAddress(Address address);

	public void deleteById(Integer id);

	public void updateAddress1(Address address);
}
