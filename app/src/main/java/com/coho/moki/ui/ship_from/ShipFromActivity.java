package com.coho.moki.ui.ship_from;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;

import com.coho.moki.BaseApp;
import com.coho.moki.R;
import com.coho.moki.adapter.customadapter.ListShipFromAdapter;
import com.coho.moki.data.constant.AppConstant;
import com.coho.moki.data.remote.BrandResponceData;
import com.coho.moki.data.remote.ShipFromResponseData;
import com.coho.moki.service.ResponseListener;
import com.coho.moki.service.UserService;
import com.coho.moki.service.UserServiceImpl;
import com.coho.moki.ui.base.BaseActivity;
import com.coho.moki.ui.product.add.AddNewAddressActivity;
import com.coho.moki.util.DialogUtil;
import com.coho.moki.util.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import se.emilsjolander.stickylistheaders.ExpandableStickyListHeadersListView;

/**
 * Created by trung on 18/11/2017.
 */

public class ShipFromActivity extends BaseActivity {

    @BindView(R.id.expandableList)
    ExpandableStickyListHeadersListView mExpandableStickyListHeadersListView;

    @BindView(R.id.edtSearch)
    EditText mEdtSearch;

    List<ShipFromResponseData> shipFromResponseDatas;
    ListShipFromAdapter adapter;
    UserService service = new UserServiceImpl();

    int level = 1;
    int parentId = -1;

    @Override
    public int setContentViewId() {
        return R.layout.sticklist;
    }

    @Override
    public void handleIntent(Intent intent) {
        super.handleIntent(intent);
        level = intent.getIntExtra(AppConstant.LEVEL_TAG, 1);
        parentId = intent.getIntExtra(AppConstant.PARENT_ID_TAG, -1);
    }

    @Override
    public void initView() {
        shipFromResponseDatas = new ArrayList<ShipFromResponseData>();
        adapter = new ListShipFromAdapter(BaseApp.getContext(), shipFromResponseDatas);
        mExpandableStickyListHeadersListView.setAdapter(adapter);

        mExpandableStickyListHeadersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = getIntent();
                intent.putExtra(AppConstant.SHIPFROM_TAG_NAME, shipFromResponseDatas.get(i).getName());
                intent.putExtra(AppConstant.SHIPFROM_TAG_ID, shipFromResponseDatas.get(i).getOrder());
                Utils.toastShort(BaseApp.getContext(), shipFromResponseDatas.get(i).getOrder() + "");
                setResult(AppConstant.RESULT_CODE_SHIPFORM, intent);
                finish();
            }
        });

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

    @Override
    public void initData() {

        DialogUtil.showProgress(this);
        service.getUserShipFrom(level, parentId, new ResponseListener<List<ShipFromResponseData>>() {
            @Override
            public void onSuccess(List<ShipFromResponseData> dataResponse) {
                DialogUtil.hideProgress();
                shipFromResponseDatas.addAll(dataResponse);
                Collections.sort(shipFromResponseDatas);

                adapter.cloneData();
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(String errorMessage) {
            }
        });
    }

    private void searchText(String text){
        if (adapter != null){
            adapter.searchText(text);
        }
    }
}
