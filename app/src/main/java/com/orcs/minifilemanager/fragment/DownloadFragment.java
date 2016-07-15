package com.orcs.minifilemanager.fragment;

import android.os.Bundle;
import android.view.View;

import com.orcs.minifilemanager.R;

/**
 * Created by Administrator on 2016/7/12.
 * 正在下载的Fragment
 */
public class DownloadFragment extends BaseFragment{

    private static final String ARG_TITLE = "title";

    public static DownloadFragment getInstance(String title){
        DownloadFragment fragment = new DownloadFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_TITLE,title);
        fragment.setArguments(bundle);
        return  fragment;
    }

    @Override
    protected int setLayout() {
        return R.layout.fragment_download;
    }

    @Override
    protected void findViews(View rootView) {

    }

    @Override
    protected void initialization() {

    }

    @Override
    protected void bindEvent() {

    }

    @Override
    protected void onCreateView() {

    }
}
