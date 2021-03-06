package com.scand.commerce.widgets;


import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class NonScrollableViewPager extends ViewPager {


    public NonScrollableViewPager(Context context) {
        super(context);
    }

    public NonScrollableViewPager(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override
    public boolean performClick() {
        super.performClick();
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.performClick();
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return false;
    }
}