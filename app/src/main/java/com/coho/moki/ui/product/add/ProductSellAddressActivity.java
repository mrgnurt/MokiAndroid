package com.coho.moki.ui.product.add;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.coho.moki.R;
import com.coho.moki.adapter.product.ProductSellAddressAdapter;
import com.coho.moki.callback.MyOnClickListener;
import com.coho.moki.data.remote.ProductSellAddressResponse;
import com.coho.moki.ui.base.BaseActivity;
import com.coho.moki.util.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Khanh Nguyen on 11/16/2017.
 */

public class ProductSellAddressActivity extends BaseActivity {

    @BindView(R.id.listView)
    ListView listView;

    @BindView(R.id.txtFinish)
    TextView txtFinish;

    @BindView(R.id.btnNavLeft)
    ImageButton btnBack;

    @BindView(R.id.btnNavRight)
    ImageButton btnNavRight;

    @BindView(R.id.txtHeader)
    TextView txtHeader;

    private ProductSellAddressAdapter mAddressAdapter;
    private List<ProductSellAddressResponse> mAddressResponseList;

    @Override
    public int setContentViewId() {
        return R.layout.address_choose;
    }

    @Override
    public void initView() {
        btnNavRight.setVisibility(View.INVISIBLE);
        txtHeader.setText(Utils.toTitleCase(getResources().getString(R.string.address)));
    }

    @Override
    public void initData() {
        // TODO: remove fakeDate when call api
        fakeData();
    }

    private void fakeData() {
        mAddressResponseList = new ArrayList<>();
        mAddressResponseList.add(null);
        mAddressResponseList.add(null);
        mAddressResponseList.add(null);
        mAddressAdapter = new ProductSellAddressAdapter(this, R.layout.order_address_item, mAddressResponseList);
        mAddressAdapter.setOnClickEditAddressListener(new MyOnClickListener() {
            @Override
            public void onClick() {
                openDialog();
            }
        });
        listView.setAdapter(mAddressAdapter);
    }

    private void openDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.address_dialog);
        final Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setWindowAnimations(R.style.DialogAnimation);
        // bind view
        View viewTop = dialog.findViewById(R.id.viewTop);
        Button btnDelete = dialog.findViewById(R.id.btnDelete);
        Button btnEdit = dialog.findViewById(R.id.btnEdit);
        Button btnSetDefault = dialog.findViewById(R.id.btnSetDefault);
        Button btnCancel = dialog.findViewById(R.id.btnCancel);
        // check if default => btnSetDefault is gone
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.viewTop:
                    case R.id.btnCancel:
                        dialog.dismiss();
                        break;
                    case R.id.btnEdit:
                        // TODO: start activity edit address
                        break;
                    case R.id.btnSetDefault:
                        // TODO: call api to set default
                        break;
                    case R.id.btnDelete:
                        // TODO: call api to delete at here
                        break;
                }
            }
        };
        viewTop.setOnClickListener(listener);
        btnDelete.setOnClickListener(listener);
        btnEdit.setOnClickListener(listener);
        btnSetDefault.setOnClickListener(listener);
        btnCancel.setOnClickListener(listener);
        dialog.show();
    }

    @OnClick(R.id.txtFinish)
    public void onClickButtonFinish() {
        Intent intent = new Intent(this, AddNewAddressActivity.class);
    }

    @OnClick(R.id.btnNavLeft)
    public void onClickButtonBack() {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
