package com.coho.moki.ui.main_search;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coho.moki.BaseApp;
import com.coho.moki.R;
import com.coho.moki.data.constant.AppConstant;
import com.coho.moki.data.model.Size;
import com.coho.moki.ui.base.BaseActivity;
import com.coho.moki.ui.product_search.ProductSearchActivity;
import com.coho.moki.ui.size.SizeActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by trung on 03/11/2017.
 */

public class MainSearchActivity extends BaseActivity implements MainSearchContract.View {

    String mKeyword;
    String mSizeId;

    @Inject
    MainSearchContract.Presenter mPresenter;

    @BindView(R.id.edtKeyword)
    EditText mEdtKeyword;

    @BindView(R.id.txtSize)
    TextView mTxtSize;

    @OnClick(R.id.layout_size)
    public void clickChooseSize(){
        startActivityForResult(new Intent(BaseApp.getContext(), SizeActivity.class), AppConstant.REQUEST_CODE_SIZE);
    }

    @OnClick(R.id.btn_search)
    public void onClickButtonSearch(){
        mKeyword = mEdtKeyword.getText().toString();
//        mPresenter.callSearchProduct(keyword);

        openProductSearchActivity(mKeyword, mSizeId);

    }

    @Override
    public int setContentViewId() {
        return R.layout.activity_main_search;
    }

    @Override
    public void initView() {
        BaseApp.getActivityComponent().inject(this);
        mPresenter.onAttach(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void openProductSearchActivity(String keyword, String sizeId) {
        Intent intent = new Intent(BaseApp.getContext(), ProductSearchActivity.class);
        intent.putExtra(AppConstant.KEYWORD_TAG, keyword);
        intent.putExtra(AppConstant.PRODUCT_SIZE_ID_TAG, sizeId);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == AppConstant.REQUEST_CODE_SIZE){
            if (resultCode == AppConstant.RESULT_CODE_SIZE){
                Size size = data.getParcelableExtra(AppConstant.SIZE_TAG);
                Log.d("sizeactivity", size.getSizeName());
                mSizeId = size.getSizeId();
                mTxtSize.setText(size.getSizeName());
            }
        }
    }
}
