package com.coho.moki.ui.product;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
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
import com.coho.moki.data.remote.BaseResponse;
import com.coho.moki.data.remote.Conversation;
import com.coho.moki.data.remote.ConversationResponseData;
import com.coho.moki.data.remote.ProductCons;
import com.coho.moki.service.ConversationService;
import com.coho.moki.service.ConversationServiceImpl;
import com.coho.moki.service.ResponseListener;
import com.coho.moki.ui.base.BaseActivity;
import com.coho.moki.ui.user.UserInfoActivity;
import com.coho.moki.util.AccountUntil;
import com.coho.moki.util.DialogUtil;
import com.coho.moki.util.StringUtil;
import com.coho.moki.util.Utils;
import com.coho.moki.util.network.LoadImageUtils;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.OnClick;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import ru.noties.scrollable.CanScrollVerticallyDelegate;
import ru.noties.scrollable.ScrollableLayout;

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

    @BindView(R.id.scrollable_layout)
    ScrollableLayout mScrollableLayout;

    @BindView(R.id.header)
    LinearLayout mHeader;

    List<ProductChatItem> chatItemList;

    ProductChatAdapter productChatAdapter;

    RecyclerView.LayoutManager mRvLayoutManager;

    ConversationService conversationService;

    private boolean isConnected = true;

    private Socket mSocket;

    private String mToken;

    private String mProductId;

    private String mProductAvatar;

    private String mMyId;

    private String mMyAvatar;

    private String mMyUsername;

    private String mPartnerId;

    private String mPartnerAvatar;

    private String mPartnerUsername;

    private String mSellerName;

    private String mSellerAvatar;

    private String mSellerId;

    private boolean isOwnerProduct;

    private String mSendToken;

    private int currIndex;

    private int limitPerLoad;

    private ProductCons mProductCons;

    private boolean isSetReadMessage = false;


    @Override
    public int setContentViewId() {
        return R.layout.product_messenger;
    }

    @Override
    public void handleIntent(Intent intent) {
        extractDataFromIntent(intent);
        Log.d("tuton", "handle intent");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        extractDataFromIntent(intent);
        initView();
        initData();
        Log.d("tuton", "on new Intent");
    }

    public void extractDataFromIntent(Intent intent) {
        Bundle data = intent.getBundleExtra(AppConstant.PACKAGE_TAG);
        if (data != null) {
            mProductId = data.getString(AppConstant.PRODUCT_ID_CHAT_TAG);
            mProductAvatar = data.getString(AppConstant.PRODUCT_AVATAR_CHAT_TAG);
            mPartnerId = data.getString(AppConstant.PARTNER_ID_CHAT_TAG);
            mPartnerUsername = data.getString(AppConstant.PARTNER_USERNAME_CHAT_TAG);
            mPartnerAvatar = data.getString(AppConstant.PARTNER_AVATAR_CHAT_TAG);
        } else {
            mProductId = intent.getStringExtra(AppConstant.PRODUCT_ID_CHAT_TAG);
            mProductAvatar = intent.getStringExtra(AppConstant.PRODUCT_AVATAR_CHAT_TAG);
            mPartnerId = intent.getStringExtra(AppConstant.PARTNER_ID_CHAT_TAG);
            mPartnerUsername = intent.getStringExtra(AppConstant.PARTNER_USERNAME_CHAT_TAG);
            mPartnerAvatar = intent.getStringExtra(AppConstant.PARTNER_AVATAR_CHAT_TAG);
        }
    }

    @Override
    public void initView() {
        btnNavRight.setBackgroundResource(R.drawable.ic_icon_message);
        if (mProductAvatar != null) {
            LoadImageUtils.loadImageFromUrl(mProductAvatar, imgProduct, null);
        }

        llTransaction.setVisibility(View.GONE);
        displayInstruction(View.GONE);

        mRvLayoutManager = new LinearLayoutManager(getApplicationContext());
        rvMessage.setLayoutManager(mRvLayoutManager);
        rvMessage.setItemAnimator(new DefaultItemAnimator());

        chatItemList = new ArrayList<>();
        productChatAdapter = new ProductChatAdapter(chatItemList);
        rvMessage.setAdapter(productChatAdapter);

        productChatAdapter.setListener(new ProductChatAdapter.ItemClickListener() {
            @Override
            public void onClick(int position) {
                ProductChatItem item = productChatAdapter.getItem(position);
                Log.d("tuton", "item:" + item.getMessageOwner());
                if (item != null) {
                    Intent userIntent = new Intent(ProductChatActivity.this, UserInfoActivity.class);
                    userIntent.putExtra("userId", item.getMessageOwner());
                    ProductChatActivity.this.startActivity(userIntent);
                }
            }
        });

        btnSent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });

        editTextMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("tuton", "edit clicked");
                mScrollableLayout.scrollTo(0, mScrollableLayout.getMaxScrollY());

                if (rvMessage.getAdapter().getItemCount() > 0){
                    rvMessage.smoothScrollToPosition(rvMessage.getAdapter().getItemCount() - 1);
                }
