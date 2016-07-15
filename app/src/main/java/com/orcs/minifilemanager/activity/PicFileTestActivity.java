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
import com.orcs.minifilemanager.adapter.PicFileAdapter;
import com.orcs.minifilemanager.fragment.FileTypeFragment;
import com.orcs.minifilemanager.util.ALog;
import com.orcs.minifilemanager.util.SimpleUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/14.
 * 扫描某一文件夹下面所有图片的Activity
 */
public class PicFileTestActivity extends AppCompatActivity {

    private String mDir;

    private ListView mListView;
    private TextView mEmptyTxt;
    private PicFileAdapter mAdapter;
    private List<Map<String, Object>> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pic_file);
        Intent intent = this.getIntent();
        Uri uri = intent.getData();
        mDir = uri.getPath();
        ALog.d("----显示图片查询路径-----" + mDir.toString());
        mListView = (ListView) findViewById(R.id.pic_file_lv);
        mEmptyTxt = (TextView) findViewById(R.id.pic_empty);
        mEmptyTxt.setVisibility(TextView.GONE);
        mData = getData();
        if (mData.size() == 0) {
            mEmptyTxt.setText(TextView.VISIBLE);
        }

        mAdapter = new PicFileAdapter(mData, this);
        mListView.setAdapter(mAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                File file = new File((String) mData.get(position).get("info"));
                SimpleUtils.openFile(PicFileTestActivity.this, file);
            }
        });

        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder b = new AlertDialog.Builder(PicFileTestActivity.this);
                b.setTitle(mData.get(position).get("title") + getString(R.string.will_delete));
                b.setMessage(R.string.cannotbeundoneareyousureyouwanttodelete);
                b.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SimpleUtils.deleteTarget((String) mData.get(position).get("info"));
                        mData = getData();
                        mAdapter = new PicFileAdapter(mData, PicFileTestActivity.this);
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
            //加返回上一层目录项
            map = new HashMap<String, Object>();
            map.put("title", R.string.back);
            map.put("info", f.getParent());
            map.put("img", R.drawable.back);
            list.add(map);
        }
        if (files != null) {
            for (File file : files) {
                if (checkIsImageFiles(file.getPath())) {
                    map = new HashMap<String, Object>();
                    map.put("title", file.getName());
                    map.put("info", file.getPath());
                    map.put("img", R.drawable.type_pic);
                    list.add(map);
                }
            }
        }
        return list;
    }

    /**
     * 判断文件是否是图片
     *
     * @param fName 文件的路径
     * @return true:是图片 false 不是图片
     */
    private boolean checkIsImageFiles(String fName) {
        boolean isImageFile = false;
        String FileEnd = fName.substring(fName.lastIndexOf(".") + 1,
                fName.length()).toLowerCase();
        if (FileEnd.equals("jpg") || FileEnd.equals("png") || FileEnd.equals("gif")
                || FileEnd.equals("jpeg") || FileEnd.equals("bmp") || FileEnd.equals("gif")
                || FileEnd.equals("tif")) {
            isImageFile = true;
        }
        return isImageFile;
    }

}
