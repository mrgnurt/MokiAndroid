package com.coho.moki.ui.fragment;

import android.content.Intent;
import android.widget.ListView;

import com.coho.moki.BaseApp;
import com.coho.moki.R;
import com.coho.moki.adapter.customadapter.NotificationAdapter;
import com.coho.moki.api.CONSAPI;
import com.coho.moki.data.remote.ChatConsersation;
import com.coho.moki.data.remote.LastMessageChat;
import com.coho.moki.data.remote.ListConversationResponceData;
import com.coho.moki.data.remote.PartnerChat;
import com.coho.moki.data.remote.ProductChat;
import com.coho.moki.service.CONSService;
import com.coho.moki.service.CONSServiceImpl;
import com.coho.moki.service.ResponseListener;
import com.coho.moki.ui.base.BaseFragment;
import com.coho.moki.ui.login.LoginActivity;
import com.coho.moki.util.AccountUntil;
import com.coho.moki.util.Utils;
import com.costum.android.widget.PullAndLoadListView;
import com.costum.android.widget.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by trung on 14/11/2017.
 */

public class MessageFragment extends BaseFragment {

    @BindView(R.id.notiListView)
    PullAndLoadListView mNotiListView;

    CONSService mConsService;
    ListConversationResponceData listConversationResponceData;

    @Override
    protected int getLayoutId() {
        return R.layout.message_list;
    }

    @Override
    protected void initView() {
        super.initView();
        mConsService = new CONSServiceImpl();

        initListMessage();
    }

    @Override
    protected void initData() {
        super.initData();
//        getConversationServcie();
    }

    public void getConversationServcie(){
        mConsService.getListConversation(AccountUntil.getUserToken(), "0", "10", new ResponseListener<ListConversationResponceData>() {
            @Override
            public void onSuccess(ListConversationResponceData dataResponse) {
                listConversationResponceData = dataResponse;

            }

            @Override
            public void onFailure(String errorMessage) {
                Utils.toastShort(BaseApp.getContext(), errorMessage);
            }
        });
    }

    public void initListMessage(){

        List<ChatConsersation> chats = new ArrayList<ChatConsersation>();
        chats.add(new ChatConsersation("1", new PartnerChat("2", "trung", ""), new ProductChat("1", "abc", "", 10), new LastMessageChat("dmmay", "2 gio truoc", "")));
        chats.add(new ChatConsersation("1", new PartnerChat("2", "trung", ""), new ProductChat("1", "abc", "", 10), new LastMessageChat("dmmay", "2 gio truoc", "")));
//        chats.add(new ChatConsersation("1", new PartnerChat("2", "trung", ""), new ProductChat("1", "abc", "", 10), new LastMessageChat("dmmay", "2 gio truoc", "")));
//        chats.add(new ChatConsersation("1", new PartnerChat("2", "trung", ""), new ProductChat("1", "abc", "", 10), new LastMessageChat("dmmay", "2 gio truoc", "")));
//        chats.add(new ChatConsersation("1", new PartnerChat("2", "trung", ""), new ProductChat("1", "abc", "", 10), new LastMessageChat("dmmay", "2 gio truoc", "")));
//        chats.add(new ChatConsersation("1", new PartnerChat("2", "trung", ""), new ProductChat("1", "abc", "", 10), new LastMessageChat("dmmay", "2 gio truoc", "")));
//        chats.add(new ChatConsersation("1", new PartnerChat("2", "trung", ""), new ProductChat("1", "abc", "", 10), new LastMessageChat("dmmay", "2 gio truoc", "")));
//        chats.add(new ChatConsersation("1", new PartnerChat("2", "trung", ""), new ProductChat("1", "abc", "", 10), new LastMessageChat("dmmay", "2 gio truoc", "")));
//        chats.add(new ChatConsersation("1", new PartnerChat("2", "trung", ""), new ProductChat("1", "abc", "", 10), new LastMessageChat("dmmay", "2 gio truoc", "")));
//        chats.add(new ChatConsersation("1", new PartnerChat("2", "trung", ""), new ProductChat("1", "abc", "", 10), new LastMessageChat("dmmay", "2 gio truoc", "")));
//        chats.add(new ChatConsersation("1", new PartnerChat("2", "trung", ""), new ProductChat("1", "abc", "", 10), new LastMessageChat("dmmay", "2 gio truoc", "")));
//        chats.add(new ChatConsersation("1", new PartnerChat("2", "trung", ""), new ProductChat("1", "abc", "", 10), new LastMessageChat("dmmay", "2 gio truoc", "")));

        NotificationAdapter adapter = new NotificationAdapter(getActivity(), R.layout.notification_item, chats);
        mNotiListView.setAdapter(adapter);

    }


}
