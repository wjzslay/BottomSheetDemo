package com.example.bottomsheetdemo.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


public class FlexibleSwipeRefreshLayout extends SwipeRefreshLayout {

    private SwipeRefreshListener mSwipeListener;

    public FlexibleSwipeRefreshLayout(Context context) {
        super(context);
    }

    public FlexibleSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setSwipeRefreshListener(SwipeRefreshListener listener) {
        this.mSwipeListener = listener;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if(null != mSwipeListener && !mSwipeListener.canRefresh()){
            return false;
        }

        boolean result = false;
        try {
            result = super.onInterceptTouchEvent(ev);
        } catch (IllegalArgumentException  e) {
            Log.d("Exception", e.toString());
        }
        return result;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if(null != mSwipeListener && !mSwipeListener.canRefresh()){
            return false;
        }

        boolean result = false;
        try {
            result = super.onTouchEvent(ev);
        } catch (IllegalArgumentException  e) {
            Log.d("Exception", e.toString());
        }
        return result;
    }

    public interface SwipeRefreshListener {
        boolean canRefresh();
    }
}
