package com.example.a03;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by yinrong on 2016/12/17.
 */

public class MyAdapter extends BaseAdapter {
    private ArrayList<String> mMsgs;
    private Context mContext;
    public MyAdapter(Context context, ArrayList<String> msgs) {
        mMsgs = msgs;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mMsgs.size();
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder viewHolder;
        if (convertView != null){
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }else{
            view = View.inflate(mContext,R.layout.item_main,null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }
        viewHolder.tv_msg.setText(mMsgs.get(position));
        viewHolder.iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //1.把点击的条在列表中移除
                mMsgs.remove(position);
                //2.更新数据
                notifyDataSetChanged();
            }
        });
        return view;
    }
    class ViewHolder{
        @butterknife.Bind(R.id.tv_msg)
        TextView tv_msg;
        @butterknife.Bind(R.id.iv_delete)
        ImageView iv_delete;
        public ViewHolder(View view) {
            butterknife.ButterKnife.bind(this,view);
        }
    }
}
