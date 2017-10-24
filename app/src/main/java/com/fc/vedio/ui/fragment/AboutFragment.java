package com.fc.vedio.ui.fragment;

import android.support.v7.widget.RecyclerView;

import com.fc.vedio.R;
import com.fc.vedio.base.BaseFragment;

import butterknife.BindView;

/**
 * @author 范超 on 2017/9/21
 */

public class AboutFragment extends BaseFragment {
    @BindView(R.id.video_recyclerView)
    RecyclerView videoRecyclerView;

    @Override
    protected int getContentLayout() {
        return R.layout.fragment_main;
    }

    @Override
    protected void initData() {
        super.initData();

    }


}
