package com.ministore.android.fragment;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.ministore.android.R;
import com.ministore.android.activity._CategoryInfoActivity;
import com.ministore.android.activity._ProductInfoActivity;
import com.ministore.android.adapter._ProductAdapter;
import com.ministore.android.model.Product;

public class _ProductFragment extends Fragment
        implements _ProductAdapter.OnItemClickListener, _ProductInfoFragment.OnActionListener {

    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout._fragment_product, container, false);
        Configuration configuration = getResources().getConfiguration();
        if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            replaceFragment(new _BlankFragment());
        }
        return view;
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fm = getChildFragmentManager();
        if (fm != null) {
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.container, fragment);
            ft.commit();
        }
    }

    @Override
    public void onClick(Product product) {
        Configuration configuration = getResources().getConfiguration();
        // Landscape
        if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            _ProductInfoFragment fragment = _ProductInfoFragment.newInstance(product).setListener(this);
            replaceFragment(fragment);
        }
        // Portrait
        else {
            Intent intent = new Intent(getContext(), _ProductInfoActivity.class);
            intent.putExtra(_ProductInfoActivity.KEY_PRODUCT, product);
            startActivity(intent);
        }
    }

    @Override
    public void onActionCompleted(int action) {
        if (action == _ProductInfoFragment.DELETE_ACTION) {
            replaceFragment(new _BlankFragment());
        }
        _ProductListFragment fragment = (_ProductListFragment) getChildFragmentManager().findFragmentById(R.id.fragment_product_list);
        fragment.loadProductList();
    }

    @Override
    public void onResume() {
        super.onResume();
        _ProductListFragment fragment = (_ProductListFragment) getChildFragmentManager().findFragmentById(R.id.fragment_product_list);
        fragment.loadProductList();
    }
}
