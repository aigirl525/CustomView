package com.example.a07;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by hasee on 2017/1/4.
 */
public class MyAdapter extends BaseAdapter {

    private Context mContext;
    private List<Person> mPersons;
    public MyAdapter(Context context,List<Person> persons) {
        mContext = context;
        mPersons = persons;
    }

    @Override
    public int getCount() {
        return mPersons.size();
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
        ViewHolder viewHolder;
        if (convertView == null){
            convertView = View.inflate(mContext,R.layout.item_main,null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }
        //根据位置得到数据
        Person person = mPersons.get(position);
        viewHolder.tv_name.setText(person.getName());

        String word = person.getPinyin().substring(0,1);
        viewHolder.tv_word.setText(word);

        //隐藏不是第0个字母的item
        if(position == 0 ){
            viewHolder.tv_word.setVisibility(View.VISIBLE);
        }else{
            //得到前一个item的首个汉字的首字母
            String preWord = mPersons.get(position - 1).getPinyin().substring(0,1);
            if(preWord.equals(word)){
                viewHolder.tv_word.setVisibility(View.GONE);
            }else{
                viewHolder.tv_word.setVisibility(View.VISIBLE);
            }
        }
        return convertView;
    }

    class ViewHolder{
        @butterknife.Bind(R.id.tv_word)
        TextView tv_word;
        @butterknife.Bind(R.id.tv_name)
        TextView  tv_name;
        public ViewHolder(View view) {
            ButterKnife.bind(this,view);
        }
    }
}
