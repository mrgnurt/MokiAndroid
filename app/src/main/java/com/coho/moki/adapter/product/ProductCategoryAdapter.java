package com.coho.moki.adapter.product;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.coho.moki.R;
import com.coho.moki.data.remote.ProductCategoryResponse;
import com.coho.moki.data.remote.ProductCommentResponse;
import com.coho.moki.util.network.LoadImageUtils;

import java.util.List;

/**
 * Created by Khanh Nguyen on 11/15/2017.
 */

public class ProductCategoryAdapter extends ArrayAdapter<ProductCategoryResponse> {

    private static final String TAG = "ProductCategoryAdapter";

    LayoutInflater mLayoutInflater;
    List<ProductCategoryResponse> mProductCommentList;

    public ProductCategoryAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<ProductCategoryResponse> objects) {
        super(context, resource, objects);
        mLayoutInflater = LayoutInflater.from(context);
        this.mProductCommentList = objects;
    }

    /**
     * Return a view: view item in ListView
     * @param position position in the product list
     * @param convertView is new view inflate from xml layout, or scrapview in case reuse
     * @param parent parent view, in this case is ListView
     * @return
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.product_category_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        bindItem(viewHolder, position);
        return convertView;
    }



    private void bindItem(ViewHolder viewHolder, int position) {
        ProductCategoryResponse response = mProductCommentList.get(position);
        viewHolder.txtName.setText(response.getName());
        // check: if has sub category then set imgNext is visible else gone
        if (response.getHasChild() == 1) {
            LoadImageUtils.loadImageFromDrawable(R.drawable.icon_nextarrow_normal, viewHolder.imgNext);
        } else {
            viewHolder.imgNext.setVisibility(View.GONE);
        }
    }

    // using ViewHolder pattern to optimize performance of ListView
    private static class ViewHolder {
        private TextView txtName;
        private ImageView imgNext;

        private ViewHolder(View view) {
            txtName = view.findViewById(R.id.txtName);
            imgNext = view.findViewById(R.id.imgNext);
        }

    }

}
