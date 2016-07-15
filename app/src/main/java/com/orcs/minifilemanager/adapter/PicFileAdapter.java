package com.orcs.minifilemanager.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.orcs.minifilemanager.R;
import com.orcs.minifilemanager.util.SimpleUtils;

import java.io.File;
import java.text.DateFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/15.
 * 图片界面的Adapter
 */
public class PicFileAdapter extends BaseAdapter {
    private List<Map<String, Object>> mData;
    private Context mContext;
    private final Resources mResources;

    public PicFileAdapter(List<Map<String, Object>> data, Context context) {
        mData = data;
        mContext = context;
        mResources = context.getResources();
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder holder;
        int numItems = 0;
        final File file = new File((String) mData.get(position).get("info"));
        DateFormat df = DateFormat.getDateTimeInstance(DateFormat.SHORT,
                DateFormat.SHORT, Locale.getDefault());
        if (convertView == null) {
            holder = new ViewHolder();
            view = View.inflate(mContext, R.layout.item_file_info, null);
            holder.img = (ImageView) view.findViewById(R.id.fileinfo_row_image);
            holder.title = (TextView) view.findViewById(R.id.fileinfo_top_view_txt);
            holder.info = (TextView) view.findViewById(R.id.fileinfo_dataview_txt);
            holder.bottomView = (TextView) view.findViewById(R.id.fileinfo_bottom_text);
            view.setTag(holder);
        } else {
            view = convertView;
            holder = (ViewHolder) view.getTag();
        }
        holder.img.setImageResource((Integer) mData.get(position).get("img"));
        holder.title.setText((String)mData.get(position).get("title"));
        holder.info.setText(df.format(file.lastModified()));
        if (file.isFile()) {
            holder.bottomView.setText(SimpleUtils.formatCalculatedSize(file.length()));
        } else {
            String[] list = file.list();
            if (list != null)
                numItems = list.length;
            holder.bottomView.setText(numItems + mResources.getString(R.string.files));
        }
        return view;
    }

    class ViewHolder {
        public ImageView img;   //图片
        public TextView title;  //名称
        public TextView info;   //操作时间
        public TextView bottomView; //文件大小
    }

}
