package com.coho.moki.ui.fragment.NewsPager;

import android.widget.ListView;

import com.coho.moki.BaseApp;
import com.coho.moki.R;
import com.coho.moki.adapter.customadapter.ListMyLikeAdapter;
import com.coho.moki.data.remote.MyLikeResponseData;
import com.coho.moki.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by trung on 18/11/2017.
 */

public class MyLikeFragment extends BaseFragment {

    List<MyLikeResponseData> myLikeResponseDataList;
    ListMyLikeAdapter adapter;

    @BindView(R.id.listView)
    ListView mListView;

    @Override
    protected int getLayoutId() {
        return R.layout.refresh_list2;
    }

    @Override
    protected void initView() {
        List<MyLikeResponseData> datas = new ArrayList<MyLikeResponseData>();

        datas.add(new MyLikeResponseData("", "trung", 10000, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSA5JFj6p6JLc4IZ6rid_4e_O2GGPtFJXH8e2Ji-9y0yUTpx4gT"));
        datas.add(new MyLikeResponseData("", "trung", 10000, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSA5JFj6p6JLc4IZ6rid_4e_O2GGPtFJXH8e2Ji-9y0yUTpx4gT"));
        datas.add(new MyLikeResponseData("", "trung", 10000, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSA5JFj6p6JLc4IZ6rid_4e_O2GGPtFJXH8e2Ji-9y0yUTpx4gT"));
        datas.add(new MyLikeResponseData("", "trung", 10000, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSA5JFj6p6JLc4IZ6rid_4e_O2GGPtFJXH8e2Ji-9y0yUTpx4gT"));
        adapter = new ListMyLikeAdapter(BaseApp.getContext(), datas);
        mListView.setAdapter(adapter);

    }
}
