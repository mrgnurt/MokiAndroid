package com.coho.moki.ui.fragment.ListProduct;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ViewFlipper;

import com.coho.moki.BaseApp;
import com.coho.moki.R;
import com.coho.moki.adapter.customadapter.ListProductAdapter;
import com.coho.moki.adapter.customadapter.ListProductTimelineAdapter;
import com.coho.moki.adapter.product.ProductChatAdapter;
import com.coho.moki.callback.OnClickCommentListener;
import com.coho.moki.callback.OnClickLikeListener;
import com.coho.moki.callback.OnClickProductItemListenner;
import com.coho.moki.data.constant.AppConstant;
import com.coho.moki.data.model.Brand;
import com.coho.moki.data.model.Category;
import com.coho.moki.data.model.Image;
import com.coho.moki.data.model.Product;
import com.coho.moki.data.remote.BrandResponceData;
import com.coho.moki.data.remote.GetListProductResponceData;
import com.coho.moki.data.remote.ImageResponseData;
import com.coho.moki.data.remote.ProductSmallResponceData;
import com.coho.moki.data.remote.SellerResponceData;
import com.coho.moki.service.ProductDetailServiceImpl;
import com.coho.moki.service.ResponseListener;
import com.coho.moki.ui.base.BaseFragment;
import com.coho.moki.ui.fragment.ProductPager.ProductPagerFragment;
import com.coho.moki.ui.login.LoginActivity;
import com.coho.moki.ui.main.MainActivity;
import com.coho.moki.ui.product.ProductCommentActivity;
import com.coho.moki.ui.product.ProductDetailActivity;
import com.coho.moki.ui.product.ProductDetailPresenter;
import com.coho.moki.ui.product.ProductDetailPresenterImpl;
import com.coho.moki.ui.user.UserInfoActivity;
import com.coho.moki.util.APICacheUtils;
import com.coho.moki.util.AccountUntil;
import com.coho.moki.util.DialogUtil;
import com.coho.moki.util.SpaceItem;
import com.coho.moki.util.StringUtil;
import com.coho.moki.util.Utils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import ru.noties.scrollable.CanScrollVerticallyDelegate;
import ru.noties.scrollable.OnScrollChangedListener;
import ru.noties.scrollable.ScrollableLayout;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

/**
 * Created by trung on 14/10/2017.
 */

public class ListProductFragment extends BaseFragment implements ListProductContract.View {

    @BindView(R.id.rv_product_list)
    RecyclerView mRVProductList;

    @BindView(R.id.layout_timeline)
    StickyListHeadersListView mLayoutTimeLine;

    @BindView(R.id.refresh_layout)
    SmartRefreshLayout mRefreshLayout;

//    @BindView(R.id.refresh_layout_timeline)
//    SmartRefreshLayout mRefreshLayoutTimeLine;

    @BindView(R.id.view_flipper)
    ViewFlipper mViewFlipper;

    ProductPagerFragment mProductPagerFragment;

    ListProductAdapter mListProductAdapter;
    ListProductTimelineAdapter mListProductTimelineAdapter;

    ListProductContract.Presenter mPresenter;

    Button btnLikeTimeLine;
    boolean mIsLikeTimeLine = false;
    ProductSmallResponceData mProductSmallResponceData;

    public static ListProductFragment newInstance(Category category){
        ListProductFragment listProductFragment = new ListProductFragment();

        Bundle args = new Bundle();
        args.putParcelable(AppConstant.CATEGORY_TAG, category);
        listProductFragment.setArguments(args);

        return listProductFragment;
    }

    @Override
    protected void handleArguments(Bundle arguments) {
        Category category = arguments.getParcelable(AppConstant.CATEGORY_TAG);
        mPresenter = new ListProductPresenter();
        mPresenter.onAttach(this);
        mPresenter.setCategory(category);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.refresh_gridview;
    }

