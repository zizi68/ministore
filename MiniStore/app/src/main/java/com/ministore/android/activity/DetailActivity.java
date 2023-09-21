package com.ministore.android.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ministore.android.MyApplication;
import com.ministore.android.R;
import com.ministore.android.adapter.DetailAdapter;
import com.ministore.android.adapter.FeedbackAdapter;
import com.ministore.android.adapter.ProductAdatper;
import com.ministore.android.api.ApiService;
import com.ministore.android.fragment.RemoveCartBottomSheetFragment;
import com.ministore.android.model.Cart;
import com.ministore.android.model.Detail;
import com.ministore.android.model.Feedback;
import com.ministore.android.model.Product;
import com.ministore.android.model.ProductOutput;
import com.ministore.android.util.AnimationUtil;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.nikartm.support.ImageBadgeView;

public class DetailActivity extends AppCompatActivity implements MyApplication.OnCartActionListener {

    private Toolbar toolbar;
    private ImageBadgeView ibvCartItemCount;
    private ImageView animationView;
    private View endView;

    private ImageView imgImage;
    private TextView tvName, tvSoldQuantity, tvPriceBefore, tvPriceAfter, tvDiscount;
    private TextView tvDescription;
    private TextView tvNumComment, tvRateSummary, tvNumrateSummary;
    private RatingBar rtbRate, rtbRateSummary;
    private RecyclerView rcvDetails, rcvComments, rcvProducts;
    private EditText edtQuantity;
    private ImageButton btnMinus, btnPlus;
    private AppCompatButton btnAddToCart;
    private View layoutSelectQuantity;

    private NestedScrollView nestedScrollView;
    private ProgressBar progressBar;
    private boolean isLoadingData;
    private boolean isLastPage;
    private int currentPage = 0;
    private int totalPage;
    private static final int PAGE_SIZE = 10;
    private ProductAdatper productAdatper;

