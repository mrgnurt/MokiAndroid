package com.coho.moki.ui.product;

import android.content.Intent;
import android.os.Bundle;
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
import com.coho.moki.data.constant.ResponseCode;
import com.coho.moki.data.model.Product;
import com.coho.moki.data.model.ProductChatItem;
import com.coho.moki.data.remote.Conversation;
import com.coho.moki.data.remote.ConversationResponseData;
import com.coho.moki.data.remote.ProductCons;
import com.coho.moki.service.ConversationService;
import com.coho.moki.service.ConversationServiceImpl;
import com.coho.moki.service.ResponseListener;
import com.coho.moki.ui.base.BaseActivity;
import com.coho.moki.util.AccountUntil;
import com.coho.moki.util.StringUtil;
import com.coho.moki.util.Utils;
import com.coho.moki.util.network.LoadImageUtils;

import org.json.JSONException;
import org.json.JSONObject;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Khanh Nguyen on 10/19/2017.
 */

public class ProductChatActivity extends BaseActivity {

    private static final String LOG_TAG = "ProductChatActivity";

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

    ConversationService conversationService;

    private boolean isConnected = true;

    private Socket mSocket;

    private String mToken;

    private String mProductId;

    private String mMyId;

    private String mMyAvatar;

    private String mPartnerId;

    private String mPartnerAvatar;

    private String mSellerName;

    private String mSellerAvatar;

    private String mSellerId;

    private boolean isOwnerProduct;

    private String mSendToken;

    private int currIndex;

    private int limitPerLoad;

    private ProductCons mProductCons;


    @Override
    public int setContentViewId() {
        return R.layout.product_messenger;
    }

    @Override
    public void handleIntent(Intent intent) {
        Bundle data = intent.getBundleExtra("package");
        mPartnerId = data.getString("partner_id");
        mPartnerAvatar = data.getString("partner_avatar");
        mSellerName = data.getString("seller_name");
        mSellerId = data.getString("seller_id");
        mSellerAvatar = data.getString("seller_avatar");
        isOwnerProduct = data.getBoolean("is_owner_product");
    }

