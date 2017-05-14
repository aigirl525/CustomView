package com.example.a08;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by hasee on 2017/1/7.
 */
public class MyAdapter extends BaseAdapter {
    private Context mContext;
    private List<MyBean> myBeanList;
    private SlideLayout slideLayout;
    public MyAdapter(Context context ,List<MyBean> myBeanList) {
        mContext = context;
        this.myBeanList = myBeanList;
    }

    @Override
    public int getCount() {
        return  this.myBeanList.size();
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
    public View getView(int position, View convertView, final ViewGroup parent) {
        final ViewHolder viewHolder;
        if(convertView == null){
            convertView = View.inflate(mContext,R.layout.item_main,null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }
        viewHolder.item_content.setText(myBeanList.get(position).getName());
        viewHolder.item_content.setTag(position);
        viewHolder.item_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (int) v.getTag();
                Toast.makeText(mContext, "bean==" + myBeanList.get(position).getName(), Toast.LENGTH_SHORT).show();
            }
        });
        viewHolder.item_menu.setTag(position);
        viewHolder.item_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 解决删除后还显示打开的删除TextView
                 */
                SlideLayout slideLayout = (SlideLayout) v.getParent();
                slideLayout.closeMenu();
                int position = (int) v.getTag();
                myBeanList.remove(position);
                notifyDataSetChanged();
            }
        });
        SlideLayout slideLayout = (SlideLayout)convertView;
        slideLayout.setOnStateChangeListener(onStateChangeListener);
        return convertView;
    }
    static class ViewHolder{
    @butterknife.Bind(R.id.item_content)
    TextView item_content;
    @butterknife.Bind(R.id.item_menu)
    TextView item_menu;
    private ViewHolder(View view) {
            ButterKnife.bind(this,view);
        }
    }
    private SlideLayout.OnStateChangeListener onStateChangeListener = new SlideLayout.OnStateChangeListener() {
        @Override
        public void onOpen(SlideLayout layout) {
            slideLayout = layout;//保持到内存中
        }

        @Override
        public void onDown(SlideLayout layout) {
            if (slideLayout != null && slideLayout != layout){
                slideLayout.closeMenu();
            }

        }

        @Override
        public void onClose(SlideLayout layout) {
            if (slideLayout == layout){
                slideLayout = null;//保持的值为空
            }
        }
    };
}
