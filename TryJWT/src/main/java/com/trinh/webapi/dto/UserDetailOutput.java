package com.trinh.webapi.dto;

import java.util.ArrayList;
import java.util.List;

import com.trinh.webapi.model.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class UserDetailOutput {
	private int page;
	private int totalPage;
	private List<UserDTO> listResult = new ArrayList<UserDTO>();
}
