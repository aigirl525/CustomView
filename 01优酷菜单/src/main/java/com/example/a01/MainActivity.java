package com.example.a01;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import butterknife.OnClick;

public class MainActivity extends Activity {
    @butterknife.Bind(R.id.icon_home)
    ImageView icon_home;
    @butterknife.Bind(R.id.icon_menu)
    ImageView icon_menu;
    @butterknife.Bind(R.id.level1)
    RelativeLayout level1;
    @butterknife.Bind(R.id.level2)
    RelativeLayout level2;
    @butterknife.Bind(R.id.level3)
    RelativeLayout level3;
    /**
     * 是否显示第三圆环
     * true:显示
     * false:隐藏
     */
    private boolean isShowLevel3 = true;
    /**
     * 是否显示第二圆环
     * true:显示
     * false:隐藏
     */
    private boolean isShowLevel2  = true;
    /**
     * 是否显示第一圆环
     * true:显示
     * false:隐藏
     */
    private boolean isShowLevel1  = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        butterknife.ButterKnife.bind(this);

    }
    @OnClick(R.id.icon_home)
    void icon_homeClick(View view ){
        //如果三级菜单和二级菜单是显示，都设置隐藏
        if(isShowLevel2){
            //隐藏二级菜单
            isShowLevel2 = false;
             Tools.hideView(level2);

            if(isShowLevel3){
                //隐藏三级菜单
                isShowLevel3 = false;
                Tools.hideView(level3,200);
            }
        }else{
            //如果都是隐藏的，二级菜单显示
            //显示二级菜单
            isShowLevel2 = true;
            Tools.showView(level2);
        }
    }
    @OnClick(R.id.icon_menu)
    void icon_menuClick(View view ){
        if (isShowLevel3){
            //隐藏
            isShowLevel3 = false;
            Tools.hideView(level3);
        }else{
            //显示
            isShowLevel3 = true;
            Tools.showView(level3);
        }

    }
    @OnClick(R.id.level1)
    void level1Click(View view ){
        Toast.makeText(MainActivity.this,"level1",Toast.LENGTH_SHORT).show();

    }
    @OnClick(R.id.level2)
    void level2Click(View view ){
        Toast.makeText(MainActivity.this,"level2",Toast.LENGTH_SHORT).show();
    }
    @OnClick(R.id.level3)
    void level3Click(View view ){
        Toast.makeText(MainActivity.this,"level3",Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU){
            //如果一级、二级、三级菜单都显示的就全部隐藏
            if(isShowLevel1){
                //隐藏一级菜单
                isShowLevel1 = false;
                Tools.hideView(level1);
                if(isShowLevel2){
                    //隐藏二级菜单
                    isShowLevel2 = false;
                    Tools.hideView(level2,200);
                    if(isShowLevel3){
                        //隐藏三级菜单
                        isShowLevel3 = false;
                        Tools.hideView(level3,400);
                    }
                }
            }else{
                //如果一级，二级菜单隐藏，就显示
                //显示一级菜单
                isShowLevel1 = true;
                Tools.showView(level1);

                //显示二级菜单
                isShowLevel2 = true;
                Tools.showView(level2,200);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
