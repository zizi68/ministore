package com.trinh.webapi.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductOutput {

	private int page;
	private int totalPage;
	private List<FullProduct> listResult = new ArrayList<>();	
	
}
