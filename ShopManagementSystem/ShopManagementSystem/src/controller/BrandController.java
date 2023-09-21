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
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import model.Brand;
import model.Response;
import output.BrandDTO;
import output.BrandOutput;
import utils.ConnectAPI;

/**
 *
 * @author TRINH
 */
public class BrandController extends BaseController {

    private String getMostPurchased;
    
    public BrandController() {
        getAll = "/api/brand/all";
        getItemInOnePage = "/api/brand?pageNo=%d&pageSize=20&sortField=id&sortDirection=desc";
        getOneByID = "/api/brand/";
        addOne = "/api/admin/brand";
        editOrDelete = "/api/admin/brand/";
        getImage = "/api/brand/image/";
        getMostPurchased = "/api/admin/brand/most-purchased";
    }

    public Brand getBrandById(String id) {
        Brand b = null;
        try {
            Response response = ConnectAPI.excuteHttpMethod("", getOneByID + id, "GET", true);
            b = gson.fromJson(response.getMessage(), Brand.class);

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return b;
    }

    public List<Brand> getAllBrands() {
        List<Brand> founderList = null;
        try {
            Response response = ConnectAPI.excuteHttpMethod("", getAll, "GET", true);
            Type typeOfT = new TypeToken<ArrayList<Brand>>() {
            }.getType();
            founderList = gson.fromJson(response.getMessage(), typeOfT);
        } catch (IOException ex) {
            Logger.getLogger(BrandController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return founderList;
    }

    public BrandOutput getBrandInOnePage(int pageNo) {
        String str = String.format(getItemInOnePage, pageNo);
        System.out.println(str);
        BrandOutput founderList = null;
        try {
            Response response = ConnectAPI.excuteHttpMethod("", str, "GET", true);
            founderList = gson.fromJson(response.getMessage(), BrandOutput.class);

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return founderList;
    }

    public Response addBrand(Brand b) {
        Response response = null;
        try {
            String json = gson.toJson(b);
            response = ConnectAPI.excuteHttpMethod(json, addOne, "POST", true);
            //print in String
            System.out.println(response.getMessage());

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return response;
    }

    public Response updateBrandByID(int id, Brand b) {
        Response response = null;
        try {
            String json = gson.toJson(b);
            response = ConnectAPI.excuteHttpMethod(json, editOrDelete + id, "PUT", true);
            //print in String
            System.out.println(response.getMessage());

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return response;
    }

    public Response deleteBrandByID(String id) {
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
    
    public List<BrandDTO> getMostPurchased(){
        List<BrandDTO> founderList = null;
        try {
            Response reponse = ConnectAPI.excuteHttpMethod("", getMostPurchased, "GET", true);
            Type typeOfT = new TypeToken<ArrayList<BrandDTO>>() {
            }.getType();
            founderList = gson.fromJson(reponse.getMessage(), typeOfT);
        } catch (IOException ex) {
            Logger.getLogger(CategoryController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return founderList;
    }

    public void loadTable(List<Brand> list, DefaultTableModel dtm) {
        dtm.setNumRows(0);
        Vector vt;
        for (Brand b : list) {
            vt = new Vector();
            vt.add(b.getBrandId());
            vt.add(b.getName());
            vt.add(b.getDescription());
            dtm.addRow(vt);
        }
    }
}
