package com.coho.moki.ui.product;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coho.moki.R;
import com.coho.moki.adapter.product.ProductChatAdapter;
import com.coho.moki.data.model.ProductChatItem;
import com.coho.moki.ui.base.BaseActivity;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Khanh Nguyen on 10/19/2017.
 */

public class ProductMessengerActivity extends BaseActivity {

    @BindView(R.id.img_product)
    ImageView imgProduct;

    @BindView(R.id.txtName)
    TextView txtName;

    @BindView(R.id.txtPrice)
    TextView txtPrice;

    @BindView(R.id.txtHeader)
    TextView txtHeader;

    @BindView(R.id.btnNavLeft)
    ImageButton btnNavLeft;

    @BindView(R.id.btnNavRight)
    ImageButton btnNavRight;

    @BindView(R.id.llTransaction)
    LinearLayout llTransaction;

    @BindView(R.id.messInstruct)
    TextView messInstruct;

    @BindView(R.id.rv_message)
    RecyclerView rvMessage;

    @BindView(R.id.btnSent)
    Button btnSent;

    @BindView(R.id.edtComment)
    EditText editTextMessage;

    List<ProductChatItem> chatItemList;
    ProductChatAdapter productChatAdapter;

    private Socket mSocket;
    {
        try {
            mSocket = IO.socket("http://chat.socket.io");
        } catch (URISyntaxException e) {}
    }

    @Override
    public int setContentViewId() {
        return R.layout.product_messenger;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        initFakeData();
        mSocket.connect();
        mSocket.on("sendMessage", onSendMessage);
    }

    private void initFakeData() {
        imgProduct.setImageResource(R.drawable.hm);
        txtName.setText("Gấu bông chất lượng cao");
        txtPrice.setText("100,000 VNĐ");
        btnNavRight.setBackgroundResource(R.drawable.ic_icon_message);
        txtHeader.setText("Shop ABC");
        llTransaction.setVisibility(View.VISIBLE);
        messInstruct.setVisibility(View.GONE);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rvMessage.setLayoutManager(mLayoutManager);
        rvMessage.setItemAnimator(new DefaultItemAnimator());

        chatItemList = new ArrayList<>();
        chatItemList.add(null);
        chatItemList.add(null);
        chatItemList.add(null);
        productChatAdapter = new ProductChatAdapter(chatItemList);
        rvMessage.setAdapter(productChatAdapter);

        btnSent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chatItemList.add(null);
                productChatAdapter.notifyDataSetChanged();
            }
        });

    }

    private void attemptSend() {
        String message = editTextMessage.getText().toString().trim();
        if (TextUtils.isEmpty(message)) {
            return;
        }

        editTextMessage.setText("");
        mSocket.emit("new message", message);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSocket.disconnect();
    }

    private Emitter.Listener onSendMessage = new Emitter.Listener() {
        @Override
        public void call(Object... args) {

        }
    };
}
