package com.yzq.customview.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import com.yzq.customview.R;


/**
 * @author : yzq
 * @description: 字母索引View
 * @date : 2018/9/20
 * @time : 14:50
 */

public class IndexView extends View {
    private final String[] letters;
    private final Paint reactPaint;
    private int width;
    private int height;
    private int letterHeight;
    private Paint paint;
    private int textSize = sp2px(14);
    private int currentLetterIndex;
    private String touchLetter;
    private TouchLetterListener listener;

    public IndexView(Context context) {
        this(context, null);
    }

    public IndexView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IndexView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.IndexView);
        textSize = typedArray.getDimensionPixelSize(R.styleable.IndexView_mTextSize, textSize);
        typedArray.recycle();
        letters = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
                "U", "V", "W", "X", "Y", "Z"};

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTextSize(textSize);
        paint.setColor(Color.BLACK);


        reactPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        reactPaint.setStrokeWidth(1);
        reactPaint.setColor(Color.RED);
        reactPaint.setStyle(Paint.Style.STROKE);


    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = MeasureSpec.getSize(widthMeasureSpec) + getPaddingLeft() + getPaddingRight();
        height = MeasureSpec.getSize(heightMeasureSpec);

        letterHeight = (height - getPaddingBottom() - getPaddingTop()) / letters.length;

        setMeasuredDimension(width, height);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        for (int i = 0; i < letters.length; i++) {


            String currentText = letters[i];

            int letterWidth = (int) paint.measureText(currentText);

            int startX = (width - getPaddingRight() - getPaddingLeft()) / 2 - letterWidth / 2;


            Paint.FontMetricsInt fontMetricsInt = paint.getFontMetricsInt();
            int dy = (fontMetricsInt.bottom - fontMetricsInt.top) / 2 - fontMetricsInt.bottom;

            int baseLine = letterHeight * i + letterHeight / 2 + dy + getPaddingTop();
//            RectF rect = new RectF();
//            rect.top = getPaddingTop() + letterHeight * i;
//            rect.left = 0;
//            rect.bottom = getPaddingTop() + letterHeight * (i + 1);
//            rect.right = width;
//
//            canvas.drawRect(rect, reactPaint);

            paint.setColor(Color.BLACK);
            canvas.drawText(currentText, startX, baseLine, paint);


        }

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {


        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:

                int index = (int) (event.getY() / letterHeight);

                if (index >= 0 && index < letters.length) {

                    if (currentLetterIndex != index) {
                        currentLetterIndex = index;
                        touchLetter = letters[currentLetterIndex];
                        if (listener != null) {
                            listener.showLetter(touchLetter);
                        }
                        invalidate();

                    }


                }

                break;
            case MotionEvent.ACTION_UP:
                if (listener != null) {
                    listener.hideLetter();
                }

                break;

            default:


        }
        return true;
    }

    private int sp2px(int sp) {

        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, getResources().getDisplayMetrics());
    }


    public void setOnTouchLetterListener(TouchLetterListener listener) {
        this.listener = listener;

    }

    public interface TouchLetterListener {
        void showLetter(String touchLetter);

        void hideLetter();
    }

}

