package com.coho.moki.adapter.customadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.coho.moki.R;
import com.coho.moki.data.constant.NewsItem;

import java.util.List;

/**
 * Created by trung on 20/11/2017.
 */

public class ListNewsAdapter extends BaseAdapter {

    LayoutInflater mLayoutInflater;
    List<NewsItem> newsItems;

    public ListNewsAdapter(Context context, List<NewsItem> newsItems){
        mLayoutInflater = LayoutInflater.from(context);
        this.newsItems = newsItems;
    }

    @Override
    public int getCount() {
        if (newsItems == null){
            return 0;
        }
        else {
            return newsItems.size();
        }
    }

    @Override
    public Object getItem(int i) {
        return newsItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;

        if (view == null){

            view = mLayoutInflater.inflate(R.layout.news_list_item, viewGroup, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.txtCreated.setText(newsItems.get(i).getTime());
        viewHolder.txtTitle.setText(newsItems.get(i).getName());

        return view;
    }

    class ViewHolder{

        TextView txtCreated;
        TextView txtTitle;

        public ViewHolder(View view){

            txtCreated = (TextView) view.findViewById(R.id.txtCreated);
            txtTitle = (TextView) view.findViewById(R.id.txtTitle);
        }
    }
}
