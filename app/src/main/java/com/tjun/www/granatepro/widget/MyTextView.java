package com.tjun.www.granatepro.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

import com.tjun.www.granatepro.R;

/**
 * Created by tanjun on 2018/4/8.
 */

@SuppressLint("AppCompatCustomView")
public class MyTextView extends TextView {
    int mViewWidth = 0; //设置View的宽度变量
    Paint mPaint;       //获得TextView的画笔
    LinearGradient mLinearGradient; //渐变渲染器
    Matrix mGradientMatrix;//为了处理平移转换
    int mTranslate = 0;//平移转换量



    public MyTextView(Context context) {
        super(context);
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (mViewWidth == 0) {
            mViewWidth = getMeasuredWidth();//View全部的宽度，包括隐藏的
            if (mViewWidth > 0) {
                mPaint = getPaint();
                mLinearGradient = new LinearGradient(0, 0,
                        mViewWidth, 0,
                        new int[]{Color.WHITE,Color.BLACK,getResources().getColor(R.color.colorPrimary),
                                Color.TRANSPARENT,Color.WHITE,}, null
                        , Shader.TileMode.CLAMP);
                //Gradient是基于Shader类，所以我们通过Paint的setShader方法来设置这个渐变
                mPaint.setShader(mLinearGradient);
                //初始化Matrix，为绘制做准备
                mGradientMatrix = new Matrix();
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mGradientMatrix != null) {
            mTranslate += mViewWidth / 5;
            if (mTranslate > 2 * mViewWidth) {
                mTranslate = -mViewWidth;
            }
            //设置平移转换量
            mGradientMatrix.setTranslate(mTranslate, 0);
            mLinearGradient.setLocalMatrix(mGradientMatrix);
            //延迟刷新界面
            postInvalidateDelayed(100);
        }
    }
}
