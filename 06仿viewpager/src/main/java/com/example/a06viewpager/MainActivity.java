package com.example.a06viewpager;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class MainActivity extends Activity {
     @butterknife.Bind(R.id.msv)
    MyViewPager msv;
    @butterknife.Bind(R.id.radioGroup)
    RadioGroup radioGroup;
    private int[] ids = {R.drawable.a1,R.drawable.a2,R.drawable.a3,
            R.drawable.a4,R.drawable.a5,R.drawable.a6};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        butterknife.ButterKnife.bind(this);
        //添加view到自定义的MyScrollView类里面
        for (int i = 0 ; i < ids.length ;i++){
            ImageView image = new ImageView(this);
            image.setBackgroundResource(ids[i]);
            msv.addView(image);
        }
        //添加页面
        View view = View.inflate(this,R.layout.test,null);
        msv.addView(view,2);

        //遍历MyScrollView有多少个子View，就给RadioGroup添加多少个RadioButton
        for (int i = 0 ; i < msv.getChildCount() ; i++){
            RadioButton button = new RadioButton(this);
            button.setId(i);
            radioGroup.addView(button);
            if (i == 0){
                button.setChecked(true);
            }
        }
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            //刚好RadioButton的id(checkedId)和MyScrollView子View的下标一样
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                msv.moveTo(checkedId);
            }
        });
        msv.setChangeListener(new MyViewPager.PageChangeListener() {
            @Override
            public void moveTo(int curIndex) {
                radioGroup.check(curIndex);
            }
        });
    }
}
