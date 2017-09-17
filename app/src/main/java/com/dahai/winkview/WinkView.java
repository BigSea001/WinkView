package com.dahai.winkview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * 描述：
 * <p>
 * 由 大海 于 2017/9/16 创建
 */

public class WinkView extends View {

    private Paint bgPaint;
    private ValueAnimator animator;
    private String TAG="HHH";
    private boolean isOpen = false;

    public WinkView(Context context) {
        this(context,null);
    }

    public WinkView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public WinkView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.WinkViewAttr, defStyleAttr, 0);
        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.WinkViewAttr_isOpen:
                    isOpen = a.getBoolean(attr, false);
                    Log.e(TAG, "WinkView: " + isOpen );
                    break;
                default:
                    break;
            }

        }
        a.recycle();

        bgPaint = new Paint();
        bgPaint.setAntiAlias(true);
        bgPaint.setColor(Color.GRAY);

        animator = new ValueAnimator();
        animator.setDuration(200);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);


    }
    private int animatedValue;
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        if (!isOpen) animatedValue = 3*getMeasuredHeight()/4-getMeasuredHeight()/12;

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                animatedValue = ((int) animation.getAnimatedValue());
                invalidate();
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        int x = width/2;
        int y = height/2;
        int t = height/4;
        int b = 3*height/4;
        bgPaint.setColor(Color.GRAY);
        canvas.drawArc(0,t,width,b,180,360,true,bgPaint);
        canvas.drawArc(0,t,width,b,0,180,true,bgPaint);
        bgPaint.setColor(Color.WHITE);
        canvas.drawCircle(x,y,height/6,bgPaint);
        bgPaint.setColor(Color.GRAY);
        int r = height/12;  //眼仁的半径

        canvas.drawCircle(x-r,y-r,r,bgPaint);

        bgPaint.setColor(Color.WHITE);
        if (animatedValue>0)
        canvas.drawArc(0,0,width,animatedValue,0,360,true,bgPaint);

        Log.e(TAG, "onDraw: " + animatedValue );

        if (animatedValue>=3*height/4-height/12-2) {
            bgPaint.setColor(Color.GRAY);
            bgPaint.setStrokeWidth(3);
            canvas.drawLine(x,b,x+height/8,b+height/8,bgPaint);
            canvas.drawLine(x-height/10,b,x,b+height/10,bgPaint);
            canvas.drawLine(x+height/10,b,x+height/5,b+height/10,bgPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                if (isOpen) {
                    isOpen = false;
                    animator.setIntValues(0,3*getMeasuredHeight()/4-getMeasuredHeight()/12);
                    animator.start();
                    if (onShowHidClickListener!=null)
                        onShowHidClickListener.change(false);
                }  else {
                    isOpen = true;
                    animator.setIntValues(3*getMeasuredHeight()/4-getMeasuredHeight()/12,0);
                    animator.start();
                    if (onShowHidClickListener!=null)
                        onShowHidClickListener.change(true);
                }
                break;
            default:

                break;
        }


        return super.onTouchEvent(event);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (animator!=null) {
            animator.cancel();
            animator =null;
        }
        Log.e(TAG, "onDetachedFromWindow: " );
    }

    public interface OnShowHidClickListener {
        void change(boolean isOpen);
    }
    private OnShowHidClickListener onShowHidClickListener;

    public void setOnShowHidClickListener(OnShowHidClickListener onShowHidClickListener) {
        this.onShowHidClickListener = onShowHidClickListener;
    }
}
