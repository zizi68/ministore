package com.ministore.android.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ministore.android.R;
import com.ministore.android.model.Onboarding;


public class OnboardingFragment extends Fragment {

    public interface OnActionListener {
        void onActionButtonClick();
    }

    private View view;
    private TextView tvTitle, tvDescription;
    private AppCompatButton btnAction;

    private boolean showButton;

//    private boolean isShowActionButton;
    private String title = "", description = "";
    private String btnText;
    private OnActionListener onActionListener;

    public OnboardingFragment() {
//        this.background = android.R.color.white;
    }

//    public OnboardingFragment(Onboarding onboarding) {
//        this.onboarding = onboarding;
//        this.background = android.R.color.white;
//    }

    public OnboardingFragment setTitle(String title) {
        this.title = title;
        return this;
    }

    public OnboardingFragment setDescription(String description) {
        this.description = description;
        return this;
    }



    public OnboardingFragment setActionButton(String text,  OnActionListener onActionListener) {
        showButton = true;
        this.btnText = text;
        this.onActionListener = onActionListener;
        return this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_onboarding, container, false);
        setControl();
        return view;
    }

    private void setControl() {
        tvTitle = view.findViewById(R.id.tv_title);
        tvDescription = view.findViewById(R.id.tv_description);
        btnAction = view.findViewById(R.id.btn_get_started);


        tvTitle.setText(title);
        tvDescription.setText(description);

        if (showButton) {
            btnAction.setVisibility(View.VISIBLE);
            btnAction.setText(btnText);
            btnAction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onActionListener != null) {
                        onActionListener.onActionButtonClick();
                    }
                }
            });
        }
        else {
            btnAction.setVisibility(View.GONE);
        }
    }
}