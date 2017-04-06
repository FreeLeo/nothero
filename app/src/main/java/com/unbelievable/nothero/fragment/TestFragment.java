package com.unbelievable.nothero.fragment;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.unbelievable.library.android.base.adapter.BaseFragment;
import com.unbelievable.nothero.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class TestFragment extends BaseFragment {

    @BindView(R.id.content_tv)
    TextView contentTv;
    Unbinder unbinder;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_test;
    }

    @Override
    protected void init(View view) {
        unbinder = ButterKnife.bind(this, view);
    }

    @Override
    protected void setupView() {
        super.setupView();
        String content = getArguments().getString("content");
        if(!TextUtils.isEmpty(content)){
            contentTv.setText(content);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
