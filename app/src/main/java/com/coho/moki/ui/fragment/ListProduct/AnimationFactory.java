package com.coho.moki.ui.fragment.ListProduct;

import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ViewAnimator;

public class AnimationFactory {

    public enum FlipDirection {
        LEFT_RIGHT,
        RIGHT_LEFT;

        public float getStartDegreeForFirstView() {
            return 0.0f;
        }

        public float getStartDegreeForSecondView() {
            switch (this) {
                case LEFT_RIGHT:
                    return -90.0f;
                case RIGHT_LEFT:
                    return 90.0f;
                default:
                    return 0.0f;
            }
        }

        public float getEndDegreeForFirstView() {
            switch (this) {
                case LEFT_RIGHT:
                    return 90.0f;
                case RIGHT_LEFT:
                    return -90.0f;
                default:
                    return 0.0f;
            }
        }

        public float getEndDegreeForSecondView() {
            return 0.0f;
        }

        public FlipDirection theOtherDirection() {
            switch (this) {
                case LEFT_RIGHT:
                    return RIGHT_LEFT;
                case RIGHT_LEFT:
                    return LEFT_RIGHT;
                default:
                    return null;
            }
        }
    }

    public static Animation[] flipAnimation(View fromView, View toView, FlipDirection dir, long duration, Interpolator interpolator) {
        Interpolator accelerateInterpolator;
        Animation[] result = new Animation[2];
        float centerX = ((float) fromView.getWidth()) / 2.0f;
        float centerY = ((float) fromView.getHeight()) / 2.0f;
        Animation outFlip = new FlipAnimation(dir.getStartDegreeForFirstView(), dir.getEndDegreeForFirstView(), centerX, centerY, 0.75f, FlipAnimation.ScaleUpDownEnum.SCALE_DOWN);
        outFlip.setDuration(duration);
        outFlip.setFillAfter(true);
        if (interpolator == null) {
            accelerateInterpolator = new AccelerateInterpolator();
        } else {
            accelerateInterpolator = interpolator;
        }
        outFlip.setInterpolator(accelerateInterpolator);
        AnimationSet outAnimation = new AnimationSet(true);
        outAnimation.addAnimation(outFlip);
        result[0] = outAnimation;
        Animation inFlip = new FlipAnimation(dir.getStartDegreeForSecondView(), dir.getEndDegreeForSecondView(), centerX, centerY, 0.75f, FlipAnimation.ScaleUpDownEnum.SCALE_UP);
        inFlip.setDuration(duration);
        inFlip.setFillAfter(true);
        if (interpolator == null) {
            interpolator = new AccelerateInterpolator();
        }
        inFlip.setInterpolator(interpolator);
        inFlip.setStartOffset(duration);
        AnimationSet inAnimation = new AnimationSet(true);
        inAnimation.addAnimation(inFlip);
        result[1] = inAnimation;
        return result;
    }

    public static void flipTransition(ViewAnimator viewAnimator, FlipDirection dir) {
        FlipDirection theOtherDirection;
        View fromView = viewAnimator.getCurrentView();
        int currentIndex = viewAnimator.getDisplayedChild();
        int nextIndex = (currentIndex + 1) % viewAnimator.getChildCount();
        View toView = viewAnimator.getChildAt(nextIndex);
        if (nextIndex < currentIndex) {
            theOtherDirection = dir.theOtherDirection();
        } else {
            theOtherDirection = dir;
        }
        Animation[] animc = flipAnimation(fromView, toView, theOtherDirection, 300, null);
        viewAnimator.setOutAnimation(animc[0]);
        viewAnimator.setInAnimation(animc[1]);
        viewAnimator.showNext();
    }

    public static Animation inFromLeftAnimation(long duration, Interpolator interpolator) {
        Animation inFromLeft = new TranslateAnimation(2, -1.0f, 2, 0.0f, 2, 0.0f, 2, 0.0f);
        inFromLeft.setDuration(duration);
        if (interpolator == null) {
            interpolator = new AccelerateInterpolator();
        }
        inFromLeft.setInterpolator(interpolator);
        return inFromLeft;
    }

    public static Animation outToRightAnimation(long duration, Interpolator interpolator) {
        Animation outtoRight = new TranslateAnimation(2, 0.0f, 2, 1.0f, 2, 0.0f, 2, 0.0f);
        outtoRight.setDuration(duration);
        if (interpolator == null) {
            interpolator = new AccelerateInterpolator();
        }
        outtoRight.setInterpolator(interpolator);
        return outtoRight;
    }

