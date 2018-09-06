package com.yzq.customview.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;

import com.yzq.customview.R;
import com.yzq.customview.utils.LogUtils;

public class ColorText extends android.support.v7.widget.AppCompatTextView {

    private String text;
    private int changeColor;

    private Paint defaultPaint, changePaint;
    private int defaltColor = getTextColors().getDefaultColor();


    /*滑动的进度*/
    private float progress=0;
    private Direction direction = Direction.RIGHT_TO_LEFT;

    public static enum Direction {
        LEFT_TO_RIGHT, RIGHT_TO_LEFT
    }


    private Rect clipRect;

    public ColorText(Context context) {
        this(context, null);
    }

    public ColorText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ColorText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ColorText);

        changeColor = typedArray.getColor(R.styleable.ColorText_changeColor, getTextColors().getDefaultColor());


        typedArray.recycle();
        defaltColor = getTextColors().getDefaultColor();
        text = getText().toString();
        clipRect = new Rect();


        initPaint();
    }

    private void initPaint() {
        LogUtils.i("initPaint");
        defaultPaint = new Paint();
        defaultPaint.setAntiAlias(true);
        defaultPaint.setColor(defaltColor);
        defaultPaint.setTextSize(getTextSize());

        changePaint = new Paint();
        changePaint.setAntiAlias(true);
        changePaint.setColor(changeColor);
        changePaint.setTextSize(getTextSize());


    }


    @Override
    protected void onDraw(Canvas canvas) {
        int middle = (int) (getWidth() * progress);


        if (direction == Direction.LEFT_TO_RIGHT) {

            /*右边变黑 左边是红色*/
            drawText(canvas, 0, middle, changePaint);
            drawText(canvas, middle, getWidth(), defaultPaint);
        } else {
            /*左边变黑  右边是红色*/
            drawText(canvas, 0, getWidth() - middle, defaultPaint);//左边
            drawText(canvas, getWidth() - middle, getWidth(), changePaint);//右边

        }


    }

    private void drawText(Canvas canvas, int start, int end, Paint paint) {

        text = getText().toString();

        float textWidth = defaultPaint.measureText(text);


        LogUtils.i("onDraw" + text);
        /*先获取裁剪区域*/
        canvas.save();
        clipRect.left = start;
        clipRect.top = 0;
        clipRect.right = end;
        clipRect.bottom = getHeight();
        canvas.clipRect(clipRect);
        /*绘制文字*/
        canvas.drawText(text, getWidth() / 2 - textWidth / 2, getBaseline(), paint);

        canvas.restore();


    }


    public void setProgress(float progress) {
        this.progress = progress;
        invalidate();
    }


    public void setDirection(Direction direction) {
        this.direction = direction;
        invalidate();
    }


    public int getChangeColor() {

        return changeColor;
    }

    public void setChangeColor(int changeColor) {
        this.changeColor = changeColor;
        changePaint.setColor(changeColor);
    }
}
