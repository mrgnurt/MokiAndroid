package com.coho.moki.ui.fragment.ListProduct;

import com.coho.moki.data.remote.GetListProductResponceData;
import com.coho.moki.service.ResponseListener;
import com.coho.moki.ui.base.BasePresenter;

/**
 * Created by trung on 23/10/2017.
 */

public interface ListProductContract {

    public interface View{
        void showProducts();
    }

    public interface Presenter extends BasePresenter<View>{

        void callGetProductsService(String token, String categoryId, String campaignId, String lastId,
                                    String index, int count, final ResponseListener<GetListProductResponceData> listener);
    }
}
