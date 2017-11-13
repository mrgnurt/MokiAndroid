package com.coho.moki.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.coho.moki.ui.main.MainActivity;
import com.coho.moki.util.DebugLog;

import butterknife.ButterKnife;

/**
 * Created by trung on 11/10/2017.
 */

public abstract class BaseFragment extends Fragment {

    private boolean reInitDataOnResume;
    protected ViewGroup rootView;

    protected boolean isLoading = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DebugLog.i("Lifecycle " + this.getClass().getSimpleName());
        Bundle arguments = getArguments();
        if (arguments != null) {
            handleArguments(arguments);
        }

        if (savedInstanceState != null) {
            handleSavedInstanceState(savedInstanceState);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        DebugLog.i("Lifecycle " + this.getClass().getSimpleName());
        return rootView != null ? rootView : createRootView(inflater, container);
    }

    private View createRootView(LayoutInflater inflater, ViewGroup container) {
        rootView = (ViewGroup) inflater.inflate(getLayoutId(), container, false);
        ButterKnife.bind(this, rootView);
        initPresenter();
        initView();
        initData();
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    protected void handleArguments(Bundle arguments) {
        // TODO
    }

    protected void handleSavedInstanceState(Bundle savedInstanceState) {
        // TODO
    }

    protected void initView() {
        setHasOptionsMenu(true);
    }

    protected void initData() {
    }

    protected void initPresenter(){}

    protected void reInitData() {
        if (isVisible()) {
            reInitDataOnResume = false;
            initData();
        } else {
            reInitDataOnResume = true;
        }
    }

    public void setReInitDataOnResume(boolean reInitDataOnResume) {
        this.reInitDataOnResume = reInitDataOnResume;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        DebugLog.i("Lifecycle " + this.getClass().getSimpleName());
        if (isCancelRequestOnDestroyView()) {
            isLoading = isLoadingMore();
        }
    }

    @Override
    public void onDestroy() {
        DebugLog.i("Lifecycle " + this.getClass().getSimpleName());
        ButterKnife.bind(this, rootView).unbind();
        rootView = null;
        super.onDestroy();
    }

    protected boolean isCancelRequestOnDestroyView() {
        return false;
    }

    protected boolean isLoadingMore() {
        return false;
    }

    @Override
    public void onResume() {
        DebugLog.i("Lifecycle " + this.getClass().getSimpleName());
        super.onResume();
        if (reInitDataOnResume) {
            reInitData();
        }
    }

    abstract protected int getLayoutId();

    public BaseActivity getBaseActivity() {
        return (BaseActivity) getActivity();
    }

//    @Nullable
//    /**
//     * @return MainActivity instance or null if activity is not MainActivity
//     */
//    public MainActivity getMainActivity() {
//        FragmentActivity activity = getActivity();
//        return activity instanceof MainActivity ? (MainActivity) activity : null;
//    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        menu.clear();
        super.onCreateOptionsMenu(menu, inflater);
    }
}
