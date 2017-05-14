package com.example.a01; /**
 * Created by yinrong on 2016/12/15.
 */

import android.animation.ObjectAnimator;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;

/**
 * 显示和隐藏指定控件
 */
public class Tools {
    public static  void hideView(ViewGroup view){
        hideView(view,0);
    }
    public static  void showView(ViewGroup view){
        showView(view,0);
    }

    public static  void hideView(ViewGroup view , int startOffset){
        /**
         * fromDegrees  从多少度开始
         * toDegress 旋转到多少度
         * pivotX 中心点x坐标
         * pivotY 中心点y坐标
         */
       /* RotateAnimation ra = new RotateAnimation(0,180,view.getWidth()/2,view.getHeight());
        //播放时长
        ra.setDuration(500);
        //延时显示
        ra.setStartOffset(startOffset);
        //停留在播放完成状态
        ra.setFillAfter(true);
        view.startAnimation(ra);
        //1.用View和ViewGroup的区别解决bug
        for(int i = 0 ; i < view.getChildCount() ; i++){
            view.getChildAt(i).setEnabled(false);
        }*/

        //2.用属性动画解决bug

        ObjectAnimator animator = ObjectAnimator.ofFloat(view,"rotation",0,180);
        animator.setDuration(500);
        //设置延迟播放
        animator.setStartDelay(startOffset);
        animator.start();
        //单独设置中心点击
        view.setPivotX(view.getWidth()/2);
        view.setPivotY(view.getHeight());

    }

    public static  void showView(ViewGroup view , int startOffset){
       /* RotateAnimation ra = new RotateAnimation(180,360,view.getWidth()/2,view.getHeight());
        ra.setDuration(500);
        ra.setStartOffset(startOffset);
        ra.setFillAfter(true);
        view.startAnimation(ra);

        for(int i = 0 ; i < view.getChildCount() ; i++){
            view.getChildAt(i).setEnabled(true);
        }*/

        ObjectAnimator animator = ObjectAnimator.ofFloat(view,"rotation",180,360);
        animator.setDuration(500);
        animator.setStartDelay(startOffset);
        animator.start();
        view.setPivotX(view.getWidth()/2);
        view.setPivotY(view.getHeight());
    }
}
