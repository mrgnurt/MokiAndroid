package com.coho.moki.adapter.customadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.coho.moki.R;
import com.coho.moki.data.remote.ShipFromResponseData;

import java.util.ArrayList;
import java.util.List;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Created by trung on 18/11/2017.
 */

public class ListShipFromAdapter  extends BaseAdapter implements StickyListHeadersAdapter {

    List<ShipFromResponseData> shipFromResponseDatas;
    List<ShipFromResponseData> temp = new ArrayList<ShipFromResponseData>();
    LayoutInflater mLayoutInflater;

    public ListShipFromAdapter(Context context, List<ShipFromResponseData> shipFromResponseDatas){
        mLayoutInflater = LayoutInflater.from(context);
        this.shipFromResponseDatas = shipFromResponseDatas;
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

        String headerText = "" + shipFromResponseDatas.get(position).getName().subSequence(0, 1).charAt(0);
        headerViewHolder.text.setText(headerText);

        return view;
    }

    public void cloneData(){
        temp.addAll(shipFromResponseDatas);
    }

    @Override
    public long getHeaderId(int i) {
        return shipFromResponseDatas.get(i).getName().subSequence(0, 1).charAt(0);
    }

    @Override
    public int getCount() {
        if (shipFromResponseDatas == null){
            return 0;
        }
        else {
            return shipFromResponseDatas.size();
        }
    }

    @Override
    public Object getItem(int i) {
        return shipFromResponseDatas.get(i);
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

        viewHolder.text.setText(shipFromResponseDatas.get(i).getName());

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
        shipFromResponseDatas.clear();

        if (text.length() == 0){
            shipFromResponseDatas.addAll(temp);
        }
        else {
            for (ShipFromResponseData data : temp){
                name = data.getName().toLowerCase();
                if (name.contains(text)){
                    shipFromResponseDatas.add(data);
                }
            }
        }
        notifyDataSetChanged();
    }
}
