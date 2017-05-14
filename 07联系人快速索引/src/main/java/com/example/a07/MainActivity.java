package com.example.a07;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import butterknife.ButterKnife;

public class MainActivity extends Activity {
    @butterknife.Bind(R.id.lv_main_contact)
    ListView lv_main_contact;

    @butterknife.Bind(R.id.tv_main_word)
    TextView tv_main_word;

    @butterknife.Bind(R.id.iv_main_words)
    IndexView iv_main_words;

    private Handler handler = new Handler();

    private ArrayList<Person> persons;

    private MyAdapter myAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initData();
        myAdapter = new MyAdapter(this,persons);
        lv_main_contact.setAdapter(myAdapter);
        //设置页面监听
        iv_main_words.setOnIndexChangeListener(new IndexView.OnIndexChangeListener() {
            @Override
            public void onIndexChange(String word) {
                updateWord(word);
                updateListView(word);
            }
        });

    }

    private void updateWord(String word) {
        tv_main_word.setVisibility(View.VISIBLE);
        tv_main_word.setText(word);

        //把所有消息移除
        handler.removeCallbacksAndMessages(null);
        //发消息2秒钟后自动消失
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                tv_main_word.setVisibility(View.GONE);
            }
        }, 2000);
    }
    /**
     * 在按下和移动时更新列表
     */
    private void updateListView(String word){
        for (int i = 0 ; i < persons.size() ;i++){
            //查找每个名字的汉字首字母
            String preWord = persons.get(i).getPinyin().substring(0,1);
            //判断是否相同
            if(preWord.equals(word)){
               lv_main_contact.setSelection(i);
                return;
            }
        }
    }
    /**
     * 初始化数据
     */
    private void initData() {
        persons = new ArrayList<>();
        persons.add(new Person("张晓飞"));
        persons.add(new Person("杨光福"));
        persons.add(new Person("胡继群"));
        persons.add(new Person("刘畅"));
        persons.add(new Person("钟泽兴"));
        persons.add(new Person("尹革新"));
        persons.add(new Person("安传鑫"));
        persons.add(new Person("张骞壬"));
        persons.add(new Person("温松"));
        persons.add(new Person("李凤秋"));
        persons.add(new Person("刘甫"));
        persons.add(new Person("娄全超"));
        persons.add(new Person("张猛"));
        persons.add(new Person("王英杰"));
        persons.add(new Person("李振南"));
        persons.add(new Person("孙仁政"));
        persons.add(new Person("唐春雷"));
        persons.add(new Person("牛鹏伟"));
        persons.add(new Person("姜宇航"));
        persons.add(new Person("刘挺"));
        persons.add(new Person("张洪瑞"));
        persons.add(new Person("张建忠"));
        persons.add(new Person("侯亚帅"));
        persons.add(new Person("刘帅"));
        persons.add(new Person("乔竞飞"));
        persons.add(new Person("徐雨健"));
        persons.add(new Person("吴亮"));
        persons.add(new Person("王兆霖"));
        persons.add(new Person("阿三"));
        //排序
        Collections.sort(persons, new Comparator<Person>() {
            @Override
            public int compare(Person lhs, Person rhs) {
                return lhs.getPinyin().compareTo(rhs.getPinyin());
            }
        });
    }
}
