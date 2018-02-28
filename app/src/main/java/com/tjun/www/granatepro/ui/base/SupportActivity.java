package com.tjun.www.granatepro.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import me.yokeyword.fragmentation.ExtraTransaction;
import me.yokeyword.fragmentation.ISupportActivity;
import me.yokeyword.fragmentation.ISupportFragment;
import me.yokeyword.fragmentation.SupportActivityDelegate;
import me.yokeyword.fragmentation.SupportHelper;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * Created by tanjun on 2018/2/28.
 */

public class SupportActivity extends RxAppCompatActivity implements  ISupportActivity{
    final SupportActivityDelegate mDelegate = new SupportActivityDelegate(this);

    @Override
    public SupportActivityDelegate getSupportDelegate() {
        return mDelegate;
    }

    /**
     * 额外事物，自定义tag,添加shareElement动画，操作非回退栈fragment
     * @return
     */
    @Override
    public ExtraTransaction extraTransaction() {
        return mDelegate.extraTransaction();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDelegate.onCreate(savedInstanceState);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDelegate.onPostCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        mDelegate.onDestroy();
        super.onDestroy();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return mDelegate.dispatchTouchEvent(ev) || super.dispatchTouchEvent(ev);
    }

    /**
     * 建议使用onBackPressedSupport
     */
    @Override
    public void onBackPressed() {
        mDelegate.onBackPressed();
    }

    /**
     * 获取全局动画
     * @return
     */
    @Override
    public FragmentAnimator getFragmentAnimator() {
        return mDelegate.getFragmentAnimator();
    }

    /**
     * 设置动画
     * @param fragmentAnimator
     */
    @Override
    public void setFragmentAnimator(FragmentAnimator fragmentAnimator) {
        mDelegate.setFragmentAnimator(fragmentAnimator);
    }

    /**
     * Set all fragments animation.
     * 构建Fragment转场动画
     * <p/>
     * 如果是在Activity内实现,则构建的是Activity内所有Fragment的转场动画,
     * 如果是在Fragment内实现,则构建的是该Fragment的转场动画,此时优先级 > Activity的onCreateFragmentAnimator()
     *
     * @return FragmentAnimator对象
     */
    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return mDelegate.onCreateFragmentAnimator();
    }

    @Override
    public void post(Runnable runnable) {

    }

    /**
     * 该方法回调时机为,Activity回退栈内Fragment的数量 小于等于1 时,默认finish Activity
     * 请尽量复写该方法,避免复写onBackPress(),以保证SupportFragment内的onBackPressedSupport()回退事件正常执行
     */
    @Override
    public void onBackPressedSupport() {
        mDelegate.onBackPressedSupport();
    }

    /**
     * 获取栈内fragment
     * @param fragmentClass
     * @param <T>
     * @return
     */
    public <T extends ISupportFragment> T findFragment(Class<T> fragmentClass){
        return SupportHelper.findFragment(getSupportFragmentManager(),fragmentClass);
    }
}
