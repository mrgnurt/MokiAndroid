package com.coho.moki.ui.fragment;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.coho.moki.R;
import com.coho.moki.ui.base.BaseFragment;
import com.coho.moki.ui.start_tutorial.InfiniteScrollImage;
import com.coho.moki.ui.start_tutorial.StartTutorialActivity2;

import butterknife.BindView;

/**
 * Created by trung on 10/11/2017.
 */

public class TutorialFragment3 extends BaseFragment {

//    @BindView(R.id.list_product2)
//    ImageView imageView;
//
//    private Handler handler = new Handler();
//    Runnable runnable = new C28491();
//
//    class C28491 implements Runnable {
//        C28491() {
//        }
//
//        public void run() {
//            TutorialFragment3.this.imageView.invalidate();
//            TutorialFragment3.this.handler.postDelayed(TutorialFragment3.this.runnable, 1);
//        }
//    }

    @Override
    protected int getLayoutId() {
        return R.layout.test_tutorial3;
    }

    @Override
    protected void initView() {
        super.initView();

    }
}
