package com.ministore.android.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.ministore.android.adapter.ResponseTestString;
import com.ministore.android.model.Address;
import com.ministore.android.model.Brand;
import com.ministore.android.model.Cart;
import com.ministore.android.model.Category;
import com.ministore.android.model.District;
import com.ministore.android.model.Feedback;
import com.ministore.android.model.Import;
import com.ministore.android.model.ImportDetail;
import com.ministore.android.model.JwtResponse;
import com.ministore.android.model.LoginRequest;
import com.ministore.android.model.MyItem;
import com.ministore.android.model.Order;
import com.ministore.android.model.OrderDetail;
import com.ministore.android.model.OrderStatus;
import com.ministore.android.model.OrderSummaryDTO;
import com.ministore.android.model.Poster;
import com.ministore.android.model.Product;
import com.ministore.android.model.ProductOutput;
import com.ministore.android.model.Province;
import com.ministore.android.model.ResponseCastObject;
import com.ministore.android.model.ResponseObject;
import com.ministore.android.model.Return;
import com.ministore.android.model.ReturnDetail;
import com.ministore.android.model.ReturnSummaryDTO;
import com.ministore.android.model.SignupRequest;
import com.ministore.android.model.UpdatePasswordRequest;
import com.ministore.android.model.UpdateProfileRequest;
import com.ministore.android.model.User;
import com.ministore.android.model.Ward;
import com.ministore.android.request.CartRequest;
import com.ministore.android.response.ResponseBody;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    String BASE_URL = "http://172.20.10.7:80/api/";

    String POSTER_IMAGE_URL = BASE_URL + "posters/";
    String CATEGORY_IMAGE_URL = BASE_URL + "category/image/";
    String PRODUCT_IMAGE_URL = BASE_URL + "product/image/";
    String USER_IMAGE_URL = BASE_URL + "users/image/";

    Gson gson = new GsonBuilder().setLenient().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z+7'").create();

//    OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
//            .connectTimeout(40, TimeUnit.SECONDS)
//            .readTimeout(60, TimeUnit.SECONDS)
//            .writeTimeout(60, TimeUnit.SECONDS)
//            .build();

    ApiService apiService = new Retrofit.Builder()
//            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService.class);

    /*
        Poster
    */
    @GET("posters")
    Call<List<Poster>> getListPosters();

    /*
        Category
    */
    @GET("category/all")
    Call<List<Category>> getListCategories();

    @POST("admin/category")
    Call<ResponseBody> insertCategory(@Body Category category);

    @PUT("admin/category/{id}")
    Call<ResponseBody> updateCategory(@Path("id") long id, @Body Category category);

    @DELETE("admin/category/{id}")
    Call<ResponseBody> deleteCategory(@Path("id") long id);

    /*
        Product
    */
    @GET("product")
    Call<ProductOutput> findProducts(@Query("pageNo") Integer pageNo, @Query("pageSize") Integer pageSize,
                                     @Query("sortField") String sortField, @Query("sortDirection") String sortDir,
                                     @Query("categoryId") Long categoryId);

    @GET("product/all")
    Call<List<Product>> getListProducts();

    @GET("product/search")
    Call<List<Product>> searchProducts(@Query("keyword") String keyword);

    @POST("admin/product")
    Call<ResponseBody> insertProduct(@Body Product product);

    @PUT("admin/product/{id}")
    Call<ResponseBody> updateProduct(@Path("id") long id, @Body Product product);

    @DELETE("admin/product/{id}")
    Call<ResponseBody> deleteProduct(@Path("id") long id);

    /*
    * User
    * */

    @GET("admin/user/all")
    Call<List<User>> getListUser();

    @PUT("admin/user/add")
    Call<ResponseBody> saveUser(@Body User user);

    @DELETE("admin/user/{id}")
    Call<ResponseBody> deleteUser(@Path("id") long id);

    /*
        Brand
    */

    @GET("brand/all")
    Call<List<Brand>> getListBrands();

    /*
        Report
    */

    @GET("order/report")
    Call<List<MyItem>> getReport(@Header("Authorization") String authorization);

    /*
        Feedback
    */
    @GET("feedbacks")
    Call<List<Feedback>> getFeedbacksByProductId(@Query("productId") int productId);
    @GET("feedbacks/{orderDetailId}")
    Call<Feedback> getFeedbackByOrderDetailId(@Header("Authorization") String authorization,
                                                         @Path("orderDetailId") int orderDetailId);
    @POST("feedbacks/{orderDetailId}")
    Call<ResponseObject> insertFeedback(@Header("Authorization") String authorization, @Path("orderDetailId") int orderDetailId, @Body Feedback feedback);

    /*
        Authentication
    */
    @POST("auth/signin")
    Call<JwtResponse> authenticateUser(@Body LoginRequest loginRequest);

    @POST("auth/signup")
    Call<ResponseObject> registerUser(@Body SignupRequest signUpRequest);

    /*
        User
    */
    @GET("users/{id}")
    Call<User> getUserById(@Path("id") int userId);

    @PUT("users/edit-profile")
    Call<String> updateProfile(@Body UpdateProfileRequest request);

    @PUT("users/change-password")
    Call<String> updatePassword(@Body UpdatePasswordRequest request);

    @GET("verify/{username}")
    Call<User> getGmailByUsername(@Path("username") String  username);

    @GET("verify/phone/{phone}")
    Call<String> getOTPbyPhone(@Path("phone") String  phone);

    @GET("verify/email/{email}/{OTP}")
    Call<String> getOTPbyEmail(@Path("email") String  email, @Path("OTP") String  OTP);

    @GET("admin/user/change-password/{password}/{username}")
    Call<String> updatePasswordForgot(@Path("password") String  password, @Path("username") String  username);

    /*
        Cart
    */
    @GET("cart")
    Call<List<Cart>> getCartByUserId(@Header("Authorization") String authorization, @Query("userId") int userId);

    @POST("cart")
    Call<ResponseObject> addToCart(@Header("Authorization") String authorization, @Body CartRequest cartRequest);

    @PUT("cart")
    Call<ResponseObject> editCart(@Header("Authorization") String authorization, @Body CartRequest cartRequest);