//                int chatListSize = productChatAdapter.getItemCount();
//                if (chatListSize > 0) {
//                    Log.d("tuton", "chatListSize: " + chatListSize);
//                    mRvLayoutManager.scrollToPosition(chatListSize - 1);
//
//                }

            }
        });

        editTextMessage.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {

                if (hasFocus){
                    Log.d("trunggocus", "vaoday");
                    mScrollableLayout.scrollTo(0, mScrollableLayout.getMaxScrollY());
                    if (rvMessage.getAdapter().getItemCount() > 0){
                        rvMessage.smoothScrollToPosition(rvMessage.getAdapter().getItemCount() - 1);
                    }
                }
                else {

                }
            }
        });



        mHeader.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Log.d("tuton", mHeader.getHeight() + "");
                mScrollableLayout.setMaxScrollY(mHeader.getHeight());
            }
        });

        mScrollableLayout.setCanScrollVerticallyDelegate(new CanScrollVerticallyDelegate() {
            @Override
            public boolean canScrollVertically(int direction) {
                return rvMessage.canScrollVertically(direction);
            }
        });

//        this.getWindow().setSoftInputMode(
//                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

    }

    @Override
    public void initData() {
        mProductCons = null;
        conversationService = new ConversationServiceImpl();
        currIndex = 0;
        limitPerLoad = 20;
        loadMyInfo();

        if (Utils.checkInternetAvailable()) {
            loadHistoryConversations();
        } else {
            DialogUtil.showPopup(ProductChatActivity.this, AppConstant.NO_INTERNET);
        }
    }

    @OnClick(R.id.btnNavLeft)
    public void btnNavLeftOnClick() {
        this.finish();
    }

    public void loadMyInfo() {
        mToken = AccountUntil.getUserToken();
        mMyId = AccountUntil.getAccountId();
        mMyAvatar = AccountUntil.getAvatarUrl();
        mMyUsername = AccountUntil.getUsername();
    }

    public void loadHistoryConversations() {

        DialogUtil.showProgress(ProductChatActivity.this);
        conversationService.loadConversationDetail(mToken, mPartnerId, mProductId, limitPerLoad, currIndex, new ResponseListener<ConversationResponseData>() {
            @Override
            public void onSuccess(ConversationResponseData dataResponse) {
                List<Conversation> chats = dataResponse.getConversation();
                ProductCons product = dataResponse.getProduct();
                populateConversation(chats, product);
                DialogUtil.hideProgress();
            }

            @Override
            public void onFailure(String errorMessage) {
                DialogUtil.hideProgress();
                DialogUtil.showPopup(ProductChatActivity.this, errorMessage);
                Log.d(LOG_TAG, errorMessage);
            }
        });
    }

    public void populateConversation(List<Conversation> historyChats, ProductCons product) {

        if (product == null) {
            return;
        }

        if (this.mProductCons == null) {
            this.mProductCons = product;
            if (mMyId.equals(product.getSellerId())) {
                mSellerId = mMyId;
                mSellerAvatar = mMyAvatar;
                mSellerName = mMyUsername;
                isOwnerProduct = true;
            } else {
                isOwnerProduct = false;
                mSellerId = mPartnerId;
                mSellerName = mPartnerUsername;
                mSellerAvatar = mProductAvatar;

                llTransaction.setVisibility(View.VISIBLE);
                displayInstruction(View.VISIBLE);
            }

            if (mProductAvatar == null) {
                mProductAvatar = product.getImage();
                LoadImageUtils.loadImageFromUrl(mProductAvatar, imgProduct, null);
            }

            txtName.setText(product.getName());
            String price = product.getPrice() + "";
            String priceFormated = Utils.formatPrice(price);
            txtPrice.setText(priceFormated);
            txtHeader.setText(mSellerName);
        }

        if (historyChats == null || historyChats.size() < 1) {
            return;
        }

        displayInstruction(View.GONE);

        List<ProductChatItem> addItems = new ArrayList<>();
        for (Conversation historyLine : historyChats) {
            ProductChatItem addItem = new ProductChatItem();
            addItem.setMessage(historyLine.getMessage());
            addItem.setSendAt(new Date());

            int role = ProductChatItem.SENDER;
            String avatar = mMyAvatar;

            if (!mMyId.equals(historyLine.getSender().getId())) {
                role = ProductChatItem.RECEIVER;
                avatar = mPartnerAvatar;
            }
            addItem.setRole(role);
            addItem.setAvatar(avatar);
            addItem.setMessageOwner(historyLine.getSender().getId());

            addItems.add(addItem);
        }


        int preItemCount = productChatAdapter.getItemCount();
        productChatAdapter.addItemsToFirst(addItems);

        if (preItemCount == 0 && addItems.size() > 0) {
            conversationService.setReadMessage(mToken, mPartnerId, mProductId, new ResponseListener<BaseResponse>() {
                @Override
                public void onSuccess(BaseResponse dataResponse) {
                    Log.d("tuton", "set read message successful");
                }

                @Override
                public void onFailure(String errorMessage) {
                    Log.d("tuton", "set read message failure");
                }
            });
        }
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
           Log.d("tuton", "disconnection socket");
           mSocket.disconnect();
           mSocket.off(Socket.EVENT_CONNECT, onConnect);
           mSocket.off(Socket.EVENT_DISCONNECT, onDisconnect);
           mSocket.off(Socket.EVENT_CONNECT_ERROR, onConnectError);
           mSocket.off(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);

           mSocket.off(AppConstant.SOCKET_JOIN_ROOM_RESPONSE, onJoinRoomResponse);
           mSocket.off(AppConstant.SOCKET_MESSAGE, onNewMessage);
           mSocket.off(AppConstant.SOCKET_UPDATE_MSG_STATUS, onUpdateMsgStatus);

           mSocket.close();
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
        Log.d("tuton", "onStop");
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
            DialogUtil.showPopup(ProductChatActivity.this, AppConstant.NO_INTERNET);
            return;
        }

        ProductChatItem addItem = getMyProductChatItem(input);
        productChatAdapter.addItem(addItem);
        if (rvMessage.getAdapter().getItemCount() > 0){
            rvMessage.smoothScrollToPosition(rvMessage.getAdapter().getItemCount() - 1);
        }
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
            String senderId = sender.getString("id");
            String message = messageObj.getString("content");
            String messageId = messageObj.getString("id");
            String sendAtString = messageObj.getString("sentAt");

            Date sendAt = new Date();
            int role = ProductChatItem.RECEIVER;

            receiveItem = new ProductChatItem();
            receiveItem.setMessage(message);
            receiveItem.setAvatar(avatar);
            receiveItem.setRole(role);
            receiveItem.setSendAt(sendAt);
            receiveItem.setServerMsgId(messageId);
            receiveItem.setMessageOwner(senderId);

            Log.d("tuton", message);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return receiveItem;
    }

    public ProductChatItem getMyProductChatItem(String message) {
        String avatar = AccountUntil.getAvatarUrl();
        Date sendAt = new Date();
        int role = ProductChatItem.SENDER;
        return new ProductChatItem(role, message, avatar, sendAt, mMyId);
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

    public void displayInstruction(int visibility) {
        messInstruct.setVisibility(visibility);
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
