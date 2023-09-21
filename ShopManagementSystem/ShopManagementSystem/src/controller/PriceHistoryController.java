/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Brand;
import model.ImportDetail;
import model.PriceHistory;
import model.Response;
import utils.ConnectAPI;

/**
 *
 * @author HP
 */
public class PriceHistoryController extends BaseController{
    
    private String getImportHistory;
    
    public PriceHistoryController() {
        getOneByID = "/api/admin/product/price-history/%s/%s";
        getAll = "/api/admin/product/price-history/";
        addOne = "/api/admin/product/price-history";
        getImportHistory = "/api/admin/product/import-history/";
    }
    
    public PriceHistory getPriceByOption(String option, String id) {
        String str = String.format(getOneByID, option, id);
        PriceHistory b = null;
        try {
            Response response = ConnectAPI.excuteHttpMethod("", str, "GET", true);
            b = gson.fromJson(response.getMessage(), PriceHistory.class);

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return b;
    }
    
    public List<PriceHistory> getPriceHistoryByProductId(String productId) {
        List<PriceHistory> founderList = null;
        try {
            Response response = ConnectAPI.excuteHttpMethod("", getAll + productId, "GET", true);
            Type typeOfT = new TypeToken<ArrayList<PriceHistory>>() {
            }.getType();
            founderList = gson.fromJson(response.getMessage(), typeOfT);
        } catch (IOException ex) {
            Logger.getLogger(PriceHistoryController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return founderList;
    }
    
    public List<ImportDetail> getImportHistoryByProductId(String productId) {
        List<ImportDetail> founderList = null;
        try {
            Response response = ConnectAPI.excuteHttpMethod("", getImportHistory + productId, "GET", true);
            Type typeOfT = new TypeToken<ArrayList<ImportDetail>>() {
            }.getType();
            founderList = gson.fromJson(response.getMessage(), typeOfT);
        } catch (IOException ex) {
            Logger.getLogger(PriceHistoryController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return founderList;
    }
    
    public Response addPrice(PriceHistory p) {
        Response response = null;
        try {
            String json = gson.toJson(p);
            response = ConnectAPI.excuteHttpMethod(json, addOne, "POST", true);
            //print in String
            System.out.println(response.getMessage());

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return response;
    }
}
