/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.google.gson.reflect.TypeToken;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import model.Category;
import model.Report;
import model.Response;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import output.BrandDTO;
import output.CategoryDTO;
import utils.ConnectAPI;

/**
 *
 * @author Admin
 */
public class ReportController extends BaseController{

    private String getByDate;
    private String getByMonth;
    private String getByYear;
    
    public ReportController() {
        getByDate = "/api/statistics/date/%s/%s";
        getByMonth = "/api/statistics/month/";
        getByYear = "/api/statistics/year/%s/%s";
    }
    
    public List<Report> getData(String api) {
        List<Report> founderList = null;
        try {
            Response response = ConnectAPI.excuteHttpMethod("", api, "GET", true);
            Type typeOfT = new TypeToken<ArrayList<Report>>() {
            }.getType();
            founderList = gson.fromJson(response.getMessage(), typeOfT);
        } catch (IOException ex) {
            Logger.getLogger(ReportController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return founderList;
    }
    
    public void setDataToBarChart(JPanel panel, DefaultTableModel dtm, String type, String xTitle, String yTitle, String... range) {
        String api = "";
        String title = "";
        if(type.equalsIgnoreCase("date")) {
            api = String.format(getByDate, range[0], range[1]);
            title = "Revenue statistics chart in " + range[0] + " - " + range[1];
        }
        else if(type.equalsIgnoreCase("month")){
            api = getByMonth + range[0];
            title = "Revenue statistics chart in " + range[0];
        }
        else {
            api = String.format(getByYear, range[0], range[1]);
            title = "Revenue statistics chart in " + range[0] + " - " + range[1];
        }
        
        List<Report> list = getData(api);
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        
        dtm.setNumRows(0);
        Vector vt;
        for (Report b : list) {
            vt = new Vector();
            vt.add(b.getLabel());
            vt.add(b.getRevenue());
            vt.add(b.getFunds());
            vt.add(b.getReturns());
            vt.add(b.getProfit());
            vt.add(b.getProfitRate());
            dtm.addRow(vt);
            
            dataset.addValue(b.getRevenue(), "Revenue", b.getLabel());
            dataset.addValue(b.getFunds(), "Funds", b.getLabel());
            dataset.addValue(b.getReturns(), "Return", b.getLabel());
        }
        
        JFreeChart barChart = ChartFactory.createBarChart3D(title.toUpperCase(), xTitle, yTitle, dataset, PlotOrientation.VERTICAL, true, true, false);
    
        CategoryPlot categoryPlot = barChart.getCategoryPlot();
        categoryPlot.setBackgroundPaint(Color.WHITE);
        ChartPanel chartPanel = new ChartPanel(barChart);
        chartPanel.setMouseWheelEnabled(true);
        chartPanel.setPreferredSize(new Dimension(panel.getWidth() - 300, 550));
        
        panel.removeAll();
        panel.setLayout(new CardLayout());
        panel.add(chartPanel);
        panel.validate();
        panel.repaint();
    }
    
    public void setCategoryToPieChart(JPanel panel, DefaultTableModel dtm, List<CategoryDTO> list) {

        DefaultPieDataset dataset = new DefaultPieDataset();
        
        dtm.setNumRows(0);
        Vector vt;
        int i = 0;
        for (CategoryDTO b : list) {
            i++;
            vt = new Vector();
            vt.add(i);
            vt.add(b.getCategory().getCategoryId());
            vt.add(b.getCategory().getName());
            vt.add(b.getCategory().getNote());
            vt.add(b.getSoldQuantity());
            dtm.addRow(vt);
            
            dataset.setValue(b.getCategory().getName(), b.getSoldQuantity());
        }
        String title = "Most purchased categories";
        
        createPieChart(panel, title, dataset);
    }
    
    public void setBrandToPieChart(JPanel panel, DefaultTableModel dtm, List<BrandDTO> list) {

        DefaultPieDataset dataset = new DefaultPieDataset();
        
        dtm.setNumRows(0);
        Vector vt;
        int i = 0;
        for (BrandDTO b : list) {
            i++;
            vt = new Vector();
            vt.add(i);
            vt.add(b.getBrand().getBrandId());
            vt.add(b.getBrand().getName());
            vt.add(b.getBrand().getDescription());
            vt.add(b.getSoldQuantity());
            dtm.addRow(vt);
            
            dataset.setValue(b.getBrand().getName(), b.getSoldQuantity());
        }
        String title = "Most purchased brands";
        
        createPieChart(panel, title, dataset);
    }
    
    public void createPieChart(JPanel panel, String title, DefaultPieDataset dataset){
        JFreeChart pieChart = ChartFactory.createPieChart(title.toUpperCase(), dataset, false, true, false);
        
        PiePlot piePlot = (PiePlot) pieChart.getPlot();
        piePlot.setBackgroundPaint(Color.WHITE);
        
        ChartPanel chartPanel = new ChartPanel(pieChart);
        
        panel.removeAll();
        panel.setLayout(new CardLayout());
        panel.add(chartPanel);
        panel.validate();
        panel.repaint();
    }
    
    public long sumColumn(JTable tb, int index){
        long sum = 0;
        
        try {
            for (int row = 0; row < tb.getRowCount(); row ++) 
            {
                if (tb.getValueAt(row, index).toString().isEmpty()) return 0;
                sum += Integer.parseInt(tb.getValueAt(row, index).toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return sum;
    }
    
    public void SetDataToCategoryChart (JPanel panel, int year)
    {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        
        String query = "select top 10  c.category, num = count(c.category)\n" +
                                "from loan l, loan_detail dt, book b, category c\n" +
                                "where l.loan_id = dt.loan_id\n" +
                                "and dt.book_id = b.book_id\n" +
                                "and b.category_id = c.category_id\n" +
                                "and year(l.date_start) = " + year + "\n" +
                                "group by c.category\n" +
                                "order by num desc";
        
//        try (
//            Connection con = Connect.GetConnect();
//            PreparedStatement ps = con.prepareStatement(query);
//            ResultSet rs = ps.executeQuery()) {
//            while (rs.next()) {
//                dataset.addValue(rs.getInt("num"), "Genres", rs.getString("category"));
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(BookLoan.class.getName()).log(Level.SEVERE, null, ex);
//        }
        
        String title = "top 10 most borrowed genres in " + String.valueOf(year);
        JFreeChart barChart = ChartFactory.createBarChart(title.toUpperCase(), "Genres", "Number", dataset, PlotOrientation.VERTICAL, false, true, false);
    
        ChartPanel chartPanel = new ChartPanel(barChart);
        chartPanel.setPreferredSize(new Dimension(panel.getWidth(), 550));
        
        panel.removeAll();
        panel.setLayout(new CardLayout());
        panel.add(chartPanel);
        panel.validate();
        panel.repaint();
    }
    
    public void SetDataToAuthorChart (JPanel panel, int year)
    {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        
        String query = "select top 10 a.name, num = count(a.name)\n" +
                        "from loan l, loan_detail dt, book b, author a\n" +
                        "where l.loan_id = dt.loan_id\n" +
                        "and dt.book_id = b.book_id\n" +
                        "and b.author_id = a.author_id\n" +
                        "and year(l.date_start) = " + year + "\n" +
                        "group by a.name\n" +
                        "order by num desc";
        
//        try (
//            Connection con = Connect.GetConnect();
//            PreparedStatement ps = con.prepareStatement(query);
//            ResultSet rs = ps.executeQuery()) {
//            while (rs.next()) {
//                dataset.addValue(rs.getInt("num"), "Author", rs.getString("name"));
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(BookLoan.class.getName()).log(Level.SEVERE, null, ex);
//        }
        
        String title = "top 10 most borrowed authors in " + String.valueOf(year);
        JFreeChart barChart = ChartFactory.createBarChart(title.toUpperCase(), "Author", "Number", dataset, PlotOrientation.VERTICAL, false, true, false);
    
        ChartPanel chartPanel = new ChartPanel(barChart);
        chartPanel.setPreferredSize(new Dimension(panel.getWidth(), 550));
        
        panel.removeAll();
        panel.setLayout(new CardLayout());
        panel.add(chartPanel);
        panel.validate();
        panel.repaint();
    }
    
    public void SetDataToPublisherChart (JPanel panel, int year)
    {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        
        String query = "select top 10 p.name, num = count(p.name)\n" +
                        "from loan l, loan_detail dt, book b, publisher p\n" +
                        "where l.loan_id = dt.loan_id\n" +
                        "and dt.book_id = b.book_id\n" +
                        "and b.publisher_id = p.publisher_id\n" +
                        "and year(l.date_start) = " + year + "\n" +
                        "group by p.name\n" +
                        "order by num desc";
        
//        try (
//            Connection con = Connect.GetConnect();
//            PreparedStatement ps = con.prepareStatement(query);
//            ResultSet rs = ps.executeQuery()) {
//            while (rs.next()) {
//                dataset.addValue(rs.getInt("num"), "Publisher", rs.getString("name"));
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(BookLoan.class.getName()).log(Level.SEVERE, null, ex);
//        }
        
        String title = "top 10 most borrowed publishers in " + String.valueOf(year);
        JFreeChart barChart = ChartFactory.createBarChart(title.toUpperCase(), "Publisher", "Number", dataset, PlotOrientation.VERTICAL, false, true, false);
    
        ChartPanel chartPanel = new ChartPanel(barChart);
        chartPanel.setPreferredSize(new Dimension(panel.getWidth(), 550));
        
        panel.removeAll();
        panel.setLayout(new CardLayout());
        panel.add(chartPanel);
        panel.validate();
        panel.repaint();
    }
}
