package com.neopi.soundwaves

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.animation.LinearInterpolator
import android.widget.FrameLayout

/**
 * Author    :  NeoPi
 * Date      :  18-1-9
 * Describe  :
 */
class SoundWavesView
@JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0) :
        FrameLayout(context, attrs, defStyleAttr, defStyleRes), Runnable {


    private var mPaint: Paint? = null
    private var radius = 0f

    private var mWidth = 0
    private var mHeight = 0
    private var waveCount = 5
    private var waveSpace = 0f
    private val WAVE_DURATION = 5_000L

    init {
        init(context, attrs, defStyleAttr, defStyleRes)
    }

    private fun init(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) {

        setWillNotDraw(false)

        mPaint = Paint()
        mPaint!!.isAntiAlias = true
        mPaint!!.color = Color.parseColor("#4A90E2")
        mPaint!!.style = Paint.Style.STROKE
        mPaint!!.strokeWidth = 10f
        mPaint!!.alpha = 255
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        mWidth = measuredWidth
        mHeight = measuredHeight
        waveSpace = Math.min(mWidth, mHeight) .toFloat() / waveCount
    }

    override fun onDrawForeground(canvas: Canvas) {
        super.onDrawForeground(canvas)

        for (i in 0 until waveCount) {
            val circleRadius = i * waveSpace + radius
            mPaint!!.alpha = 255 - (circleRadius / Math.min(mWidth, mHeight) * 255).toInt()
            canvas.drawCircle(0f, mHeight.toFloat(), circleRadius, mPaint!!)
        }

    }

    fun setWaveCount(waveCount: Int) {
        if (waveCount > 0) {
            this.waveCount = waveCount
        }
    }

    fun startAnim() {
        postDelayed(this, 300)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
    }

    override fun run() {
        Log.e("111", waveSpace.toString() + "")
        val radiusAnim = ValueAnimator.ofFloat(0f, waveSpace)
        radiusAnim.repeatCount = ValueAnimator.INFINITE
        radiusAnim.duration = WAVE_DURATION
        radiusAnim.addUpdateListener { animation ->
            radius = animation.animatedValue as Float
            invalidate()
        }
        radiusAnim.interpolator = LinearInterpolator()
        radiusAnim.start()
    }
}