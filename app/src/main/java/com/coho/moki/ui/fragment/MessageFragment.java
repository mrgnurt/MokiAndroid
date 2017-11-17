package com.coho.moki.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.coho.moki.BaseApp;
import com.coho.moki.R;
import com.coho.moki.adapter.customadapter.NotificationAdapter;
import com.coho.moki.data.constant.AppConstant;
import com.coho.moki.data.model.Product;
import com.coho.moki.data.remote.ChatConsersation;
import com.coho.moki.data.remote.LastMessageChat;
import com.coho.moki.data.remote.ListConversationResponceData;
import com.coho.moki.data.remote.PartnerChat;
import com.coho.moki.data.remote.ProductChat;
import com.coho.moki.service.ConversationService;
import com.coho.moki.service.ConversationServiceImpl;
import com.coho.moki.service.ResponseListener;
import com.coho.moki.ui.base.BaseFragment;
import com.coho.moki.ui.main.MainActivity;
import com.coho.moki.ui.product.ProductChatActivity;
import com.coho.moki.util.AccountUntil;
import com.coho.moki.util.DialogUtil;
import com.coho.moki.util.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by trung on 14/11/2017.
 */

public class MessageFragment extends BaseFragment {

    @BindView(R.id.notiListView)
    ListView mLvConversation;

    ConversationService mConsService;
    NotificationAdapter adapter;

    String mToken;

    int index;

    int count;

    Activity mActivity;

    @Override
    protected int getLayoutId() {
        return R.layout.message_list;
    }

    @Override
    protected void initView() {
        super.initView();
        mActivity = getActivity();
        mConsService = new ConversationServiceImpl();
        adapter = new NotificationAdapter(getActivity(), R.layout.notification_item, null);
        mLvConversation.setAdapter(adapter);
        mLvConversation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ChatConsersation conversation = adapter.getChats().get(i);
                if (conversation != null) {
                    if (mActivity instanceof MainActivity) {
                        ((MainActivity) mActivity).hideMessageFragment();
                    }
                    openProductChatActivity(conversation);
                }
            }
        });
    }

    @Override
    protected void initData() {
        index = 0;
        count = 10;
    }

    private void doLoad() {
        if (getActivity() == null) {
            return;
        }

        DialogUtil.showProgress(getActivity());
        mConsService.loadConversations(AccountUntil.getUserToken(), index, count, new ResponseListener<ListConversationResponceData>() {
            @Override
            public void onSuccess(ListConversationResponceData data) {
                populateConversations(data);
                DialogUtil.hideProgress();
            }

            @Override
            public void onFailure(String errorMessage) {
                DialogUtil.hideProgress();
                if (getActivity() != null) {
                    DialogUtil.showPopup(getActivity(), errorMessage);
                }
            }
        });
    }

    public void loadConversations() {
        mToken = AccountUntil.getUserToken();
        if (mToken != null && adapter.getCount() == 0) {
            if (Utils.checkInternetAvailable()) {
                doLoad();
            } else if (getActivity() != null){
                DialogUtil.showPopup(getActivity(), AppConstant.NO_INTERNET);
            }
        }
    }

    public void populateConversations(ListConversationResponceData data) {
        if (data == null) {
            return;
        }

        if (data.chats == null || data.chats.size() < 1) {
            return;
        }

        adapter.addItemsToLast(data.chats);
    }

    public void openProductChatActivity(ChatConsersation conversation) {
        Intent intent = new Intent(getActivity(), ProductChatActivity.class);
        Bundle data = new Bundle();

        ProductChat product = conversation.product;
        PartnerChat partner = conversation.partner;

        data.putString(AppConstant.PRODUCT_ID_CHAT_TAG, product.id);
        data.putString(AppConstant.PRODUCT_AVATAR_CHAT_TAG, product.image);
        data.putString(AppConstant.PARTNER_ID_CHAT_TAG, partner.id);
        data.putString(AppConstant.PARTNER_USERNAME_CHAT_TAG, partner.username);
        data.putString(AppConstant.PARTNER_AVATAR_CHAT_TAG, partner.avatar);

        intent.putExtra("package", data);
        getActivity().startActivity(intent);
    }

}
