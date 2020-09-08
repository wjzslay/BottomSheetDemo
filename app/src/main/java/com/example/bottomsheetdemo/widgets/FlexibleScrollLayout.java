package com.example.bottomsheetdemo.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import androidx.core.view.ViewCompat;
import androidx.customview.widget.ViewDragHelper;

public class FlexibleScrollLayout extends FrameLayout {

    private ViewDragHelper mDragHelper;

    public FlexibleScrollLayout(Context context) {
        this(context, null);
    }

    public FlexibleScrollLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlexibleScrollLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mDragHelper = ViewDragHelper.create(this, 1.0f, new FlexibleScrollCallBack());
    }

    private FlexibleScrollLayoutCallback flexibleScrollLayoutCallback;

    public void setFlexibleScrollLayoutCallback(FlexibleScrollLayoutCallback callback) {
        this.flexibleScrollLayoutCallback = callback;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean shouldIntercept = false;
        try {
            if(null == flexibleScrollLayoutCallback || !flexibleScrollLayoutCallback.forbidDrag(ev.getX(), ev.getY())){
                shouldIntercept = mDragHelper.shouldInterceptTouchEvent(ev);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return shouldIntercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        try {
            mDragHelper.processTouchEvent(event);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public void computeScroll() {
        if (mDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    private int tempTop;
    private class FlexibleScrollCallBack extends ViewDragHelper.Callback {
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return true;
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            MarginLayoutParams params = (MarginLayoutParams) releasedChild.getLayoutParams();
            mDragHelper.smoothSlideViewTo(releasedChild, params.leftMargin, params.topMargin);
            ViewCompat.postInvalidateOnAnimation(FlexibleScrollLayout.this);
            if (flexibleScrollLayoutCallback != null) {
                flexibleScrollLayoutCallback.onScrollEnd(tempTop);
            }
        }

        @Override
        public int getViewVerticalDragRange(View childView) {
            return Math.abs(childView.getHeight());
        }

        @Override
        public int clampViewPositionHorizontal(View childView, int left, int dx) {
            return childView.getLeft();
        }

        @Override
        public int clampViewPositionVertical(View childView, int top, int dy) {
            tempTop = childView.getTop();
            if (flexibleScrollLayoutCallback != null) {
                flexibleScrollLayoutCallback.onScrollY(childView.getTop());
            }
            return childView.getTop() + dy / 2;
        }
    }

    public interface FlexibleScrollLayoutCallback {
        void onScrollY(int dy);
        void onScrollEnd(int dy);
        boolean forbidDrag(float x, float y);
    }
}
