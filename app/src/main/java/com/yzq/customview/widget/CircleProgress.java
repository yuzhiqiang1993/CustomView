package com.yzq.customview.widget;

import android.animation.ValueAnimator;
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
import android.view.animation.DecelerateInterpolator;

import com.yzq.customview.R;



public class CircleProgress extends View {

    private final Paint outerPaint;
    private final Paint textPaint;
    private final Paint progressPaint;
    private int outerColor = Color.BLUE;
    private int innerColor = Color.RED;
    private int mTextSize = sp2px(16);



    private int mTextColor = Color.RED;
    private int roundWidth = dp2px(4);
    private int width = dp2px(100);
    private int height = dp2px(100);

    private int maxProgress=100;
    private int currentProgress=80;
    private float precnet;
    private String progressStr;

    public CircleProgress(Context context) {
        this(context, null);
    }

    public CircleProgress(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleProgress(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleProgress);
        outerColor = typedArray.getColor(R.styleable.CircleProgress_outerColor, outerColor);
        innerColor = typedArray.getColor(R.styleable.CircleProgress_innerColor, innerColor);
        mTextSize = typedArray.getDimensionPixelSize(R.styleable.CircleProgress_mTextSize, mTextSize);
        mTextColor = typedArray.getColor(R.styleable.CircleProgress_mTextColor, mTextColor);
        roundWidth = typedArray.getDimensionPixelSize(R.styleable.CircleProgress_roundWidth, roundWidth);


        typedArray.recycle();


        outerPaint = new Paint();
        outerPaint.setColor(outerColor);
        outerPaint.setAntiAlias(true);
        outerPaint.setStrokeWidth(roundWidth);
        outerPaint.setStyle(Paint.Style.STROKE);


        textPaint=new Paint();
        textPaint.setTextSize(mTextSize);
        textPaint.setColor(mTextColor);
        textPaint.setAntiAlias(true);


        progressPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        progressPaint.setStrokeWidth(roundWidth);
        progressPaint.setColor(innerColor);
        progressPaint.setStrokeCap(Paint.Cap.ROUND);
        progressPaint.setStyle(Paint.Style.STROKE);

        initAnimate(currentProgress);


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);

        if (MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.AT_MOST) {
            width = dp2px(100);
        }
        if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.AT_MOST) {
            height = dp2px(100);

        }

        setMeasuredDimension(Math.min(width, height), Math.min(width, height));


    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        /*先画默认的圆*/

        int center = getWidth() / 2;
        int radius = getWidth() / 2;
        canvas.drawCircle(center,center,center-roundWidth/2,outerPaint);

        /*画文字*/

        Paint.FontMetricsInt fontMetricsInt=textPaint.getFontMetricsInt();
        int dy=(fontMetricsInt.bottom-fontMetricsInt.top)/2-fontMetricsInt.bottom;
        int baseLine=getHeight()/2+dy;

        float textWidth=textPaint.measureText(progressStr);
        canvas.drawText(progressStr,center-textWidth/2,baseLine,textPaint);

        RectF rectF=new RectF(roundWidth/2,roundWidth/2,width-roundWidth/2,width-roundWidth/2);
        //canvas.drawRect(rectF,progressPaint);
        canvas.drawArc(rectF,0,precnet*360,false,progressPaint);

    }


    public int getCurrentProgress() {
        return currentProgress;
    }

    public void setCurrentProgress( int currentProgress) {
        this.currentProgress = currentProgress;

        initAnimate(currentProgress);
    }

    private void initAnimate(int currentProgress) {
        ValueAnimator valueAnimator=ValueAnimator.ofInt(currentProgress);
        valueAnimator.setDuration(1000);
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
              int progress= (int) animation.getAnimatedValue();
              precnet=(float)progress/maxProgress;
                progressStr=progress+"";
              invalidate();
            }
        });
        valueAnimator.start();
    }

    private int sp2px(int sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,sp,getResources().getDisplayMetrics());
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp,getResources().getDisplayMetrics());
    }
}
