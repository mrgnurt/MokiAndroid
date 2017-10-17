package com.coho.moki.adapter.customadapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.coho.moki.BaseApp;
import com.coho.moki.R;
import com.coho.moki.adapter.ViewHolder.ListProductViewHolder;
import com.coho.moki.data.model.Product;

import java.util.ArrayList;
import java.util.List;

import dagger.Provides;

/**
 * Created by trung on 16/10/2017.
 */

public class ListProductAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<Product> mProducts;

    public ListProductAdapter(ArrayList<Product> products){
        mProducts = products;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(BaseApp.getContext());

        View view = layoutInflater.inflate(R.layout.product_item_small, parent, false);

        ListProductViewHolder viewHolder = new ListProductViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ListProductViewHolder) holder).populate(mProducts.get(position));
    }

    @Override
    public int getItemCount() {
        return mProducts.size();
    }
}
