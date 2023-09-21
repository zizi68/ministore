/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import model.Imports;
import model.ImportDetail;
import model.Response;
import utils.ConnectAPI;

/**
 *
 * @author HP
 */
public class ImportsController extends BaseController{
    
    private String getImportDetail;
    private String searchImports;

    public ImportsController() {
        getAll = "/api/admin/import";
        getImportDetail = "/api/admin/import/import-detail?importId=";
        addOne = "/api/admin/import?userId=%d&totalPrice=%f";
        searchImports = "/api/admin/import/search/%s/%s";
    }
    
    public List<Imports> getAllImports() {
        List<Imports> founderList = null;
        try {
            Response response = ConnectAPI.excuteHttpMethod("", getAll, "GET", true);
            Type typeOfT = new TypeToken<ArrayList<Imports>>() {
            }.getType();
            founderList = gson.fromJson(response.getMessage(), typeOfT);
        } catch (IOException ex) {
            Logger.getLogger(ImportsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return founderList;
    }
    
    public List<Imports> searchImports(Date startDate, Date finishDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String startDateStr = sdf.format(startDate) + "T00:00:00";
        String finishDateStr = sdf.format(finishDate) + "T23:59:59";
        String str = String.format(searchImports, startDateStr, finishDateStr);
        List<Imports> founderList = null;
        try {
            Response response = ConnectAPI.excuteHttpMethod("", str, "GET", true);
            Type typeOfT = new TypeToken<ArrayList<Imports>>() {
            }.getType();
            founderList = gson.fromJson(response.getMessage(), typeOfT);
        } catch (IOException ex) {
            Logger.getLogger(ImportsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return founderList;
    }
    
    public List<ImportDetail> getImportDetailByImportId(int id) {
        List<ImportDetail> founderList = null;
        try {
            Response response = ConnectAPI.excuteHttpMethod("", getImportDetail + id, "GET", true);
            Type typeOfT = new TypeToken<ArrayList<ImportDetail>>() {
            }.getType();
            founderList = gson.fromJson(response.getMessage(), typeOfT);
        } catch (IOException ex) {
            Logger.getLogger(ImportsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return founderList;
    }
    
    public Response addImport(int userId, float totalPrice, List<ImportDetail> list) {
        String str = String.format(addOne, userId, totalPrice);
        Response response = null;
        try {
            String json = gson.toJson(list);
            response = ConnectAPI.excuteHttpMethod(json, str, "POST", true);
            //print in String
            System.out.println(response.getMessage());

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return response;
    }
    
    public void loadTable(List<Imports> list, DefaultTableModel dtm) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        dtm.setNumRows(0);
        Vector vt;
        for (Imports o : list) {
            vt = new Vector();
            vt.add(o.getId());
            vt.add(o.getUser().getUsername());
            vt.add(o.getTotalPrice());
            vt.add(sdf.format(o.getDate()));
            dtm.addRow(vt);
        }
    }

    public void loadProductTable(List<ImportDetail> list, DefaultTableModel dtm) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        dtm.setNumRows(0);
        Vector vt;
        for (ImportDetail o : list) {
            vt = new Vector();
            vt.add(o.getProduct().getProductId());
            vt.add(o.getProduct().getName());
            vt.add(o.getQuantity());
            vt.add(o.getPrice());
            dtm.addRow(vt);
        }
    }
}
