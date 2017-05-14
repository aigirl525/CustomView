package com.example.a08;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class MainActivity extends Activity {
@butterknife.Bind(R.id.listview)
    ListView listview;
    private List<MyBean> myBeanList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        myBeanList = new ArrayList<MyBean>();
        for (int i = 0 ; i < 100 ; i++){
            myBeanList.add(new MyBean("Content"+i));
        }
        listview.setAdapter(new MyAdapter(this,myBeanList));
    }
}
