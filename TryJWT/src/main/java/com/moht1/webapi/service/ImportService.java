package com.moht1.webapi.service;

import java.util.Date;
import java.util.List;

import com.moht1.webapi.model.Import;
import com.moht1.webapi.model.ImportDetail;

public interface ImportService {

	public Import findById(Integer id);
	public Import addImport(Import imports);
	public List<ImportDetail> findImportDetailByImportId(Integer importId);
	public List<Import> findAllByOrderByDateDesc();
	public List<Import> searchImport(Date date1, Date date2);
}
