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
import model.Order;
import model.Product;
import model.Response;
import netscape.javascript.JSObject;
import output.ProductOutput;
import utils.ConnectAPI;

/**
 *
 * @author TRINH
 */
public class ProductController extends BaseController {

    private String getLastImportPrice;
    
    public ProductController() {
        getOneByID = "/api/product/";
        getAll = "/api/product/all";
        addOne = "/api/admin/product";
        editOrDelete = "/api/admin/product/";
        getItemInOnePage = "/api/admin/product?pageNo=%d&pageSize=20&sortField=id&sortDirection=desc";
        getImage = "/api/product/image/";
        getLastImportPrice = "/api/admin/product/lasted-import-price/";
    }
    
    public Float getLastedImportPrice(String id) {
        Response response = null;
        try {
            response = ConnectAPI.excuteHttpMethod("", getLastImportPrice + id, "GET", true);

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return Float.parseFloat(convertResponse(response.getMessage()).getMessage());
    }

    public Product getProductById(String id) {
        Product p = null;
        try {
            Response response = ConnectAPI.excuteHttpMethod("", getOneByID + id, "GET", true);
            p = gson.fromJson(response.getMessage(), Product.class);

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return p;
    }

    public List<Product> getAllProduct() {
        List<Product> founderList = null;
        try {
            Response response = ConnectAPI.excuteHttpMethod("", getAll, "GET", true);
            Type typeOfT = new TypeToken<ArrayList<Product>>() {
            }.getType();
            founderList = gson.fromJson(response.getMessage(), typeOfT);

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return founderList;
    }
    
    public ProductOutput getProductInOnePage(int pageNo) {
        String str = String.format(getItemInOnePage, pageNo);
        System.out.println(str);
        ProductOutput founderList = null;
        try {
            Response response = ConnectAPI.excuteHttpMethod("", str, "GET", true);
            founderList = gson.fromJson(response.getMessage(), ProductOutput.class);

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return founderList;
    }

    public Response addProduct(Product p, Float price) {
        Response response = null;
        try {
            String json = gson.toJson(p);
            response = ConnectAPI.excuteHttpMethod(json, addOne + "/" + price, "POST", true);
            //print in String
            System.out.println(response.getMessage());

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return response;
    }

    public Response updateProductByID(int id, float price, Product p) {
        Response response = null;
        try {
            String json = gson.toJson(p);
            response = ConnectAPI.excuteHttpMethod(json, editOrDelete + id + "/" + price, "PUT", true);
            //print in String
            System.out.println(response.getMessage());

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return response;
    }

    public Response deleteProductByID(String id) {
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

    public void loadTable(List<Product> list, DefaultTableModel dtm) {
        dtm.setNumRows(0);
        Vector vt;
        for (Product p : list) {
            vt = new Vector();
            vt.add(p.getProductId());
            vt.add(p.getName());
            vt.add(p.getCategory().getName());
            vt.add(p.getBrand().getName());
            vt.add(p.getCalculationUnit());
            vt.add(p.getPrice());
            vt.add(p.getQuantity() - p.getSoldQuantity());
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
