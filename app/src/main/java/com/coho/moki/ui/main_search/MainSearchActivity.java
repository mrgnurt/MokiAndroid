package com.coho.moki.ui.main_search;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coho.moki.BaseApp;
import com.coho.moki.R;
import com.coho.moki.data.constant.AppConstant;
import com.coho.moki.data.model.Size;
import com.coho.moki.ui.base.BaseActivity;
import com.coho.moki.ui.brand_search.SearchBrandActivity;
import com.coho.moki.ui.product_search.ProductSearchActivity;
import com.coho.moki.ui.size.SizeActivity;
import com.coho.moki.util.Utils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by trung on 03/11/2017.
 */

public class MainSearchActivity extends BaseActivity implements MainSearchContract.View {

    String mKeyword;
    String mSizeId = null;
    String mBrandId = null;

    boolean checkSearch = false;

    @Inject
    MainSearchContract.Presenter mPresenter;

    @BindView(R.id.edtKeyword)
    EditText mEdtKeyword;

    @BindView(R.id.txtSize)
    TextView mTxtSize;

    @BindView(R.id.txtBrand)
    TextView mTxtBrand;

    @BindView(R.id.btn_search)
    Button mBtnSearch;

    @OnClick(R.id.layout_size)
    public void clickChooseSize(){
        startActivityForResult(new Intent(BaseApp.getContext(), SizeActivity.class), AppConstant.REQUEST_CODE_SIZE);
    }

    @OnClick(R.id.layout_brand)
    public void clickChooseBrand(){
        startActivityForResult(new Intent(BaseApp.getContext(), SearchBrandActivity.class), AppConstant.REQUEST_CODE_BRAND);
    }

    @OnClick(R.id.btn_search)
    public void onClickButtonSearch(){

        if (checkSearch){
            mKeyword = mEdtKeyword.getText().toString();
            openProductSearchActivity(mKeyword, mSizeId, mBrandId);
        }
    }

    @Override
    public int setContentViewId() {
        return R.layout.activity_main_search;
    }

    @Override
    public void initView() {
        BaseApp.getActivityComponent().inject(this);
        mPresenter.onAttach(this);
        mEdtKeyword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {

                checkSearch();
            }
        });
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @OnClick(R.id.btnNavLeft)
    public void btnNavaLeftOnClick() {
        this.finish();
    }

    public void enableSearch(){
        checkSearch = true;
        mBtnSearch.setBackgroundResource(R.drawable.background_enable_button);
    }

    public void disableSearch(){
        checkSearch = false;
        mBtnSearch.setBackgroundResource(R.drawable.background_disable_button);
    }

    @Override
    public void initData() {

    }

    @Override
    public void openProductSearchActivity(String keyword, String sizeId, String brandId) {
        Intent intent = new Intent(BaseApp.getContext(), ProductSearchActivity.class);
        intent.putExtra(AppConstant.KEYWORD_TAG, keyword);
        intent.putExtra(AppConstant.PRODUCT_SIZE_ID_TAG, sizeId);
        intent.putExtra(AppConstant.PRODUCT_BRAND_ID_TAG, brandId);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == AppConstant.REQUEST_CODE_SIZE){
            if (resultCode == AppConstant.RESULT_CODE_SIZE){
                Size size = data.getParcelableExtra(AppConstant.SIZE_TAG);
                mSizeId = size.getSizeId();
                mTxtSize.setText(size.getSizeName());
                checkSearch();
            }
        }
        else if (requestCode == AppConstant.REQUEST_CODE_BRAND){
            if (resultCode == AppConstant.RESULT_CODE_BRAND){
                mBrandId = data.getStringExtra(AppConstant.BRAND_TAG_ID);
                mTxtBrand.setText(data.getStringExtra(AppConstant.BRAND_TAG_NAME));
                checkSearch();
            }
        }
    }

    private void checkSearch(){

        if (mEdtKeyword.getText().length() == 0 && mSizeId == null && mBrandId == null){
            disableSearch();
        }
        else {
            enableSearch();
        }
    }

}
