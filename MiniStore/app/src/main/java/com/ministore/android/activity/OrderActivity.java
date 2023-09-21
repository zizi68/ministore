package com.ministore.android.activity;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.ministore.android.R;
import com.ministore.android.adapter.OrderStatusViewPagerAdapter;
import com.ministore.android.model.OrderStatus;

public class OrderActivity extends AppCompatActivity {

    public static final String KEY_ORDER_STATUS = "order_status";

    private Toolbar toolbar;
    private NestedScrollView nestedScrollView;
    private TabLayout tabLayout;
    private ViewPager2 viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        setControl();
        setEvent();
        loadOrderStatus();
    }

    private void setControl() {
        toolbar = findViewById(R.id.toolbar);
        nestedScrollView = findViewById(R.id.nested_scroll_view);
        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("");
        }
    }

    private void setEvent() {
        toolbar.setNavigationOnClickListener(view -> {
            finish();
        });
    }

    private void loadOrderStatus() {
        OrderStatus selectedOrderStatus = (OrderStatus) getIntent().getExtras().get(KEY_ORDER_STATUS);
        OrderStatusViewPagerAdapter orderStatusViewPagerAdapter = new OrderStatusViewPagerAdapter(OrderActivity.this);
//        viewPager.setOffscreenPageLimit(1);
        viewPager.setAdapter(orderStatusViewPagerAdapter);
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                nestedScrollView.scrollTo(0, 0);
            }
        });
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            OrderStatus orderStatus = orderStatusViewPagerAdapter.getItemAt(position);
            tab.setText(orderStatus.getStatus().getDescription());
            if (orderStatus.getStatus().getId() == selectedOrderStatus.getStatus().getId()) {
                Log.e("xxx", String.format("%s - %s", orderStatus.getStatus().getId(), selectedOrderStatus.getStatus().getId()));
                viewPager.setCurrentItem(position);
            }
        }).attach();
    }
}