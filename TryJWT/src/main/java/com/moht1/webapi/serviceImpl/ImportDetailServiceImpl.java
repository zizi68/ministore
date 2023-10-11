package com.moht1.webapi.serviceImpl;

import java.util.List;

import com.moht1.webapi.model.ImportDetail;
import com.moht1.webapi.service.ImportDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moht1.webapi.repository.ImportDetailRepository;
import com.moht1.webapi.repository.ImportRepository;
import com.moht1.webapi.repository.ProductRepository;

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
