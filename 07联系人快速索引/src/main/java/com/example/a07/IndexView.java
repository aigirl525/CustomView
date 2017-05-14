package com.example.a07;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by hasee on 2017/1/3.
 * 初始化显示字母列表
 * 1.重新onMeasure();得到视图的宽和高，计算出item的高和宽
 * 2.重写onDraw():绘制所有字母（计算出字母的坐标）
 */
public class IndexView extends View {
    /**
     * 每个item的宽和高
     */
    private float itemWidth;
    private float itemHeight;
    private String[] words = {"A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z"};
    private Paint paint;
    private int touchIndex = -1;
    public IndexView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setColor(Color.WHITE);
        //设置粗体字
        paint.setTypeface(Typeface.DEFAULT);
        paint.setTextSize(30);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //得到item的宽和高
        itemWidth = getMeasuredWidth();
        itemHeight = getMeasuredHeight()/words.length;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0 ; i < words.length ; i++){
            //设置当前下标对应的字母为灰色，其它为白色
            if(i == touchIndex){
                paint.setColor(Color.GRAY);
            }else{
                paint.setColor(Color.WHITE);
            }
            //得到文本
            String word = words[i];

            //求文本的宽和高
            Rect bounds = new Rect();
            paint.getTextBounds(word,0,1,bounds);

            //计算每个文字的宽和高
            int wordWidth = bounds.width();
            int wordHeight = bounds.height();

            //求每个字母绘制的坐标
            float wordX  = itemWidth/2 - wordWidth/2;
            float wordY = itemHeight/2 + wordHeight/2 + i * itemHeight;

            canvas.drawText(word , wordX , wordY , paint);
        }
    }
    /**
    * 在按下和移动的时候操作字母变色
    * 1).在按下和移动时候，使操作的字母变色
    *  a.重写onTouchEvent(),返回true
    *  b.在down/move时，计算出操作的下标，并且在onDraw(),设置不同颜色画笔，强制绘制
    *  c.在up时，重新操作下标，强制重新绘制
    */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                float Y = event.getY();
                int index = (int)(Y/itemHeight);
                //表示不同的字母位置
                if (index != touchIndex){
                    //当前字母的下标位置
                    touchIndex = index;
                    //强制绘制
                    //会导致onDraw();需要设置不同颜色的画笔
                    invalidate();

                    //调用接口的方法
                    if(OnIndexChangeListener  != null && touchIndex < words.length){
                        OnIndexChangeListener.onIndexChange(words[touchIndex]);
                    }
                }
                break;
            case MotionEvent.ACTION_UP://离开
                //重置下标位置
                touchIndex = -1;
                invalidate();
                break;
        }
        return true;
    }
    /**
     * 在按下和移动时显示更新提示字母
     * 定义接口
     * 监听字母下标的变化
     */
    public interface OnIndexChangeListener{
        /**
         * 当字母下标位置变化的时候，回调该方法
         * @param word 字母
         */
       public void onIndexChange(String word);
    }
    private OnIndexChangeListener OnIndexChangeListener;

    public IndexView.OnIndexChangeListener getOnIndexChangeListener() {
        return OnIndexChangeListener;
    }

    /**
     * 设置监听下标位置变化
     * @param onIndexChangeListener
     */
    public void setOnIndexChangeListener(IndexView.OnIndexChangeListener onIndexChangeListener) {
        OnIndexChangeListener = onIndexChangeListener;
    }
}
