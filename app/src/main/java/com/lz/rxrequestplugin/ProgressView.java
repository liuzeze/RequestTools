package com.lz.rxrequestplugin;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * -----------作者----------日期----------变更内容-----
 * -          刘泽      2018-10-18       创建class
 */
public class ProgressView extends View {
    private int progress;
    private int mMeasureSpec;
    private Paint mPaint;

    public ProgressView(Context context) {
        super(context);
        initPaint();

    }

    public ProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();

    }

    public ProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initPaint();

    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setStrokeWidth(5);
        mPaint.setTextSize(30);
    }

    public void setProgress(int progress) {
        this.progress = progress;
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //获取宽 - 测量规则的模式和大小
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
//        获取高 - 测量规则的模式和大小
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        // 设置wrap_content的默认宽 / 高值 // 默认宽/高的设定并无固定依据,根据需要灵活设置
        // 类似TextView,ImageView等针对wrap_content均在onMeasure()对设置默认宽
        // / 高值有特殊处理,具体读者可以自行查看
        int mWidth = getPaddingLeft() + getPaddingRight();
        int mHeight = getPaddingTop() + getPaddingBottom();
        // 当布局参数设置为wrap_content时，设置默认值
        if (getLayoutParams().width == ViewGroup.LayoutParams.WRAP_CONTENT
                && getLayoutParams().height == ViewGroup.LayoutParams.WRAP_CONTENT) {
            setMeasuredDimension(mWidth, mHeight);
            // 宽 / 高任意一个布局参数为= wrap_content时，都设置默认值
        } else if (getLayoutParams().width == ViewGroup.LayoutParams.WRAP_CONTENT) {
            setMeasuredDimension(mWidth, heightSize);
        } else if (getLayoutParams().height == ViewGroup.LayoutParams.WRAP_CONTENT) {
            setMeasuredDimension(widthSize, mHeight);
        }

        mMeasureSpec = MeasureSpec.getSize(widthMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        System.out.println("下载进度" + progress);
        if (progress != 0) {
            LinearGradient shader = new LinearGradient(0, 0, mMeasureSpec, 0, Color.BLUE, Color.RED, Shader.TileMode.CLAMP);
            mPaint.setShader(shader);
            canvas.drawLine(getPaddingLeft(), getPaddingTop(), (mMeasureSpec - getPaddingLeft() - getPaddingRight()) / 100 * progress, getPaddingTop(), mPaint);
            canvas.drawText(progress + "%", (mMeasureSpec - getPaddingRight()) / 100 * progress, 45, mPaint);
        }
    }
}
