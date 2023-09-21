package com.ministore.android.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.ministore.android.MyApplication;
import com.ministore.android.R;
import com.ministore.android.adapter.CategoryViewPagerAdapter;
import com.ministore.android.adapter.ProductAdatper;
import com.ministore.android.api.ApiService;
import com.ministore.android.model.Category;
import com.ministore.android.model.Product;
import com.ministore.android.util.AnimationUtil;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.nikartm.support.ImageBadgeView;

public class ProductActivity extends AppCompatActivity
        implements ProductAdatper.OnClickListener, MyApplication.OnCartActionListener {

    private Toolbar toolbar;
    private ImageBadgeView ibvCartItemCount;
    private ImageView animationView;
    private View endView;

    private NestedScrollView nestedScrollView;
    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private CategoryViewPagerAdapter categoryViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        setControl();
        setEvent();
        loadCategories();
    }

    private void setControl() {
        toolbar = findViewById(R.id.toolbar);
        ibvCartItemCount = findViewById(R.id.ibv_cart_item_count);
        animationView = findViewById(R.id.animation_view);
        endView = findViewById(R.id.end_view);

        nestedScrollView = findViewById(R.id.nested_scroll_view);
        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("");
        }
        ibvCartItemCount.setBadgeValue(MainActivity.getCartItemCount());
    }

    private void setEvent() {
        toolbar.setNavigationOnClickListener(view -> {
            finish();
        });
        ibvCartItemCount.setOnClickListener(view -> {
            if (!MyApplication.checkUserLogged()) {
                MyApplication.goToLoginActivity(ProductActivity.this);
                return;
            }
            Intent intent = new Intent(this, CartActivity.class);
            startActivity(intent);
        });
    }

    private void loadCategories() {
        ApiService.apiService.getListCategories().enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                List<Category> categoryList = response.body();
                if (categoryList == null || categoryList.isEmpty()) return;
                Category selectedCategory = (Category) getIntent().getExtras().get(MyApplication.KEY_CATEGORY);
                categoryViewPagerAdapter = new CategoryViewPagerAdapter(ProductActivity.this, categoryList);
                viewPager.setAdapter(categoryViewPagerAdapter);
                viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                    @Override
                    public void onPageSelected(int position) {
                        super.onPageSelected(position);
                        nestedScrollView.scrollTo(0, 0);
                    }
                });
                new TabLayoutMediator(tabLayout, viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override
                    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                        Category category = categoryViewPagerAdapter.getItemAt(position);
                        tab.setText(category.getName());
                        if (category.getCategoryId() == selectedCategory.getCategoryId()) {
                            viewPager.setCurrentItem(position);
                        }
                    }
                }).attach();
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
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

    private void viewDetail(Product product) {
        Intent intent = new Intent(this, DetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(MyApplication.KEY_PRODUCT, product);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void showMessage(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
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
                MyApplication.addToCart(product, 1, ProductActivity.this);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
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
}