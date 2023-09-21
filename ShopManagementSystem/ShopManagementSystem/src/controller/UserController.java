/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import com.google.gson.Gson;
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
import model.Password;
import model.Response;
import model.User;
import model.UserDB;
import output.UserOutput;
import utils.ConnectAPI;

/**
 *
 * @author TRINH
 */
public class UserController extends BaseController {

    private final Gson gson = new Gson();
    private final String signIn = "/api/auth/signin";
    private final String setStatus = "/api/users/setStatus";
    private final String updatePassword = "/api/users/change-password";
    private final String addShipper = "/api/auth/signup";

    public UserController() {
        getOneByID = "/api/users/";
        getItemInOnePage = "/api/users/numorders?status=%s&pageNo=%d&pageSize=20&sortField=id&sortDirection=desc";
        getImage = "/api/users/image/";
        editOrDelete = "/api/users/edit-profile";
    }
    
    public Response addShipper(UserDB user){
        Response response = null;
        try {
            String json = gson.toJson(user);
            response = ConnectAPI.excuteHttpMethod(json, addShipper, "POST", true);
            System.out.println(response.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return response;
    }

    public Response updatePassword(Password pass) {
        Response response = null;
        try {
            String json = gson.toJson(pass);
            response = ConnectAPI.excuteHttpMethod(json, updatePassword, "PUT", true);
            System.out.println(response.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return response;
    }

    public Response updateUser(UserDB user) {
        Response response = null;
        try {
            String json = gson.toJson(user);
            response = ConnectAPI.excuteHttpMethod(json, editOrDelete, "PUT", true);
            System.out.println(response.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return response;
    }

    public Response updateStatusByID(User u) {
        Response response = null;
        try {
            String json = gson.toJson(u);
            response = ConnectAPI.excuteHttpMethod(json, setStatus, "PUT", true);
            System.out.println(response.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return response;
    }

    public UserDB getUserById(String id) {
        UserDB u = null;
        try {
            Response response = ConnectAPI.excuteHttpMethod("", getOneByID + id, "GET", true);
            u = gson.fromJson(response.getMessage(), UserDB.class);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return u;
    }
    
    public List<UserDB> getUserByRole(String role) {
        List<UserDB> founderList = null;
        try {
            Response response = ConnectAPI.excuteHttpMethod("", getOneByID + "role/" + role, "GET", true);
            Type typeOfT = new TypeToken<ArrayList<UserDB>>() {
            }.getType();
            founderList = gson.fromJson(response.getMessage(), typeOfT);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return founderList;
    }

    public List<User> signIn() {
        List<User> founderList = null;
        try {
            Response response = ConnectAPI.excuteHttpMethod("", signIn, "GET", false);
            Type typeOfT = new TypeToken<ArrayList<User>>() {
            }.getType();
            founderList = gson.fromJson(response.getMessage(), typeOfT);

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        if (founderList.isEmpty()) {
            return null;
        }
        return founderList;
    }
    
    public UserOutput getUserInOnePage(int pageNo) {
        String str = String.format(getItemInOnePage, "true", pageNo);
        System.out.println(str);
        UserOutput founderList = null;
        try {
            Response response = ConnectAPI.excuteHttpMethod("", str, "GET", true);
            founderList = gson.fromJson(response.getMessage(), UserOutput.class);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return founderList;
    }

    public UserOutput getUserDeleted(int pageNo) {
        String str = String.format(getItemInOnePage, "false", pageNo);
        System.out.println(str);
        UserOutput founderList = null;
        try {
            Response response = ConnectAPI.excuteHttpMethod("", str, "GET", true);
            founderList = gson.fromJson(response.getMessage(), UserOutput.class);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return founderList;
    }

    public void loadTable(List<User> list, DefaultTableModel dtm) {
        dtm.setNumRows(0);
        Vector vt;
        for (User u : list) {
            vt = new Vector();
            vt.add(u.getId());
            vt.add(u.getUsername());
            vt.add(u.getLastName());
            vt.add(u.getFirstName());
            vt.add(u.getEmail());
            vt.add(u.getPhone());
            if (!u.getAddresses().isEmpty()) {
                vt.add(u.getAddresses().get(0).getWard().getDistrict().getProvince().getProvinceName());
            } else {
                vt.add("None");
            }
            dtm.addRow(vt);
        }
    }

    public Image getImage(String imageName) {
        Image img = null;
        try {
            img = ConnectAPI.getImageHasAuthentication(getImage + imageName);
        } catch (IOException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return img;
    }
}
