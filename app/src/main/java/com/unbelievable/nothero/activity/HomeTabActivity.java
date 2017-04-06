package com.unbelievable.nothero.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TabHost;

import com.unbelievable.library.android.app.BaseActivity;
import com.unbelievable.library.android.base.adapter.BaseFragment;
import com.unbelievable.library.implementation.main.HeroTabActivity;
import com.unbelievable.nothero.R;
import com.unbelievable.nothero.download.DownloadFragment;

import java.util.HashMap;
import java.util.Map;

public class HomeTabActivity extends HeroTabActivity implements View.OnClickListener,BaseFragment.OnFragmentInteractionListener{

    public final static String TAG_FRAGMENT_HOME = "tag_home";
    public final static String TAG_FRAGMENT_PRODUCT = "tag_product";
    public final static String TAG_FRAGMENT_PUBLISHED = "tag_published";
    public final static String TAG_FRAGMENT_CART = "tag_cart";
    public final static String TAG_FRAGMENT_I = "tag_i";

    @Override
    public void addTab(HeroTabActivity.TabManager mTabManager, TabHost mTabHost) {
        RelativeLayout indicator1 = (RelativeLayout) getLayoutInflater().inflate(R.layout.tab_indicator_home, null);
        mTabManager.addTab(mTabHost.newTabSpec(TAG_FRAGMENT_HOME).setIndicator(indicator1), DownloadFragment.class, null);
        RelativeLayout indicator2 = (RelativeLayout) getLayoutInflater().inflate(R.layout.tab_indicator_home, null);
        mTabManager.addTab(mTabHost.newTabSpec(TAG_FRAGMENT_PRODUCT).setIndicator(indicator2), DownloadFragment.class, null);
        RelativeLayout indicator3 = (RelativeLayout) getLayoutInflater().inflate(R.layout.tab_indicator_home, null);
        mTabManager.addTab(mTabHost.newTabSpec(TAG_FRAGMENT_PUBLISHED).setIndicator(indicator3), DownloadFragment.class, null);
        RelativeLayout indicator4 = (RelativeLayout) getLayoutInflater().inflate(R.layout.tab_indicator_home, null);
        mTabManager.addTab(mTabHost.newTabSpec(TAG_FRAGMENT_CART).setIndicator(indicator4),DownloadFragment.class, null);
        RelativeLayout indicator5 = (RelativeLayout) getLayoutInflater().inflate(R.layout.tab_indicator_home, null);
        mTabManager.addTab(mTabHost.newTabSpec(TAG_FRAGMENT_I).setIndicator(indicator5),DownloadFragment.class, null);
    }

}
