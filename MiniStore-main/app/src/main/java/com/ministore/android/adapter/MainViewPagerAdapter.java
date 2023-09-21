package com.ministore.android.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.ministore.android.fragment.AccountFragment;
import com.ministore.android.fragment._OrderFragment;

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
                return new AccountFragment();
            default:
                return new _OrderFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
