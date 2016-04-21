package com.dawin.androidhelper.viewpager_fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yuheng.dawin.androidcodehelper.R;

/**
 * Created by admin on 2016/4/15.da
 */
public class SampleFragment extends BaseFragment {
    private static final String FRAGMENT_INDEX = "fragment_index";
    private final int FIRST_FRAGMENT = 0;
    private final int SECOND_FRAGMENT = 1;
    private final int THIRD_FRAGMENT = 2;

    private TextView mFragmentView;

    private int mCurIndex = -1;
    /**
     * 标志位，标志已经初始化完成
     */
    private boolean isPrepared;
    /**
     * 是否已被加载过一次，第二次就不再去请求数据了
     */
    private boolean mHasLoadedOnce;

    /**
     * 创建新实例
     *
     * @param index
     * @return
     */
    public static SampleFragment newInstance(int index) {
        Bundle bundle = new Bundle();
        bundle.putInt(FRAGMENT_INDEX, index);
        SampleFragment fragment = new SampleFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mFragmentView == null) {
            mFragmentView = (TextView) inflater.inflate(R.layout.fragment, container, false);
            //获得索引值
            Bundle bundle = getArguments();
            if (bundle != null) {
                mCurIndex = bundle.getInt(FRAGMENT_INDEX);
            }
            isPrepared = true;
            lazyLoad();
        }

        //因为共用一个Fragment视图，所以当前这个视图已被加载到Activity中，必须先清除后再加入Activity
        ViewGroup parent = (ViewGroup) mFragmentView.getParent();
        if (parent != null) {
            parent.removeView(mFragmentView);
        }
        return mFragmentView;
    }

    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible || mHasLoadedOnce) {
            return;
        }
        //Params 启动任务执行的输入参数
        //Progress 后台任务执行的百分比
        //Result 后台执行任务最终的返回结果
        new AsyncTask<Void, Boolean, String>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //显示加载进度对话框
                // UIHelper.showDialogForLoading(getActivity(), "正在加载...", true);
            }

            @Override
            protected String doInBackground(Void... params) {
                try {
                    Thread.sleep(2000);
                    //在这里添加调用接口获取数据的代码
                    //doSomething()
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return "finish";
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
            }
        };
    }

    private void setView() {
        // 根据索引加载不同视图
        switch (mCurIndex) {
            case FIRST_FRAGMENT:
                mFragmentView.setText("第一个");
                break;

            case SECOND_FRAGMENT:
                mFragmentView.setText("第二个");
                break;

            case THIRD_FRAGMENT:
                mFragmentView.setText("第三个");
                break;
        }
    }
}
