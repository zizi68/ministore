package com.ministore.android.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.ministore.android.fragment.OrderFragment;
import com.ministore.android.model.OrderStatus;

public class OrderStatusViewPagerAdapter extends FragmentStateAdapter {

    public OrderStatusViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        OrderStatus orderStatus = OrderStatus.getOrderStatusList().get(position);
        return OrderFragment.newInstance(orderStatus);
    }

    @Override
    public int getItemCount() {
        return OrderStatus.getOrderStatusList().size();
    }

    public OrderStatus getItemAt(int position) {
        return OrderStatus.getOrderStatusList().get(position);
    }
}
