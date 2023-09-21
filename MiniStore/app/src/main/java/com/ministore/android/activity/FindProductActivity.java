package com.ministore.android.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import com.ministore.android.MyApplication;
import com.ministore.android.R;
import com.ministore.android.adapter.ProductAdatper;
import com.ministore.android.api.ApiService;
import com.ministore.android.fragment.HomeFragment;
import com.ministore.android.model.Product;
import com.ministore.android.model.ProductOutput;
import com.ministore.android.util.AnimationUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.nikartm.support.ImageBadgeView;

public class FindProductActivity extends AppCompatActivity implements ProductAdatper.OnClickListener, MyApplication.OnCartActionListener{

    private String keyword;
    private Toolbar toolbar;
    private ImageBadgeView ibvCartItemCount;
    private ImageView animationView;
    private SearchView svSearchProduct;
    private View endView;
    private RecyclerView rcvProducts;
    private ProductAdatper productAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_product);

        keyword = getIntent().getStringExtra("keyword");

        setControl();
        setEvent();
        loadProducts();

        Toast.makeText(this, keyword, Toast.LENGTH_SHORT).show();
    }

    private void setEvent() {
        toolbar.setNavigationOnClickListener(view -> {
            finish();
        });

        ibvCartItemCount.setOnClickListener(view -> {
            if (!MyApplication.checkUserLogged()) {
                MyApplication.goToLoginActivity(FindProductActivity.this);
                return;
            }
            Intent intent = new Intent(this, CartActivity.class);
            startActivity(intent);
        });

        svSearchProduct.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
//                Toast.makeText(MainActivity.this, "Click search", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), FindProductActivity.class);
                intent.putExtra("keyword", svSearchProduct.getQuery() + "");
                startActivity(intent);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void setControl() {
        toolbar = findViewById(R.id.toolbar);
        svSearchProduct = findViewById(R.id.sv_search_product);
        ibvCartItemCount = findViewById(R.id.ibv_cart_item_count);
        animationView = findViewById(R.id.animation_view);
        endView = findViewById(R.id.end_view);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("");
        }

        ibvCartItemCount.setBadgeValue(MainActivity.getCartItemCount());

        rcvProducts = findViewById(R.id.rcv_search_product);
        rcvProducts.setHasFixedSize(true);
        rcvProducts.setLayoutManager(new GridLayoutManager(this, 2));
        rcvProducts.setFocusable(false);
        rcvProducts.setNestedScrollingEnabled(false);
    }

    private void loadProducts() {
        ApiService.apiService.searchProducts(keyword)
                .enqueue(new Callback<List<Product>>() {
                    @Override
                    public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {

                        List<Product> productList = response.body();
                        if (productList == null || productList.isEmpty()) {
                            Toast.makeText(FindProductActivity.this, "Không tìm thấy sản phẩm", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        ProductAdatper productAdatper = new ProductAdatper(productList);
                        if (FindProductActivity.this instanceof ProductAdatper.OnClickListener) {

                            productAdatper.setOnClickListener((ProductAdatper.OnClickListener) FindProductActivity.this);
                        }
                        rcvProducts.setAdapter(productAdatper);
                    }

                    @Override
                    public void onFailure(Call<List<Product>> call, Throwable t) {
                        Toast.makeText(getParent(), t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
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

    private void viewDetail(Product product) {
        Intent intent = new Intent(this, DetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(MyApplication.KEY_PRODUCT, product);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onAuthenticationFailed() {
        MyApplication.goToLoginActivity(this);
    }

    @Override
    public void onCartShowMessage(String message) {
        showMessage(message);
        updateCartItemCount();
    }

    @Override
    public void onClick(Product product) {
        viewDetail(product);
    }

    @Override
    public void onAddToCartClick(ImageView imgProduct, Product product) {
        AnimationUtil.translateAnimation(animationView, imgProduct, endView, new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                MyApplication.addToCart(product, 1, FindProductActivity.this);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}