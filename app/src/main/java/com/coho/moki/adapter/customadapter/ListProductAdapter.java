package com.coho.moki.adapter.customadapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.coho.moki.BaseApp;
import com.coho.moki.R;
import com.coho.moki.adapter.ViewHolder.ListProductViewHolder;
import com.coho.moki.callback.OnClickProductItemListenner;
import com.coho.moki.callback.OnClickSideMenuItemListener;
import com.coho.moki.data.model.Product;
import com.coho.moki.ui.main.MainActivity;
import com.coho.moki.ui.product.ProductDetailActivity;

import java.util.ArrayList;
import java.util.List;

import dagger.Provides;

/**
 * Created by trung on 16/10/2017.
 */

public class ListProductAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<Product> mProducts;
    OnClickProductItemListenner mListener;

    public ListProductAdapter(ArrayList<Product> products){
        mProducts = products;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(BaseApp.getContext());

        View view = layoutInflater.inflate(R.layout.product_item_small, parent, false);

        ListProductViewHolder viewHolder = new ListProductViewHolder(view);
        viewHolder.setListener(mListener);

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

    public void addListener(OnClickProductItemListenner listener){
        mListener = listener;
    }

    public void insertLastItem(List<Product> products){

        if (products != null && products.size() > 0){

            int positionStart = mProducts.size();
            mProducts.addAll(products);
            notifyItemRangeInserted(positionStart, products.size());
        }
    }

    public void insertHeadItem(List<Product> products){

        if (products != null && products.size() > 0){

            mProducts.addAll(0, products);
            notifyItemRangeInserted(0, products.size());
        }
    }
}
