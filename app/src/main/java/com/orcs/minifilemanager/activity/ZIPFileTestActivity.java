package com.orcs.minifilemanager.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.orcs.minifilemanager.R;
import com.orcs.minifilemanager.adapter.FileListAdapter;
import com.orcs.minifilemanager.fragment.FileTypeFragment;
import com.orcs.minifilemanager.util.ALog;
import com.orcs.minifilemanager.util.SimpleUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/15.
 */
public class ZIPFileTestActivity extends AppCompatActivity{

    private ListView mListView;
    private TextView mEmptyTxt;

    private FileListAdapter mAdapter;

    private String mDir;
    private List<Map<String, Object>> mData;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pic_file);

        Intent intent = this.getIntent();
        Uri uri = intent.getData();
        mDir = uri.getPath();
        ALog.d("----显示文档的查询路径-------" + mDir.toString());

        mListView = (ListView) findViewById(R.id.pic_file_lv);
        mEmptyTxt = (TextView) findViewById(R.id.pic_empty);
        mEmptyTxt.setVisibility(TextView.GONE);

        mData = getData();
        ALog.d(mData.size()+"-------文档的大小------");
        if (mData.size()==0) {
            mEmptyTxt.setVisibility(TextView.VISIBLE);
        }
        mAdapter = new FileListAdapter(mData, this);
        mListView.setAdapter(mAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                File file = new File((String) mData.get(position).get("info"));
                SimpleUtils.openFile(ZIPFileTestActivity.this, file);
            }
        });

        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder b = new AlertDialog.Builder(ZIPFileTestActivity.this);
                b.setTitle(mData.get(position).get("title") + getString(R.string.will_delete));
                b.setMessage(R.string.cannotbeundoneareyousureyouwanttodelete);
                b.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SimpleUtils.deleteTarget((String) mData.get(position).get("info"));
                        mData = getData();
                        mAdapter = new FileListAdapter(mData, ZIPFileTestActivity.this);
                        mListView.setAdapter(mAdapter);
                    }
                });
                b.setNegativeButton(R.string.cancel, null);
                b.create().show();
                return true;
            }
        });


    }

    public List<Map<String, Object>> getData() {
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map = null;
        File f = new File(mDir);
        File[] files = f.listFiles();
        if (!mDir.equals(FileTypeFragment.PIC_PATH)) {
            map = new HashMap<String, Object>();
            map.put("title", R.string.back);
            map.put("info", f.getParent());
            map.put("img", R.drawable.back);
            list.add(map);
        }
        if (files != null) {
            for (File file : files) {
                if (checkIsZipFiles(file.getPath())) {
                    map = new HashMap<String, Object>();
                    map.put("title", file.getName());
                    map.put("info", file.getPath());
                    map.put("img", R.drawable.type_zip);
                    list.add(map);
                }

            }

        }
        return list;
    }

    private boolean checkIsZipFiles(String fName) {
        boolean isWordFile = false;
        String FileEnd = fName.substring(fName.lastIndexOf(".") + 1,
                fName.length()).toLowerCase();
        if (FileEnd.equals("rar") || FileEnd.equals("zip") || FileEnd.equals("arj") || FileEnd.equals("gz")
                || FileEnd.equals("z")) {
            isWordFile = true;
        }
        return isWordFile;
    }

}
