/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.io.IOException;
import model.Login;
import model.Response;
import utils.ConnectAPI;

/**
 *
 * @author TRINH
 */
public class LoginController extends BaseController {

    private String login;

    public LoginController() {
        login = "/api/auth/signin";
    }

    public Login login(String username, String password) {
        Login input = new Login();
        input.setUsername(username);
        input.setPassword(password);

        String json = gson.toJson(input);
        System.out.println("Json: " + json);
        Login output = null;
        try {
            Response response = ConnectAPI.excuteHttpMethod(json, login, "POST", false);
            output = gson.fromJson(response.getMessage(), Login.class);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        return output;
    }
}
