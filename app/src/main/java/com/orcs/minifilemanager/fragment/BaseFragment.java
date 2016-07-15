package com.orcs.minifilemanager.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2016/7/12.
 */
public abstract class BaseFragment extends Fragment {

    protected View mFragmentView;
    protected Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mFragmentView = inflater.inflate(setLayout(), container, false);
        findViews(mFragmentView);
        initialization();
        bindEvent();
        onCreateView();
        return mFragmentView;
    }

    protected abstract int setLayout();

    protected abstract void findViews(View rootView);

    protected abstract void initialization();

    protected abstract void bindEvent();

    protected abstract void onCreateView();




}
