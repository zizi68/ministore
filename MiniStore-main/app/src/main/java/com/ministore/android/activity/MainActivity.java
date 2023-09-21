package com.ministore.android.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager2.widget.ViewPager2;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.google.android.material.navigation.NavigationView;
import com.ministore.android.MyApplication;
import com.ministore.android.R;
import com.ministore.android.adapter.MainViewPagerAdapter;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    public static final int FRAGMENT_HOME = 0;
    public static final int FRAGMENT_ACCOUNT = 1;
    private int currentFragment = FRAGMENT_HOME;

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private BottomNavigationBar bottomNavigationBar;
    private Menu navMenu;
    private ViewPager2 viewPager;
    private MainViewPagerAdapter mainViewPagerAdapter;


//

    ActionBar actionBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setControl();
        setEvent();
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

    private void setControl() {
        toolbar = findViewById(R.id.toolbarMain);
        drawerLayout = findViewById(R.id.drawer_layout);
        viewPager = findViewById(R.id.view_pager);
        navigationView = findViewById(R.id.navigation_view);
        bottomNavigationBar = findViewById(R.id.bottom_navigation);

        navMenu = navigationView.getMenu();

        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_category, null);
        upArrow.setColorFilter(Color.parseColor("#E14D2A"), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("");
        }

        viewPager.setOffscreenPageLimit(1);
        bottomNavigationBar.setInActiveColor("#FFFFFF");
        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.ic_home, getString(R.string.home))
                        .setActiveColorResource(R.color.blue_bg_light))
                .addItem(new BottomNavigationItem(R.drawable.ic_person, getString(R.string.account))
                        .setActiveColorResource(R.color.blue_active))
                .setFirstSelectedPosition(0)
                .initialise();
    }

    private void setEvent() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.getDrawerArrowDrawable().setColor(ContextCompat.getColor(this, R.color.colorPrimaryVariant));
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().findItem(R.id.nav_home).setChecked(true);

        bottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                openFragment(position);
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {

            }
        });

        mainViewPagerAdapter = new MainViewPagerAdapter(this);
        viewPager.setAdapter(mainViewPagerAdapter);
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position) {
                    case 0:
                        currentFragment = FRAGMENT_HOME;
                        navigationView.getMenu().findItem(R.id.nav_home).setChecked(true);
                        break;
                    case 1:
                        currentFragment = FRAGMENT_ACCOUNT;
                        navigationView.getMenu().findItem(R.id.nav_account).setChecked(true);
                        break;
                }
                bottomNavigationBar.selectTab(position);
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_home:
                openFragment(0);
                toolbar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#E14D2A")));
                setSupportActionBar(toolbar);
                break;
            case R.id.nav_account:
                openFragment(1);
                toolbar.setBackgroundColor(getColor(R.color.dark_red));
                setSupportActionBar(toolbar);
                break;

            case R.id.nav_login:
                MyApplication.goToLoginActivity(this);
                break;
            case R.id.nav_logout:
                MyApplication.signOut(this);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void openFragment(int position) {
        if (currentFragment != position) {
            viewPager.setCurrentItem(position);
        }
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

}