    @Override
    protected void initView() {
        super.initView();
        initRV();
        initTimeLineView();
        initViewProduct();
        initRefreshLayout();
        getProducts();
        mProductPagerFragment= (ProductPagerFragment) getFragmentManager().findFragmentByTag("home");
        mRVProductList.addOnScrollListener(onScrollListener);
        mLayoutTimeLine.setOnScrollListener(onScrollListenerTimeLine);

        mProductPagerFragment.mScrollableLayout.setCanScrollVerticallyDelegate(new CanScrollVerticallyDelegate() {
            @Override
            public boolean canScrollVertically(int direction) {
                return mLayoutTimeLine.canScrollVertically(direction);
            }
        });

    }

    @Override
    protected void initData() {
        super.initData();
    }

    public void initRV(){

        mPresenter.initProducts();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        mRVProductList.setLayoutManager(gridLayoutManager);

        int space = 13;
        SpaceItem spaceItem = new SpaceItem(space);
        mRVProductList.addItemDecoration(spaceItem);

        mListProductAdapter = new ListProductAdapter(mPresenter.getProducts());
        mRVProductList.setAdapter(mListProductAdapter);

        mListProductAdapter.addListener(new OnClickProductItemListenner() {
            @Override
            public void onClick(String productId) {
                startActivityProductDetail(productId);
            }
        });
    }

    public void initTimeLineView(){

        List<ProductSmallResponceData> product = new ArrayList<ProductSmallResponceData>();

//        List<String> image1 = new ArrayList<String>();
//        image1.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSA5JFj6p6JLc4IZ6rid_4e_O2GGPtFJXH8e2Ji-9y0yUTpx4gT");
//
//        List<String> image2 = new ArrayList<String>();
//        image2.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSA5JFj6p6JLc4IZ6rid_4e_O2GGPtFJXH8e2Ji-9y0yUTpx4gT");
//        image2.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR5qZC0nGLRNSCMxdFDyg6c0eG395runV_JYyDyVdwxpjhFkyZO-A");
//
//        List<String> image3 = new ArrayList<String>();
//        image3.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSA5JFj6p6JLc4IZ6rid_4e_O2GGPtFJXH8e2Ji-9y0yUTpx4gT");
//        image3.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR5qZC0nGLRNSCMxdFDyg6c0eG395runV_JYyDyVdwxpjhFkyZO-A");
//        image3.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR5qZC0nGLRNSCMxdFDyg6c0eG395runV_JYyDyVdwxpjhFkyZO-A");
//        image3.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR5qZC0nGLRNSCMxdFDyg6c0eG395runV_JYyDyVdwxpjhFkyZO-A");
//
//        product.add(new ProductSmallResponceData("", "khan tam1", image3, 20000, 0, null, "Chat mem xin", "8 gio truoc", 10, 10, 0, 0, 0, 0, new SellerResponceData("", "trung1", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRHh7OTh5WxNuFFcsfgj7dQhY7r0S_XKiZxUChQs0NCyFgZ8NmRzw")));
//        product.add(new ProductSmallResponceData("", "khan tam2", image2, 20000, 0, null, "Chat mem xin", "7 gio truoc", 10, 10, 0, 0, 0, 0, new SellerResponceData("", "trung2", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRHh7OTh5WxNuFFcsfgj7dQhY7r0S_XKiZxUChQs0NCyFgZ8NmRzw")));
//        product.add(new ProductSmallResponceData("", "khan tam3", image2, 20000, 0, null, "Chat mem xin", "6 gio truoc", 10, 10, 0, 0, 0, 0, new SellerResponceData("", "trung3", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRHh7OTh5WxNuFFcsfgj7dQhY7r0S_XKiZxUChQs0NCyFgZ8NmRzw")));
//        product.add(new ProductSmallResponceData("", "khan tam4", image3, 20000, 0, null, "Chat mem xin", "5 gio truoc", 10, 10, 0, 0, 0, 0, new SellerResponceData("", "trung4", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRHh7OTh5WxNuFFcsfgj7dQhY7r0S_XKiZxUChQs0NCyFgZ8NmRzw")));
//        product.add(new ProductSmallResponceData("", "khan tam5", image1, 20000, 0, null, "Chat mem xin", "4 gio truoc", 10, 10, 0, 0, 0, 0, new SellerResponceData("", "trung5", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRHh7OTh5WxNuFFcsfgj7dQhY7r0S_XKiZxUChQs0NCyFgZ8NmRzw")));
//        product.add(new ProductSmallResponceData("", "khan tam6", image1, 20000, 0, null, "Chat mem xin", "3 gio truoc", 10, 10, 0, 0, 0, 0, new SellerResponceData("", "trung6", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRHh7OTh5WxNuFFcsfgj7dQhY7r0S_XKiZxUChQs0NCyFgZ8NmRzw")));

        mListProductTimelineAdapter = new ListProductTimelineAdapter(BaseApp.getContext(), product);
        mLayoutTimeLine.setAdapter(mListProductTimelineAdapter);

        mListProductTimelineAdapter.setmOnClickProductItemListenner(new OnClickProductItemListenner() {
            @Override
            public void onClick(String productId) {
                startActivityProductDetail(productId);
            }
        });

        mListProductTimelineAdapter.setmOnClickLikeListener(new OnClickLikeListener() {
            @Override
            public void onClick(ProductSmallResponceData product, View view, boolean isLike) {
                String token = AccountUntil.getUserToken();

                if (token != null){
                    mProductSmallResponceData = product;
                    btnLikeTimeLine = (Button) view;
                    mIsLikeTimeLine = isLike;
                    DialogUtil.showProgress(getActivity());
                    mPresenter.likeProductRemote(token, product.getId());
                }
                else {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }
            }
        });

        mListProductTimelineAdapter.setmOnClickCommentListener(new OnClickCommentListener() {
            @Override
            public void onClick(String productId) {
                Intent intent = new Intent(BaseApp.getContext(), ProductCommentActivity.class);
                intent.putExtra(AppConstant.PRODUCT_ID, productId);
                DialogUtil.showProgress(getActivity());
                startActivity(intent);
            }
        });

        mListProductTimelineAdapter.setListener(new ProductChatAdapter.ItemClickListener() {
            @Override
            public void onClick(int position) {

                String userId = mListProductTimelineAdapter.mProducts.get(position).getSeller().getId();
                Intent intent = new Intent(getActivity(), UserInfoActivity.class);
                DialogUtil.showProgress(getActivity());
                intent.putExtra("userId", userId);
                intent.putExtra("numProduct", 0);
                intent.putExtra("score", 0);
                startActivity(intent);
            }
        });
    }

