package com.example.a06viewpager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * Created by yinrong on 2016/12/19.
 */

public class MyViewPager  extends ViewGroup {

    /**
     * 用手势识别监听手指在屏幕上触摸滑动
     * 系统提供的工具
     * 手势识别器 - 解析手势
     */
    private GestureDetector detector;

    /**
     * 手指第一次按下的x的坐标
     */
    private int startX = 0;

    /**
     *  当前页面的下标
     */
    private int curIndex;

    //private MyScroller scroller;
    // 使用系统的 Scroller Scroller
    private  Scroller scroller;


    public MyViewPager(Context context) {
        super(context);
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        // scroller = new MyScroller(context);
        // 使用系统的 Scroller Scroller
        scroller = new Scroller(context);
        detector = new GestureDetector(context ,new GestureDetector.SimpleOnGestureListener(){
            /**
             * 当手指在屏幕上触摸滑动的时候回调这个方法
             */
            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                /**
                 * 移动view中内容
                 * 竖直方向设置为0
                 * x:距离x的方向的距离
                 * y:距离y的方向的距离
                 */
                scrollBy((int)distanceX,0);
                /**
                 * 移动到某个点
                 * x，y是坐标点
                 * scrollTo(int x, int y)
                 */
                return true;
            }
        });
    }

    public MyViewPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 指定当前View的位置
     * 如果当前View是viewgroup的话，应该在此方法指定子view的位置
     * @param changed
     * @param l
     * @param t
     * @param r
     * @param b
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = 0 ; i <getChildCount() ; i++){
            //获取指定下标的子view
            View child = getChildAt(i);
            //填充满只有一个页面的时候
            //child.layout(0,0,getWidth(),getHeight());
            //绘制出最后一张图片
            //支持多个图片滑动
            child.layout(i * getWidth() , 0 , getWidth() * (i+1) , getHeight());
        }
    }

    /**
     *对View进行测量
     * 如果当前View是ViewGroup的话，那么ViewGroup有义务对每个子View测量大小
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        for(int i = 0;i < getChildCount() ;i++){
            View child = getChildAt(i);
            child.measure(widthMeasureSpec,heightMeasureSpec);
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        detector.onTouchEvent(event);
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN://手指按下
                //1.手指按下第一次的X的坐标
                startX = (int)event.getX();
                break;
            case MotionEvent.ACTION_MOVE://手指在屏幕移动
                break;
            case MotionEvent.ACTION_UP://手指离开屏幕
                //2.移动到新的X坐标
                int endX = (int)event.getX();
                //3.计算
                int tempIndex = curIndex;
                int i = endX - startX;
                int i1 = getWidth()/2;
                if ((startX - endX) > getWidth()/2){
                    //移动到下一个页面
                    tempIndex++;
                }else if ((endX - startX) > getWidth()/2){
                    //移动到上一个页面
                    tempIndex--;
                }
                //4.移动指定的某个页面
                moveTo(tempIndex);
                break;
        }
        return true;
    }

    /**
     * 移动到指定页面并且屏蔽异常
     * @param tempIndex
     */
    public void moveTo(int tempIndex) {
        if (tempIndex < 0){
            tempIndex = 0;
        }
        if (tempIndex >= getChildCount() - 1){
            tempIndex = getChildCount() - 1;
        }
        curIndex = tempIndex;
        if (changeListener != null){
            changeListener.moveTo(curIndex);
        }
        //下面是瞬间移动，但是效果不好，我们需要在有些时间的移动
        //移动到指定的子View上
        //scrollTo(curIndex * getWidth(),0);

        //定位到指定的子view里
        //得到要移动的距离
        int distance = curIndex * getWidth() - getScrollX();
        //  scroller.startScroll(getScrollX(),0,distance,0);

        //点击最左边和右的 点击最左边和右的 RadioButton  会有问题并解决
        // 解决思路：使用系统自带的 ScrollerScroller；
        // 使用系统的 Scroller Scroller
        scroller.startScroll(getScrollX(),0,distance,0,Math.abs(distance));
        /**
         * invalidate();会导致computeScroll的执行，该方式是空的
         */
        invalidate();
    }

    @Override
    public void computeScroll() {
        if(scroller.computeScrollOffset()){
            //float curX = scroller.getCurX();
            // 使用系统的 Scroller Scroller
             float curX = scroller.getCurrX();
            scrollTo((int)curX,0);
            /**
             * 会导致computeScroll的执行，该方式是空的
             */
            invalidate();
        }
    }

    private PageChangeListener changeListener;

    public PageChangeListener getChangeListener() {
        return changeListener;
    }

    /**
     * 由外界实例化传进来
     * @param changeListener
     */
    public void setChangeListener(PageChangeListener changeListener) {
        this.changeListener = changeListener;
    }

    /**
     * 监听页面改变时得到下标
     */
    public interface PageChangeListener {
        /**
         * 当页面改变的时候移动到指定的下标
         * @param curIndex 指定下标
         */
        public void moveTo(int curIndex);
    }
    /**
     * 自定义ViewGroup事件中断和消费
     */
    /**
     * 第一次按下的X的坐标
     */
    private float downX;
    /**
     * 第一次按下的Y的坐标
     */
    private float downY;

    /**
     * 是否中断事件的传递，默认返回false，意思是。不中断，按正常情况，传递事件如果为true，就将事件中断
     * 直接执行自己的onTouchEvent方法
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        detector.onTouchEvent(ev);
        boolean result = false;
        //如果水平方向滑动的距离大于竖直方向滑动的距离，就是左右滑动，中断事件；否则，事件继续传递
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                //1.第一次按下坐标
                downX = ev.getX();
                downY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                //2.来到新的坐标
                float newdownX = ev.getX();
                float newdownY = ev.getY();
                //3.计算距离
                int distanceX = (int)Math.abs(newdownX - downX);
                int distanceY = (int)Math.abs(newdownY - downY);
                //distanceX>10防止抖动为1左右的情况
                if(distanceX > distanceY && distanceX > 10){
                    result = true;
                }else{
                    moveTo(curIndex);
                }

                break;
            case MotionEvent.ACTION_UP:break;
        }
        return result;

    }
}
