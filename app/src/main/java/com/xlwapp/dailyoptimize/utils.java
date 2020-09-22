package com.xlwapp.dailyoptimize;

import android.app.Activity;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class utils {
    private static  final  String TAG ="TouchUtil";
    private static View view;
    private static float x;
    private static float y;

    /**
     * 模仿手指点击控件事件
     * @param view  控件
     * @param x     相对控件的X坐标
     * @param y     相对控件的Y坐标
     */
    private static void simulateClick(View view, float x, float y) {
        utils.view = view;
        utils.x = x;
        utils.y = y;
        long downTime = SystemClock.uptimeMillis();
        final MotionEvent downEvent = MotionEvent.obtain(downTime, downTime,MotionEvent.ACTION_DOWN, x, y, 0);
        downTime += 1000;
        final MotionEvent upEvent = MotionEvent.obtain(downTime, downTime,MotionEvent.ACTION_UP, x, y, 0);
        view.onTouchEvent(downEvent);
        view.onTouchEvent(upEvent);
        downEvent.recycle();
        upEvent.recycle();
    }

    /**
     * 模仿手机点击屏幕事件
     * @param x X坐标
     * @param y Y坐标
     * @param activity 传进去的活动对象
     */
    public static void setFingerClick(int x, int y, Activity activity){
        MotionEvent evenDownt = MotionEvent.obtain(System.currentTimeMillis(),
                System.currentTimeMillis() + 100, MotionEvent.ACTION_DOWN, x, y, 0);
        activity.dispatchTouchEvent(evenDownt);
        MotionEvent eventUp = MotionEvent.obtain(System.currentTimeMillis(),
                System.currentTimeMillis() + 100, MotionEvent.ACTION_UP, x, y, 0);
        activity.dispatchTouchEvent(eventUp);
        evenDownt.recycle();
        eventUp.recycle();
        Log.d(TAG, "setFingerClick: ");
    }

    /**
     * 模拟向下滑动事件
     * @param distance 滑动的距离
     * @param activity 传进去的活动对象
     */
    public static void setMoveToBottom(int distance,Activity activity){
        activity.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(),
                MotionEvent.ACTION_DOWN, 400, 500, 0));
        activity.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(),
                MotionEvent.ACTION_MOVE, 400, 500-distance/2, 0));
        activity.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(),
                MotionEvent.ACTION_MOVE, 400, 500-distance, 0));
        activity.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(),
                MotionEvent.ACTION_UP, 400, 500-distance, 0));
        Log.d(TAG, "setMoveToBottom: ");
    }

    /**
     * 模拟向上滑动事件
     * @param distance 滑动的距离
     * @param activity 传进去的活动对象
     */
    public static void setMoveToTop(int distance,Activity activity){
        activity.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(),
                MotionEvent.ACTION_DOWN, 400, 500, 0));
        activity.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(),
                MotionEvent.ACTION_MOVE, 400, 500+distance/2, 0));
        activity.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(),
                MotionEvent.ACTION_MOVE, 400, 500+distance, 0));
        activity.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(),
                MotionEvent.ACTION_UP, 400, 500+distance, 0));
        Log.d(TAG, "setMoveToTop: ");
    }

}
