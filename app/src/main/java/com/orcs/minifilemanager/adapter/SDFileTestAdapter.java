package com.orcs.minifilemanager.adapter;

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
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/13.
 * 测试SD卡界面的Adapter
 */
public class SDFileTestAdapter extends BaseAdapter {

    private List<Map<String, Object>> mData;
    private Context mContext;
    private final Resources mResources;

    public SDFileTestAdapter(Context context, List<Map<String, Object>> datas) {
        mData = datas;
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder holder;
        int numItems = 0;
        final File file = new File((String) mData.get(position).get("info"));
        if (convertView == null) {
            holder = new ViewHolder();
            view = View.inflate(mContext, R.layout.item_sdfiletest, null);
            holder.img = (ImageView) view.findViewById(R.id.sdfiletest_row_image);
            holder.title = (TextView) view.findViewById(R.id.sdfiletest_top_view_txt);
            holder.info = (TextView) view.findViewById(R.id.sdfiletest_dataview_txt);
            holder.bottomView = (TextView) view.findViewById(R.id.sdfiletest_bottom_text);
            view.setTag(holder);
        } else {
            view = convertView;
            holder = (ViewHolder) view.getTag();
        }
        holder.img.setBackgroundResource((Integer) mData.get(position).get(
                "img"));
        holder.title.setText((String) mData.get(position).get("title"));
        holder.info.setText((String) mData.get(position).get("info"));

        if (file.isFile()){
            holder.bottomView.setText(SimpleUtils.formatCalculatedSize(file.length()));
        }else {
            String[] list = file.list();
            if (list != null)
                numItems = list.length;
            holder.bottomView.setText(numItems + mResources.getString(R.string.files));
        }

        return view;
    }

    class ViewHolder {
        public ImageView img;
        public TextView title;
        public TextView info;
        public TextView bottomView;
    }

}
