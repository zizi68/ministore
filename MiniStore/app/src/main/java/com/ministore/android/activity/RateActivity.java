package com.ministore.android.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ministore.android.MyApplication;
import com.ministore.android.R;
import com.ministore.android.api.ApiService;
import com.ministore.android.model.Cart;
import com.ministore.android.model.Feedback;
import com.ministore.android.model.FeedbackId;
import com.ministore.android.model.Order;
import com.ministore.android.model.OrderDetail;
import com.ministore.android.model.Product;
import com.ministore.android.model.ResponseObject;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RateActivity extends AppCompatActivity {

    public static final String KEY_PRODUCT = "product";
    public static final String KEY_DETAIL = "detail";

    private Toolbar toolbar;
    private ImageView imgProduct;
    private TextView tvName;
    private EditText edtComment;
    private RatingBar rtbRate;
    private TextView tvRate;
    private Button btnPost;

    private Product product;
    private OrderDetail orderDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);

        setControl();
        setEvent();
        loadDetail();
        loadFeedback();
    }

    private void loadFeedback() {
        if (!MyApplication.checkUserLogged()) {
            MyApplication.goToLoginActivity(this);
            return;
        }
        int userId = MyApplication.getUserId();
        String auth = MyApplication.getAuthorization();
        ApiService.apiService.getFeedbackByOrderDetailId(auth, orderDetail.getId())
                .enqueue(new Callback<Feedback>() {
                    @Override
                    public void onResponse(Call<Feedback> call, Response<Feedback> response) {
                        Feedback feedback = response.body();
                        if (feedback == null) return;
                        rtbRate.setRating(feedback.getVote());
                        edtComment.setText(feedback.getComment());
                    }

                    @Override
                    public void onFailure(Call<Feedback> call, Throwable t) {
//                        showMessage(t.getMessage());
                    }
                });
    }

    private void loadDetail() {
        product = (Product) getIntent().getSerializableExtra(KEY_PRODUCT);
        orderDetail = (OrderDetail) getIntent().getSerializableExtra(KEY_DETAIL);
        if (product == null) return;
        Picasso.get().load(ApiService.PRODUCT_IMAGE_URL + product.getImage()).into(imgProduct);
        tvName.setText(product.getName());
    }

    private void setEvent() {
        toolbar.setNavigationOnClickListener(view -> {
            finish();
        });
        rtbRate.setOnRatingBarChangeListener((ratingBar, v, b) -> {
            tvRate.setText(MyApplication.formatNumber(v));
        });
        btnPost.setOnClickListener(view -> {
            if (!MyApplication.checkUserLogged()) {
                MyApplication.goToLoginActivity(this);
                return;
            }
            int userId = MyApplication.getUserId();
            String auth = MyApplication.getAuthorization();
            String comment = edtComment.getText().toString().trim();
            if (comment.isEmpty()) {
                showMessage("Your comment cannot be empty!");
                return;
            }
            Feedback feedback = new Feedback();
//            feedback.setDate(new Date());
            feedback.setVote((int) rtbRate.getRating());
            feedback.setComment(comment);
            ApiService.apiService.insertFeedback(auth, orderDetail.getId(), feedback)
                    .enqueue(new Callback<ResponseObject>() {
                        @Override
                        public void onResponse(Call<ResponseObject> call, Response<ResponseObject> response) {
                            ResponseObject responseObject;
                            switch (response.code()) {
                                case 401:
                                {
                                    MyApplication.goToLoginActivity(RateActivity.this);
                                    return;
                                }
                                case 400:
                                {
                                    Gson gson = new GsonBuilder().create();
                                    try {
                                        if (response.errorBody() == null) {
                                            showMessage(response.message());
                                            return;
                                        }
                                        responseObject = gson.fromJson(response.errorBody().string(), ResponseObject.class);
                                        showMessage(responseObject.getMessage());
                                    }
                                    catch (IOException e) {
                                        showMessage(e.getMessage());
                                    }
                                }
                                case 200:
                                {
                                    responseObject = response.body();
                                    showMessage(responseObject.getMessage());
                                    finish();
                                    break;
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseObject> call, Throwable t) {
                            showMessage(t.getMessage());
                        }
                    });
        });
    }

    private void setControl() {
        toolbar = findViewById(R.id.toolbar);
        imgProduct = findViewById(R.id.img_product_image);
        tvName = findViewById(R.id.tv_name);
        rtbRate = findViewById(R.id.rtb_rate);
        tvRate = findViewById(R.id.tv_rate);
        edtComment = findViewById(R.id.edt_comment);
        btnPost = findViewById(R.id.btn_post);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                MyApplication.viewSettings(this);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void showMessage(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }
}