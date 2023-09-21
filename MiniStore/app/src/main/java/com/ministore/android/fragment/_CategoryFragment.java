package com.ministore.android.fragment;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.ministore.android.R;
import com.ministore.android.activity._CategoryInfoActivity;
import com.ministore.android.adapter._CategoryAdapter;
import com.ministore.android.model.Category;

public class _CategoryFragment extends Fragment
        implements _CategoryAdapter.OnItemClickListener, _CategoryInfoFragment.OnActionListener {

    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout._fragment_category, container, false);
        Configuration configuration = getResources().getConfiguration();
        if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            replaceFragment(new _BlankFragment());
        }
        return view;
    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fm = getChildFragmentManager();
        if (fm != null) {
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.container, fragment);
            ft.commit();
        }
    }

    @Override
    public void onClick(Category category) {
        Configuration configuration = getResources().getConfiguration();
        // Landscape
        if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            _CategoryInfoFragment fragment = _CategoryInfoFragment.newInstance(category).setListener(this);
            replaceFragment(fragment);
        }
        // Portrait
        else {
            Intent intent = new Intent(getContext(), _CategoryInfoActivity.class);
            intent.putExtra(_CategoryInfoActivity.KEY_CATEGORY, category);
            startActivity(intent);
        }
    }

    @Override
    public void onActionCompleted(int action) {
        if (action == _CategoryInfoFragment.DELETE_ACTION) {
            replaceFragment(new _BlankFragment());
        }
        _CategoryListFragment fragment = (_CategoryListFragment) getChildFragmentManager().findFragmentById(R.id.fragment_category_list);
        fragment.loadCategoryList();
    }

    @Override
    public void onResume() {
        super.onResume();
        _CategoryListFragment fragment = (_CategoryListFragment) getChildFragmentManager().findFragmentById(R.id.fragment_category_list);
        fragment.loadCategoryList();
    }
}