    public void setViewLikeTimeLine(int numLike){
        if (mIsLikeTimeLine){
            btnLikeTimeLine.setBackgroundResource(R.drawable.icon_time_line_like_on);
            btnLikeTimeLine.setText(numLike + "");

            if (mProductSmallResponceData != null){
                mProductSmallResponceData.setLike(numLike);
                mProductSmallResponceData.setIsLiked(1);
            }
        }
        else{
            btnLikeTimeLine.setBackgroundResource(R.drawable.icon_time_line_like_off);
            btnLikeTimeLine.setText(numLike + "");


            if (mProductSmallResponceData != null){
                mProductSmallResponceData.setLike(numLike);
                mProductSmallResponceData.setIsLiked(0);
            }
        }
    }

    @Override
    public void setVisibleNewItems(boolean visible) {

        if (visible){
            mProductPagerFragment.mNewItemsBtn.setVisibility(View.GONE);
        }
        else {
            mProductPagerFragment.mNewItemsBtn.setVisibility(View.GONE);
        }
    }

    @Override
    public ListProductTimelineAdapter getTimeLineAdapter() {
        return mListProductTimelineAdapter;
    }

    private void startActivityProductDetail(String productId){
        Intent intent = new Intent(BaseApp.getContext(), ProductDetailActivity.class);
        intent.putExtra(AppConstant.PRODUCT_ID, productId);
        DialogUtil.showProgress(getActivity());
        startActivity(intent);
    }

    RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener(){
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
//                Log.d("trung", "dx" + dx);
//            Log.d("trung", "dy " + dy);

            if (dy > 15){
                mProductPagerFragment.setVisibleButtonCamera(false);
            }
            else if (dy < -15){
                mProductPagerFragment.setVisibleButtonCamera(true);
            }

            int offsetScroll = recyclerView.computeVerticalScrollOffset();
//            Log.d("trung", "offset " + offsetScroll);
            if (offsetScroll < 3){
                mProductPagerFragment.setVisibleScrollableLayout(true);
            }
            else{
                mProductPagerFragment.setVisibleScrollableLayout(false);
            }
        }
    };

    AbsListView.OnScrollListener onScrollListenerTimeLine = new AbsListView.OnScrollListener() {

        int mLastFirstVisibleItem = 0;

        @Override
        public void onScrollStateChanged(AbsListView absListView, int i) {

        }

        @Override
        public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            Log.d("trung", firstVisibleItem + "  " + visibleItemCount + "  " + totalItemCount + " " + mLayoutTimeLine.getHeight());

            if (totalItemCount > 0){
//                if (firstVisibleItem == 0 && visibleItemCount == 1){
//                    mProductPagerFragment.setVisibleScrollableLayout(true);
//                }
//                else {
//                    mProductPagerFragment.setVisibleScrollableLayout(false);
//                }


                if (mLastFirstVisibleItem < firstVisibleItem){
                    mProductPagerFragment.setVisibleButtonCamera(false);
                }
                else if (mLastFirstVisibleItem == firstVisibleItem){

                }
                else {
                    mProductPagerFragment.setVisibleButtonCamera(true);
                }

                mLastFirstVisibleItem = firstVisibleItem;
            }
        }
    };

    public void initRefreshLayout(){
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {

                if (Utils.checkInternetAvailable()){
                    mPresenter.callPullToRefreshProducts();
                }
                else {
                    mRefreshLayout.finishRefresh();
                    DialogUtil.showPopupError(getActivity(), BaseApp.getContext().getString(R.string.error_msg_internet_not_connect));
                }

            }
        });

        mRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {

                if (Utils.checkInternetAvailable()){
                    mPresenter.callGetLoadMoreProducts();
                }
                else {
                    mRefreshLayout.finishLoadmore();
                    DialogUtil.showPopupError(getActivity(), BaseApp.getContext().getString(R.string.error_msg_internet_not_connect));
                }

            }
        });
    }

    public void getProducts(){

//        ArrayList<ProductSmallResponceData> products = APICacheUtils.get()
//                        .getProducts(mPresenter.getCategoty().getCategoryId(), ProductSmallResponceData.getType());
//
//
//        if (products == null){
//            mPresenter.callGetProducts();
//        }
//        else {
//            mPresenter.getProductFromLocal();
//            mPresenter.checkNewItem();
//        }

//        if (Utils.checkInternetAvailable()){
        if (!Utils.checkInternetAvailable()){
            DialogUtil.showPopupError(getActivity(), BaseApp.getContext().getString(R.string.error_msg_internet_not_connect));
        }
            mPresenter.callGetProducts();
//        }
//        else {
//            mPresenter.getProductFromLocal();
//            DialogUtil.showPopupError(getActivity(), BaseApp.getContext().getString(R.string.error_msg_internet_not_connect));
//        }
    }

    public void showProducts(List<Product> products){
        mListProductAdapter.insertLastItem(products);
    }



    public void showProductsTimeLine(List<ProductSmallResponceData> products){
        mListProductTimelineAdapter.insertLastItem(products);
    }

    @Override
    public void showProductsRefresh(List<Product> products) {
        mListProductAdapter.insertHeadItem(products);
    }

    @Override
    public void showProductsTimeLineRefresh(List<ProductSmallResponceData> products) {
        mListProductTimelineAdapter.insertHeadItem(products);
    }

    public void invisibleLoadMore(){
        mRefreshLayout.finishLoadmore();
    }

    public void invisibleRefresh(){
        mRefreshLayout.finishRefresh();
    }

    public void initViewProduct(){
        int viewType = ((MainActivity)getActivity()).mViewType;

        mViewFlipper.setDisplayedChild(viewType);
    }

    public void changeViewMode() {
        if (mViewFlipper != null){
            refreshView();
            AnimationFactory.flipTransition(this.mViewFlipper, AnimationFactory.FlipDirection.LEFT_RIGHT);
        }
    }

    public void refreshView(){
        mProductPagerFragment.setVisibleScrollableLayout(true);
        mProductPagerFragment.mScrollableLayout.scrollTo(0, 0);
        mRVProductList.getLayoutManager().scrollToPosition(0);
        mLayoutTimeLine.setSelection(0);
    }


}
