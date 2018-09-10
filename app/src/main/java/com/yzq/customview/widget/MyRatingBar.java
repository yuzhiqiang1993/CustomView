package com.yzq.customview.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import com.yzq.customview.R;
import com.yzq.customview.utils.LogUtils;


public class MyRatingBar extends View {
    private final Bitmap startNormal;
    private final Bitmap starSelected;
    private int starNum = 5;
    private int starNormalId = R.drawable.star_normal;
    private int starSelectedId = R.drawable.star_selected;
    private int starWidth = dp2px(16);
    private int spacing = dp2px(6);
    private float eventX = 0;
    /*当前已选择星星的个数*/
    private int selectedStarNum = -1;

    public MyRatingBar(Context context) {
        this(context, null);
    }

    public MyRatingBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyRatingBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyRatingBar);

        starNum = typedArray.getInteger(R.styleable.MyRatingBar_starNum, starNum);
        starWidth = typedArray.getDimensionPixelSize(R.styleable.MyRatingBar_startWidth, starWidth);
        starNormalId = typedArray.getResourceId(R.styleable.MyRatingBar_starNormal, starNormalId);
        starSelectedId = typedArray.getResourceId(R.styleable.MyRatingBar_starSelected, starSelectedId);
        spacing = typedArray.getDimensionPixelSize(R.styleable.MyRatingBar_spacing, spacing);
        typedArray.recycle();


        Bitmap oldStartNormal = BitmapFactory.decodeResource(getResources(), starNormalId);
        Bitmap oldStarSelected = BitmapFactory.decodeResource(getResources(), starSelectedId);

        startNormal = Bitmap.createScaledBitmap(oldStartNormal, starWidth, starWidth, true);
        starSelected = Bitmap.createScaledBitmap(oldStarSelected, starWidth, starWidth, true);


        Log.e("starWidth:", startNormal.getWidth() + "");
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = startNormal.getWidth() * starNum + spacing * starNum + getPaddingLeft() + getPaddingRight();
        int height = startNormal.getHeight() + getPaddingBottom() + getPaddingTop();

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        LogUtils.i("draw");

        for (int i = 0; i < starNum; i++) {
            int startX = startNormal.getWidth() * i + spacing * (i + 1);

            if (startX < eventX) {
                /*画黄色星星*/
                canvas.drawBitmap(starSelected, startX, getHeight() / 2 - starWidth / 2, null);
            } else {
                canvas.drawBitmap(startNormal, startX, getHeight() / 2 - starWidth / 2, null);
            }


        }


    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:

                eventX = event.getX();


                int moveIndex = (int) ((eventX - getPaddingLeft()) / (startNormal.getWidth() + spacing));

                /*如果当前手指滑动位置大于等于最大个数时，改变值*/
                if (moveIndex >= starNum) {
                    moveIndex = starNum - 1;
                }


                /*当手指移动的index和已绘制的黄色星星值不一样时  再绘制  否则不需要绘制*/
                if (selectedStarNum != moveIndex) {

                    selectedStarNum = moveIndex;

                    invalidate();
                }


                break;
        }
        return true;
    }

    private int sp2px(int sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, getResources().getDisplayMetrics());

    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());

    }
}

