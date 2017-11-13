package com.coho.moki.ui.start_tutorial;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.widget.Button;

import com.coho.moki.BaseApp;
import com.coho.moki.R;
import com.coho.moki.adapter.customadapter.TutorialPagerAdapter;
import com.coho.moki.ui.base.BaseActivity;
import com.coho.moki.ui.login.LoginActivity;
import com.coho.moki.ui.main.MainActivity;
import com.coho.moki.util.AccountUntil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by trung on 10/11/2017.
 */

public class StartTutorialActivity2 extends BaseActivity {

    @BindView(R.id.vp_tutorial)
    ViewPager mVPTutorial;

    @BindView(R.id.indicator)
    public PageIndicator pageIndicator;

    @BindView(R.id.skip_button)
    Button mSkipButton;

    @OnClick(R.id.skip_button)
    public void onClickSkipButton(){
        AccountUntil.passTutorialScreen();
        String userId = AccountUntil.getAccountId();

        if (userId == null){
            Intent intent = new Intent(BaseApp.getContext(), LoginActivity.class);
            startActivity(intent);
            this.finish();
        }
        else{
            Intent intent = new Intent(BaseApp.getContext(), MainActivity.class);
            startActivity(intent);
            this.finish();
        }
    }



    @Override
    public int setContentViewId() {
        return R.layout.tutorial;
    }

    @Override
    public void initView() {
        TutorialPagerAdapter adapter = new TutorialPagerAdapter(getSupportFragmentManager());
        mVPTutorial.setAdapter(adapter);

        mVPTutorial.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 1){
                    pageIndicator.current = 2;
                    pageIndicator.invalidate();
                    mSkipButton.setText("Bỏ qua");
                }
                else if (position == 2){
                    pageIndicator.current = 3;
                    pageIndicator.invalidate();
                    mSkipButton.setText("Bắt đầu");
                }
                else {
                    pageIndicator.current = 1;
                    pageIndicator.invalidate();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void initData() {

    }
}
