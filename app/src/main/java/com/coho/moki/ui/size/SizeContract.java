package com.coho.moki.ui.size;

import com.coho.moki.data.model.Product;
import com.coho.moki.data.model.Size;
import com.coho.moki.ui.base.BasePresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by trung on 07/11/2017.
 */

public interface SizeContract {
    interface View {
        void showSizes(List<Size> sizes);
    }

    interface Presenter extends BasePresenter<SizeContract.View> {
        void initSizes();

        void callSearchProduct(String categoryId);

        ArrayList<Size> getSizes();
    }
}
