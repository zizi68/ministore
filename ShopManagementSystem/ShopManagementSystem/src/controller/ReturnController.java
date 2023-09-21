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
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import model.Order;
import model.OrderDetail;
import model.Response;
import model.Return;
import model.ReturnDetail;
import utils.ConnectAPI;

/**
 *
 * @author TRINH
 */
public class ReturnController extends BaseController {

    private String getOrderDetail;

    public ReturnController() {
        getOneByID = "/api/return/order/";
        getOrderDetail = "/api/return/return-detail?returnId=";
        editOrDelete = "/api/return/%d?statusId=%d";
    }

    public Response updateReturnByID(int returnId, int statusId) {
        String str = String.format(editOrDelete, returnId, statusId);
        Response response = null;
        try {
            response = ConnectAPI.excuteHttpMethod("", str, "PUT", true);
            //print in String
            System.out.println(response.getMessage());

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return response;
    }

    public List<ReturnDetail> getReturnDetailByReturnId(int id) {
        List<ReturnDetail> founderList = null;
        try {
            Response response = ConnectAPI.excuteHttpMethod("", getOrderDetail + id, "GET", true);
            Type typeOfT = new TypeToken<ArrayList<ReturnDetail>>() {
            }.getType();
            founderList = gson.fromJson(response.getMessage(), typeOfT);
        } catch (IOException ex) {
            Logger.getLogger(ReturnController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return founderList;
    }
    
    public Return getReturnByOrderId(int orderId) {
        Return founderList = null;
        try {
            Response response = ConnectAPI.excuteHttpMethod("", getOneByID + orderId, "GET", true);
            founderList = gson.fromJson(response.getMessage(), Return.class);
        } catch (IOException ex) {
            Logger.getLogger(ReturnController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return founderList;
    }

    public void loadProductTable(List<ReturnDetail> list, DefaultTableModel dtm) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        dtm.setNumRows(0);
        Vector vt;
        for (ReturnDetail o : list) {
            vt = new Vector();
            vt.add(o.getProduct().getProductId());
            vt.add(o.getProduct().getName());
            vt.add(o.getQuantity());
            dtm.addRow(vt);
        }
    }
}
