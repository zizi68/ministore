package com.ministore.android.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.ministore.android.MyApplication;
import com.ministore.android.R;
import com.ministore.android.adapter.LoginViewPagerAdapter;

public class LoginActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager2 viewPager;
//    private FloatingActionButton fabGoogle, fabFacebook;
    private LoginViewPagerAdapter loginViewPagerAdapter;

    float v = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setControl();
        setAnimation();
    }

    private void setControl() {
        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager_login);
//        fabGoogle = findViewById(R.id.fab_google);
//        fabFacebook = findViewById(R.id.fab_facebook);

        loginViewPagerAdapter = new LoginViewPagerAdapter(this);
        viewPager.setAdapter(loginViewPagerAdapter);

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Login");
                    break;
                case 1:
                    tab.setText("Sign up");
                    break;
            }
        }).attach();
    }

    private void setAnimation() {
//        fabGoogle.setTranslationY(300);
//        fabFacebook.setTranslationY(300);
        tabLayout.setTranslationY(300);
//        fabGoogle.setAlpha(v);
//        fabFacebook.setAlpha(v);
        tabLayout.setAlpha(v);

//        fabGoogle.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(400).start();
//        fabFacebook.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(600).start();
        tabLayout.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(100).start();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        MyApplication.goToMainActivity(this);
    }
}