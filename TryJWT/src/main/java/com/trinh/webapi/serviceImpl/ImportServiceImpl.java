package com.trinh.webapi.serviceImpl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trinh.webapi.model.Import;
import com.trinh.webapi.model.ImportDetail;
import com.trinh.webapi.model.Order;
import com.trinh.webapi.model.OrderDetail;
import com.trinh.webapi.model.OrderStatus;
import com.trinh.webapi.model.User;
import com.trinh.webapi.repository.ImportDetailRepository;
import com.trinh.webapi.repository.ImportRepository;
import com.trinh.webapi.repository.OrderDetailRepository;
import com.trinh.webapi.repository.OrderRepository;
import com.trinh.webapi.repository.OrderStatusRepository;
import com.trinh.webapi.service.ImportService;
import com.trinh.webapi.service.OrderService;

@Service
public class ImportServiceImpl implements ImportService {

	@Autowired
	ImportRepository importRepository;
	
	@Autowired
	ImportDetailRepository importDetailRepository;

	@Override
	public Import findById(Integer id) {
		Optional<Import> imports = importRepository.findById(id);
		if(!imports.isPresent()) {
			return null;
		}
		return imports.get();
	}

	@Override
	public List<ImportDetail> findImportDetailByImportId(Integer importId) {
		Import imports = importRepository.getById(importId);
		List<ImportDetail> list = importDetailRepository.findByImports(imports);
		return list;
	}

	@Override
	public List<Import> findAllByOrderByDateDesc() {
		return importRepository.findAllByOrderByDateDesc();
	}

	@Override
	public Import addImport(Import imports) {
		return importRepository.save(imports);
	}

	@Override
	public List<Import> searchImport(Date date1, Date date2) {
		// TODO Auto-generated method stub
		return importRepository.findByDateBetweenOrderByIdDesc(date1, date2);
	}
}
