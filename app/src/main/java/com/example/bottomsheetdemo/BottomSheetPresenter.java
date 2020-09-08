package com.example.bottomsheetdemo;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.example.bottomsheetdemo.widgets.BottomSheetBehavior2;

public class BottomSheetPresenter {

    private View mBottomSheetView;
    private View mOverlayView;
    private BottomSheetBehavior2 mBehavior;
    private int mBottomSheetViewHeight = 0;


    public BottomSheetPresenter(View bottomSheet, View overlayView) {
        mBottomSheetView = bottomSheet;
        mOverlayView = overlayView;
        initBottomSheet();
    }

    private void initBottomSheet() {
        mBehavior = BottomSheetBehavior2.from(mBottomSheetView);
        mOverlayView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if (mBottomSheetViewHeight == 0) {
                    mBottomSheetViewHeight = bottom - top;
                    ViewGroup.LayoutParams params = mBottomSheetView.getLayoutParams();
                    params.height = mBottomSheetViewHeight;
                    mBottomSheetView.setLayoutParams(params);
                }
            }
        });

        mBehavior.setBottomSheetCallback(new BottomSheetBehavior2.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, @BottomSheetBehavior2.State int newState) {
                String state = "null";
                switch (newState) {
                    case 1:
                        state = "STATE_DRAGGING";
                        break;
                    case 2:
                        state = "STATE_SETTLING";
                        break;
                    case 3:
                        state = "STATE_EXPANDED";
                        break;
                    case 4:
                        state = "STATE_COLLAPSED";

                        break;
                    case 5:
                        state = "STATE_HIDDEN";
                        break;
                }
                Log.d("BottomSheet", "BottomSheet state: " + state);
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
    }

    public void updateTop(int top){
        if(null != mBehavior){
            mBehavior.updateTop(top);
        }
    }

    public void expandBehavior() {
        if (mBehavior != null && mBehavior.getState() != BottomSheetBehavior2.STATE_EXPANDED) {
            mBehavior.setState(BottomSheetBehavior2.STATE_EXPANDED);
        }
    }

    public void collapsedBehavior() {
        if (mBehavior != null && mBehavior.getState() != BottomSheetBehavior2.STATE_COLLAPSED) {
            mBehavior.setState(BottomSheetBehavior2.STATE_COLLAPSED);
        }
    }

}
