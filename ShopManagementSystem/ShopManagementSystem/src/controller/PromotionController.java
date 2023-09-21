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
import model.Promotion;
import model.PromotionDetail;
import model.Response;
import utils.ConnectAPI;

/**
 *
 * @author HP
 */
public class PromotionController extends BaseController{
    
    private String getPromotionDetail;
    private String searchPromotion;

    public PromotionController() {
        getOneByID = "/api/admin/promotion/";
        getAll = "/api/admin/promotion/status/";
        getPromotionDetail = "/api/admin/promotion/promotion-detail?promotionId=";
        addOne = "/api/admin/promotion/%d/%s/%s";
        editOrDelete = "/api/admin/promotion/";
        searchPromotion = "/api/admin/promotion/search/%s/%s";
    }
    
    public Promotion getPromotionByID(String id) {
        Promotion founderList = null;
        try {
            Response response = ConnectAPI.excuteHttpMethod("", getOneByID + id, "GET", true);
            founderList = gson.fromJson(response.getMessage(), Promotion.class);
        } catch (IOException ex) {
            Logger.getLogger(PromotionController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return founderList;
    }
    
    public List<Promotion> getAllPromotions(String status) {
        List<Promotion> founderList = null;
        try {
            Response response = ConnectAPI.excuteHttpMethod("", getAll + status, "GET", true);
            Type typeOfT = new TypeToken<ArrayList<Promotion>>() {
            }.getType();
            founderList = gson.fromJson(response.getMessage(), typeOfT);
        } catch (IOException ex) {
            Logger.getLogger(PromotionController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return founderList;
    }
    
    public List<Promotion> searchPromotions(Date startDate, Date finishDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String startDateStr = sdf.format(startDate) + "T00:00:00";
        String finishDateStr = sdf.format(finishDate) + "T23:59:59";
        String str = String.format(searchPromotion, startDateStr, finishDateStr);
        List<Promotion> founderList = null;
        try {
            Response response = ConnectAPI.excuteHttpMethod("", str, "GET", true);
            Type typeOfT = new TypeToken<ArrayList<Promotion>>() {
            }.getType();
            founderList = gson.fromJson(response.getMessage(), typeOfT);
        } catch (IOException ex) {
            Logger.getLogger(PromotionController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return founderList;
    }
    
    public List<PromotionDetail> getPromotionDetailByPromotionId(String id) {
        List<PromotionDetail> founderList = null;
        try {
            Response response = ConnectAPI.excuteHttpMethod("", getPromotionDetail + id, "GET", true);
            Type typeOfT = new TypeToken<ArrayList<PromotionDetail>>() {
            }.getType();
            founderList = gson.fromJson(response.getMessage(), typeOfT);
        } catch (IOException ex) {
            Logger.getLogger(PromotionController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return founderList;
    }
    
    public Response addPromotion(int userId, Date startDate, Date finishDate, List<PromotionDetail> list) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String startDateStr = sdf.format(startDate).replace(" ", "T");
        String finishDateStr = sdf.format(finishDate).replace(" ", "T");
        String str = String.format(addOne, userId, startDateStr, finishDateStr);
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
    
    public Response editPromotion(int promotionId, Date startDate, Date finishDate, List<PromotionDetail> list) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String startDateStr = sdf.format(startDate).replace(" ", "T");
        String finishDateStr = sdf.format(finishDate).replace(" ", "T");
        String str = String.format(addOne, promotionId, startDateStr, finishDateStr);
        Response response = null;
        try {
            String json = gson.toJson(list);
            System.out.println(json);
            response = ConnectAPI.excuteHttpMethod(json, str, "PUT", true);
            //print in String
            System.out.println(response.getMessage());

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return response;
    }
    
    public Response deletePromotionByID(String id) {
        Response response = null;
        try {
            response = ConnectAPI.excuteHttpMethod("", editOrDelete + id, "DELETE", true);
            //print in String
            System.out.println(response.getMessage());

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return response;
    }
    
    public void loadTable(List<Promotion> list, DefaultTableModel dtm) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        dtm.setNumRows(0);
        Vector vt;
        for (Promotion o : list) {
            vt = new Vector();
            vt.add(o.getId());
            vt.add(o.getUser().getUsername());
            vt.add(sdf.format(o.getStartDate()));
            vt.add(sdf.format(o.getFinishDate()));
            dtm.addRow(vt);
        }
    }

    public void loadProductTable(List<PromotionDetail> list, DefaultTableModel dtm) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        dtm.setNumRows(0);
        Vector vt;
        for (PromotionDetail o : list) {
            vt = new Vector();
            vt.add(o.getProduct().getProductId());
            vt.add(o.getProduct().getName());
            vt.add(o.getPercentage());
            dtm.addRow(vt);
        }
    }
}
