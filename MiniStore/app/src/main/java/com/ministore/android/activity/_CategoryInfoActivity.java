package com.ministore.android.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.ministore.android.R;
import com.ministore.android.adapter._CategoryAdapter;
import com.ministore.android.fragment._CategoryInfoFragment;
import com.ministore.android.fragment._CategoryListFragment;
import com.ministore.android.model.Category;

public class _CategoryInfoActivity extends AppCompatActivity {

    public static final String KEY_CATEGORY = "category";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout._activity_category_info);
        Intent intent = getIntent();
        if (intent == null) return;
        Category category = (Category) intent.getSerializableExtra(KEY_CATEGORY);
        _CategoryInfoFragment fragment = (_CategoryInfoFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_category_info);
        if (fragment != null) {
            fragment.setInfo(category);
        }
    }
}