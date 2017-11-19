package com.coho.moki.ui.size;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.coho.moki.BaseApp;
import com.coho.moki.R;
import com.coho.moki.adapter.customadapter.ListSizeAdapter;
import com.coho.moki.callback.OnClickSizeItemListener;
import com.coho.moki.data.constant.AppConstant;
import com.coho.moki.data.model.Size;
import com.coho.moki.ui.base.BaseActivity;
import com.coho.moki.util.SpaceItem;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by trung on 07/11/2017.
 */

public class SizeActivity extends BaseActivity implements SizeContract.View {

    @BindView(R.id.rv_list)
    RecyclerView mRVList;

    @Inject
    SizeContract.Presenter mPresenter;

    ListSizeAdapter mListSizeAdapter;

    @Override
    public int setContentViewId() {
        return R.layout.activity_size;
    }

    @Override
    public void initPresenter() {
        super.initPresenter();
        BaseApp.getActivityComponent().inject(this);
        mPresenter.onAttach(this);
    }

    @Override
    public void initView() {
        initRV();
    }

    @Override
    public void initData() {
        mPresenter.callSearchProduct(null);
    }

    @Override
    public void showSizes(List<Size> sizes) {
        mListSizeAdapter.insertLastItem(sizes);
    }

    @OnClick(R.id.btnNavLeft)
    public void btnNavLeftOnClick() {
        this.finish();
    }


    public void initRV(){

        mPresenter.initSizes();

        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(BaseApp.getContext());
        mRVList.setLayoutManager(gridLayoutManager);

//        int space = 13;
//        SpaceItem spaceItem = new SpaceItem(space);
//        mRVList.addItemDecoration(spaceItem);

        mListSizeAdapter = new ListSizeAdapter(mPresenter.getSizes());
        mRVList.setAdapter(mListSizeAdapter);

        mListSizeAdapter.addListener(new OnClickSizeItemListener() {
            @Override
            public void onClick(Size size) {
                Intent intent = getIntent();
                intent.putExtra(AppConstant.SIZE_TAG, size);
                setResult(AppConstant.RESULT_CODE_SIZE, intent);
                finish();
            }
        });
    }
}
