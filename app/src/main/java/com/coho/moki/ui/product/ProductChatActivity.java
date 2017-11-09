package com.coho.moki.ui.product;

import android.os.AsyncTask;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coho.moki.BaseApp;
import com.coho.moki.R;
import com.coho.moki.adapter.product.ProductChatAdapter;
import com.coho.moki.data.constant.AppConstant;
import com.coho.moki.data.model.Product;
import com.coho.moki.data.model.ProductChatItem;
import com.coho.moki.ui.base.BaseActivity;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONObject;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Khanh Nguyen on 10/19/2017.
 */

public class ProductChatActivity extends BaseActivity {

    private static final String LOG_TAG = ProductChatActivity.class.getSimpleName();

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

//    private static final String CHAT_SERVER_URL = "http://chat.socket.io";
    private static final String NEW_MESSAGE = "receiveNewMessage";

    private boolean isConnected = true;
    String mUsername = "username";
    String message = "message to server";

    private Socket mSocket;

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
        initSocket();
    }

    private void initFakeData() {
        imgProduct.setImageResource(R.drawable.hm01);
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
                attemptSend();
            }
        });

    }

    public void initSocket() {
        try {
            mSocket = IO.socket(AppConstant.BASEURL_TAG);
            if (mSocket == null) {
                Log.e(LOG_TAG, "mSocket is null");
            } else {
                mSocket.on(Socket.EVENT_CONNECT, onConnect);
                mSocket.on(Socket.EVENT_DISCONNECT, onDisconnect);
                mSocket.on(Socket.EVENT_CONNECT_ERROR, onConnectError);
                mSocket.on(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);
                mSocket.on(NEW_MESSAGE, onNewMessage);
                mSocket.connect();
            }
        } catch (URISyntaxException e) {
            Log.e(LOG_TAG, "error socket: " + e.getMessage());
        }
    }

    public void destroySocket() {
        mSocket.disconnect();
        mSocket.off(Socket.EVENT_CONNECT, onConnect);
        mSocket.off(Socket.EVENT_DISCONNECT, onDisconnect);
        mSocket.off(Socket.EVENT_CONNECT_ERROR, onConnectError);
        mSocket.off(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);
        mSocket.off(NEW_MESSAGE, onNewMessage);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        destroySocket();
    }

    private void attemptSend() {
        if (null == mUsername || !mSocket.connected()) return;

        // set empty text for textview
        // add text message to recycler view

        // perform the sending message attempt.
        mSocket.emit("new message", message);
    }

    private Emitter.Listener onConnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(!isConnected) {
                        Log.i(LOG_TAG, "Socket Connected!");
                        if(null != mUsername)
                            mSocket.emit("add user", mUsername);
                        isConnected = true;
                    }
                }
            });
        }
    };

    private Emitter.Listener onDisconnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.i(LOG_TAG, "socket disconnected");
                    isConnected = false;
                }
            });
        }
    };

    private Emitter.Listener onConnectError = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.i(LOG_TAG, "socket error connect");
                }
            });
        }
    };

    private Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    Log.i(LOG_TAG, "receive new message");
                    Log.i(LOG_TAG, "data receive" + data.toString());
                }
            });
        }
    };

}
