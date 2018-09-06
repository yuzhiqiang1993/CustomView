package com.yzq.customview.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.yzq.customview.R;
import com.yzq.customview.utils.LogUtils;

public class MyTextView extends View {


    private String mText = "TextView";
    private int textColor = Color.BLACK;
    private int textSize = 14;
    private Paint textPaint = new Paint();
    private int width;
    private int height;
    private Rect rect;
    private Paint.FontMetricsInt fontMetrics;
    private Paint baseLinePaint;


    public MyTextView(Context context) {
        this(context, null);
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyTextView);
        try {
            mText = typedArray.getString(R.styleable.MyTextView_mText);
            textSize = typedArray.getDimensionPixelSize(R.styleable.MyTextView_mTextSize, sp2px(textSize));
            textColor = typedArray.getColor(R.styleable.MyTextView_mTextColor, textColor);
        } finally {
            typedArray.recycle();
        }

        initPaint();


    }

    private void initPaint() {

        textPaint.setAntiAlias(true);
        textPaint.setTextSize(textSize);
        textPaint.setColor(textColor);
        fontMetrics = textPaint.getFontMetricsInt();
        baseLinePaint = new Paint();
        baseLinePaint.setColor(Color.RED);
        baseLinePaint.setStrokeWidth(1);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);


        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);

        LogUtils.i("测量的宽度：" + width);
        LogUtils.i("测量的高度：" + height);

        rect = new Rect();
        textPaint.getTextBounds(mText, 0, mText.length(), rect);

        if (widthMode == MeasureSpec.AT_MOST) {
            width = rect.width() + getPaddingLeft() + getPaddingRight();

        }

        if (heightMode == MeasureSpec.AT_MOST) {

            /*这个获取高度是有问题的  可能造成底部部分字体显示不完全*/
            height = rect.height() + getPaddingTop() + getPaddingBottom();
            LogUtils.i("文字的高度：" + height);
            /*这个才是实际的高度*/
            height = fontMetrics.bottom - fontMetrics.top + getPaddingBottom() + getPaddingTop();

        }
        LogUtils.i("最终的宽度：" + width);
        LogUtils.i("最终的高度：" + height);

        setMeasuredDimension(width, height);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        /*计算基线*/
        float dy = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;
        float baseLine = height/ 2 + dy;

        /*绘制文字*/
        canvas.drawText(mText, getPaddingLeft(), baseLine, textPaint);

        /*绘制基线*/
        canvas.drawLine(getPaddingLeft(), baseLine, width, baseLine, baseLinePaint);

    }

    private int sp2px(float spVal) {

        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spVal, getResources().getDisplayMetrics());
    }

}
