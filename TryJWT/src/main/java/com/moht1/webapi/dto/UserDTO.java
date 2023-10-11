package com.moht1.webapi.dto;

import java.util.List;

import com.moht1.webapi.model.Address;
import com.moht1.webapi.model.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

	private Integer id;

	private String username;

	private String email;

	private String firstName;

	private String lastName;

	private String phone;

	private String image;

	private int numord;
	
	private List<Address> addresses;

	public UserDTO(User u, int numord, List<Address> addresses) {
		this.id = u.getId();
		this.username = u.getUsername();
		this.email = u.getEmail();
		this.firstName = u.getFirstName();
		this.lastName = u.getLastName();
		this.phone = u.getPhone();
		this.image = u.getImage();
		this.numord = numord;
		this.addresses = addresses;
	}
}
