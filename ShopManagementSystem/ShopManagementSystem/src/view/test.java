/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import com.google.gson.Gson;
import controller.CategoryController;
import controller.LoginController;
import controller.ProductController;
import java.io.IOException;
import model.Login;
import model.Product;
import model.Response;
import output.ResponseMessage;
import utils.ConnectAPI;

/**
 *
 * @author TRINH
 */
public class test {

    public static void main(String[] args) throws IOException {
        Gson gson = new Gson();
        ProductController pc = new ProductController();
        CategoryController cc = new CategoryController();
        /*
        Product p = pc.getProductsById(11);
        if(p != null) {
            System.out.println(p.getBrand());
            System.out.println(p.getCategory());
            System.out.println(p.getName());
            System.out.println(p.getProductId());
            System.out.println(p.getCalculationUnit());
        }
        
        for (Product pp : pc.getAllProducts()) {
            System.out.println(pp.getBrand());
            System.out.println(pp.getCategory());
            System.out.println(pp.getName());
            System.out.println(pp.getProductId());
            System.out.println(pp.getCalculationUnit());
        }
         */

        LoginController loginController = new LoginController();
        Login output = loginController.login("reintrinh", "123456");
        System.out.println(output.getRoles().toArray()[0]);
        ConnectAPI.tokenType = output.getTokenType();
        ConnectAPI.accessToken = output.getAccessToken();
        Response res = ConnectAPI.excuteHttpMethod("", "/api/category", "POST", true);
//        ResponseMessage str = gson.fromJson(res.getMessage(), ResponseMessage.class);
        System.out.println(res.getMessage());
    }
}
