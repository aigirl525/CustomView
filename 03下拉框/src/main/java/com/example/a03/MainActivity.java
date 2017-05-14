package com.example.a03;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.OnClick;

public class MainActivity extends Activity {
    @butterknife.Bind(R.id.et_input)
    EditText et_input;
    @butterknife.Bind(R.id.down_arrow)
    ImageView down_arrow;

    private PopupWindow popupWindow;

    private ArrayList<String> msgs;

    private ListView listview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        butterknife.ButterKnife.bind(this);

        //准备数据
        msgs = new ArrayList<String>();
        for (int i = 0;i <= 500 ; i++){
            msgs.add(i+"--aaaaaaaaaa--"+i);
        }
        listview = new ListView(this);
        //解决按下变白的问题
        listview.setSelector(android.R.color.transparent);
        listview.setAdapter(new MyAdapter(this,msgs));
        //设置选择某一条，并且显示在输入框中
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                et_input.setText(msgs.get(position));
                popupWindow.dismiss();
            }
        });

    }

    @OnClick(R.id.down_arrow)
    void cbButterKnife(View view ){
        if (popupWindow == null){
            popupWindow = new PopupWindow(MainActivity.this);
            popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
            popupWindow.setWidth(et_input.getWidth());
            popupWindow.setHeight(DensityUtil.dip2px(this,200));
            popupWindow.setContentView(listview);
            popupWindow.setFocusable(true);//设置焦点，否则点击不起作用
            popupWindow.setOutsideTouchable(true);//解决点击popupwindow外部，无法消掉问题
        }
        popupWindow.showAsDropDown(et_input,0,0);

    }
}
