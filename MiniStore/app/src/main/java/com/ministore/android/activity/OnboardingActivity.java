package com.ministore.android.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ministore.android.R;
import com.ministore.android.adapter.OnboardingViewPagerAdapter;
import com.ministore.android.model.Onboarding;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator3;

public class OnboardingActivity extends AppCompatActivity {

    private TextView tvSkip;
    private ViewPager2 viewPager;
    private CircleIndicator3 circleIndicator;
    private AppCompatButton btnPrevious, btnNext;

    private OnboardingViewPagerAdapter onboardingViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        setControl();
        setEvent();
    }

    private void setControl() {
        tvSkip = findViewById(R.id.tv_skip);
        viewPager = findViewById(R.id.view_pager);
        circleIndicator = findViewById(R.id.circle_indicator);
        btnPrevious = findViewById(R.id.btn_previous);
        btnNext = findViewById(R.id.btn_next);

        onboardingViewPagerAdapter = new OnboardingViewPagerAdapter(this, getListOnboarding());
        viewPager.setAdapter(onboardingViewPagerAdapter);
        circleIndicator.setViewPager(viewPager);
    }

    private void setEvent() {
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    btnPrevious.setVisibility(View.INVISIBLE);
                    btnNext.setVisibility(View.VISIBLE);
                }
                else if (position == onboardingViewPagerAdapter.getItemCount() - 1) {
                    btnPrevious.setVisibility(View.VISIBLE);
                    btnNext.setVisibility(View.INVISIBLE);
                }
                else {
                    btnPrevious.setVisibility(View.VISIBLE);
                    btnNext.setVisibility(View.VISIBLE);
                }
                tvSkip.setVisibility(btnNext.getVisibility());
            }
        });
        tvSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(onboardingViewPagerAdapter.getItemCount() - 1);
            }
        });
        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
            }
        });
    }

    private List<Onboarding> getListOnboarding() {
        List<Onboarding> list = new ArrayList<>();
        list.add(new Onboarding(R.drawable.img_cute_book_pencil_cartoon, "High quality",
                "We guarantee everything you buy here is high quality products"));
        list.add(new Onboarding(R.drawable.img_fast_delivery, "Fast delivery",
                "All you need to do is order your products from your device"));
        list.add(new Onboarding(R.drawable.img_easy_payment, "Easy payment",
                "Both credit card and cash on delivery are available for your ease"));
        return list;
    }
}