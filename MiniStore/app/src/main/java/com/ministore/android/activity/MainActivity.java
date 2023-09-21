package com.ministore.android.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.ashokvarma.bottomnavigation.TextBadgeItem;
import com.google.android.material.navigation.NavigationView;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;
import com.ministore.android.MyApplication;
import com.ministore.android.R;
import com.ministore.android.adapter.CartAdapter;
import com.ministore.android.adapter.MainViewPagerAdapter;
import com.ministore.android.api.ApiService;
import com.ministore.android.fragment.CartFragment;
import com.ministore.android.fragment.HomeFragment;
import com.ministore.android.fragment.HostFragment;
import com.ministore.android.fragment.OnboardingFragment;
import com.ministore.android.model.Cart;
import com.ministore.android.model.JwtResponse;
import com.ministore.android.model.User;
import com.nex3z.notificationbadge.NotificationBadge;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;

import gun0912.tedbottompicker.TedBottomPicker;
import gun0912.tedbottompicker.TedBottomSheetDialogFragment;
import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        HostFragment.OnGoShoppingListener, HomeFragment.OnAddToCartListener {

    public static final int FRAGMENT_HOME = 0;
    public static final int FRAGMENT_CATEGORY = 1;
    public static final int FRAGMENT_CART = 2;
    public static final int FRAGMENT_ACCOUNT = 3;
    private int currentFragment = FRAGMENT_HOME;

    private Toolbar toolbar;
    private SearchView svSearchProduct;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private BottomNavigationBar bottomNavigationBar;
    private Menu navMenu;
    private ViewPager2 viewPager;
    private MainViewPagerAdapter mainViewPagerAdapter;

    private ImageView imgUser;
    private TextView txtName, txtEmail;

//    private NotificationBadge notificationBadge;
    private static TextBadgeItem numberBadgeItem;

    private ImageView animationView;
    private View endView;
    public ImageView getAnimationView() {
        return animationView;
    }
    public View getEndView() {
        return endView;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setControl();
        loadCart();
        setEvent();
        loadUserInfo();
    }

    private void loadCart() {
        if (!MyApplication.checkUserLogged()) return;
        int userId = MyApplication.getUserId();
        String authorization = MyApplication.getAuthorization();
        ApiService.apiService.getCartByUserId(authorization, userId)
                .enqueue(new Callback<List<Cart>>() {
                    @Override
                    public void onResponse(Call<List<Cart>> call, Response<List<Cart>> response) {
                        List<Cart> cartList = response.body();
                        if (cartList != null && !cartList.isEmpty()) {
                            if (MyApplication.cartAdapter == null) {
                                MyApplication.cartAdapter = new CartAdapter(cartList);
                            }
                            else {
                                MyApplication.cartAdapter.setData(cartList);
                            }
                            updateCartItemCount();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Cart>> call, Throwable t) {

                    }
                });
    }

    private void loadUserInfo() {
        if (MyApplication.checkUserLogged()) {
            int id = MyApplication.getUserId();
            ApiService.apiService.getUserById(id)
                    .enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            User user = response.body();
                            Picasso.get().load(ApiService.USER_IMAGE_URL + user.getImage())
                                    .error(R.drawable.img_default_avatar)
                                    .placeholder(R.drawable.img_default_avatar)
                                    .into(imgUser);
                            txtName.setText(String.format("%s %s", user.getLastName(), user.getFirstName()));
                            txtEmail.setVisibility(View.VISIBLE);
                            txtEmail.setText(user.getEmail());
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {

                        }
                    });
            navMenu.findItem(R.id.nav_login).setVisible(false);
            navMenu.findItem(R.id.nav_logout).setVisible(true);
            imgUser.setOnClickListener(view -> {
                MyApplication.viewProfile(this);
            });
            txtName.setOnClickListener(view -> {
                MyApplication.viewProfile(this);
            });
            txtEmail.setOnClickListener(view -> {
                MyApplication.viewProfile(this);
            });
        }
        else {
            imgUser.setImageResource(R.drawable.img_default_avatar);
            txtName.setText("Anonymous user");
            txtEmail.setVisibility(View.INVISIBLE);
            navMenu.findItem(R.id.nav_login).setVisible(true);
            navMenu.findItem(R.id.nav_logout).setVisible(false);
        }

        if (MyApplication.checkAdminPermission()) {
            navMenu.findItem(R.id.nav_admin).setVisible(true);
        }
        else {
            navMenu.findItem(R.id.nav_admin).setVisible(false);
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

    private void setControl() {
        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        viewPager = findViewById(R.id.view_pager);
        navigationView = findViewById(R.id.navigation_view);
        bottomNavigationBar = findViewById(R.id.bottom_navigation);
        animationView = findViewById(R.id.animation_view);
        endView = findViewById(R.id.end_view);
        svSearchProduct = findViewById(R.id.sv_search_product);

        imgUser = navigationView.getHeaderView(0).findViewById(R.id.img_user_avatar);
        txtName = navigationView.getHeaderView(0).findViewById(R.id.tv_name);
        txtEmail = navigationView.getHeaderView(0).findViewById(R.id.tv_email);

        numberBadgeItem = new TextBadgeItem().hide();
        navMenu = navigationView.getMenu();

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("");
        }

        viewPager.setOffscreenPageLimit(1);
        bottomNavigationBar.setInActiveColor("#FFFFFF");
        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.ic_home, "Home")
                        .setActiveColorResource(R.color.dark_green))
                .addItem(new BottomNavigationItem(R.drawable.ic_category, "Category")
                        .setActiveColorResource(R.color.orange))
                .addItem(new BottomNavigationItem(R.drawable.ic_shopping_cart, "Cart")
                        .setActiveColorResource(R.color.light_blue).setBadgeItem(numberBadgeItem))
                .addItem(new BottomNavigationItem(R.drawable.ic_person, "Account")
                        .setActiveColorResource(R.color.dark_red))
                .setFirstSelectedPosition(0)
                .initialise();
    }

    private void setEvent() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.getDrawerArrowDrawable().setColor(ContextCompat.getColor(this, R.color.white));
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
                        currentFragment = FRAGMENT_CATEGORY;
                        navigationView.getMenu().findItem(R.id.nav_category).setChecked(true);
                        break;
                    case 2:
                        currentFragment = FRAGMENT_CART;
                        navigationView.getMenu().findItem(R.id.nav_cart).setChecked(true);
                        break;
                    case 3:
                        currentFragment = FRAGMENT_ACCOUNT;
                        navigationView.getMenu().findItem(R.id.nav_account).setChecked(true);
                        break;
                }
                bottomNavigationBar.selectTab(position);
            }
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_home:
                openFragment(0);
                break;
            case R.id.nav_category:
                openFragment(1);
                break;
            case R.id.nav_cart:
                openFragment(2);
                break;
            case R.id.nav_account:
                openFragment(3);
                break;
            case R.id.nav_settings:
                MyApplication.viewSettings(this);
                break;
            case R.id.nav_admin:
                if (MyApplication.checkAdminPermission()) {
                    MyApplication.goToAdmin(this);
                }
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

    public static int getCartItemCount() {
        return MyApplication.cartAdapter.getCartItemCount();
    }

    public static void updateCartItemCount() {
        if (MyApplication.cartAdapter == null) {
            numberBadgeItem.hide();
        }
        else {
            int count = MyApplication.cartAdapter.getCartItemCount();
            if (count > 0) {
                numberBadgeItem.show();
                numberBadgeItem.setText(String.valueOf(count));
            }
            else {
                numberBadgeItem.hide();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadCart();
        loadUserInfo();
    }

    @Override
    public void onGoShoppingAction() {
        openFragment(FRAGMENT_HOME);
    }

    @Override
    public void onAddToCartCompleted() {

    }
}