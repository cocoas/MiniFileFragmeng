package com.orcs.minifilemanager;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.orcs.minifilemanager.fragment.DownloadFragment;
import com.orcs.minifilemanager.fragment.FileTypeFragment;

import java.util.ArrayList;

/**
 * 文件管理的主界面
 */
public class MainActivity extends AppCompatActivity {

    private String[] titles = {"分类", "正在下载"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化Fragment
        ArrayList<Fragment> fragments = new ArrayList<>();
        for (String title : titles) {
            if (title.equals("分类")) {
                fragments.add(FileTypeFragment.getInstance("分类"));
            }
            if (title.equals("正在下载")) {
                fragments.add(DownloadFragment.getInstance("正在下载"));
            }
        }

        //初始化ViewPager
        ViewPager viewPager = (ViewPager) findViewById(R.id.vp);
        if (viewPager != null) {
            viewPager.setOffscreenPageLimit(2);
        }
        if (viewPager != null) {
            viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager(), fragments, titles));
        }

        //
        TabLayout tabLayout = (TabLayout) findViewById(R.id.activity_main_tabLayout);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        assert viewPager != null;
        tabLayout.setupWithViewPager(viewPager);
    }


    class MyPagerAdapter extends FragmentPagerAdapter {

        private ArrayList<Fragment> mFragments = new ArrayList<>();
        private String[] mTitles;

        public MyPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments, String[] titles) {
            super(fm);
            mFragments = fragments;
            mTitles = titles;
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }
    }

}
