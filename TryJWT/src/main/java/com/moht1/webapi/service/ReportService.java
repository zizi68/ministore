package com.moht1.webapi.service;

import com.moht1.webapi.dto.MyItem;
import com.moht1.webapi.dto.Report;

import java.util.Date;
import java.util.List;

public interface ReportService {
    public List<MyItem> reportReceipt(Date date, int limit);

    public List<MyItem> newReportReceipt(Date date, int limit);

    public Date addDays(Date date, int days);

    public Date subDays(Date date, int days);

    public String covertD2S(Date date, String format);

    public List<Report> reportRevenueByMonth(int year);

    public List<Report> reportRevenueByYear(int year1, int year2);

    public List<Report> reportRevenueByDate(String date1, String date2);
}
