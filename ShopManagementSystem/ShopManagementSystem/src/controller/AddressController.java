/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import model.Address;
import model.District;
import model.Province;
import model.Response;
import model.Ward;
import utils.ConnectAPI;

/**
 *
 * @author PC
 */
public class AddressController extends BaseController {

    private final Gson gson = new Gson();
    private final String getProvince = "/api/address/province";
    private final String getAllDistrictByProvinceId = "/api/address/district/";
    private final String getAllVillageByDistrictId = "/api/address/ward/";
    private final String edit = "/api/address/address";
    private final String delete = "/api/address/address/";

    public AddressController() {
        getAll = "/api/address/address/";
        addOne = "/api/address/address/";
    }
    
    public Response updateAddressByID(Address a) {
        Response response = null;
        try {
            String json = gson.toJson(a);
            response = ConnectAPI.excuteHttpMethod(json, edit, "PUT", true);
            System.out.println(response.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return response;
    }

    public Response deleteAddressByID(int id) {
        Response response = null;
        try {
            response = ConnectAPI.excuteHttpMethod("", delete + id, "DELETE", true);
            System.out.println(response.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return response;
    }

    public Response addAddress(int id, Address a) {
        Response response = null;
        try {
            String json = gson.toJson(a);
            response = ConnectAPI.excuteHttpMethod(json, addOne + id, "POST", true);
            System.out.println(response);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return response;
    }

    public List<Address> getAllAddressByUserId(int userId) {
        List<Address> founderList = null;
        try {
            Response reponse = ConnectAPI.excuteHttpMethod("", getAll + userId, "GET", true);
            Type typeOfT = new TypeToken<ArrayList<Address>>() {
            }.getType();
            founderList = gson.fromJson(reponse.getMessage(), typeOfT);
        } catch (IOException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return founderList;
    }

    public List<Province> getAllProvince() {
        List<Province> founderList = null;
        try {
            Response reponse = ConnectAPI.excuteHttpMethod("", getProvince, "GET", true);
            Type typeOfT = new TypeToken<ArrayList<Province>>() {
            }.getType();
            founderList = gson.fromJson(reponse.getMessage(), typeOfT);
        } catch (IOException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return founderList;
    }

    public List<District> getAllDistrictByProvinceId(int provinceId) {
        List<District> founderList = null;
        try {
            Response reponse = ConnectAPI.excuteHttpMethod("", getAllDistrictByProvinceId + provinceId, "GET", true);
            Type typeOfT = new TypeToken<ArrayList<District>>() {
            }.getType();
            founderList = gson.fromJson(reponse.getMessage(), typeOfT);
        } catch (IOException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return founderList;
    }

    public List<Ward> getAllVillageByDistrictId(int districtId) {
        List<Ward> founderList = null;
        try {
            Response reponse = ConnectAPI.excuteHttpMethod("", getAllVillageByDistrictId + districtId, "GET", true);
            Type typeOfT = new TypeToken<ArrayList<Ward>>() {
            }.getType();
            founderList = gson.fromJson(reponse.getMessage(), typeOfT);
        } catch (IOException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return founderList;
    }

    public void loadTable(List<Address> list, DefaultTableModel dtm) {
        dtm.setNumRows(0);
        Vector vt;
        for (Address a : list) {
            vt = new Vector();
            vt.add(a.getAddressId());
            vt.add(a.getSpecificAddress());
            vt.add(a.getWard().getWardName());
            vt.add(a.getWard().getDistrict().getDistrictName());
            vt.add(a.getWard().getDistrict().getProvince().getProvinceName());
            dtm.addRow(vt);
        }
    }
}
