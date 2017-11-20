package com.coho.moki.ui.fragment.NewsPager;

import android.widget.ListView;

import com.coho.moki.R;
import com.coho.moki.adapter.customadapter.ListNewsAdapter;
import com.coho.moki.data.constant.NewsItem;
import com.coho.moki.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by trung on 01/11/2017.
 */

public class NewsPagerFragment extends BaseFragment {

    @BindView(R.id.listView)
    ListView mListView;

    @Override
    protected int getLayoutId() {
        return R.layout.refresh_list2;
    }

    @Override
    protected void initView() {
        List<NewsItem> newsItems = new ArrayList<NewsItem>();

        newsItems.add(new NewsItem("20/11/2017 16:24", "GIẢM SỐC LÊN ĐẾN 45% ĐỒ CHO MẸ VÀ BÉ"));
        newsItems.add(new NewsItem("19/10/2017 14:28", "CHÚC MỪNG NGÀY PHỤ NỮ VIỆT NAM 20/10"));
        newsItems.add(new NewsItem("26/04/2017 9:42", "THÔNG BÁO LỊCH NGHỈ LỄ QUỐC KHÁNH 02/09/2017"));
        newsItems.add(new NewsItem("23/09/2016 9:10", "SIÊU BÃO MOKI - 300 QUÀ MIỄN PHÍ"));
        newsItems.add(new NewsItem("23/09/2016 5:30", "SỬ DỤNG TIỀN TRONG TÀI KHOẢN MOKI"));

        ListNewsAdapter adapter = new ListNewsAdapter(getActivity(), newsItems);
        mListView.setAdapter(adapter);
    }
}
