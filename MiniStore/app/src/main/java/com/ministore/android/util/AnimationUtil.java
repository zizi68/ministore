package com.ministore.android.util;

import android.graphics.Bitmap;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.Toast;

public class AnimationUtil {

    private static final int ANIMATION_DELAY = 200;
    private static final int ANIMATION_DURATION = 1000;
    private static boolean isAnimationStart;

    public static void translateAnimation(final ImageView animationView, final View startView, final View endView,
                                          final Animation.AnimationListener animationListener) {
        startView.setDrawingCacheEnabled(true);
        Bitmap cache = startView.getDrawingCache();
        if (cache == null) {
            return;
        }
        Bitmap bitmap = Bitmap.createBitmap(cache);
        startView.setDrawingCacheEnabled(false);

        animationView.setImageBitmap(bitmap);

        float startViewWidthCenter = startView.getWidth() / 2f;
        float startViewHeightCenter = startView.getHeight() / 2f;

        float endViewWidthCenter = endView.getWidth() * 0.75f;
        float endViewHeightCenter = endView.getHeight() / 2f;

        final int[] startPos = new int[2];
        startView.getLocationOnScreen(startPos);

        final int[] endPos = new int[2];
        endView.getLocationOnScreen(endPos);

        float fromX = startPos[0];
        float toX = endPos[0] + endViewWidthCenter - startViewWidthCenter;
        float fromY = startPos[1] - startViewHeightCenter;
        float toY = endPos[1] - endViewHeightCenter + startViewHeightCenter;

        AnimationSet animationSet = new AnimationSet(true);
        animationSet.setInterpolator(new AccelerateInterpolator());

        ScaleAnimation startScaleAnimation = new ScaleAnimation(1.0f, 1.5f, 1.0f, 1.5f,
                startViewWidthCenter, startViewHeightCenter);
        startScaleAnimation.setDuration(ANIMATION_DELAY);

        TranslateAnimation translateAnimation = new TranslateAnimation(fromX, toX, fromY, toY);
        translateAnimation.setStartOffset(ANIMATION_DELAY);
        translateAnimation.setDuration(ANIMATION_DURATION);

        ScaleAnimation translateScaleAnimation = new ScaleAnimation(1.0f, 0.0f, 1.0f, 0.0f,
                toX, toY);
        translateScaleAnimation.setStartOffset(ANIMATION_DELAY);
        translateScaleAnimation.setDuration(ANIMATION_DURATION);

        animationSet.addAnimation(startScaleAnimation);
        animationSet.addAnimation(translateAnimation);
        animationSet.addAnimation(translateScaleAnimation);

        if (isAnimationStart) {
            animationView.clearAnimation();
//            if (animationListener != null) {
//                animationListener.onAnimationEnd(null);
//            }
        }

        animationView.startAnimation(animationSet);

        animationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                isAnimationStart = true;

                animationView.setVisibility(View.VISIBLE);
                startView.setVisibility(View.INVISIBLE);

                if (animationListener != null) {
                    animationListener.onAnimationStart(animation);
                }
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                animationView.setVisibility(View.GONE);
                startView.setVisibility(View.VISIBLE);

                if (animationListener != null) {
                    animationListener.onAnimationEnd(animation);
                }

                isAnimationStart = false;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                if (animationListener != null) {
                    animationListener.onAnimationRepeat(animation);
                }
            }
        });
    }
}
