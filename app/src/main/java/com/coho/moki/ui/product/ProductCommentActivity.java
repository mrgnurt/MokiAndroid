package com.coho.moki.ui.product;

import android.os.AsyncTask;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.coho.moki.R;
import com.coho.moki.adapter.product.ProductCommentAdapter;
import com.coho.moki.data.remote.ProductCommentResponse;
import com.coho.moki.ui.base.BaseActivity;
import com.costum.android.widget.PullAndLoadListView;
import com.costum.android.widget.PullToRefreshListView;

import java.util.Collections;
import java.util.LinkedList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Khanh Nguyen on 11/8/2017.
 */

public class ProductCommentActivity extends BaseActivity {

    @BindView(R.id.listView)
    PullAndLoadListView listView;

    @BindView(R.id.edtComment)
    EditText txtComment;

    @BindView(R.id.btnDelete)
    Button btnDelete;

    @BindView(R.id.btnComment)
    Button btnComment;

    @BindView(R.id.btnNavLeft)
    ImageButton btnBack;

    LinkedList<ProductCommentResponse> commentList;
    ProductCommentAdapter commentAdapter;

    @Override
    public int setContentViewId() {
        return R.layout.product_comment;
    }

    @Override
    public void initView() {

        listView.setOnRefreshListener(new PullToRefreshListView.OnRefreshListener() {

            public void onRefresh() {
                // Do work to refresh the list here.
                new PullToRefreshDataTask().execute();
            }
        });

        listView.setOnLoadMoreListener(new PullAndLoadListView.OnLoadMoreListener() {

            public void onLoadMore() {
                // Do the work to load more items at the end of list
                // here
                new LoadMoreDataTask().execute();
            }
        });


    }

    @Override
    public void initData() {
        fakeComment();
    }

    private ProductCommentResponse fake() {
        ProductCommentResponse.Poster poster = new ProductCommentResponse.Poster();
        poster.setId("id");
        poster.setName("poster name");
        poster.setAvatar("null");
        ProductCommentResponse response = new ProductCommentResponse();
        response.setId("id");
        response.setComment("My first comment");
        response.setCreated("2016-09-13T23:30:52.123Z");
        response.setPoster(poster);
        return response;
    }

    public void fakeComment() {
        commentList = new LinkedList<>();
        commentList.add(fake());
        commentList.add(fake());
        commentList.add(fake());

        if (commentList != null && commentList.size() > 0) {
            Collections.reverse(commentList);
            commentAdapter = new ProductCommentAdapter(
                    this, R.layout.product_comment_item, commentList);
            listView.setAdapter(commentAdapter);
        }
    }

    private class LoadMoreDataTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {

            if (isCancelled()) {
                return null;
            }

            // Simulates a background task
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }

//            for (int i = 0; i < mNames.length; i++)
//                mListItems.add(mNames[i]);
            commentList.add(fake());


            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
//            mListItems.add("Added after load more");

            // We need notify the adapter that the data have been changed
            commentAdapter.notifyDataSetChanged();

            // Call onLoadMoreComplete when the LoadMore task, has finished
            listView.onLoadMoreComplete();

            super.onPostExecute(result);
        }

        @Override
        protected void onCancelled() {
            // Notify the loading more operation has finished
            listView.onLoadMoreComplete();
        }
    }

    private class PullToRefreshDataTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {

            if (isCancelled()) {
                return null;
            }

            // Simulates a background task
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }

//            for (int i = 0; i < mAnimals.length; i++)
//                mListItems.addFirst(mAnimals[i]);
            commentList.addFirst(fake());

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
//            mListItems.addFirst("Added after pull to refresh");

            // We need notify the adapter that the data have been changed
            commentAdapter.notifyDataSetChanged();

            // Call onLoadMoreComplete when the LoadMore task, has finished
            listView.onRefreshComplete();

            super.onPostExecute(result);
        }

        @Override
        protected void onCancelled() {
            // Notify the loading more operation has finished
            listView.onLoadMoreComplete();
        }
    }

    @OnClick(R.id.btnNavLeft)
    public void onClickButtonBack() {
        onBackPressed();
    }

}
