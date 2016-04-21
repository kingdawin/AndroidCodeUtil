package com.dawin.androidhelper.viewpager_fragment;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
/**
 * ViewPage+Fragment优化
 *
 * ViewPage会预加载第二个页面，
 * 取消预加载页面的方法:
 * 用Fragment的setUserVisibleHint和getUerVisibleHint方法设置|获取Fragment可见状态
 */

public abstract class BaseFragment extends Fragment {
    //fragment当前是否可见
    public boolean isVisible;
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
    //子类实现懒加载。当Fragment对用户可见是在lazyLoad中加载数据
    protected abstract void lazyLoad();
}
