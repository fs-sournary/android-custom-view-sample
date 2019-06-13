package com.sournary.chartviewsample

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View

/**
 * Created in 6/7/19 by Sang
 * Description:
 */
class ChartView : View {

    private var floatWidth = 0f
    private var floatHeight = 0f
    private var maxPrice = 0f
    private var minPrice = 0f
    private var textHeight: Float = 0f
    private val textBound: Rect = Rect()

    private var data = mutableListOf<StockData>()
    private var subData = mutableListOf<StockData>()
    private var paint: Paint = Paint()
    private var strokePaint: Paint = Paint()
    private var textPaint: Paint = Paint()

    constructor(context: Context, resId: Int) : super(context) {
        val inputStream = resources.openRawResource(resId)
        data = inputStream.readStockData()
        showLast()
        // Paint
        strokePaint.color = Color.BLACK
        textPaint.apply {
            color = Color.BLACK
            textSize = 40f
            textAlign = Paint.Align.RIGHT
            getTextBounds("1", 0, 1, textBound)
            textHeight = textBound.height().toFloat()
        }
    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet){
        val typedArray = context.theme.obtainStyledAttributes(attributeSet, R.styleable.ChartView, 0, 0)
        val resId = typedArray.getResourceId(R.styleable.ChartView_data, 0)
        typedArray.recycle()
        val inputStream = resources.openRawResource(resId)
        data = inputStream.readStockData()
        showLast()
        // Paint
        strokePaint.color = Color.BLACK
        textPaint.apply {
            color = Color.BLACK
            textSize = 40f
            textAlign = Paint.Align.RIGHT
            getTextBounds("1", 0, 1, textBound)
            textHeight = textBound.height().toFloat()
        }
    }

    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(
        context,
        attributeSet,
        defStyleAttr
    )

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        floatWidth = width.toFloat()
        floatHeight = height.toFloat()
        val chartWidth = floatWidth - textPaint.measureText("1000")
        val rectWidth: Float = chartWidth / subData.size
        strokePaint.strokeWidth = rectWidth / 8
        var start = left.toFloat()
        var top: Float
        var bottom: Float
        // the lowest value is bottom screen, the highest value is top screen.
        for (i in subData.size - 1 downTo 0) {
            val stockData = subData[i]
            if (stockData.close >= stockData.open) {
                paint.color = Color.GREEN
                top = stockData.close
                bottom = stockData.open
            } else {
                paint.color = Color.RED
                top = stockData.open
                bottom = stockData.close
            }
            canvas.drawLine(
                start + rectWidth / 2,
                getYPosition(stockData.high),
                start + rectWidth / 2,
                getYPosition(stockData.low),
                strokePaint
            )
            canvas.drawRect(
                start, getYPosition(top), start + rectWidth, getYPosition(bottom), paint
            )
            start += rectWidth
        }
        for (i in minPrice.toInt() until maxPrice.toInt()) {
            if (i % 20 == 0) {
                strokePaint.strokeWidth = 1f
                canvas.drawLine(
                    0f,
                    getYPosition(i.toFloat()),
                    chartWidth,
                    getYPosition(i.toFloat()),
                    strokePaint
                )
                canvas.drawText(
                    "$i",
                    floatWidth,
                    getYPosition(i.toFloat()) + textHeight / 2,
                    textPaint
                )
            }
        }
    }

    private fun getYPosition(price: Float): Float {
        val scaleFactorY = (price - minPrice) / (maxPrice - minPrice)
        return floatHeight - floatHeight * scaleFactorY
    }

    fun showLast(n: Int) {
        subData = data.subList(0, n)
        updateMaxAndMin()
        invalidate()
    }

    private fun updateMaxAndMin() {
        maxPrice = -1f
        minPrice = 99999f
        subData.forEach {
            if (it.high > maxPrice) {
                maxPrice = it.high
            }
            if (it.low < minPrice) {
                minPrice = it.low
            }
        }
    }

    fun showLast() {
        showLast(data.size)
    }
}