package com.example.a06viewpager;


import android.content.Context;
import android.os.SystemClock;
import android.provider.Settings;

/**
 * Created by yinrong on 2016/12/19.
 */

public class MyScroller{

    private Context context;

    /**
     * 基准点的X的坐标
     */
    private float startX;

    /**
     *  基准点的Y的坐标
     */
    private float startY;
    /**
     * x方向要移动的距离
     */
    private int distanceX;
    /**
     * y方向要移动的距离
     */
    private int distanceY;
    /**
     * 开始执行动画的时间
     */
    private long startTime;
    /**
     * 用于判断动画是否结束
     */
    private boolean isFinish;
    /**
     * 总共运动的时间
     */
    private long totalTime = 500;
    /**
     * 当前要移动的X方向距离
     */
    private float curX;
    public MyScroller(Context context) {
        this.context = context;
    }
/**
 * 开始执行动画 开始执行动画 开始执行动画
 * @param startX 基准点的 x坐标
 * @param startY 基准点的 y坐标
 * @param distanceX  x方向要移动的距离
 * @param distanceY  y方向要移动的距离
 */
    public void startScroll(int startX, int startY, int distanceX, int distanceY) {
        this.startX = startX;
        this.startY = startY;
        this.distanceX = distanceX;
        this.distanceY = distanceY;
        //开始执行动画的时间
        startTime = SystemClock.uptimeMillis();
        this.isFinish = false;

    }

    /**
     * 计算滑动的偏移量
     * @return true ，还在滑动
     *          false 说明已经结束
     */
    public boolean computeScrollOffset() {
        if(isFinish){
            return false;
        }
        long endTime = SystemClock.uptimeMillis();
        //花了多少时间
        long passTime = endTime - startTime;
        if (passTime < totalTime){
            //运动还在进行
            //x方向滑动的距离 = 距离/时间
            long velocityX = distanceX / totalTime;
            //当前移动的距离
            this.curX = startX + passTime * distanceX / totalTime;
        }else{
            //动画结束，时间到了 滑动该停止
            curX = startX + distanceX;
            isFinish = true;
        }
        return  true;
    }

    public float getCurX() {
        return curX;
    }
    public void setCurX(float curX) {
        this.curX = curX;
    }
}
