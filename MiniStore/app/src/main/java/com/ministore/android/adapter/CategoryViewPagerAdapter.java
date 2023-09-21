package com.ministore.android.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.ministore.android.fragment.ProductFragment;
import com.ministore.android.model.Category;

import java.util.List;

public class CategoryViewPagerAdapter extends FragmentStateAdapter {

    private List<Category> categoryList;

    public CategoryViewPagerAdapter(@NonNull FragmentActivity fragmentActivity, List<Category> categoryList) {
        super(fragmentActivity);
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Category category = categoryList.get(position);
        return new ProductFragment(category);
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public Category getItemAt(int position) {
        return categoryList.get(position);
    }
}