//    @DELETE("cart/{id}")
//    Call<ResponseBody> deleteCart(@Header("Authorization") String authorization, @Path("id") int id);

    /*
        Image
    */
    @Multipart
    @POST("image/user")
    Call<String> uploadUserImage(@Header("Authorization") String authorization, @Part MultipartBody.Part image);
    @Multipart
    @POST("image/category")
    Call<String> uploadCategoryImage(@Header("Authorization") String authorization, @Part MultipartBody.Part image);

    /*
        Address
    */
    @GET("address/province")
    Call<List<Province>> getAllProvince(@Header("Authorization") String authorization);
    @GET("address/district/{id}")
    Call<List<District>> getAllDistrictByProvinceId(@Header("Authorization") String authorization, @Path("id") int provinceId);
    @GET("address/ward/{id}")
    Call<List<Ward>> getAllWardByDistrictId(@Header("Authorization") String authorization, @Path("id") int districtId);
    @GET("address/address/{id}")
    Call<List<Address>> getAllAddressByUserId(@Header("Authorization") String authorization, @Path("id") int userId);
    @POST("address/address/{id}")
    Call<ResponseObject> saveAddressByUserId(@Header("Authorization") String authorization, @Path("id") int userId, @Body Address address);
    @DELETE("address/address/{id}")
    Call<ResponseObject> deleteAddressById(@Header("Authorization") String authorization, @Path("id") int addressId);

    /*
        Order
    */
    @GET("order/user/{id}")
    Call<List<Order>> getOrdersByUserIdAndStatusIdOrderByDateDesc(
            @Header("Authorization") String authorization,
            @Path("id") int userId, @Query("statusId") int statusId);
    @POST("order/{id}")
    Call<ResponseObject> insertOrderByUserId(@Header("Authorization") String authorization, @Path("id") int userId, @Body Order order);
    @PUT("order/{id}")
    Call<ResponseObject> changeOrderStatus(@Header("Authorization") String authorization, @Path("id") int orderId, @Query("statusId") int statusId);
    @PUT("order/{id}")
    Call<ResponseObject> payOrder(@Header("Authorization") String authorization, @Path("id") int orderId, @Query("statusId") int statusId, @Query("payment") String payment);
    @GET("order")
    Call<List<Order>> getOrderByStatus(@Header("Authorization") String authorization, @Query("statusId") int statusId);
    @GET("order/order-summary")
    Call<OrderSummaryDTO> getOrderSummaryByOrderId(@Header("Authorization") String authorization, @Query("orderId") int orderId);
    @POST("order/repurchase/{id}")
    Call<ResponseObject> repurchaseByOrderId(@Header("Authorization") String authorization, @Path("id") int orderId);

    /*
        Return
    */
    @GET("return/user/{id}")
    Call<List<Order>> getReturnsByUserId(@Header("Authorization") String authorization, @Path("id") int userId);
    @POST("return/{id}")
    Call<ResponseObject> insertReturnByOrderId(@Header("Authorization") String authorization, @Path("id") int orderId, @Body Return return0);
    @PUT("return/{id}")
    Call<ResponseObject> changeReturnStatus(@Header("Authorization") String authorization, @Path("id") int returnId, @Query("statusId") int statusId);
    @GET("return/return-summary")
    Call<ReturnSummaryDTO> getReturnSummaryByReturnId(@Header("Authorization") String authorization, @Query("returnId") int returnId);
    @POST("return/return-detail/{id}")
    Call<ResponseObject> insertReturnDetailByReturnId(@Header("Authorization") String authorization,
                                                    @Path("id") int returnId, @Body List<ReturnDetail> returnDetails);

    @GET("return/return-detail")
    Call<List<ReturnDetail>> getReturnDetailByReturnId(@Header("Authorization") String authorization, @Query("returnId") int returnId);
    @GET("return/order/{orderId}")
    Call<Return> getReturnByOrderId(@Header("Authorization") String authorization, @Path("orderId") int orderId);


    /*
        Import
    */
    @GET("admin/import")
    Call<List<Import>> getAllImports(@Header("Authorization") String authorization);

    @GET("admin/import/{id}")
    Call<Import> getImportById(@Header("Authorization") String authorization, @Path("id") int id);

    @GET("admin/import/import-detail")
    Call<List<ImportDetail>> getImportDetailByImportId(@Header("Authorization") String authorization, @Query("importId") int importId);

    @POST("admin/import")
    Call<ResponseObject> insertImportByUserId(@Header("Authorization") String authorization, @Query("userId") int userId, @Query("totalPrice") float totalPrice, @Body List<ImportDetail> list);

    /*
        Order detail
    */
    @POST("order-detail/{id}")
    Call<ResponseObject> insertOrderDetailByOrderId(@Header("Authorization") String authorization,
                                                    @Path("id") int orderId, @Body List<OrderDetail> orderDetails);

    @GET("order/order-detail")
    Call<List<OrderDetail>> getOrderDetailByOrderId(@Header("Authorization") String authorization, @Query("orderId") int orderId);

    @Multipart
    @POST("image/product")
    Call<String> uploadProductImage(@Header("Authorization") String authorization, @Part MultipartBody.Part image);
}
