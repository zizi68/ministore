package com.ministore.android.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.ministore.android.R;
import com.ministore.android.fragment._CategoryInfoFragment;
import com.ministore.android.fragment._ProductInfoFragment;
import com.ministore.android.model.Category;
import com.ministore.android.model.Product;

public class _ProductInfoActivity extends AppCompatActivity {

    public static final String KEY_PRODUCT = "product";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout._activity_product_info);
        Intent intent = getIntent();
        if (intent == null) return;
        Product product = (Product) intent.getSerializableExtra(KEY_PRODUCT);
        _ProductInfoFragment fragment = (_ProductInfoFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_product_info);
        if (fragment != null) {
            fragment.setInfo(product);
        }
    }
}