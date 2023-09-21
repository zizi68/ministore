package com.trinh.webapi.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trinh.webapi.model.ImportDetail;
import com.trinh.webapi.model.OrderDetail;
import com.trinh.webapi.model.Product;
import com.trinh.webapi.repository.ImportDetailRepository;
import com.trinh.webapi.repository.ImportRepository;
import com.trinh.webapi.repository.OrderDetailRepository;
import com.trinh.webapi.repository.OrderRepository;
import com.trinh.webapi.repository.OrderStatusRepository;
import com.trinh.webapi.repository.ProductRepository;
import com.trinh.webapi.service.ImportDetailService;
import com.trinh.webapi.service.OrderDetailService;

@Service
public class ImportDetailServiceImpl implements ImportDetailService {

	@Autowired
	ImportRepository importRepository;
	
	@Autowired
	ImportDetailRepository importDetailRepository;
	
	@Autowired
	ProductRepository productRepository;

	@Override
	public List<ImportDetail> saveListImportDetail(List<ImportDetail> importDetails) {
		// TODO Auto-generated method stub
		return importDetailRepository.saveAll(importDetails);
	}
	
}
