package com.trinh.webapi.serviceImpl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trinh.webapi.dto.MyItem;
import com.trinh.webapi.dto.Report;
import com.trinh.webapi.model.OrderStatus;
import com.trinh.webapi.repository.ImportRepository;
import com.trinh.webapi.repository.OrderRepository;
import com.trinh.webapi.repository.OrderStatusRepository;
import com.trinh.webapi.repository.ReturnRepository;
import com.trinh.webapi.service.ReportService;

@Service
public class ReportServiceImpl implements ReportService{
	
	@Autowired
	OrderRepository orderRepository;
	
	@Autowired
	ImportRepository importRepository;
	
	@Autowired
	ReturnRepository returnRepository;
	
	@Autowired
	OrderStatusRepository statusRepository;

	@Override
	public List<MyItem> reportReceipt(Date date, int limit) {
		List<MyItem> list = new ArrayList<>();
        for (OrderStatus s: statusRepository.findAll()) {
            MyItem myItem = new MyItem();
            myItem.setLabel(s.getDescription());
            myItem.setValue(orderRepository.countByStatus(s));
            list.add(myItem);
        }
        return list;
	}
	
	@Override
	public List<MyItem> newReportReceipt(Date date, int limit) {
		List<MyItem> list = new ArrayList<>();
		Long value;
        for (int i = limit - 1; i >= 0; i--) {
            Date d = subDays(date, i);
            MyItem myItem = new MyItem();
            myItem.setLabel(covertD2S(d, "dd/MM"));
            value = orderRepository.countItem(d);
            myItem.setValue(value == null ? 0 : value);
            list.add(myItem);
        }
        return list;
	}

	@Override
	public Date addDays(Date date, int days) {
		GregorianCalendar cal =  new GregorianCalendar();
        cal.setTime(date);
        cal.add(Calendar.DATE, days);
        return cal.getTime();
	}

	@Override
	public Date subDays(Date date, int days) {
		GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.add(Calendar.DATE, -days);
        return cal.getTime();
	}

	@Override
	public String covertD2S(Date date, String format) {
		DateFormat df = new SimpleDateFormat(format);
        return df.format(date);
	}

	@Override
	public List<Report> reportRevenueByMonth(int year) {
		List<Report> list = new ArrayList<>();
		long revenue = 0;
		long funds = 0;
		long returns = 0;
		long profit = 0;
		long profitRate = 15;
		Report report = null;
		for(Integer i = 1; i <= 12; i++) {
			revenue = (orderRepository.sumOrderByYearAndMonth(year, i) == null) ? 0 : orderRepository.sumOrderByYearAndMonth(year, i);
			funds = (importRepository.sumImportByYearAndMonth(year, i) == null) ? 0 : importRepository.sumImportByYearAndMonth(year, i);
			returns = (returnRepository.sumReturnsByYearAndMonth(year, i) == null) ? 0 : returnRepository.sumReturnsByYearAndMonth(year, i);
			profit = revenue - funds - returns;
			profitRate = (long) ((profit <=0 ) ? 0 : (100.0 * profit / revenue));
			report = new Report(i.toString(), revenue, funds, returns, profit, profitRate);
			list.add(report);
		}
		
		return list;
	}

	@Override
	public List<Report> reportRevenueByYear(int year1, int year2) {
		List<Report> list = new ArrayList<>();
		long revenue = 0;
		long funds = 0;
		long returns = 0;
		long profit = 0;
		long profitRate = 15;
		Report report = null;
		for(Integer i = year1; i <= year2; i++) {
			revenue = (orderRepository.sumOrderByYear(i) == null) ? 0 : orderRepository.sumOrderByYear(i);
			funds = (importRepository.sumImportByYear(i) == null) ? 0 : importRepository.sumImportByYear(i);
			returns = (returnRepository.sumReturnsByYear(i) == null) ? 0 : returnRepository.sumReturnsByYear(i);
			profit = revenue - funds - returns;
			profitRate = (long) ((profit <=0 ) ? 0 : (100.0 * profit / revenue));
			report = new Report(i.toString(), revenue, funds, returns, profit, profitRate);
			list.add(report);
		}
		
		return list;
	}

	@Override
	public List<Report> reportRevenueByDate(String date1, String date2) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy"); 
		List<Report> list = new ArrayList<>();
		long revenue = 0;
		long funds = 0;
		long returns = 0;
		long profit = 0;
		long profitRate = 15;
		Report report = null;
		Date date01 = null;
		Date date02 = null;
		try {
			date01 = formatter.parse(date1);
			date02 = formatter.parse(date2);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		while(date01.compareTo(date02) <= 0) {
			revenue = (orderRepository.sumOrderByDate(date01) == null) ? 0 : orderRepository.sumOrderByDate(date01);
			funds = (importRepository.sumImportByDate(date01) == null) ? 0 : importRepository.sumImportByDate(date01);
			returns = (returnRepository.sumReturnsByDate(date01) == null) ? 0 : returnRepository.sumReturnsByDate(date01);
			profit = revenue - funds - returns;
			profitRate = (long) ((profit <=0 ) ? 0 : (100.0 * profit / revenue));
			report = new Report(formatter.format(date01), revenue, funds, returns, profit, profitRate);
			list.add(report);
			date01 = addDays(date01, 1);
		}
		
		return list;
	}

}
