/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package service;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 *
 * @author TRINH
 */
public interface UploadFileService {

    @Multipart
    @POST("image/product")
    Call<ResponseBody> uploadProductImage(@Part MultipartBody.Part part);

    @Multipart
    @POST("image/category")
    Call<ResponseBody> uploadCategoryImage(@Part MultipartBody.Part part);
    
    @Multipart
    @POST("image/user")
    Call<ResponseBody> uploadUserImage(@Part MultipartBody.Part part);
}
