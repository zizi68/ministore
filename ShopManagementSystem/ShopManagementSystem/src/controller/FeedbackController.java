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
import model.Category;
import model.Feedback;
import model.Response;
import utils.ConnectAPI;

/**
 *
 * @author TRINH
 */
public class FeedbackController extends BaseController{

    public FeedbackController() {
        getAll = "/api/feedbacks?productId=";
    }

    public List<Feedback> getFeedbackByProductId(String productId) {
        List<Feedback> founderList = null;
        try {
            Response reponse = ConnectAPI.excuteHttpMethod("", getAll + productId, "GET", true);
            Type typeOfT = new TypeToken<ArrayList<Feedback>>() {}.getType();
            founderList = gson.fromJson(reponse.getMessage(), typeOfT);
        } catch (IOException ex) {
            Logger.getLogger(CategoryController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return founderList;
    }
}