    @Override
    public void initView() {
        btnNavRight.setBackgroundResource(R.drawable.ic_icon_message);

        if (isOwnerProduct) {
            llTransaction.setVisibility(View.GONE);
            messInstruct.setVisibility(View.GONE);
        } else {
            llTransaction.setVisibility(View.VISIBLE);
            messInstruct.setVisibility(View.VISIBLE);
        }

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rvMessage.setLayoutManager(mLayoutManager);
        rvMessage.setItemAnimator(new DefaultItemAnimator());

        chatItemList = new ArrayList<>();
        productChatAdapter = new ProductChatAdapter(chatItemList);
        rvMessage.setAdapter(productChatAdapter);

        btnSent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });
    }

    @Override
    public void initData() {
        conversationService = new ConversationServiceImpl();
        currIndex = 0;
        limitPerLoad = 20;
        loadMyInfo();
        loadHistoryConversations();
    }

    private void initFakeData() {
        imgProduct.setImageResource(R.drawable.hm01);
        txtName.setText("Gấu bông chất lượng cao");
        txtPrice.setText("100,000 VNĐ");
        txtHeader.setText("Shop ABC");



        mToken = AccountUntil.getUserToken();
        Log.d("user_token", mToken);

//        mToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc0xvZ2luIjp0cnVlLCJ1c2VyIjp7ImlkIjoiNWEwNzc4ZmVlZDAxOWMwZTkzZWNkYmI4IiwidXNlcm5hbWUiOiJKb2R5IFplbWxhayIsInBob25lTnVtYmVyIjoiMDE2NzIwMTgyNDAiLCJyb2xlIjoxLCJ1cmwiOiJodHRwOi8vcmhlYS5iaXoiLCJhdmF0YXIiOiJodHRwczovL3MzLmFtYXpvbmF3cy5jb20vdWlmYWNlcy9mYWNlcy90d2l0dGVyL3RvZGRyZXcvMTI4LmpwZyJ9LCJleHBpcmVkQXQiOiIyMDE3LTExLTE1VDA5OjU1OjMwLjc4MVoifQ.FcnGxmlehCCDwtGnLp6OZiMkPmllyYCKIxTM_0IyrJE";
        mPartnerId = "5a077900ed019c0e93ecdbd6";
        mProductId = "5a077903ed019c0e93ecdc1a";
    }

    public void loadMyInfo() {
        mToken = AccountUntil.getUserToken();
        mMyId = AccountUntil.getAccountId();
        mMyAvatar = AccountUntil.getAvatarUrl();
    }

    public void loadHistoryConversations() {

        conversationService.loadConversationDetail(mToken, mPartnerId, mProductId, limitPerLoad, currIndex, new ResponseListener<ConversationResponseData>() {
            @Override
            public void onSuccess(ConversationResponseData dataResponse) {
                List<Conversation> chats = dataResponse.getConversation();
                ProductCons product = dataResponse.getProduct();

                populateConversation(chats, product);
            }

            @Override
            public void onFailure(String errorMessage) {
                Log.d("failure_get_cons_detail", errorMessage);
            }
        });
    }

    public void populateConversation(List<Conversation> historyChats, ProductCons product) {

        if (product == null) {
            return;
        }

        this.mProductCons = product;
        LoadImageUtils.loadImageFromUrl(product.getImage(), imgProduct, null);
        txtName.setText(product.getName());
        String price = product.getPrice() + "";
        String priceFormated = Utils.formatPrice(price);
        txtPrice.setText(priceFormated);
        txtHeader.setText(mSellerName);

        if (historyChats == null || historyChats.size() < 1) {
            return;
        }

        List<ProductChatItem> addItems = new ArrayList<>();
        for (Conversation historyLine : historyChats) {
            ProductChatItem addItem = new ProductChatItem();
            addItem.setMessage(historyLine.getMessage());
            addItem.setSendAt(new Date());

            int role = ProductChatItem.SENDER;
            String avatar = mMyAvatar;
            if (mProductId.equals(historyLine.getSender().getId())) {
                role = ProductChatItem.RECEIVER;
                avatar = mPartnerAvatar;
            }
            addItem.setRole(role);
            addItem.setAvatar(avatar);

            addItems.add(addItem);
        }

        productChatAdapter.addItems(addItems);
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

                mSocket.on(AppConstant.SOCKET_JOIN_ROOM_RESPONSE, onJoinRoomResponse);
                mSocket.on(AppConstant.SOCKET_MESSAGE, onNewMessage);
                mSocket.on(AppConstant.SOCKET_UPDATE_MSG_STATUS, onUpdateMsgStatus);
                mSocket.connect();
            }
        } catch (URISyntaxException e) {
            Log.e(LOG_TAG, "error socket: " + e.getMessage());
        }
    }

    public void reconnect() {
        if (mSocket == null) {
            initSocket();
        } else if (!mSocket.connected()) {
            destroySocket();
            initSocket();
        }
    }

    public void destroySocket() {
       if (mSocket != null) {
           mSocket.disconnect();
           mSocket.off(Socket.EVENT_CONNECT, onConnect);
           mSocket.off(Socket.EVENT_DISCONNECT, onDisconnect);
           mSocket.off(Socket.EVENT_CONNECT_ERROR, onConnectError);
           mSocket.off(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);

           mSocket.off(AppConstant.SOCKET_JOIN_ROOM_RESPONSE, onJoinRoomResponse);
           mSocket.off(AppConstant.SOCKET_MESSAGE, onNewMessage);
           mSocket.off(AppConstant.SOCKET_UPDATE_MSG_STATUS, onUpdateMsgStatus);

           mSocket = null;
       }
    }

    @Override
    protected void onResume() {
        super.onResume();
        reconnect();
    }

    @Override
    protected void onDestroy() {
        destroySocket();
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        destroySocket();
        super.onStop();
    }

    private void sendMessage() {
        String input = editTextMessage.getText().toString().trim();
        editTextMessage.setText("");

        if (!StringUtil.checkStringValid(input)) {
            return;
        }

        if (!Utils.checkInternetAvailable()) {
            Utils.toastShort(BaseApp.getContext(), AppConstant.NO_INTERNET);
            return;
        }

        ProductChatItem addItem = getMyProductChatItem(input);
        productChatAdapter.addItem(addItem);
        doSend(input);
    }

    private void doSend(String message) {
        if (mSocket == null && !mSocket.connected()) return;

        JSONObject sendParam = getSendParam(message);
        if (sendParam == null) {
            return;
        }

        mSocket.emit(AppConstant.SOCKET_MESSAGE, sendParam);
    }

    public void receiveMessage(JSONObject receiveMsgObj) {
        final ProductChatItem receiveItem = getReceivedMessage(receiveMsgObj);
        updateMsgStatus(receiveItem.getServerMsgId());

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                productChatAdapter.addItem(receiveItem);
            }
        });
    }

    public void updateMsgStatus(String messageId) {
       JSONObject updateParam = getUpdateMsgStatusParam(messageId);
       if (updateParam == null) {
           return;
       }

       mSocket.emit(AppConstant.SOCKET_UPDATE_MSG_STATUS, updateParam);
    }

    public ProductChatItem getReceivedMessage(JSONObject receiveMsgObj) {
        ProductChatItem receiveItem = null;
        try {
            JSONObject sender = receiveMsgObj.getJSONObject("sender");
            JSONObject messageObj = receiveMsgObj.getJSONObject("message");

            String avatar = sender.getString("avatar");
            String message = messageObj.getString("content");
            String messageId = messageObj.getString("id");
            String sendAtString = sender.getString("sendAt");
            Date sendAt = new Date();
            int role = ProductChatItem.RECEIVER;

            receiveItem = new ProductChatItem();
            receiveItem.setMessage(message);
            receiveItem.setAvatar(avatar);
            receiveItem.setRole(role);
            receiveItem.setSendAt(sendAt);
            receiveItem.setServerMsgId(messageId);

            Log.d("receiveMessage", message);

            productChatAdapter.addItem(receiveItem);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return receiveItem;
    }

    public ProductChatItem getMyProductChatItem(String message) {
        String avatar = AccountUntil.getAvatarUrl();
        Date sendAt = new Date();
        int role = ProductChatItem.SENDER;
        return new ProductChatItem(role, message, avatar, sendAt);
    }

    public JSONObject getSendParam(String message) {
        JSONObject sendParam = null;

        try {
            if (mSendToken != null) {
                sendParam = new JSONObject();
                sendParam.put("message", message);
                sendParam.put("sendToken", mSendToken);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
       return sendParam;
    }

    public JSONObject getUpdateMsgStatusParam(String messageId) {
        JSONObject updateParam = null;
        try {
            if (mSendToken != null) {
                updateParam = new JSONObject();
                updateParam.put("sendToken", mSendToken);
                updateParam.put("messageId", messageId);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return updateParam;
    }

    private Emitter.Listener onConnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            isConnected = true;
            JSONObject joinRoomParam = new JSONObject();
            try {
                joinRoomParam.put("token", mToken);
                joinRoomParam.put("receiverId", mPartnerId);
                joinRoomParam.put("productId", mProductId);

                mSocket.emit(AppConstant.SOCKET_JOIN_ROOM_REQUEST, joinRoomParam);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    private Emitter.Listener onJoinRoomResponse = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            JSONObject response = (JSONObject) args[0];
            try {
                int code = response.getInt("code");
                if (code == ResponseCode.OK.code) {
                    JSONObject data = response.getJSONObject("data");
                    mSendToken = data.getString("sendToken");
                    Log.d("code", code + "");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    private Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            JSONObject response = (JSONObject) args[0];
            receiveMessage(response);

        }
    };

    private Emitter.Listener onUpdateMsgStatus = new Emitter.Listener() {
        @Override
        public void call(Object... args) {

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
}
