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
    Fragment fm;

    public ListProductAdapter(ArrayList<Product> products, Fragment fm){
        mProducts = products;
        this.fm = fm;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(BaseApp.getContext());

        View view = layoutInflater.inflate(R.layout.product_item_small, parent, false);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context ctx = fm.getContext();
                ctx.startActivity(new Intent(ctx, ProductDetailActivity.class));
            }
        });

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
