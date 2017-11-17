package com.coho.moki.adapter.customadapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.coho.moki.R;
import com.coho.moki.data.remote.BrandResponceData;
import com.coho.moki.util.Utils;

import java.util.ArrayList;
import java.util.List;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Created by trung on 17/11/2017.
 */

public class ListBrandAdapter extends BaseAdapter implements StickyListHeadersAdapter {

    List<BrandResponceData> brandResponceDatas;
    List<BrandResponceData> temp = new ArrayList<BrandResponceData>();
    LayoutInflater mLayoutInflater;

    public ListBrandAdapter(Context context, List<BrandResponceData> brandResponceDatas){
        mLayoutInflater = LayoutInflater.from(context);
        this.brandResponceDatas = brandResponceDatas;
    }


    @Override
    public View getHeaderView(int position, View view, ViewGroup viewGroup) {

        HeaderViewHolder headerViewHolder;

        if (view == null){

            view = mLayoutInflater.inflate(R.layout.sticky_pink_header_item, viewGroup, false);
            headerViewHolder = new HeaderViewHolder();
            headerViewHolder.text = view.findViewById(R.id.text1);
            view.setTag(headerViewHolder);
        }
        else {
            headerViewHolder = (HeaderViewHolder) view.getTag();
        }

        String headerText = "" + brandResponceDatas.get(position).getName().subSequence(0, 1).charAt(0);
        headerViewHolder.text.setText(headerText);

        return view;
    }

    public void cloneData(){
        temp.addAll(brandResponceDatas);
    }

    @Override
    public long getHeaderId(int i) {
        return brandResponceDatas.get(i).getName().subSequence(0, 1).charAt(0);
    }

    @Override
    public int getCount() {
        if (brandResponceDatas == null){
            return 0;
        }
        else {
            return brandResponceDatas.size();
        }
    }

    @Override
    public Object getItem(int i) {
        return brandResponceDatas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;

        if (view == null){

            view = mLayoutInflater.inflate(R.layout.size_item, viewGroup, false);
            viewHolder = new ViewHolder();
            viewHolder.text = view.findViewById(R.id.txtTextSize);
            view.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.text.setText(brandResponceDatas.get(i).getName());

        return view;
    }

    class HeaderViewHolder {
        TextView text;
    }

    class ViewHolder {
        TextView text;
    }

    public void searchText(String text){

        String name;

        text = text.toLowerCase();
        brandResponceDatas.clear();

        if (text.length() == 0){
            brandResponceDatas.addAll(temp);
        }
        else {
            for (BrandResponceData data : temp){
                name = data.getName().toLowerCase();
                if (name.contains(text)){
                    brandResponceDatas.add(data);
                }
            }
        }
        notifyDataSetChanged();
    }
}
