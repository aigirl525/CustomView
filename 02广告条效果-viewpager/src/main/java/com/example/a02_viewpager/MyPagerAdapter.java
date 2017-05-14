package com.example.a02_viewpager;

import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by yinrong on 2016/12/16.
 */

public class MyPagerAdapter extends PagerAdapter {
    private List<ImageView> mImageList;
    private Handler mHandler;
    public MyPagerAdapter(List<ImageView> imageList,Handler handler) {
        mImageList = imageList;
        mHandler = handler;
    }

    /**
     * 销毁指定位置上的view或者object
     * @param container
     * @param position
     * @param object
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
      container.removeView((View)object);
       // super.destroyItem(container, position, object);
    }

    /**
     * 给ViewPager添加指定的view
     * @param container 就是ViewPager，其实就是容器
     * @param position  具体页面或者图片的位置
     * @return
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
       //返回的值，不一定是View，也可以是和View有关系的任意的Object
        // return super.instantiateItem(container, position);
        View view = mImageList.get(position%mImageList.size());
        container.addView(view);
        //解决手指按下的时候不自动滑动
        view.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN://手指按下
                        mHandler.removeCallbacksAndMessages(null);
                        break;
                    case MotionEvent.ACTION_MOVE://手指在这个控件上移动
                        break;
                    case MotionEvent.ACTION_CANCEL:
                      /*  mHandler.removeCallbacksAndMessages(null);
                        mHandler.sendEmptyMessageDelayed(0,4000);*/
                        break;
                    case MotionEvent.ACTION_UP://手指离开
                       mHandler.removeCallbacksAndMessages(null);
                        mHandler.sendEmptyMessageDelayed(0,4000);
                        break;
                }
                return false;
            }
        });
        view.setTag(position);
        view.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                int position = (int)v.getTag()%mImageList.size();
               // String text = imageDescriptions[position];
            }
        });
        return view;
    }

    @Override
    public int getCount() {
        //页面或者图片的总数
        // return mImageList.size();
        //设置可以循环滑动
        return Integer.MAX_VALUE;
    }

    /**
     * 判断某个page和object的关系
     * @param view
     * @param object
     * @return
     */
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
