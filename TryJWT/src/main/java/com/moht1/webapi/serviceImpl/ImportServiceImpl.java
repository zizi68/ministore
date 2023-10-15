package com.moht1.webapi.serviceImpl;

import com.moht1.webapi.model.Import;
import com.moht1.webapi.model.ImportDetail;
import com.moht1.webapi.repository.ImportDetailRepository;
import com.moht1.webapi.repository.ImportRepository;
import com.moht1.webapi.service.ImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ImportServiceImpl implements ImportService {

    @Autowired
    ImportRepository importRepository;

    @Autowired
    ImportDetailRepository importDetailRepository;

    @Override
    public Import findById(Integer id) {
        Optional<Import> imports = importRepository.findById(id);
        if (!imports.isPresent()) {
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
