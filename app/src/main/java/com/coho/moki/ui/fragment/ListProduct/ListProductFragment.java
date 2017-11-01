package com.coho.moki.ui.fragment.ListProduct;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.coho.moki.R;
import com.coho.moki.adapter.customadapter.ListProductAdapter;
import com.coho.moki.data.constant.AppConstant;
import com.coho.moki.data.model.Brand;
import com.coho.moki.data.model.Category;
import com.coho.moki.data.model.Product;
import com.coho.moki.data.remote.BrandResponceData;
import com.coho.moki.data.remote.GetListProductResponceData;
import com.coho.moki.data.remote.ProductSmallResponceData;
import com.coho.moki.service.ResponseListener;
import com.coho.moki.ui.base.BaseFragment;
import com.coho.moki.ui.main.MainActivity;
import com.coho.moki.util.SpaceItem;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by trung on 14/10/2017.
 */

public class ListProductFragment extends BaseFragment implements ListProductContract.View {

    @BindView(R.id.rv_product_list)
    RecyclerView mRVProductList;

    @BindView(R.id.refresh_layout)
    SmartRefreshLayout mRefreshLayout;

    Category mCategory;
    List<Product> mProducts;
    ListProductAdapter mListProductAdapter;

    ListProductContract.Presenter mPresenter;

    public static ListProductFragment newInstance(Category category){
        ListProductFragment listProductFragment = new ListProductFragment();

        Bundle args = new Bundle();
        args.putParcelable(AppConstant.CATEGORY_TAG, category);
        listProductFragment.setArguments(args);

        return listProductFragment;
    }

    @Override
    protected void handleArguments(Bundle arguments) {
        mCategory = arguments.getParcelable(AppConstant.CATEGORY_TAG);
        mPresenter = new ListProductPresenter();
        mPresenter.onAttach(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.refresh_gridview;
    }

    @Override
    protected void initView() {
        super.initView();
        initRV();
        initRefreshLayout();
//        showProducts();
    }

    @Override
    protected void initData() {
        super.initData();
    }

    public void initRV(){
        mProducts = new ArrayList<Product>();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        mRVProductList.setLayoutManager(gridLayoutManager);

        int space = 13;
        SpaceItem spaceItem = new SpaceItem(space);
        mRVProductList.addItemDecoration(spaceItem);

//        ArrayList<Product> products = new ArrayList<Product>();
        mProducts.add(new Product("", "Đồ ăn cho bé", null, 50, 10, null, "", 100, 100));
        mProducts.add(new Product("", "Đồ ăn cho bé", null, 50, 10, null, "", 100, 100));
        mProducts.add(new Product("", "Đồ ăn cho bé", null, 50, 10, null, "", 100, 100));
        mProducts.add(new Product("", "Đồ ăn cho bé", null, 50, 10, null, "", 100, 100));
        mProducts.add(new Product("", "Đồ ăn cho bé", null, 50, 10, null, "", 100, 100));
        mProducts.add(new Product("", "Đồ ăn cho bé", null, 50, 10, null, "", 100, 100));
        mProducts.add(new Product("", "Đồ ăn cho bé", null, 50, 10, null, "", 100, 100));
        mProducts.add(new Product("", "Đồ ăn cho bé", null, 50, 10, null, "", 100, 100));

        mListProductAdapter = new ListProductAdapter(mProducts);
        mRVProductList.setAdapter(mListProductAdapter);
    }

    public void initRefreshLayout(){
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(2000);
            }
        });

        mRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                mProducts.add(new Product("", "Đồ ăn cho bé", null, 50, 10, null, "", 100, 100));
                mProducts.add(new Product("", "Đồ ăn cho bé", null, 50, 10, null, "", 100, 100));
                mProducts.add(new Product("", "Đồ ăn cho bé", null, 50, 10, null, "", 100, 100));
                mProducts.add(new Product("", "Đồ ăn cho bé", null, 50, 10, null, "", 100, 100));
                mListProductAdapter.notifyDataSetChanged();
                refreshlayout.finishLoadmore(2000);
            }
        });
    }

    public void showProducts(){
        mPresenter.callGetProductsService("", mCategory.getCategoryId(), "", "", "0", 6,
                new ResponseListener<GetListProductResponceData>() {
                    @Override
                    public void onSuccess(GetListProductResponceData dataResponse) {
                        createProducts(dataResponse.getProducts());
                        mListProductAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(String errorMessage) {

                    }
                });
    }

    public void createProducts(ArrayList<ProductSmallResponceData> productSmallResponceDatas){
        for (ProductSmallResponceData productSmallResponceData:
             productSmallResponceDatas) {

            List<BrandResponceData> brandResponceDatas = productSmallResponceData.getBrand();
            List<Brand> brands = new ArrayList<>();
            for (BrandResponceData brandResponceData: brandResponceDatas){
                Brand brand = new Brand(brandResponceData.getId(), brandResponceData.getName());
                brands.add(brand);
            }

            Product product = new Product(
                    productSmallResponceData.getId(),
                    productSmallResponceData.getName(),
                    productSmallResponceData.getImage(),
                    productSmallResponceData.getPrice(),
                    productSmallResponceData.getPricePercent(),
                    brands,
                    productSmallResponceData.getDescribed(),
                    productSmallResponceData.getLike(),
                    productSmallResponceData.getComment());
            mProducts.add(product);
        }
    }
}
