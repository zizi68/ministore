package com.ministore.android.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ministore.android.MyApplication;
import com.ministore.android.R;


public class HostFragment extends Fragment implements CartFragment.OnDeletedAllCartItemsListener {

    public static final int FRAGMENT_ORTHER = 0;
    public static final int FRAGMENT_CART = 1;
    private int currentFragment = -1;

    private View view;
    private OnGoShoppingListener onGoShoppingListener;

    @Override
    public void onDeletedAllCartItems() {
        replaceFragment(getChildFragment(), false);
    }

    public interface OnGoShoppingListener {
        void onGoShoppingAction();
    }

    public void setOnGoShoppingListener(OnGoShoppingListener onGoShoppingListener) {
        this.onGoShoppingListener = onGoShoppingListener;
    }

    public HostFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_host_cart, container, false);
        replaceFragment(getChildFragment(), false);
        return view;
    }

    private Fragment getChildFragment() {
        if (MyApplication.checkUserLogged()) {
            if (MyApplication.cartAdapter.getItemCount() <= 0) {
                if (getActivity() instanceof OnGoShoppingListener) {
                    setOnGoShoppingListener((OnGoShoppingListener) getActivity());
                }
                return new OnboardingFragment()
                        .setImage(R.drawable.img_stationery_cardboard_box)
                        .setTitle("Your cart is empty")
                        .setDescription("Shop now to get our offers!")
                        .setActionButton("GO SHOPPING NOW", R.color.light_blue, new OnboardingFragment.OnActionListener() {
                            @Override
                            public void onActionButtonClick() {
                                if (onGoShoppingListener != null) {
                                    onGoShoppingListener.onGoShoppingAction();
                                }
                            }
                        });
            }
            CartFragment cartFragment = new CartFragment();
            cartFragment.setOnDeletedAllCartItemsListener(this);
            return cartFragment;
        }
        return new OnboardingFragment()
                .setImage(R.drawable.img_cute_book_pencil_cartoon)
                .setTitle("You are not logged in")
                .setDescription("Please sign in to view your cart, rating and orders!")
                .setBackground(R.drawable.img_back_pink_1_vertical)
                .setActionButton("LOG IN", R.color.light_green, new OnboardingFragment.OnActionListener() {
                    @Override
                    public void onActionButtonClick() {
                        MyApplication.goToLoginActivity(getActivity());
                    }
                });
    }

    private void replaceFragment(Fragment fragment, boolean addToBackstack) {
        if (currentFragment == FRAGMENT_CART && fragment instanceof CartFragment) return;
        if (addToBackstack) {
            getChildFragmentManager().beginTransaction().replace(R.id.hosted_frame, fragment).addToBackStack(null).commit();
        } else {
            getChildFragmentManager().beginTransaction().replace(R.id.hosted_frame, fragment).commit();
        }
        if (fragment instanceof CartFragment) {
            currentFragment = FRAGMENT_CART;
        }
        else {
            currentFragment = FRAGMENT_ORTHER;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        replaceFragment(getChildFragment(), false);
    }
}