package com.ministore.android.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.ministore.android.R;
import com.ministore.android.fragment.OnboardingFragment;
import com.ministore.android.fragment._CategoryFragment;
import com.ministore.android.fragment._ImportFragment;
import com.ministore.android.fragment._OrderFragment;
import com.ministore.android.fragment._ProductFragment;

import com.ministore.android.fragment._UserFragment;

import com.ministore.android.fragment._StatisticsFragment;

import com.ministore.android.model.Onboarding;

import java.util.ArrayList;
import java.util.List;

public class _MainViewPagerAdapter extends FragmentStateAdapter {

    private List<Fragment> fragments;

    public _MainViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        fragments = new ArrayList<>();
        fragments.add(new _StatisticsFragment());
        fragments.add(new _OrderFragment());
        fragments.add(new _ImportFragment());
        fragments.add(new _ProductFragment());
        fragments.add(new _CategoryFragment());
        fragments.add(new _UserFragment());
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragments.get(position);
    }

    @Override
    public int getItemCount() {
        return fragments.size();
    }
}
