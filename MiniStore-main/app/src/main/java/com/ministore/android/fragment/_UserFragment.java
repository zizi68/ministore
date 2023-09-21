package com.ministore.android.fragment;

import android.app.Activity;
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
import androidx.lifecycle.viewmodel.CreationExtras;

import com.ministore.android.R;

import com.ministore.android.activity._UserInfoActivity;

import com.ministore.android.adapter._UserAdapter;
import com.ministore.android.model.User;

public class _UserFragment extends Fragment
        implements _UserAdapter.OnItemClickListener, _UserInfoFragment.OnActionListener{
    View view;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout._fragment_user, container, false);
        Configuration configuration = getResources().getConfiguration();

        return view;
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fm = getChildFragmentManager();
        if (fm != null) {
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.containerUser, fragment);
            ft.commit();
        }
    }

    @Override
    public void onActionCompleted(int action) {
               _UserListFragment fragment = (_UserListFragment) getChildFragmentManager().findFragmentById(R.id.fragment_user_list);
        fragment.loadUserList();
    }

    @Override
    public void onClick(User user) {
        Configuration configuration = getResources().getConfiguration();
        // Landscape
        if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            _UserInfoFragment fragment = _UserInfoFragment.newInstance(user).setListener(this);
            replaceFragment(fragment);
        }
        // Portrait
        else {
            Intent intent = new Intent(getContext(), _UserInfoActivity.class);
            intent.putExtra(_UserInfoActivity.KEY_USER, user);
            startActivity(intent);
        }
    }

    @NonNull
    @Override
    public CreationExtras getDefaultViewModelCreationExtras() {
        return super.getDefaultViewModelCreationExtras();
    }

    @Override
    public void onResume() {
        super.onResume();
        _UserListFragment fragment = (_UserListFragment) getChildFragmentManager().findFragmentById(R.id.fragment_user_list);
        fragment.loadUserList();
    }
}
