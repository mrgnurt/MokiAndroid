package com.coho.moki.ui.user;

import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.coho.moki.R;
import com.coho.moki.adapter.user.UserRateAdapter;
import com.coho.moki.data.remote.UserRateResponse;
import com.coho.moki.ui.base.BaseActivity;
import com.coho.moki.util.Utils;
import com.costum.android.widget.PullAndLoadListView;
import com.costum.android.widget.PullToRefreshListView;

import java.util.LinkedList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Khanh Nguyen on 10/19/2017.
 */

public class UserRateActivity extends BaseActivity {

    @BindView(R.id.btnNavLeft)
    ImageButton btnBack;

    @BindView(R.id.btnNavRight)
    ImageButton btnNavRight;

    @BindView(R.id.txtHeader)
    TextView txtHeader;

    @BindView(R.id.txtHeaderRunning)
    TextView txtHeaderRunning;

    @BindView(R.id.listView)
    PullAndLoadListView listView;

    UserRateAdapter rateAdapter;
    LinkedList<UserRateResponse> rateList;

    @Override
    public int setContentViewId() {
        return R.layout.user_rate;
    }

    @Override
    public void initView() {
        btnBack.setVisibility(View.VISIBLE);
        txtHeader.setVisibility(View.VISIBLE);
        txtHeaderRunning.setVisibility(View.GONE);
        btnNavRight.setVisibility(View.GONE);
        txtHeader.setText(Utils.toTitleCase(getResources().getString(R.string.rate_list)));

        listView.setOnRefreshListener(new PullToRefreshListView.OnRefreshListener() {

            public void onRefresh() {
                // Do work to refresh the list here.
                new UserRateActivity.PullToRefreshDataTask().execute();
            }
        });

        listView.setOnLoadMoreListener(new PullAndLoadListView.OnLoadMoreListener() {

            public void onLoadMore() {
                // Do the work to load more items at the end of list
                // here
                new UserRateActivity.LoadMoreDataTask().execute();
            }
        });
    }

    @Override
    public void initData() {
        fake();
    }

    public void fake() {
        rateList = new LinkedList<>();
        rateList.add(null);
        rateList.add(null);
        rateList.add(null);

        if (rateList != null && rateList.size() > 0) {
            rateAdapter = new UserRateAdapter(
                    this, R.layout.user_rate_item, rateList);
            listView.setAdapter(rateAdapter);
        }
    }

    private class LoadMoreDataTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            if (isCancelled()) {
                return null;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
            rateList.add(null);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            rateAdapter.notifyDataSetChanged();

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
            rateList.addFirst(null);

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
//            mListItems.addFirst("Added after pull to refresh");

            // We need notify the adapter that the data have been changed
            rateAdapter.notifyDataSetChanged();

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
    public void onClickButtonNavLeft() {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}
