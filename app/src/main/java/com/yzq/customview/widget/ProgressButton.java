package com.yzq.customview.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

public class ProgressButton extends android.support.v7.widget.AppCompatButton {



    public ProgressButton(Context context) {
        this(context, null);
    }

    public ProgressButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
