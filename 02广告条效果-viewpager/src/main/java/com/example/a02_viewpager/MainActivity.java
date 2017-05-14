package com.example.a02_viewpager;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    private static final String TAG = MainActivity.class.getSimpleName();
    @butterknife.Bind(R.id.viewpager)
    ViewPager viewpager;
    @butterknife.Bind(R.id.ll_point_group)
    LinearLayout ll_point_group;
    @butterknife.Bind(R.id.tv_desc)
    TextView tv_desc;
    private List<ImageView> imageList;

    /**
     * 上次的位置
     */
    private int lastPointIndex;
    //广告条自动翻页
    //1. 定时器 timer + Handler
    //2. while true 循环 sleep + Handler
    //3. ClockManager + Handler
    //4. Handler
    /**
     * 是否已经滚动
     */
    private boolean isDragging = false;
    /**
     * 是否自定滑动进行中
     */
    private boolean isRunning = false;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
             viewpager.setCurrentItem(viewpager.getCurrentItem() + 1);
            if (isRunning){
                handler.sendEmptyMessageDelayed(0,4000);
            }
        }
    } ;
    //图片资源
    private final int[] imageIds = {
            R.drawable.a,
            R.drawable.b,
            R.drawable.c,
            R.drawable.d,
            R.drawable.e,
    };
    //图片标题集合
    private final String[] imageDescriptions = {
            "11111111111111",
            "22222222222222",
            "33333333333333",
            "44444444444444",
            "55555555555555"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        butterknife.ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        isRunning = true;
        handler.sendEmptyMessageDelayed(0,2000);
        //准备数据
        imageList = new ArrayList<ImageView>();
        for(int i = 0 ; i < imageIds.length ; i++){
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(imageIds[i]);
            imageList.add(imageView);

            //添加指示点
            ImageView point = new ImageView(this);
            //设置指示点的间距

            //在代码中设置的都是像素
            //把8px当成是dp--->px
            int width = DensityUtil.dip2px(this,8);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width,width);
            point.setBackgroundResource(R.drawable.point_selector);

            //默认情况下，第一个小点enable为true
            if(i == 0){
                //显示红色
                point.setEnabled(true);
            }else{
                //显示灰色
                point.setEnabled(false);
                params.leftMargin = width;
            }
            point.setLayoutParams(params);
            ll_point_group.addView(point);
        }
        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(imageList,handler);
        viewpager.setAdapter(myPagerAdapter);
        //解决左滑没有效果问题
        //要求刚好是imageView.size()的整数倍
        int item = Integer.MAX_VALUE/2 - Integer.MAX_VALUE/2 % imageList.size();
        //让ViewPager跳转到指定的位置，应该保证是imageView.size()的整数倍
        viewpager.setCurrentItem(item);
        tv_desc.setText(imageDescriptions[0]);
        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            /**
             * 当页面滑动了调用该方法
             * @param position
             * @param positionOffset
             * @param positionOffsetPixels
             */
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            /**
             * 当页面被选择了回调
             * @param position 当前被显示的页面的位置：从0开始
             */
            @Override
            public void onPageSelected(int position) {
                int myIndex = position % imageList.size();
                tv_desc.setText(imageDescriptions[myIndex]);
                //设置指示点的状态enable的状态为true或者为false
                ll_point_group.getChildAt(lastPointIndex).setEnabled(false);
                ll_point_group.getChildAt(myIndex).setEnabled(true);
                lastPointIndex = myIndex;
            }

            /**
             * 当页面状态发生变化的时候调用
             * 静止--滑动
             * 滑动--静止
             * @param state
             */
            @Override
            public void onPageScrollStateChanged(int state) {
                //解决手指正在拖拽的时候不自动滑动
                if(state == ViewPager.SCROLL_STATE_DRAGGING){
                    //正在拖动页面时  正在滑动   pager处于正在拖拽中
                    isDragging = true;
                    handler.removeCallbacksAndMessages(null);
                }else if(state == ViewPager.SCROLL_STATE_SETTLING){
                    //pager正在自动沉降，相当于松手后，pager恢复到一个完整pager的过程
                }else if(state == ViewPager.SCROLL_STATE_IDLE && isDragging){
                    Log.e(TAG,"SCROLL_STATE_IDLE------------");
                    // 未拖动页面时    空闲状态  pager处于空闲状态
                    isDragging = false;
                    handler.removeCallbacksAndMessages(null);
                    handler.sendEmptyMessageDelayed(0,4000);
                }
            }
        });
    }
}
