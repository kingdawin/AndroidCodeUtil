package com.dawin.androidhelper.viewpager_fragment;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
/**
 * ViewPage+Fragment优化
 *
 * ViewPage会预加载第二个页面，
 * 取消预加载页面的方法:
 * Fragment的setUserVisibleHint和getUerVisibleHint方法
 */

public abstract class BaseFragment extends Fragment {
    //fragment当前是否可见
    private boolean isVisible;
    //设置Fragment可见或隐藏
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(getUserVisibleHint()) {
            isVisible=true;
            onVisible();
        }else
        {
            isVisible=false;
            onInVisible();
        }
    }
    protected void onVisible()
    {
        //可见时实现懒加载
        lazyLoad();
    }

    protected void onInVisible(){}
    //子类实现懒加载
    protected abstract void lazyLoad();
}
