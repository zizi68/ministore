package com.ministore.android.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.ministore.android.R;
import com.ministore.android.adapter._OrderViewPagerAdapter;

public class _OrderFragment extends Fragment {

    public static final int CHO_XU_LY = 1;
    public static final int YEU_CAU_HUY = 2;
    public static final int DANG_GIAO = 3;
    public static final int DA_GIAO = 4;
    public static final int DA_HUY = 5;
    TabLayout tabLayout;
    ViewPager viewPager;
    View mView;

    public _OrderFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout._fragment_order, container, false);

        tabLayout = mView.findViewById(R.id.tab_layout);
        viewPager = mView.findViewById(R.id.view_pager_order);

        _OrderViewPagerAdapter adapter = new _OrderViewPagerAdapter(getChildFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);

        return mView;
    }
}
