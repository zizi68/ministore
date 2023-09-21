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

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ministore.android.R;
import com.ministore.android.activity._ImportDetailActivity;
import com.ministore.android.activity._NewImportActivity;
import com.ministore.android.activity._ProductInfoActivity;
import com.ministore.android.adapter._ProductAdapter;
import com.ministore.android.model.Product;

public class _ImportFragment extends Fragment {

    View view;
    FloatingActionButton fbtnAddImport;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout._fragment_import, container, false);

        setControl();
        setEvent();
        return view;
    }

    private void setEvent() {
        fbtnAddImport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), _NewImportActivity.class);
                getContext().startActivity(intent);
            }
        });
    }

    private void setControl() {
        fbtnAddImport = view.findViewById(R.id.fbtnAddImport);
    }

    @Override
    public void onResume() {
        super.onResume();
        _ImportListFragment fragment = (_ImportListFragment) getChildFragmentManager().findFragmentById(R.id.fragment_import_list);
        fragment.loadList();
    }
}
