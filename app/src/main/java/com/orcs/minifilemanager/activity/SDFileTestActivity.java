package com.orcs.minifilemanager.activity;


import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.orcs.minifilemanager.R;
import com.orcs.minifilemanager.adapter.SDFileTestAdapter;
import com.orcs.minifilemanager.fragment.DeleteFilesDialog;
import com.orcs.minifilemanager.fragment.FileTypeFragment;
import com.orcs.minifilemanager.util.ALog;
import com.orcs.minifilemanager.util.SimpleUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/13.
 * SD卡测试的Activity
 */
public class SDFileTestActivity extends AppCompatActivity {

    private List<Map<String, Object>> mData;
    private String mDir;

    private ListView mListView;
    private SDFileTestAdapter adapter;

    public static final String TAG_DIALOG = "dialog";

    private FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_file_type);

        Intent intent = this.getIntent();
        Bundle b1 = intent.getExtras();
        //接收起始目录
        Uri uri = intent.getData();
        mDir = uri.getPath();
        ALog.d("初试化时的目录路径" + mDir.toString());
        mListView = (ListView) findViewById(R.id.fragment_file_type_lv);

        fm = getFragmentManager();

        mData = getData();
        adapter = new SDFileTestAdapter(this, mData);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ALog.d("点击每一项" + (String) mData.get(position).get("info"));

                File file = new File((String) mData.get(position).get("info"));

                if ((Integer) mData.get(position).get("img") == R.drawable.type_folder ||
                        (Integer) mData.get(position).get("img") == R.drawable.back) {
                    ALog.d("开始进入下一级");
                    mDir = (String) mData.get(position).get("info");
                    ALog.d("变换后的文件路径" + mDir.toString());
                    mData = getData();
                    adapter = new SDFileTestAdapter(SDFileTestActivity.this, mData);
                    mListView.setAdapter(adapter);
                } else {
//                    Toast.makeText(SDFileTestActivity.this, "没有下一层了", LENGTH_SHORT).show();
                    SimpleUtils.openFile(SDFileTestActivity.this, file);
                }
            }
        });

        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(SDFileTestActivity.this, "长按事件", Toast.LENGTH_SHORT).show();
                if ((Integer) mData.get(position).get("img") != R.drawable.back) {
                    String[] files = new String[1];
                    files[0] = (String) mData.get(position).get("info");
                    final DialogFragment dialog1 = DeleteFilesDialog.instantiate(files);
                    dialog1.show(SDFileTestActivity.this.getFragmentManager(), SDFileTestActivity.TAG_DIALOG);
                    adapter.notifyDataSetChanged();
                }
                return true;
            }
        });


    }


    @Override

    protected void onPause() {
        super.onPause();
        final Fragment f = fm.findFragmentByTag(TAG_DIALOG);
        if (f != null) {
            fm.beginTransaction().remove(f).commit();
            fm.executePendingTransactions();    // 立即执行事务
        }
    }

    public List<Map<String, Object>> getData() {
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map = null;
        //打开当前目录
        File f = new File(mDir);
        //获取当前文件中文件目录
        File[] files = f.listFiles();
        //不允许进入传入目录的上层目录
        if (!mDir.equals(FileTypeFragment.SDCARD_PATH)) {
            //加返回上一层目录项
            map = new HashMap<String, Object>();
            map.put("title", getString(R.string.back));
            map.put("info", f.getParent());
            map.put("img", (Integer) R.drawable.back);
            list.add(map);
        }
        if (files != null) {
            for (File file : files) {
                map = new HashMap<String, Object>();
                map.put("title", file.getName());   //文件名
                map.put("info", file.getPath());    //文件路径
                //按照不同类型的文件添加不同的图标
                if (file.isDirectory()) {
                    map.put("img", (Integer) R.drawable.type_folder);   //文件图标
                } else {
                    map.put("img", (Integer) R.drawable.type_unknown);
                }
                list.add(map);
            }
        }
        return list;
    }


}
