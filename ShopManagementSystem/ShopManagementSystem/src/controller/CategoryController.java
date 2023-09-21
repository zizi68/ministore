/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import com.google.gson.reflect.TypeToken;
import java.awt.Image;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import model.Category;
import model.Response;
import output.CategoryDTO;
import output.CategoryOutput;
import utils.ConnectAPI;

/**
 *
 * @author TRINH
 */
public class CategoryController extends BaseController {
    private String getMostPurchased;

    public CategoryController() {
        getAll = "/api/category/all";
        getItemInOnePage = "/api/category?pageNo=%d&pageSize=20&sortField=id&sortDirection=desc";
        getOneByID = "/api/category/";
        addOne = "/api/admin/category";
        editOrDelete = "/api/admin/category/";
        getImage = "/api/category/image/";
        getMostPurchased = "/api/admin/category/most-purchased";
    }

    public Category getCategoryById(String id) {
        Category c = null;
        try {
            Response response = ConnectAPI.excuteHttpMethod("", getOneByID + id, "GET", true);
            c = gson.fromJson(response.getMessage(), Category.class);

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return c;
    }

    public List<Category> getAllCategories() {
        List<Category> founderList = null;
        try {
            Response reponse = ConnectAPI.excuteHttpMethod("", getAll, "GET", true);
            Type typeOfT = new TypeToken<ArrayList<Category>>() {
            }.getType();
            founderList = gson.fromJson(reponse.getMessage(), typeOfT);
        } catch (IOException ex) {
            Logger.getLogger(CategoryController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return founderList;
    }

    public CategoryOutput getCategoryInOnePage(int pageNo) {
        String str = String.format(getItemInOnePage, pageNo);
        System.out.println(str);
        CategoryOutput founderList = null;
        try {
            Response response = ConnectAPI.excuteHttpMethod("", str, "GET", true);
            founderList = gson.fromJson(response.getMessage(), CategoryOutput.class);

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return founderList;
    }

    public Response addCategory(Category c) {
        Response response = null;
        try {
            String json = gson.toJson(c);
            response = ConnectAPI.excuteHttpMethod(json, addOne, "POST", true);
            //print in String
            System.out.println(response.getMessage());

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return response;
    }

    public Response updateCategoryByID(int id, Category c) {
        Response response = null;
        try {
            String json = gson.toJson(c);
            response = ConnectAPI.excuteHttpMethod(json, editOrDelete + id, "PUT", true);
            //print in String
            System.out.println(response.getMessage());

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return response;
    }
    
    public List<CategoryDTO> getMostPurchased(){
        List<CategoryDTO> founderList = null;
        try {
            Response reponse = ConnectAPI.excuteHttpMethod("", getMostPurchased, "GET", true);
            Type typeOfT = new TypeToken<ArrayList<CategoryDTO>>() {
            }.getType();
            founderList = gson.fromJson(reponse.getMessage(), typeOfT);
        } catch (IOException ex) {
            Logger.getLogger(CategoryController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return founderList;
    }

    public Response deleteCategoryByID(String id) {
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

    public void loadTable(List<Category> list, DefaultTableModel dtm) {
        dtm.setNumRows(0);
        Vector vt;
        for (Category c : list) {
            vt = new Vector();
            vt.add(c.getCategoryId());
            vt.add(c.getName());
            vt.add(c.getNote());
            vt.add(c.getImage());
            dtm.addRow(vt);
        }
    }

    public Image getImage(String imageName) {
        Image img = null;
        try {
            img = ConnectAPI.getImageHasAuthentication(getImage + imageName);
        } catch (IOException ex) {
            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return img;
    }
}
