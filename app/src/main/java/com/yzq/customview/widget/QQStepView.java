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
import com.yzq.customview.utils.LogUtils;

public class QQStepView extends View {

    private int mTextColor = Color.BLACK;
    private int defaultColor = Color.parseColor("#DBDBDB");
    private int progressColor = Color.parseColor("#FF00FF");
    private int roundWidth = 30;
    private int mTextSize = 18;
    private int width;
    private int height;
    private Paint roundPaint, textPaint, progressPaint;
    private int centX;
    private int centY;
    private int radius;


    private int maxProgress = 100;
    private int currentProgress = 36;
    private float percent;
    private RectF rectF;
    private String currentProgressText;

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

        initAnimator();
    }

    private void initAnimator() {

        ValueAnimator valueAnimator = ValueAnimator.ofInt(currentProgress);
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.setDuration(1000);

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int progress = (int) animation.getAnimatedValue();
                percent = (float) progress / maxProgress;
                currentProgressText = progress + "";
                invalidate();

            }
        });

        valueAnimator.start();


    }

    private void initPaint() {
        roundPaint = new Paint();
        roundPaint.setStyle(Paint.Style.STROKE);
        roundPaint.setAntiAlias(true);
        roundPaint.setStrokeCap(Paint.Cap.ROUND);
        roundPaint.setColor(defaultColor);
        roundPaint.setStrokeWidth(roundWidth);

        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setColor(mTextColor);
        textPaint.setTextSize(mTextSize);

        progressPaint = new Paint();
        progressPaint.setStyle(Paint.Style.STROKE);
        progressPaint.setAntiAlias(true);
        progressPaint.setStrokeCap(Paint.Cap.ROUND);
        progressPaint.setColor(progressColor);
        progressPaint.setStrokeWidth(roundWidth);


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
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        /*圆的中心坐标*/
        centX = w / 2;
        centY = h / 2;

        /*求圆的半径*/
        radius = centX - roundWidth;

        LogUtils.i("半径：" + radius);


        rectF = new RectF(centX - radius, centY - radius, centX + radius, centY + radius);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawDefaultRound(canvas);

        drawText(canvas);

        drawProgressRound(canvas);


    }

    /*画进度*/
    private void drawProgressRound(Canvas canvas) {


        canvas.drawArc(rectF, 135, percent * 270, false, progressPaint);


    }

    private void drawDefaultRound(Canvas canvas) {
        canvas.drawArc(rectF, 135, 270, false, roundPaint);

    }

    private void drawText(Canvas canvas) {


        /*先计算文字的宽度*/
        float textWidth = textPaint.measureText(currentProgressText);

        /*计算基线*/
        Paint.FontMetricsInt fontMetrics = textPaint.getFontMetricsInt();
        int dy = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;
        int baseLine = getHeight() / 2 + dy;

        canvas.drawText(currentProgressText, centX - textWidth / 2, baseLine, textPaint);


    }

    private int sp2px(int sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, getResources().getDisplayMetrics());

    }

    private int dp2px(int dp) {

        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }


    public void setMaxProgress(int maxProgress) {
        this.maxProgress = maxProgress;
        initAnimator();
    }


    public void setCurrentProgress(int currentProgress) {
        this.currentProgress = currentProgress;
        initAnimator();
    }
}
