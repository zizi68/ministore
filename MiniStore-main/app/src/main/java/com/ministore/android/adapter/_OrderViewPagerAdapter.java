package com.ministore.android.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.ministore.android.order._ChoXuLyFragment;
import com.ministore.android.order._YeuCauHuyFragment;
import com.ministore.android.order._DangGiaoFragment;
import com.ministore.android.order._DaGiaoFragment;
import com.ministore.android.order._DaHuyFragment;

public class _OrderViewPagerAdapter extends FragmentStatePagerAdapter {

    public _OrderViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new _DangGiaoFragment();
            case 1:
                return new _DaGiaoFragment();
            case 2:
                return new _DaHuyFragment();
            default:
                return new _DangGiaoFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {

            case 0:
                return "Đang giao";
            case 1:
                return "Đã giao";
            case 2:
                return "Đã hủy";
            default:
                return "Đang giao";
        }
    }
}
