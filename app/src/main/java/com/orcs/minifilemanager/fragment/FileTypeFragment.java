package com.orcs.minifilemanager.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.orcs.minifilemanager.R;
import com.orcs.minifilemanager.activity.APKFileTestActivity;
import com.orcs.minifilemanager.activity.PicFileTestActivity;
import com.orcs.minifilemanager.activity.SDFileTestActivity;
import com.orcs.minifilemanager.activity.WordFileActivity;
import com.orcs.minifilemanager.activity.ZIPFileTestActivity;
import com.orcs.minifilemanager.adapter.FileTypeAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/12.
 * 文件类型的Fragment
 */
public class FileTypeFragment extends BaseFragment {

    //需配置的文件夹路径
    @SuppressLint("SdCardPath")
    public static final String SDCARD_PATH = "/sdcard";
    public static final String PIC_PATH = "/storage/emulated/0/Coderfun";


    private static final String ARG_TITLE = "title";
    private ListView fileTypeListView;


    public static FileTypeFragment getInstance(String title) {
        FileTypeFragment frg = new FileTypeFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_TITLE, title);
        frg.setArguments(bundle);
        return frg;
    }

    @Override
    protected int setLayout() {
        return R.layout.fragment_file_type;
    }

    @Override
    protected void findViews(View rootView) {
        fileTypeListView = (ListView) rootView.findViewById(R.id.fragment_file_type_lv);
        fileTypeListView = (ListView) rootView.findViewById(R.id.fragment_file_type_lv);
    }

    @Override
    protected void initialization() {

        List<String> fileTypeNames = new ArrayList<>();
        fileTypeNames.add("SD卡测试");
        fileTypeNames.add("图片");
        fileTypeNames.add("文档");
        fileTypeNames.add("压缩文件");
        fileTypeNames.add("APK");

        List<Integer> fileTypeIcons = new ArrayList<>();
        fileTypeIcons.add(R.drawable.type_sd);
        fileTypeIcons.add(R.drawable.type_pic);
        fileTypeIcons.add(R.drawable.type_document);
        fileTypeIcons.add(R.drawable.type_zip);
        fileTypeIcons.add(R.drawable.type_apk);

        FileTypeAdapter fileTypeAdapter = new FileTypeAdapter(getActivity(), fileTypeNames, fileTypeIcons);
        fileTypeListView.setAdapter(fileTypeAdapter);
    }

    @Override
    protected void bindEvent() {
        fileTypeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0: //跳到sd卡测试
                        Intent intent = new Intent();
                        // 设置起始文件夹和文件类型
                        intent.setDataAndType(Uri.fromFile(new File(SDCARD_PATH)), "*/*");
                        intent.setClass(getActivity(), SDFileTestActivity.class);
                        startActivity(intent);
                        break;
                    case 1: //跳到图片
                        Intent intent1 = new Intent();
                        intent1.setDataAndType(Uri.fromFile(new File(PIC_PATH)), "*/*");
                        intent1.setClass(getActivity(), PicFileTestActivity.class);
                        startActivity(intent1);
                        break;
                    case 2: //跳到文档
                        Intent intent2 = new Intent();
                        intent2.setDataAndType(Uri.fromFile(new File(PIC_PATH)), "*/*");
                        intent2.setClass(getActivity(), WordFileActivity.class);
                        startActivity(intent2);
                        break;
                    case 3: //跳到压缩文件
                        Intent intent3 = new Intent();
                        intent3.setDataAndType(Uri.fromFile(new File(PIC_PATH)), "*/*");
                        intent3.setClass(getActivity(), ZIPFileTestActivity.class);
                        startActivity(intent3);
                        break;
                    case 4: //跳到APK
                        Intent intent4 = new Intent();
                        intent4.setDataAndType(Uri.fromFile(new File(PIC_PATH)), "*/*");
                        intent4.setClass(getActivity(), APKFileTestActivity.class);
                        startActivity(intent4);
                        break;
                    default:
                        break;
                }
            }
        });


    }

    @Override
    protected void onCreateView() {

    }
}