    public static Animation inFromRightAnimation(long duration, Interpolator interpolator) {
        Animation inFromRight = new TranslateAnimation(2, 1.0f, 2, 0.0f, 2, 0.0f, 2, 0.0f);
        inFromRight.setDuration(duration);
        if (interpolator == null) {
            interpolator = new AccelerateInterpolator();
        }
        inFromRight.setInterpolator(interpolator);
        return inFromRight;
    }

    public static Animation outToLeftAnimation(long duration, Interpolator interpolator) {
        Animation outtoLeft = new TranslateAnimation(2, 0.0f, 2, -1.0f, 2, 0.0f, 2, 0.0f);
        outtoLeft.setDuration(duration);
        if (interpolator == null) {
            interpolator = new AccelerateInterpolator();
        }
        outtoLeft.setInterpolator(interpolator);
        return outtoLeft;
    }

    public static Animation inFromTopAnimation(long duration, Interpolator interpolator) {
        Animation infromtop = new TranslateAnimation(2, 0.0f, 2, 0.0f, 2, -1.0f, 2, 0.0f);
        infromtop.setDuration(duration);
        if (interpolator == null) {
            interpolator = new AccelerateInterpolator();
        }
        infromtop.setInterpolator(interpolator);
        return infromtop;
    }

    public static Animation outToTopAnimation(long duration, Interpolator interpolator) {
        Animation outtotop = new TranslateAnimation(2, 0.0f, 2, 0.0f, 2, 0.0f, 2, -1.0f);
        outtotop.setDuration(duration);
        if (interpolator == null) {
            interpolator = new AccelerateInterpolator();
        }
        outtotop.setInterpolator(interpolator);
        return outtotop;
    }

    public static Animation fadeInAnimation(long duration, long delay) {
        Animation fadeIn = new AlphaAnimation(0.0f, 1.0f);
        fadeIn.setInterpolator(new DecelerateInterpolator());
        fadeIn.setDuration(duration);
        fadeIn.setStartOffset(delay);
        return fadeIn;
    }

    public static Animation fadeOutAnimation(long duration, long delay) {
        Animation fadeOut = new AlphaAnimation(1.0f, 0.0f);
        fadeOut.setInterpolator(new AccelerateInterpolator());
        fadeOut.setStartOffset(delay);
        fadeOut.setDuration(duration);
        return fadeOut;
    }

    public static Animation fadeInAnimation(long duration, final View view) {
        Animation animation = fadeInAnimation(500, 0);
        animation.setAnimationListener(new AnimationListener() {
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(View.VISIBLE);
            }

            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationStart(Animation animation) {
                view.setVisibility(View.GONE);
            }
        });
        return animation;
    }

    public static Animation fadeOutAnimation(long duration, final View view) {
        Animation animation = fadeOutAnimation(500, 0);
        animation.setAnimationListener(new AnimationListener() {
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(View.GONE);
            }

            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationStart(Animation animation) {
                view.setVisibility(View.VISIBLE);
            }
        });
        return animation;
    }

    public static Animation[] fadeInThenOutAnimation(long duration, long delay) {
        return new Animation[]{fadeInAnimation(duration, 0), fadeOutAnimation(duration, duration + delay)};
    }

    public static void fadeOut(View v) {
        if (v != null) {
            v.startAnimation(fadeOutAnimation(500, v));
        }
    }

    public static void fadeIn(View v) {
        if (v != null) {
            v.startAnimation(fadeInAnimation(500, v));
        }
    }

    public static void fadeInThenOut(final View v, long delay) {
        if (v != null) {
            v.setVisibility(View.VISIBLE);
            AnimationSet animation = new AnimationSet(true);
            Animation[] fadeInOut = fadeInThenOutAnimation(500, delay);
            animation.addAnimation(fadeInOut[0]);
            animation.addAnimation(fadeInOut[1]);
            animation.setAnimationListener(new AnimationListener() {
                public void onAnimationEnd(Animation animation) {
                    v.setVisibility(View.GONE);
                }

                public void onAnimationRepeat(Animation animation) {
                }

                public void onAnimationStart(Animation animation) {
                    v.setVisibility(View.VISIBLE);
                }
            });
            v.startAnimation(animation);
        }
    }
}
