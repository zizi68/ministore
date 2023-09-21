package com.ministore.android.adapter;

import android.app.Activity;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.ministore.android.MyApplication;
import com.ministore.android.R;
import com.ministore.android.activity.MainActivity;
import com.ministore.android.fragment.OnboardingFragment;
import com.ministore.android.model.Onboarding;

import java.util.List;

import io.paperdb.Paper;

public class OnboardingViewPagerAdapter extends FragmentStateAdapter {

    private FragmentActivity fragmentActivity;
    private List<Onboarding> onboardingList;

    public OnboardingViewPagerAdapter(@NonNull FragmentActivity fragmentActivity, List<Onboarding> onboardingList) {
        super(fragmentActivity);
        this.fragmentActivity = fragmentActivity;
        this.onboardingList = onboardingList;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Onboarding onboarding = onboardingList.get(position);
        boolean isLastOnboardingScreen = (position == onboardingList.size() - 1);

        OnboardingFragment onboardingFragment = new OnboardingFragment()
                .setTitle(onboarding.getTitle())
                .setDescription(onboarding.getDescription());
        if (isLastOnboardingScreen) {
            onboardingFragment.setActionButton("Get started", () -> {
                Paper.book().write(MyApplication.KEY_GET_STARTED, true);
                Intent intent = new Intent(fragmentActivity, MainActivity.class);
                fragmentActivity.startActivity(intent);
                fragmentActivity.finishAffinity();
            });
        }
        return onboardingFragment;
    }

    @Override
    public int getItemCount() {
        return onboardingList.size();
    }
}
