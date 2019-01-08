package com.yzq.customview.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import com.yzq.customview.R
import com.yzq.customview.utils.LogUtils



 /**
 * @description: 自定义的TextView
 * @author : yzq
 * @date   : 2018/11/26
 * @time   : 14:07
 *
 */
class MyTextView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {


     /*文字*/
    private var mText: String? = "TextView"
     /*文字颜色*/
    private var textColor = Color.BLACK
     /*文字大小*/
    private var textSize = 14
     /*文本画笔*/
    private val textPaint = Paint()
     /*宽度*/
    private var mWidth: Int = 0
     /*高度*/
    private var mHeight: Int = 0
    private var rect: Rect? = null
    private var fontMetrics: Paint.FontMetricsInt? = null
     /*基线画笔*/
    private var baseLinePaint: Paint? = null

    init {

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyTextView)
        try {
            mText = typedArray.getString(R.styleable.MyTextView_mText)
            textSize = typedArray.getDimensionPixelSize(R.styleable.MyTextView_mTextSize, sp2px(textSize.toFloat()))
            textColor = typedArray.getColor(R.styleable.MyTextView_mTextColor, textColor)
        } finally {
            typedArray.recycle()
        }

        initPaint()


    }

    private fun initPaint() {

        textPaint.isAntiAlias = true
        textPaint.textSize = textSize.toFloat()
        textPaint.color = textColor
        fontMetrics = textPaint.fontMetricsInt
        baseLinePaint = Paint()
        baseLinePaint!!.color = Color.RED
        baseLinePaint!!.strokeWidth = 1f
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val widthMode = View.MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = View.MeasureSpec.getMode(heightMeasureSpec)


        mWidth = View.MeasureSpec.getSize(widthMeasureSpec)
        mHeight = View.MeasureSpec.getSize(heightMeasureSpec)

        LogUtils.i("测量的宽度：$mWidth")
        LogUtils.i("测量的高度：$mHeight")

        rect = Rect()
        textPaint.getTextBounds(mText, 0, mText!!.length, rect)


        /*AT_MOST 表示尽可能的大，一般指定wrap_content时就是这种模式*/
        if (widthMode == View.MeasureSpec.AT_MOST) {
            mWidth = rect!!.width() + paddingLeft + paddingRight

        }

        if (heightMode == View.MeasureSpec.AT_MOST) {

            /*这个获取高度是有问题的  可能造成底部部分字体显示不完全*/
            mHeight = rect!!.height() + paddingTop + paddingBottom
            LogUtils.i("文字的高度：$mHeight")
            /*这个才是实际的高度*/
            mHeight = fontMetrics!!.bottom - fontMetrics!!.top + paddingBottom + paddingTop

        }
        LogUtils.i("最终的宽度：$mWidth")
        LogUtils.i("最终的高度：$mHeight")

        setMeasuredDimension(mWidth, mHeight)
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        /*计算基线*/
        val dy = ((fontMetrics!!.bottom - fontMetrics!!.top) / 2 - fontMetrics!!.bottom).toFloat()
        val baseLine = mHeight / 2 + dy

        /*绘制文字*/
        canvas.drawText(mText!!, paddingLeft.toFloat(), baseLine, textPaint)

        /*绘制基线*/
        canvas.drawLine(paddingLeft.toFloat(), baseLine, mWidth.toFloat(), baseLine, baseLinePaint!!)

    }

    private fun sp2px(spVal: Float): Int {

        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spVal, resources.displayMetrics).toInt()
    }

}
