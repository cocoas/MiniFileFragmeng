package com.orcs.minifilemanager.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.orcs.minifilemanager.R;

import java.util.List;

/**
 * Created by Administrator on 2016/7/13.
 * 主界面 文件类型的适配器
 */
public class FileTypeAdapter extends BaseAdapter {

    private Context mContext;
    private List<String> mName;
    private List<Integer> mIcon;

    public FileTypeAdapter(Context context, List<String> name, List<Integer> icon) {
        mContext = context;
        mName = name;
        mIcon = icon;
    }

    @Override
    public int getCount() {
        return mName.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder viewHolder = null;
        if (convertView == null) {
            view = View.inflate(mContext, R.layout.item_file_type, null);
            viewHolder = new ViewHolder();
            viewHolder.mItemIV = (ImageView) view.findViewById(R.id.item_filetype_row_image);
            viewHolder.mItenmTV = (TextView) view.findViewById(R.id.item_filetype_top_view_txt);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.mItemIV.setImageResource(mIcon.get(position));
        viewHolder.mItenmTV.setText(mName.get(position));
        return view;
    }

    class ViewHolder {
        ImageView mItemIV;
        TextView mItenmTV;

    }

}
