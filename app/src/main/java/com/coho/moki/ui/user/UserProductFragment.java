package com.coho.moki.ui.user;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.coho.moki.R;

/**
 * Created by Khanh Nguyen on 10/16/2017.
 */

public class UserProductFragment extends Fragment {

    public UserProductFragment() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        // bind view at here
        View view = inflater.inflate(R.layout.user_product_fragment, container, false);
        return view;
    }

}
