package com.yzq.customview.widget

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.view.animation.DecelerateInterpolator

import com.yzq.customview.R


/**
 * @description: 圆形进度条
 * @author : yzq
 * @date   : 2018/11/26
 * @time   : 14:30
 *
 */

class CircleProgress @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {

    private val outerPaint: Paint
    private val textPaint: Paint
    private val progressPaint: Paint
    private var outerColor = Color.BLUE
    private var innerColor = Color.RED
    private var mTextSize = sp2px(16)


    private var mTextColor = Color.RED
    private var roundWidth = dp2px(4)
    private var mWidth = dp2px(100)
    private var mHeight = dp2px(100)

    private val maxProgress = 100
    var currentProgress = 80
        set(currentProgress) {
            field = currentProgress

            initAnimate(currentProgress)
        }
    private var precnet: Float = 0.toFloat()
    private var progressStr: String? = null

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleProgress)
        outerColor = typedArray.getColor(R.styleable.CircleProgress_outerColor, outerColor)
        innerColor = typedArray.getColor(R.styleable.CircleProgress_innerColor, innerColor)
        mTextSize = typedArray.getDimensionPixelSize(R.styleable.CircleProgress_mTextSize, mTextSize)
        mTextColor = typedArray.getColor(R.styleable.CircleProgress_mTextColor, mTextColor)
        roundWidth = typedArray.getDimensionPixelSize(R.styleable.CircleProgress_roundWidth, roundWidth)


        typedArray.recycle()


        outerPaint = Paint()
        outerPaint.color = outerColor
        outerPaint.isAntiAlias = true
        outerPaint.strokeWidth = roundWidth.toFloat()
        outerPaint.style = Paint.Style.STROKE


        textPaint = Paint()
        textPaint.textSize = mTextSize.toFloat()
        textPaint.color = mTextColor
        textPaint.isAntiAlias = true


        progressPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        progressPaint.strokeWidth = roundWidth.toFloat()
        progressPaint.color = innerColor
        progressPaint.strokeCap = Paint.Cap.ROUND
        progressPaint.style = Paint.Style.STROKE

        initAnimate(this.currentProgress)


    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        /*测量*/
        mWidth = View.MeasureSpec.getSize(widthMeasureSpec)
        mHeight = View.MeasureSpec.getSize(heightMeasureSpec)

        if (View.MeasureSpec.getMode(widthMeasureSpec) == View.MeasureSpec.AT_MOST) {
            mWidth = dp2px(100)
        }
        if (View.MeasureSpec.getMode(heightMeasureSpec) == View.MeasureSpec.AT_MOST) {
            mHeight = dp2px(100)

        }

        setMeasuredDimension(Math.min(mWidth, mHeight), Math.min(mWidth, mHeight))


    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        /*先画默认的圆*/

        val center = getWidth() / 2
        val radius = getWidth() / 2
        canvas.drawCircle(center.toFloat(), center.toFloat(), (center - roundWidth / 2).toFloat(), outerPaint)

        /*画文字*/

        val fontMetricsInt = textPaint.fontMetricsInt
        val dy = (fontMetricsInt.bottom - fontMetricsInt.top) / 2 - fontMetricsInt.bottom
        val baseLine = getHeight() / 2 + dy

        val textWidth = textPaint.measureText(progressStr)
        canvas.drawText(progressStr!!, center - textWidth / 2, baseLine.toFloat(), textPaint)

        val rectF = RectF((roundWidth / 2).toFloat(), (roundWidth / 2).toFloat(), (mWidth - roundWidth / 2).toFloat(), (mWidth - roundWidth / 2).toFloat())
        //canvas.drawRect(rectF,progressPaint);
        canvas.drawArc(rectF, 0f, precnet * 360, false, progressPaint)

    }

    private fun initAnimate(currentProgress: Int) {
        val valueAnimator = ValueAnimator.ofInt(currentProgress)
        valueAnimator.duration = 1000
        valueAnimator.interpolator = DecelerateInterpolator()
        valueAnimator.addUpdateListener { animation ->
            val progress = animation.animatedValue as Int
            precnet = progress.toFloat() / maxProgress
            progressStr = progress.toString() + ""
            invalidate()
        }
        valueAnimator.start()
    }

    private fun sp2px(sp: Int): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp.toFloat(), resources.displayMetrics).toInt()
    }

    private fun dp2px(dp: Int): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), resources.displayMetrics).toInt()
    }
}
