package com.coho.moki.adapter.product;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coho.moki.R;
import com.coho.moki.callback.MyOnClickListener;
import com.coho.moki.data.remote.ProductSellAddressResponse;
import com.coho.moki.util.Utils;

import java.util.List;

/**
 * Created by Khanh Nguyen on 11/16/2017.
 */

public class ProductSellAddressAdapter extends ArrayAdapter<ProductSellAddressResponse> {

    // Note: The logging tag can be at most 23 characters
    private static final String TAG = ProductSellAddressAdapter.class.getSimpleName().substring(0, 22);

    LayoutInflater mLayoutInflater;
    List<ProductSellAddressResponse> mProductSellAddressList;
    MyOnClickListener listener;

    public ProductSellAddressAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<ProductSellAddressResponse> objects) {
        super(context, resource, objects);
        mLayoutInflater = LayoutInflater.from(context);
        this.mProductSellAddressList = objects;
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
            convertView = mLayoutInflater.inflate(R.layout.order_address_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        bindItem(viewHolder, position);
        return convertView;
    }

    private void bindItem(ViewHolder viewHolder, int position) {
        ProductSellAddressResponse response = mProductSellAddressList.get(position);
        // TODO: bind data at here, remove fakeData when call api
        fakeData(viewHolder, position);
    }

    private void fakeData(ViewHolder viewHolder, int position) {
        viewHolder.txtHamlet.setText("Số 10");
        viewHolder.txtWard.setText("Phường Bách Khoa");
        viewHolder.txtDistrict.setText("Quận Hai Bà Trưng");
        viewHolder.txtCity.setText("TP. Hà Nội");
        if (position == 1) {
            viewHolder.btnTick.setBackgroundResource(R.drawable.tick_box_icon);
            viewHolder.txtDefault.setText(Utils.getResourceString(R.string._default));
        }
        viewHolder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick();
            }
        });
    }

    public void setOnClickEditAddressListener(MyOnClickListener listener) {
        this.listener = listener;
    }

    // using ViewHolder pattern to optimize performance of ListView
    private static class ViewHolder {
        private LinearLayout cellLayout;
        private TextView txtHamlet;
        private TextView txtWard;
        private TextView txtDistrict;
        private TextView txtCity;
        private TextView txtDefault;
        private Button btnEdit;
        private Button btnTick;

        private ViewHolder(View v) {
            cellLayout = v.findViewById(R.id.cellLayout);
            txtHamlet = v.findViewById(R.id.txtHamlet);
            txtWard = v.findViewById(R.id.txtWard);
            txtDistrict = v.findViewById(R.id.txtDistrict);
            txtCity = v.findViewById(R.id.txtCity);
            txtDefault = v.findViewById(R.id.txtDefault);
            btnEdit = v.findViewById(R.id.btnEdit);
            btnTick = v.findViewById(R.id.btnTick);
        }

    }

}
