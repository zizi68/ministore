package com.trinh.webapi.service;

import java.util.Date;
import java.util.List;

import com.trinh.webapi.model.Import;
import com.trinh.webapi.model.ImportDetail;

public interface ImportService {

	public Import findById(Integer id);
	public Import addImport(Import imports);
	public List<ImportDetail> findImportDetailByImportId(Integer importId);
	public List<Import> findAllByOrderByDateDesc();
	public List<Import> searchImport(Date date1, Date date2);
}
