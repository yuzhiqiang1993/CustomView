package com.yzq.customview.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.yzq.customview.R;

public class QQStepView extends View {

    private int mTextColor = Color.BLACK;
    private int defaultColor = Color.parseColor("#DBDBDB");
    private int progressColor = Color.parseColor("#FF00FF");
    private int roundWidth = 30;
    private int mTextSize = 18;
    private int width;
    private int height;
    private Paint roundPaint;

    public QQStepView(Context context) {
        this(context, null);
    }

    public QQStepView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public QQStepView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.QQStepView);


        mTextColor = typedArray.getColor(R.styleable.QQStepView_mTextColor, mTextColor);
        defaultColor = typedArray.getColor(R.styleable.QQStepView_defaultColor, defaultColor);
        progressColor = typedArray.getColor(R.styleable.QQStepView_progressColor, progressColor);
        roundWidth = typedArray.getDimensionPixelSize(R.styleable.QQStepView_roundWidth, roundWidth);
        mTextSize = typedArray.getDimensionPixelSize(R.styleable.QQStepView_mTextSize, mTextSize);

        typedArray.recycle();


        initPaint();
    }

    private void initPaint() {
        roundPaint = new Paint();
        roundPaint.setStyle(Paint.Style.STROKE);
        roundPaint.setAntiAlias(true);
        roundPaint.setStrokeCap(Paint.Cap.ROUND);
        roundPaint.setColor(defaultColor);
        roundPaint.setStrokeWidth(dp2px(roundWidth));


    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);


        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);
        int finalSize = width > height ? height : width;

        if (MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.AT_MOST) {
            finalSize = 100;
        }

        if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.AT_MOST) {
            finalSize = 100;
        }


        setMeasuredDimension(finalSize, finalSize);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        RectF rectF = new RectF(roundWidth, roundWidth, getWidth() + roundWidth / 2, getHeight() + roundWidth / 2);
        Paint paint = new Paint();
        paint.setStrokeWidth(1);
        paint.setColor(Color.RED);

         canvas.drawRect(rectF,paint);
        /*先画默认的圆弧*/
        canvas.drawArc(rectF, 135, 270, false, roundPaint);


    }

    private int sp2px(int sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, getResources().getDisplayMetrics());

    }

    private int dp2px(int dp) {

        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }


}
