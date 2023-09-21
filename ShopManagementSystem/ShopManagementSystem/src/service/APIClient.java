/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import utils.ConnectAPI;

/**
 *
 * @author TRINH
 */
public class APIClient {

    private static Retrofit retrofit;

    public static Retrofit getClient() {
        try {
            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {

                @Override
                public Response intercept(Interceptor.Chain chain) throws IOException {
                    Request originalRequest = chain.request();
                    Request.Builder builder = originalRequest.newBuilder().header("Authorization", ConnectAPI.tokenType + " " + ConnectAPI.accessToken);
                    Request newRequest = builder.build();
                    return chain.proceed(newRequest);
                }

            }).build();

            retrofit = new Retrofit.Builder()
                    .baseUrl("http://localhost:80/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
            return retrofit;
        } catch (Exception ex) {
            return null;
        }
    }
}
