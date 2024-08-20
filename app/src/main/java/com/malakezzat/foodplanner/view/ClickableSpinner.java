package com.malakezzat.foodplanner.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Spinner;

public class ClickableSpinner extends androidx.appcompat.widget.AppCompatSpinner {
    private boolean isTouched = false;

    public ClickableSpinner(Context context) {
        super(context);
    }

    public ClickableSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ClickableSpinner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        isTouched = true;
        return super.onTouchEvent(event);
    }

    @Override
    public void setSelection(int position) {
        boolean sameSelected = position == getSelectedItemPosition();
        super.setSelection(position);
        if (sameSelected && isTouched) {
            getOnItemSelectedListener().onItemSelected(this, getSelectedView(), position, getSelectedItemId());
        }
        isTouched = false;
    }
}