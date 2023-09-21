package com.ministore.android.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ministore.android.model.Address;
import com.ministore.android.model.Brand;
import com.ministore.android.model.Cart;
import com.ministore.android.model.Category;
import com.ministore.android.model.District;
import com.ministore.android.model.Feedback;
import com.ministore.android.model.JwtResponse;
import com.ministore.android.model.LoginRequest;
import com.ministore.android.model.MyItem;
import com.ministore.android.model.Order;
import com.ministore.android.model.OrderDetail;
import com.ministore.android.model.OrderSummaryDTO;
import com.ministore.android.model.Poster;
import com.ministore.android.model.Product;
import com.ministore.android.model.ProductOutput;
import com.ministore.android.model.Province;
import com.ministore.android.model.ResponseObject;
import com.ministore.android.model.SignupRequest;
import com.ministore.android.model.UpdatePasswordRequest;
import com.ministore.android.model.UpdateProfileRequest;
import com.ministore.android.model.User;
import com.ministore.android.model.Ward;
import com.ministore.android.request.CartRequest;
import com.ministore.android.response.ResponseBody;

import java.util.List;

import okhttp3.MultipartBody;
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

    @POST("category")
    Call<ResponseBody> insertCategory(@Body Category category);

    @PUT("category/{id}")
    Call<ResponseBody> updateCategory(@Path("id") long id, @Body Category category);

    @DELETE("category/{id}")
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
    @GET("feedbacks/{userId}")
    Call<List<Feedback>> getFeedbackByUserIdAndProductId(@Header("Authorization") String authorization,
                                                         @Path("userId") int userId, @Query("productId") int productId);
    @POST("feedbacks")
    Call<ResponseObject> insertFeedback(@Header("Authorization") String authorization, @Body Feedback feedback);

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
    @GET("shipper/order")
    Call<List<Order>> getShipperOrderByStatus(@Header("Authorization") String authorization, @Query("statusId") int statusId, @Query("shipperId") int shipperId);
    @GET("order/order-summary")
    Call<OrderSummaryDTO> getOrderSummaryByOrderId(@Header("Authorization") String authorization, @Query("orderId") int orderId);
    @POST("order/repurchase/{id}")
    Call<ResponseObject> repurchaseByOrderId(@Header("Authorization") String authorization, @Path("id") int orderId);

    /*
        Order detail
    */
    @POST("order-detail/{id}")
    Call<ResponseObject> insertOrderDetailByOrderId(@Header("Authorization") String authorization,
                                                    @Path("id") int userId, @Body List<OrderDetail> orderDetails);

    @GET("order/order-detail")
    Call<List<OrderDetail>> getOrderDetailByOrderId(@Header("Authorization") String authorization, @Query("orderId") int orderId);

    @Multipart
    @POST("image/product")
    Call<String> uploadProductImage(@Header("Authorization") String authorization, @Part MultipartBody.Part image);
}
