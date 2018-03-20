package com.tjun.www.granatepro.utils;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import com.tjun.www.granatepro.R;

/**
 * Created by tanjun on 2018/3/20.
 */

public class CountDownTimerUtils extends CountDownTimer {
    private TextView mTvTime;

    /**
     * @param millisInFuture    The number of millis in the future from the call  倒计时
     *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
     *                          is called.
     * @param countDownInterval The interval along the way to receive  倒计时时间间隔
     *                          {@link #onTick(long)} callbacks.
     */
    public CountDownTimerUtils(TextView textView, long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        this.mTvTime = textView;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        mTvTime.setClickable(false);
        mTvTime.setText(millisUntilFinished / 1000 + "秒后可重发");
        mTvTime.setBackgroundResource(R.drawable.btn_number_send);

        SpannableString spannableString = new SpannableString(mTvTime.getText().toString());  //获取按钮上的文字
        ForegroundColorSpan span = new ForegroundColorSpan(Color.RED);
        /**
         * public void setSpan(Object what, int start, int end, int flags) {
         * 主要是start跟end，start是起始位置,无论中英文，都算一个。
         * 从0开始计算起。end是结束位置，所以处理的文字，包含开始位置，但不包含结束位置。
         */
        spannableString.setSpan(span, 0, 2, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);//将倒计时的时间设置为红色
        mTvTime.setText(spannableString);
    }

    @Override
    public void onFinish() {
        mTvTime.setText("重新获取验证码");
        mTvTime.setClickable(true);
        mTvTime.setBackgroundResource(R.drawable.btn_check_bg);
    }
}
