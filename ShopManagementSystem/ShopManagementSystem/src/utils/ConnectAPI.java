/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import model.Response;

/**
 *
 * @author TRINH
 */
public class ConnectAPI {

    public static final String LOCALHOST = "http://localhost:80";
    public static String tokenType;
    public static String accessToken;

    public static Response excuteHttpMethod(String json, String link, String type, boolean authentication) throws MalformedURLException, IOException {
        URL url = new URL(LOCALHOST + link); // địa chỉ api
        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        con.setRequestMethod(type); //type: POST, PUT, DELETE, GET

        if (authentication == true) {
            con.setRequestProperty("Authorization", tokenType + " " + accessToken);
        }

        //add request header if type is POST, PUT
        if (type.equals("POST") || type.equals("PUT")) {
            con.setDoOutput(true); // Truyền là true để biểu thị rằng connection sẽ được sử dụng cho output. Giá trị mặc định là false
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Content-Length", String.valueOf(json.getBytes("UTF-8").length));
            con.getOutputStream().write(json.getBytes("UTF-8"));
        }
        int responseCode = con.getResponseCode();
        con.getErrorStream();
        System.out.println("\nSending '" + type + "' request to URL: " + LOCALHOST + link);
        System.out.println("Response Code: " + responseCode);
        BufferedReader in = null;
        if ((responseCode >= 200) && (responseCode <= 202)) {
            in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        } else {
            in = new BufferedReader(new InputStreamReader(con.getErrorStream()));
        }
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return new Response(responseCode, response.toString());
    }

    public static Image getImageHasAuthentication(String link) throws MalformedURLException, IOException {
        Toolkit toolkit = Toolkit.getDefaultToolkit();

        // Absolute URL -
        URL url = new URL(LOCALHOST + link);
        Image monaImage = toolkit.getImage(url);
        return monaImage;
    }
}
