package com.coho.moki.ui.brand_search;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;

import com.coho.moki.R;
import com.coho.moki.adapter.customadapter.ListBrandAdapter;
import com.coho.moki.data.constant.AppConstant;
import com.coho.moki.data.remote.BrandResponceData;
import com.coho.moki.service.BrandService;
import com.coho.moki.service.BrandServiceImpl;
import com.coho.moki.service.ResponseListener;
import com.coho.moki.ui.base.BaseActivity;
import com.coho.moki.util.DialogUtil;
import com.coho.moki.util.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import se.emilsjolander.stickylistheaders.ExpandableStickyListHeadersListView;

/**
 * Created by trung on 17/11/2017.
 */

public class SearchBrandActivity extends BaseActivity {

    @BindView(R.id.expandableList)
    ExpandableStickyListHeadersListView mExpandableStickyListHeadersListView;

    @BindView(R.id.edtSearch)
    EditText mEdtSearch;

    ListBrandAdapter adapter;

    BrandService brandService = new BrandServiceImpl();

    String mCategory = null;

    List<BrandResponceData> brandResponceDatas;

    @Override
    public int setContentViewId() {
        return R.layout.sticklist;
    }

    @Override
    public void initView() {
        initStickyList();

        mEdtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                searchText(editable.toString());
            }
        });
    }

    private void initStickyList(){

        brandResponceDatas = new ArrayList<BrandResponceData>();
//        brandResponceDatas.add(new BrandResponceData("", "abbott"));
//        brandResponceDatas.add(new BrandResponceData("", "abonne"));
//        brandResponceDatas.add(new BrandResponceData("", "babioo"));
//        brandResponceDatas.add(new BrandResponceData("", "babylao"));
//        brandResponceDatas.add(new BrandResponceData("", "comfdk"));
//        brandResponceDatas.add(new BrandResponceData("", "tkgfkgf"));
//        brandResponceDatas.add(new BrandResponceData("", "woeoo"));
//        brandResponceDatas.add(new BrandResponceData("", "kaico"));

        adapter = new ListBrandAdapter(this, brandResponceDatas);
        mExpandableStickyListHeadersListView.setAdapter(adapter);

        mExpandableStickyListHeadersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = getIntent();
                intent.putExtra(AppConstant.BRAND_TAG_NAME, brandResponceDatas.get(i).getName());
                intent.putExtra(AppConstant.BRAND_TAG_ID, brandResponceDatas.get(i).getId());
                setResult(AppConstant.RESULT_CODE_BRAND, intent);
                finish();
            }
        });
//        mExpandableStickyListHeadersListView.isHeaderCollapsed();
    }

    @Override
    public void initData() {

        DialogUtil.showProgress(this);
        brandService.getListBrand(mCategory, new ResponseListener<List<BrandResponceData>>() {
            @Override
            public void onSuccess(List<BrandResponceData> dataResponse) {
                DialogUtil.hideProgress();
                brandResponceDatas.addAll(dataResponse);
                Collections.sort(brandResponceDatas);

                brandResponceDatas.add(0, new BrandResponceData(null, "Tất cả"));
                adapter.cloneData();
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(String errorMessage) {
                DialogUtil.hideProgress();
                Utils.toastShort(SearchBrandActivity.this, errorMessage);
            }
        });
    }

    private void searchText(String text){
        if (adapter != null){
            adapter.searchText(text);
        }
    }
}