    private Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        setControl();
        setEvent();
        loadDetail();
        loadComments();
        loadProducts();
        loadCartInfo();
    }

    private void loadCartInfo() {
        Cart cart = MyApplication.cartAdapter.getCartByProductId(product.getProductId());
        if (cart != null) {
            layoutSelectQuantity.setVisibility(View.VISIBLE);
            edtQuantity.setText(String.valueOf(cart.getQuantity()));
            btnMinus.setOnClickListener(view -> {
                if (cart.getQuantity() <= 1) {
                    showRemoveCartBottomSheet(Arrays.asList(cart));
                }
                else {
                    MyApplication.addToCart(cart.getProduct(), -1, this);
                }
            });
            btnPlus.setOnClickListener(view -> {
                if (!MyApplication.checkUserLogged()) {
                    MyApplication.goToLoginActivity(this);
                    return;
                }
                MyApplication.addToCart(cart.getProduct(), 1, this);
            });
            edtQuantity.setOnFocusChangeListener((view, hasFocus) -> {
                if (hasFocus) return;
                // Hide keyboard after edittext lost focus
                InputMethodManager imm = (InputMethodManager)this.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(edtQuantity.getWindowToken(), 0);
                int quantity;
                try {
                    quantity = Integer.parseInt(edtQuantity.getText().toString());
                }
                catch (NumberFormatException ex) {
                    quantity = 1;
                }
                if (quantity <= 0) {
                    quantity = 1;
                }
                MyApplication.editCart(cart.getProduct(), quantity, this);
            });
            btnAddToCart.setOnClickListener(view -> {
                edtQuantity.clearFocus();
            });
        }
        else {
            layoutSelectQuantity.setVisibility(View.INVISIBLE);
            btnAddToCart.setOnClickListener(view -> {
                MyApplication.addToCart(product, 1, DetailActivity.this);
            });
        }
    }

    private void loadDetail() {
        Bundle bundle = getIntent().getExtras();
        product = (Product) bundle.get(MyApplication.KEY_PRODUCT);
        if (product == null) return;
        Picasso.get().load(ApiService.PRODUCT_IMAGE_URL + product.getImage()).into(imgImage);
        tvName.setText(product.getName());
        int soldQuantity = product.getSoldQuantity();
        if (soldQuantity > 0) {
            tvSoldQuantity.setVisibility(View.VISIBLE);
            tvSoldQuantity.setText(String.format("%s sold", product.getSoldQuantity()));
        } else {
            tvSoldQuantity.setVisibility(View.INVISIBLE);
        }
        NumberFormat numberFormat = new DecimalFormat("###,###");
        int discount = product.getDiscount();
        if (discount > 0) {
            tvDiscount.setVisibility(View.VISIBLE);
            tvPriceBefore.setVisibility(View.VISIBLE);
            tvDiscount.setText(String.format("-%s%%", product.getDiscount()));
            tvPriceBefore.setText(String.format("%s đ", numberFormat.format(product.getPrice())));
        } else {
            tvDiscount.setVisibility(View.INVISIBLE);
            tvPriceBefore.setVisibility(View.INVISIBLE);
        }
        double priceAfterDiscount = product.getPrice() * (100 - discount) / 100;
        tvPriceAfter.setText(String.format("%s đ", numberFormat.format(priceAfterDiscount)));

        DetailAdapter detailAdapter = new DetailAdapter(getListDetail(product));
        rcvDetails.setAdapter(detailAdapter);
        tvDescription.setText(product.getDescription());
    }

    private void showRemoveCartBottomSheet(List<Cart> cartList) {
        RemoveCartBottomSheetFragment fragment = RemoveCartBottomSheetFragment.newInstance(cartList);
        fragment.setOnClickListener(new RemoveCartBottomSheetFragment.onClickListener() {
            @Override
            public void onButtonRemoveClick() {
                for (Cart cart: cartList) {
                    MyApplication.editCart(cart.getProduct(), 0, DetailActivity.this);
                }
            }

            @Override
            public void onButtonCancelClick() {

            }
        });
        fragment.show(getSupportFragmentManager(), fragment.getTag());
    }

    private void setEvent() {
        toolbar.setNavigationOnClickListener(view -> {
            finish();
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            nestedScrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    if (nestedScrollView.getChildAt(0).getBottom() <= (nestedScrollView.getHeight() + scrollY)) {
                        if (isLoadingData || isLastPage) return;
                        loadProducts();
                    }
                }
            });
        }
        ibvCartItemCount.setOnClickListener(view -> {
            if (!MyApplication.checkUserLogged()) {
                MyApplication.goToLoginActivity(DetailActivity.this);
                return;
            }
            Intent intent = new Intent(this, CartActivity.class);
            startActivity(intent);
        });
    }

    public void showMessage(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void setControl() {
        toolbar = findViewById(R.id.toolbar);
        ibvCartItemCount = findViewById(R.id.ibv_cart_item_count);
        animationView = findViewById(R.id.animation_view);
        endView = findViewById(R.id.end_view);

        imgImage = findViewById(R.id.img_image);
        tvName = findViewById(R.id.tv_name);
        tvSoldQuantity = findViewById(R.id.tv_sold_quantity);
        tvPriceBefore = findViewById(R.id.tv_price_before_discount);
        tvPriceBefore.setPaintFlags(tvPriceBefore.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        tvPriceAfter = findViewById(R.id.tv_price_after_discount);
        tvDiscount = findViewById(R.id.tv_discount);
        tvDescription = findViewById(R.id.tv_description);
        tvNumComment = findViewById(R.id.tv_num_comment);
        tvRateSummary = findViewById(R.id.tv_rate_summary);
        tvNumrateSummary = findViewById(R.id.tv_numrate_summary);
        rtbRate = findViewById(R.id.rtb_rate);
        rtbRateSummary = findViewById(R.id.rtb_rate_summary);
        nestedScrollView = findViewById(R.id.nested_scroll_view);
        progressBar = findViewById(R.id.progress_bar);
        edtQuantity = findViewById(R.id.edt_quantity);
        btnMinus = findViewById(R.id.btn_minus);
        btnPlus = findViewById(R.id.btn_plus);
        btnAddToCart = findViewById(R.id.btn_add_to_cart);
        layoutSelectQuantity = findViewById(R.id.layout_select_quantity);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("");
        }
        ibvCartItemCount.setBadgeValue(MainActivity.getCartItemCount());

        rcvDetails = findViewById(R.id.rcv_detail);
        rcvDetails.setHasFixedSize(true);
        rcvDetails.setLayoutManager(new LinearLayoutManager(this));
        rcvDetails.setFocusable(false);
        rcvDetails.setNestedScrollingEnabled(false);
        RecyclerView.ItemDecoration itemDetail = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rcvDetails.addItemDecoration(itemDetail);

        rcvComments = findViewById(R.id.rcv_comments);
        rcvComments.setHasFixedSize(true);
        rcvComments.setLayoutManager(new LinearLayoutManager(this));
        rcvComments.setFocusable(false);
        rcvComments.setNestedScrollingEnabled(false);
        RecyclerView.ItemDecoration itemComment = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rcvComments.addItemDecoration(itemComment);

        rcvProducts = findViewById(R.id.rcv_products);
        rcvProducts.setHasFixedSize(true);
        rcvProducts.setLayoutManager(new GridLayoutManager(this, 2));
        rcvProducts.setFocusable(false);
        rcvProducts.setNestedScrollingEnabled(false);
        List<Product> productList = new ArrayList<>();
        productAdatper = new ProductAdatper(productList);
        productAdatper.setOnClickListener(new ProductAdatper.OnClickListener() {
            @Override
            public void onClick(Product product) {
                MyApplication.viewDetail(getApplicationContext(), product);
            }

            @Override
            public void onAddToCartClick(ImageView imgProduct, Product product) {
                AnimationUtil.translateAnimation(animationView, imgProduct, endView, new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        MyApplication.addToCart(product, 1, DetailActivity.this);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }
        });
        rcvProducts.setAdapter(productAdatper);
    }

    private List<Detail> getListDetail(Product product) {
        List<Detail> detailList = new ArrayList<>();
        detailList.add(new Detail("Brand", product.getBrand().getName()));
        detailList.add(new Detail("Category", product.getCategory().getName()));
        detailList.add(new Detail("Specification", product.getSpecification()));
        detailList.add(new Detail("Calculation unit", product.getCalculationUnit()));
        return detailList;
    }

    private void loadComments() {
        if (product == null) return;
        ApiService.apiService.getFeedbacksByProductId(product.getProductId()).enqueue(new Callback<List<Feedback>>() {
            @Override
            public void onResponse(Call<List<Feedback>> call, Response<List<Feedback>> response) {
                List<Feedback> feedbackList = response.body();
                if (feedbackList == null || feedbackList.isEmpty()) {
                    rtbRate.setRating(0);
                    rtbRateSummary.setRating(0);
                    tvRateSummary.setText(String.valueOf(0.0));
                    tvNumrateSummary.setText("(0 rate)");
                    return;
                }
                tvNumComment.setText(String.format("%d comments", feedbackList.size()));
                FeedbackAdapter feedbackAdapter = new FeedbackAdapter(feedbackList);
                rcvComments.setAdapter(feedbackAdapter);
                int sum = 0;
                for(Feedback f : feedbackList){
                    sum += f.getVote();
                }

                float rate = (float)sum/feedbackList.size();
                rtbRate.setRating(rate);
                rtbRateSummary.setRating(rate);
                tvRateSummary.setText(String.valueOf(rate));
                tvNumrateSummary.setText("(" + feedbackList.size() + " rate)");
            }

            @Override
            public void onFailure(Call<List<Feedback>> call, Throwable t) {
                Toast.makeText(DetailActivity.this, t.getMessage() + "!!!!", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void loadProducts() {
        ++currentPage;
        isLoadingData = true;
        progressBar.setVisibility(View.VISIBLE);
        ApiService.apiService.findProducts(currentPage, PAGE_SIZE, "id", "ASC",
                (long) product.getCategory().getCategoryId())
                .enqueue(new Callback<ProductOutput>() {
                    @Override
                    public void onResponse(Call<ProductOutput> call, Response<ProductOutput> response) {
                        ProductOutput productOutput = response.body();
                        if (productOutput == null) return;

                        totalPage = productOutput.getTotalPage();
                        List<Product> productList = productOutput.getListResult();
                        if (productList == null || productList.isEmpty()) return;

                        int index = -1;
                        for (int i = 0; i < productList.size(); ++i) {
                            if (productList.get(i).getProductId() == product.getProductId()) {
                                index = i;
                            }
                        }
                        if(index != -1)
                            productList.remove(index);
                        productAdatper.addData(productList);
                        isLoadingData = false;
                        progressBar.setVisibility(View.INVISIBLE);
                        if (currentPage >= totalPage) {
                            isLastPage = true;
                        }
                    }

                    @Override
                    public void onFailure(Call<ProductOutput> call, Throwable t) {
                        showMessage(t.getMessage());
                    }
                });
    }

    private void updateCartItemCount() {
        if (MyApplication.cartAdapter == null) return;
        if (ibvCartItemCount == null) return;
        int count = MyApplication.cartAdapter.getCartItemCount();
        if (count > 0) {
            ibvCartItemCount.setVisibility(View.VISIBLE);
            ibvCartItemCount.setBadgeValue(count);
        }
        else {
            ibvCartItemCount.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateCartItemCount();
    }

    @Override
    public void onAuthenticationFailed() {
        MyApplication.goToLoginActivity(DetailActivity.this);
    }

    @Override
    public void onCartShowMessage(String message) {
        showMessage(message);
        loadCartInfo();
        updateCartItemCount();
    }
}