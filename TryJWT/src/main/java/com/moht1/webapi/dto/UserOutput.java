package com.moht1.webapi.dto;

import java.util.ArrayList;
import java.util.List;

import com.moht1.webapi.model.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserOutput {
	private int page;
	private int totalPage;
	private List<User> listResult = new ArrayList<User>();
}
