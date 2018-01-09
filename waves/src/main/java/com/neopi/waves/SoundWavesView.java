package com.neopi.waves;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;

/**
 * Author    :  NeoPi
 * Date      :  18-1-9
 * Describe  :
 */
public class SoundWavesView extends FrameLayout implements Runnable {


    private Paint mPaint = null ;
    private float radius = 0  ;

    private int mWidth= 0 ;
    private int mHeight = 0 ;
    private int waveCount = 5 ;
    private int waveSpace = 0 ;
    private final long WAVE_DURATION = 5_000L ;

    public SoundWavesView(@NonNull Context context) {
        this(context,null);
    }

    public SoundWavesView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SoundWavesView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr,0);
    }

    public SoundWavesView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes)  {

        setWillNotDraw(false) ;

        mPaint = new Paint() ;
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.parseColor("#4A90E2"));
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(10f) ;
        mPaint.setAlpha(255);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth() ;
        mHeight = getMeasuredHeight() ;
        waveSpace = Math.min(mWidth,mHeight) / waveCount ;
    }

    @Override
    public void onDrawForeground(Canvas canvas) {
        super.onDrawForeground(canvas);

        for (int i = 0; i < waveCount; i++) {
            float circleRadius = i * waveSpace + radius;
            mPaint.setAlpha(255 - (int) (circleRadius / Math.min(mWidth,mHeight) * 255));
            canvas.drawCircle(0f,mHeight, circleRadius,mPaint) ;
        }

    }

    public void setWaveCount(int waveCount) {
        if (waveCount > 0) {
            this.waveCount = waveCount;
        }
    }

    public void startAnim() {
        postDelayed(this,300);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

    }


    @Override
    public void run() {
        Log.e("111",waveSpace + "");
        ValueAnimator radiusAnim = ValueAnimator.ofFloat(0, waveSpace) ;
        radiusAnim.setRepeatCount(ValueAnimator.INFINITE) ;
        radiusAnim.setDuration(WAVE_DURATION) ;
        radiusAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                radius = (float)(animation.getAnimatedValue());
                invalidate() ;
            }
        });
        radiusAnim.setInterpolator(new LinearInterpolator());
        radiusAnim.start() ;
    }
}