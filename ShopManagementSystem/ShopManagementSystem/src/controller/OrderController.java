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
import utils.ConnectAPI;

/**
 *
 * @author TRINH
 */
public class OrderController extends BaseController {

    private String getOrderDetail;

    public OrderController() {
        getAll = "/api/order?statusId=";
        getOrderDetail = "/api/order/order-detail?orderId=";
        editOrDelete = "/api/order/%d?statusId=%d";
    }

    public List<Order> getOrderByStatusId(int id) {
        List<Order> founderList = null;
        try {
            Response response = ConnectAPI.excuteHttpMethod("", getAll + id, "GET", true);
            Type typeOfT = new TypeToken<ArrayList<Order>>() {
            }.getType();
            founderList = gson.fromJson(response.getMessage(), typeOfT);
        } catch (IOException ex) {
            Logger.getLogger(OrderController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return founderList;
    }

    public Response updateOrderByID(int orderId, int statusId, int shipperId, int approverId) {
        String str = String.format(editOrDelete, orderId, statusId);
        
        if(shipperId != 0)
            str += "&shipperId=" + shipperId;
        if(approverId != 0)
            str += "&approverId=" + approverId;
        
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

    public List<OrderDetail> getOrderDetailByOrderId(int id) {
        List<OrderDetail> founderList = null;
        try {
            Response response = ConnectAPI.excuteHttpMethod("", getOrderDetail + id, "GET", true);
            Type typeOfT = new TypeToken<ArrayList<OrderDetail>>() {
            }.getType();
            founderList = gson.fromJson(response.getMessage(), typeOfT);
        } catch (IOException ex) {
            Logger.getLogger(OrderController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return founderList;
    }

    public void loadTable(List<Order> list, DefaultTableModel dtm) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        dtm.setNumRows(0);
        Vector vt;
        for (Order o : list) {
            vt = new Vector();
            vt.add(o.getId());
            vt.add(o.getUser().getUsername());
            vt.add(o.getTotalPrice());
            vt.add(sdf.format(o.getDate()));
            vt.add((o.getShipper() != null) ? o.getShipper().getUsername() : "None");
            vt.add((o.getApprover() != null) ? o.getApprover().getUsername() : "None");
            dtm.addRow(vt);
        }
    }

    public void loadProductTable(List<OrderDetail> list, DefaultTableModel dtm) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        dtm.setNumRows(0);
        Vector vt;
        for (OrderDetail o : list) {
            vt = new Vector();
            vt.add(o.getProduct().getProductId());
            vt.add(o.getProduct().getName());
            vt.add(o.getQuantity());
            vt.add(o.getPrice());
            dtm.addRow(vt);
        }
    }
}
