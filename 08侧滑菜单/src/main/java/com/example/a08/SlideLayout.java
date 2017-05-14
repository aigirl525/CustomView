package com.example.a08;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Scroller;

/**
 * Created by hasee on 2017/1/6.
 * 1.正常显示item代码实现
 * 1.1>.得到子View对象（ContentView,MenuView）--->onFinishInflate
 * 1.2>.得到子View的宽和高--->onMeasure()
 * 1.3>.对item视图进行重新布局--->onLayout
 */
public class SlideLayout extends FrameLayout {
    private View contentView,menuView;
    private int contentWidth,menuWidth,viewHeight;
    private Scroller scroller;

    public SlideLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        scroller = new Scroller(context);
    }

    /**
     * 当布局文件加载完成后回调这个方法
     * 1.1>.得到子View对象（ContentView,MenuView）
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        contentView = getChildAt(0);
        menuView = getChildAt(1);
    }

    /**
     * 1.2>.得到子View的宽和高--->onMeasure()
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        contentWidth = contentView.getMeasuredWidth();
        //contentWidth = getMeasuredWidth(); //可以
        menuWidth = menuView.getMeasuredWidth();
        //menuWidth = getMeasuredWidth(); //不可以
        viewHeight = getMeasuredHeight();
    }

    /**
     * 1.3>.对item视图进行重新布局--->onLayout
     * @param changed
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        menuView.layout(contentWidth,0,contentWidth+menuWidth,viewHeight);
    }

    private float startX;
    private float startY;
    private float downX;//只赋值一次
    private float downY;

    /**
     * 解决item滑动后不能自动打开和关闭
     * 原因分析
     * 事件被ListView拦截，也就是说，当前ListView与子item的冲突
     * 反拦截
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                //通过收拾拖动或者关闭menu
                //2.1.记录起始坐标
                downX = startX = event.getX();
                downY = startY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float endX = event.getX();
                float endY = event.getY();

                //2.2.计算偏移量
                float distanceX = endX - startX;

                int toSrollX = (int)(getScrollX() - distanceX);

                System.out.println(toSrollX);

                //屏蔽非法值
                if(toSrollX < 0 ){
                    toSrollX = 0 ;
                }else if(toSrollX > menuWidth){
                    toSrollX = menuWidth;
                }
                // scrollTo(toSrollX,0);//也可以
                scrollTo(toSrollX,getScrollY());

                startX = event.getX();
                startY = event.getY();
                //在X轴和Y轴滑动的距离
                float DX = Math.abs(endX - downX);
                float DY = Math.abs(endY - downY);
                if (DX > DY && DX > 8){
                    //水平方向滑动
                    //响应侧滑
                    //反拦截 - 事件给SlideLayout
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                break;
            case MotionEvent.ACTION_UP:
                //2.3.当up的时候，计算总的偏移量，判断是平滑的关闭或者打开
                int totalScrollX = getScrollX();

                if (totalScrollX < menuWidth/2){
                    System.out.println("totalScrollX < menuWidth/2");
                    closeMenu();
                }else{
                    System.out.println("totalScrollX > menuWidth/2");
                    openMenu();
                }
                break;
        }
        return true;
    }
    //-->menuwidth
    private void openMenu() {
        scroller.startScroll(getScrollX(),getScrollY(),menuWidth-getScrollX(),getScrollY());
        invalidate();//会导致执行comuteScroll
        if (onStateChangeListener != null){
            onStateChangeListener.onOpen(this);
        }
    }
    //-->0
    public void closeMenu() {
        scroller.startScroll(getScrollX(),getScrollY(),0-getScrollX(),getScrollY());
        invalidate();//强行重绘制
        if (onStateChangeListener != null){
            onStateChangeListener.onClose(this);
        }
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (scroller.computeScrollOffset()){
            scrollTo(scroller.getCurrX(),scroller.getCurrY());
            invalidate();
        }
    }
    /**
     * 内容设置点击时间时不能滑动item
     * 分析原因
     * 时间被点击TextView时间消费
     * 解决办法，在item中拦截
     */
    /**
     * true ：拦截孩子的事件，但会执行当前控件的onTouchEvent()方法
     * false: 不拦截孩子的事件，时间会继续传递
     * @param event
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        boolean intercept = false;
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                //1.记录起始坐标
                downX = startX = event.getX();
                if (onStateChangeListener != null){
                    onStateChangeListener.onDown(this);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                //2.记录结束值
                float endX = event.getX();
                float endY = event.getY();

                //3.计算偏移量
                float distanceX = endX - startX;

                startX = event.getX();
                //在X轴上滑动的距离
                float DX = Math.abs(endX - downX);
                if (DX > 8){intercept = true;}
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return intercept;
    }
    /**
     * 限制只能打开一个item
     */
    public interface  OnStateChangeListener{
        /**
         * 当item被打开的时候回调
         */
        public void onOpen(SlideLayout layout);

        /**
         * 当item按下的时候被回调
         * @param layout
         */
        public void onDown(SlideLayout layout);

        /**
         * 当item关闭的时候回调
         * @param layout
         */
        public void onClose(SlideLayout layout);
    }
   private OnStateChangeListener onStateChangeListener;

    public OnStateChangeListener getOnStateChangeListener() {
        return onStateChangeListener;
    }

    public void setOnStateChangeListener(OnStateChangeListener onStateChangeListener) {
        this.onStateChangeListener = onStateChangeListener;
    }
}
