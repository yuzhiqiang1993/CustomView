package com.yzq.customview.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.blankj.utilcode.util.LogUtils;
import com.yzq.customview.R;

public class MyFirstView extends View {

    private int textColor = Color.BLACK;

    private int textSize = 12;

    private int inputType = 1;

    private int bgColor = Color.BLACK;


    /*
     * 该构造方法在代码中new的时候调用
     *
     * 例如 TextView tv=new TextView();
     * */
    public MyFirstView(Context context) {
        this(context, null);
    }

    /*在布局中使用时调用
    *
    * <com.yzq.customview.widget.TextView
        android:layout_width="match_parent"
        android:layout_height="wrap_content" />
    * */
    public MyFirstView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }


    /*
     *
     * 注意：只有在明确指定defStyleAttr时才会调用
     *
     * 并不是有些文章中所说的在view中使用style属性时就调用
     * */
    public MyFirstView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        /*获取自定义的属性*/
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyFirstView);

        try {
            textSize = typedArray.getDimensionPixelSize(R.styleable.MyFirstView_textSize, textSize);
            textColor = typedArray.getColor(R.styleable.MyFirstView_textColor, textColor);
            bgColor = typedArray.getColor(R.styleable.MyFirstView_bgColor, bgColor);
            inputType = typedArray.getInt(R.styleable.MyFirstView_inputType, inputType);


            LogUtils.i("textSize" + textSize);
            LogUtils.i("textColor" + textColor);
            LogUtils.i("bgColor" + bgColor);
            LogUtils.i("inputType" + inputType);


        } finally {
            typedArray.recycle();
        }

    }


    /*
     *
     * 测量view的宽高
     * */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);


        Log.i("测量", "onMeasure");
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        if (widthMode == MeasureSpec.AT_MOST) {
            /*表示在布局中指定了wrap_content*/
            Log.e("widthMode", "AT_MOST");
        } else if (widthMode == MeasureSpec.EXACTLY) {
            /*表示指定了确切的值 例如100dp  match_parent  fill_parent*/
            Log.e("widthMode", "EXACTLY");
        } else if (widthMode == MeasureSpec.UNSPECIFIED) {
            /*尽可能的大 不常用*/
            Log.e("widthMode", "UNSPECIFIED");
        }


    }


    /*这里主要用来绘制*/
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        LogUtils.i("绘制");

        Paint paint = new Paint();
        paint.setColor(Color.BLACK);


        canvas.drawText("sdas", 10, 10, paint);

    }


    /*这里主要用来处理事件*/
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        LogUtils.i("onTouchEvent");


        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                LogUtils.i("按下");
                break;
            case MotionEvent.ACTION_MOVE:
                LogUtils.i("移动");
                break;

            case MotionEvent.ACTION_UP:
                LogUtils.i("抬起");
                break;
        }

        return true;
    }
}
