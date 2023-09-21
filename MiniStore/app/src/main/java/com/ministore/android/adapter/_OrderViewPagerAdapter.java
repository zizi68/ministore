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
                return new _ChoXuLyFragment();
            case 1:
                return new _YeuCauHuyFragment();
            case 2:
                return new _DangGiaoFragment();
            case 3:
                return new _DaGiaoFragment();
            case 4:
                return new _DaHuyFragment();
            default:
                return new _ChoXuLyFragment();
        }
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Chờ xử lý";
            case 1:
                return "Yêu cầu hủy";
            case 2:
                return "Đang giao";
            case 3:
                return "Đã giao";
            case 4:
                return "Đã hủy";
            default:
                return "Chờ xử lý";
        }
    }
}
