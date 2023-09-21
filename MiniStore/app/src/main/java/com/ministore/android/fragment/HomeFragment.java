package com.ministore.android.fragment;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ministore.android.MyApplication;
import com.ministore.android.R;
import com.ministore.android.activity.MainActivity;
import com.ministore.android.adapter.PosterViewPagerAdapter;
import com.ministore.android.adapter.ProductAdatper;
import com.ministore.android.api.ApiService;
import com.ministore.android.model.Poster;
import com.ministore.android.model.Product;
import com.ministore.android.model.ProductOutput;
import com.ministore.android.transformer.ZoomOutPageTransformer;
import com.ministore.android.util.AnimationUtil;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator3;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements MyApplication.OnCartActionListener {

    private View view;
    private ViewPager2 viewPagerPoster;
    private CircleIndicator3 circleIndicator;
    private TextView tvSeeAllCategories;
    private RecyclerView rcvNewProducts;
    private RecyclerView rcvMostPurchased;
    private RecyclerView rcvAllProducts;
    private NestedScrollView nestedScrollView;
    private ProgressBar progressBar;

    private boolean isLoadingData;
    private boolean isLastPage;
    private int currentPage = 0;
    private int totalPage;
    private static final int PAGE_SIZE = 10;
    private ProductAdatper productAdatper;

    private List<Poster> posterList;
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (posterList == null) return;
            if (viewPagerPoster.getCurrentItem() == posterList.size() - 1) {
                viewPagerPoster.setCurrentItem(0);
            } else {
                viewPagerPoster.setCurrentItem(viewPagerPoster.getCurrentItem() + 1);
            }
        }
    };

    private OnAddToCartListener onAddToCartListener;

    public void setOnAddToCartListener(OnAddToCartListener onAddToCartListener) {
        this.onAddToCartListener = onAddToCartListener;
    }

    public interface OnAddToCartListener {
        void onAddToCartCompleted();
    }

    public HomeFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        setControl();
        setEvent();
        loadPosters();
        loadNewProducts();
        loadMostPurchasedProducts();
        loadProducts();
        return view;
    }

    private void setControl() {
        viewPagerPoster = view.findViewById(R.id.view_pager_poster);
        circleIndicator = view.findViewById(R.id.circle_indicator_poster);
        tvSeeAllCategories = view.findViewById(R.id.tv_see_all_categories);
        nestedScrollView = view.findViewById(R.id.nested_scroll_view);
        progressBar = view.findViewById(R.id.progress_bar);

        rcvNewProducts = view.findViewById(R.id.rcv_new_products);
        rcvNewProducts.setHasFixedSize(true);
        rcvNewProducts.setLayoutManager(new GridLayoutManager(getContext(), 2));
        rcvNewProducts.setFocusable(false);
        rcvNewProducts.setNestedScrollingEnabled(false);

        rcvMostPurchased = view.findViewById(R.id.rcv_most_purchased);
        rcvMostPurchased.setHasFixedSize(true);
        rcvMostPurchased.setLayoutManager(new GridLayoutManager(getContext(), 2));
        rcvMostPurchased.setFocusable(false);
        rcvMostPurchased.setNestedScrollingEnabled(false);

        rcvAllProducts = view.findViewById(R.id.rcv_all_products);
        rcvAllProducts.setHasFixedSize(true);
        rcvAllProducts.setLayoutManager(new GridLayoutManager(getContext(), 2));
        rcvAllProducts.setFocusable(false);
        rcvAllProducts.setNestedScrollingEnabled(false);

        List<Product> productList = new ArrayList<>();
        productAdatper = new ProductAdatper(productList);
        productAdatper.setOnClickListener(new ProductAdatper.OnClickListener() {
            @Override
            public void onClick(Product product) {
                MyApplication.viewDetail(getContext(), product);
            }

            @Override
            public void onAddToCartClick(ImageView imgProduct, Product product) {
                MainActivity mainActivity = ((MainActivity) getActivity());
                ImageView viewAnimation = mainActivity.getAnimationView();
                View viewEndAnimation = mainActivity.getEndView();
                AnimationUtil.translateAnimation(viewAnimation, imgProduct, viewEndAnimation, new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        MyApplication.addToCart(product, 1, HomeFragment.this);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }
        });
        rcvAllProducts.setAdapter(productAdatper);
    }

    private void setEvent() {
        viewPagerPoster.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                handler.removeCallbacks(runnable);
                handler.postDelayed(runnable, 3000);
            }
        });
        viewPagerPoster.setPageTransformer(new ZoomOutPageTransformer());
        tvSeeAllCategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.openFragment(MainActivity.FRAGMENT_CATEGORY);
            }
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
        setOnAddToCartListener((OnAddToCartListener) getActivity());
    }

    private void loadPosters() {
        ApiService.apiService.getListPosters().enqueue(new Callback<List<Poster>>() {
            @Override
            public void onResponse(Call<List<Poster>> call, Response<List<Poster>> response) {
                posterList = response.body();
                if (posterList == null || posterList.isEmpty()) return;
                PosterViewPagerAdapter adapter = new PosterViewPagerAdapter(posterList);
                viewPagerPoster.setAdapter(adapter);
                circleIndicator.setViewPager(viewPagerPoster);
            }

            @Override
            public void onFailure(Call<List<Poster>> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void loadNewProducts() {
        ApiService.apiService.findProducts(1, PAGE_SIZE, "id", "DESC", null)
                .enqueue(new Callback<ProductOutput>() {
                    @Override
                    public void onResponse(Call<ProductOutput> call, Response<ProductOutput> response) {
                        ProductOutput productOutput = response.body();
                        if (productOutput == null) return;
                        List<Product> productList = productOutput.getListResult();
                        if (productList == null || productList.isEmpty()) return;
                        ProductAdatper productAdatper = new ProductAdatper(productList);
                        productAdatper.setOnClickListener(new ProductAdatper.OnClickListener() {
                            @Override
                            public void onClick(Product product) {
                                MyApplication.viewDetail(getContext(), product);
                            }

                            @Override
                            public void onAddToCartClick(ImageView imgProduct, Product product) {
                                MainActivity mainActivity = ((MainActivity) getActivity());
                                ImageView viewAnimation = mainActivity.getAnimationView();
                                View viewEndAnimation = mainActivity.getEndView();
                                AnimationUtil.translateAnimation(viewAnimation, imgProduct, viewEndAnimation, new Animation.AnimationListener() {
                                    @Override
                                    public void onAnimationStart(Animation animation) {

                                    }

                                    @Override
                                    public void onAnimationEnd(Animation animation) {
                                        if (!MyApplication.checkUserLogged()) {
                                            MyApplication.goToLoginActivity(getActivity());
                                            return;
                                        }
                                        MyApplication.addToCart(product, 1, HomeFragment.this);
                                    }

                                    @Override
                                    public void onAnimationRepeat(Animation animation) {

                                    }
                                });
                            }
                        });
                        rcvNewProducts.setAdapter(productAdatper);
                    }

                    @Override
                    public void onFailure(Call<ProductOutput> call, Throwable t) {
                        Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void loadMostPurchasedProducts() {
        ApiService.apiService.findProducts(1, PAGE_SIZE, "soldQuantity", "DESC", null)
                .enqueue(new Callback<ProductOutput>() {
                    @Override
                    public void onResponse(Call<ProductOutput> call, Response<ProductOutput> response) {
                        ProductOutput productOutput = response.body();
                        if (productOutput == null) return;
                        List<Product> productList = productOutput.getListResult();
                        if (productList == null || productList.isEmpty()) return;
                        ProductAdatper productAdatper = new ProductAdatper(productList);
                        productAdatper.setOnClickListener(new ProductAdatper.OnClickListener() {
                            @Override
                            public void onClick(Product product) {
                                MyApplication.viewDetail(getContext(), product);
                            }

                            @Override
                            public void onAddToCartClick(ImageView imgProduct, Product product) {
                                MainActivity mainActivity = ((MainActivity) getActivity());
                                ImageView viewAnimation = mainActivity.getAnimationView();
                                View viewEndAnimation = mainActivity.getEndView();
                                AnimationUtil.translateAnimation(viewAnimation, imgProduct, viewEndAnimation, new Animation.AnimationListener() {
                                    @Override
                                    public void onAnimationStart(Animation animation) {

                                    }

                                    @Override
                                    public void onAnimationEnd(Animation animation) {
                                        MyApplication.addToCart(product, 1, HomeFragment.this);
                                    }

                                    @Override
                                    public void onAnimationRepeat(Animation animation) {

                                    }
                                });
                            }
                        });
                        rcvMostPurchased.setAdapter(productAdatper);
                    }

                    @Override
                    public void onFailure(Call<ProductOutput> call, Throwable t) {
                        Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void loadProducts() {
        ++currentPage;
        isLoadingData = true;
        progressBar.setVisibility(View.VISIBLE);
        ApiService.apiService.findProducts(currentPage, PAGE_SIZE, "id", "ASC", null)
                .enqueue(new Callback<ProductOutput>() {
                    @Override
                    public void onResponse(Call<ProductOutput> call, Response<ProductOutput> response) {
                        ProductOutput productOutput = response.body();
                        if (productOutput == null) return;

                        totalPage = productOutput.getTotalPage();
                        List<Product> productList = productOutput.getListResult();
                        if (productList == null || productList.isEmpty()) return;

                        productAdatper.addData(productList);
                        isLoadingData = false;
                        progressBar.setVisibility(View.INVISIBLE);
                        if (currentPage >= totalPage) {
                            isLastPage = true;
                        }
                    }

                    @Override
                    public void onFailure(Call<ProductOutput> call, Throwable t) {
                        Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    public void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAuthenticationFailed() {
        MyApplication.goToLoginActivity(getActivity());
    }

    @Override
    public void onCartShowMessage(String message) {
        showMessage(message);
        if (onAddToCartListener != null) {
            onAddToCartListener.onAddToCartCompleted();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }

    @Override
    public void onResume() {
        super.onResume();
        handler.postDelayed(runnable, 3000);
    }
}