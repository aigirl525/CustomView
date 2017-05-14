package com.example.a04;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by yinrong on 2016/12/19.
 */

/**
 * 自定义按钮
 */
/**
 * 一个 View 从创建到显示屏幕上的主要步骤： 从创建到显示屏幕上的主要步骤： 从创建到显示屏幕上的主要步骤： 从创建到显示屏幕上的主要步骤： 从创建到显示屏幕上的主要步骤： 从创建到显示屏幕上的主要步骤： 从创建到显示屏幕上的主要步骤： 从创建到显示屏幕上的主要步骤：
 * 1. 执行 view 构造方法，创建对象 构造方法，创建对象 构造方法，创建对象 构造方法，创建对象
 * 2. 测量 view 大小
 * onMeasure(* onMeasure(* onMeasure(* onMeasure(* onMeasure(* onMeasure(* onMeasure(* onMeasure(* onMeasure(* onMeasure(* onMeasure(* onMeasure(* onMeasure(int ,int ); 来完成测量动作 来完成测量动作 来完成测量动作
 * 3. 指定 view 的位置 的位置 ,子View 只有建议权，父 只有建议权，父 只有建议权，父 View 才有决定权； 才有决定权； 才有决定权；
 * onLayout(boolean, * onLayout(boolean, intintint,int ,int ,intintint);
 —————————————————————————————
 * 这个方法一般用不着，如果自定义 这个方法一般用不着，如果自定义 这个方法一般用不着，如果自定义 这个方法一般用不着，如果自定义 这个方法一般用不着，如果自定义 这个方法一般用不着，如果自定义 这个方法一般用不着，如果自定义 ViewGoup 才用到
 * 4. 绘制 view 的内容 的内容
 * onDraw(canvas); * onDraw(canvas);
 *
 */
public class MyToggleButton extends View implements View.OnClickListener{
    private Paint paint;
    private Bitmap backGroundBitmap;
    private Bitmap slideBitmap;
    private Context context;
    /**
     * 距离左边的距离
     */
    private float slideLeft;
    /**
     * 判断当前开关状态
     * true为开
     * false为关
     */
    private boolean curState = false;
    /**
     * 第一次按下的x坐标
     */
    private int startX = 0 ;
    int maxLeft;

    /**
     * 最初的历史位置
     */
    int lastX = 0;
    /**
     * 点击事件是否可用
     * true 可用
     * false不可用
     */
    boolean isClickEnable;
    /**
     * 测量
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
       // super.onMeasure(widthMeasureSpec, heightMeasureSpec);
       //设置当前view的测量大小
        // setMeasuredDimension(100,100);
        setMeasuredDimension(backGroundBitmap.getWidth(),backGroundBitmap.getHeight());
    }
    /**
     * 绘制
     */
    @Override
    protected void onDraw(Canvas canvas) {
        //super.onDraw(canvas);
        //绘制颜色，可以理解成背景颜色
        //canvas.drawColor(Color.RED);
        //绘制圆形
        //canvas.drawCircle(50,50,20,paint);
        canvas.drawBitmap(backGroundBitmap,0,0,paint);
        canvas.drawBitmap(slideBitmap,slideLeft,0,paint);
    }

    private void init(Context context){
        paint = new Paint();
        paint.setColor(Color.GREEN);
        //设置抗锯齿，让边缘光滑，一般都会设置
        paint.setAntiAlias(true);

        //初始化图片 -  从资源文件中解析成Bitmap对象
        slideBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.slide_button );
        backGroundBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.switch_background);
        //滑动图片距离左边的距离
        maxLeft =  backGroundBitmap.getWidth() - slideBitmap.getWidth();
        setOnClickListener(this);
    }
    /**
     * 在代码中new实例化时调用
     * @param context
     */
    public MyToggleButton(Context context) {
        super(context);
        init(context);
    }

    /**
     * 在布局文件中声明view的时候，该方法由系统调用
     * @param context
     * @param attrs
     */
    public MyToggleButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    /**
     * 增加一个默认显示样式时候使用
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public MyToggleButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @Override
    public void onClick(View v) {
        if(isClickEnable){
            curState = !curState;
            flushState();
        }
    }

    /**
     * 刷新状态
     */
    private void flushState() {
      //设置距离左边的距离
        if (curState){
            slideLeft = backGroundBitmap.getWidth() - slideBitmap.getWidth();
        }else{
            slideLeft = 0;
        }
        /**
         * 刷新View，会导致当前View的onDraw方法执行
         */
        flushView();
    }

    /**
     * 实现滑动效果
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
         super.onTouchEvent(event);
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN://按下
                //1.记录第一次按下的坐标
                lastX =  startX = (int)event.getRawX();
                isClickEnable = true;
                break;
            case MotionEvent.ACTION_MOVE://滑动
                //2.来到新的坐标
                int newX = (int)event.getRawX();
                //3.计算偏移量
                int dX = newX - startX;
                slideLeft += dX;
                //4.更新UI - onDraw方法即可 -- invalidate（）;
                if (Math.abs(newX - lastX) > 5){
                    isClickEnable = false;
                }
                flushView();
                //5.重新记录坐标
                startX = (int)event.getRawX();
                break;
            case MotionEvent.ACTION_UP://离开
                /**
                 * 当UP 事件发生的时候，由按钮左边距离 事件发生的时候，由按钮左边距离 事件发生的时候，由按钮左边距离 事件发生的时候，由按钮左边距离 事件发生的时候，由按钮左边距离 事件发生的时候，由按钮左边距离 事件发生的时候，由按钮左边距离 事件发生的时候，由按钮左边距离 (btn_left)(btn_left)(btn_left)(btn_left)(btn_left)(btn_left)(btn_left)(btn_left)(btn_left)(btn_left)确定 View 的状态； 的状态；
                 * 当btn_left >= maxLeft/2 >= maxLeft/2 >= maxLeft/2 设置为开状态 设置为开状态 设置为开状态
                 * 当btn_left < maxLeft/2 btn_left < maxLeft/2 btn_left < maxLeft/2 btn_left < maxLeft/2 设置为 设置为 关闭状态
                 */
                if (!isClickEnable){
                    if (slideLeft >= maxLeft/2){
                        curState = true;
                    }else{
                        curState = false;
                    }
                    flushState();
                }
                break;
            default:break;
        }
        return true;
    }

    /**
     * 刷新View的状态，并且纠正非法滑动
     */
    private void flushView() {
        if(slideLeft < 0 ){
            slideLeft = 0;
        }
        if (slideLeft > maxLeft){
            slideLeft = maxLeft;
        }
        //屏蔽非法滑动
        invalidate();

    }
}
