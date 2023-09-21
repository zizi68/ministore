package com.ministore.android.adapter;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.ministore.android.MyApplication;
import com.ministore.android.R;
import com.ministore.android.activity.LoginActivity;
import com.ministore.android.fragment.AccountFragment;
import com.ministore.android.fragment.CategoryFragment;
import com.ministore.android.fragment.HomeFragment;
import com.ministore.android.fragment.HostFragment;
import com.ministore.android.fragment.OnboardingFragment;

public class MainViewPagerAdapter extends FragmentStateAdapter {

    private FragmentActivity fragmentActivity;

    public MainViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        this.fragmentActivity = fragmentActivity;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 1:
                return new CategoryFragment();
            case 2:
            {
                return new HostFragment();
            }
            case 3:
            {
                if (MyApplication.checkUserLogged()) {
                    return new AccountFragment();
                }
                OnboardingFragment fragment = new OnboardingFragment();
                return fragment
                        .setImage(R.drawable.img_cute_book_pencil_cartoon)
                        .setTitle("You are not logged in")
                        .setDescription("Please sign in to view your cart, rating and orders!")
                        .setBackground(R.drawable.img_back_pink_1_vertical)
                        .setActionButton("LOG IN", R.color.light_green, new OnboardingFragment.OnActionListener() {
                            @Override
                            public void onActionButtonClick() {
                                Intent intent = new Intent(fragmentActivity, LoginActivity.class);
                                fragmentActivity.startActivity(intent);
                            }
                        });
            }
            default:
                return new HomeFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
