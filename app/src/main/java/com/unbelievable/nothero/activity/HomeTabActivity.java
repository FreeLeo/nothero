package com.unbelievable.nothero.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TabHost;

import com.unbelievable.library.nothero.base.adapter.BaseFragment;
import com.unbelievable.nothero.R;
import com.unbelievable.nothero.download.DownloadFragment;

import java.util.HashMap;
import java.util.Map;

public class HomeTabActivity extends BaseActivity implements View.OnClickListener,BaseFragment.OnFragmentInteractionListener{
    private Intent intent;
    private TabHost mTabHost;
    private TabManager mTabManager;

    private long mExitTime;

    public final static String TAG_FRAGMENT_HOME = "tag_home";
    public final static String TAG_FRAGMENT_PRODUCT = "tag_product";
    public final static String TAG_FRAGMENT_PUBLISHED = "tag_published";
    public final static String TAG_FRAGMENT_CART = "tag_cart";
    public final static String TAG_FRAGMENT_I = "tag_i";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_tab);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);

        initData();
        initView();
        mTabHost = (TabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup();

        mTabManager = new TabManager(this, mTabHost, R.id.containertabcontent);

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

        if (savedInstanceState != null) {
            mTabHost.setCurrentTabByTag(savedInstanceState.getString("tag"));
        }
    }

    private void initView() {}


    private void initData() {
        intent = getIntent();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * 销毁之前
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("tag", mTabHost.getCurrentTabTag());
    }

    public class TabManager implements TabHost.OnTabChangeListener {
        private final HomeTabActivity mActivity;
        // 保存tab
        private final Map<String, TabInfo> mTabs = new HashMap<String, TabInfo>();
        private final TabHost mTabHost;
        private final int mContainerID;
        private TabInfo mLastTab;

        /**
         * @param activity
         *            context
         * @param tabHost
         *            tab
         * @param containerID
         *            fragment's parent note
         */
        public TabManager(HomeTabActivity activity, TabHost tabHost,
                          int containerID) {
            mActivity = activity;
            mTabHost = tabHost;
            mContainerID = containerID;
            mTabHost.setOnTabChangedListener(this);
        }

        final class TabInfo {
            private final String tag;
            private final Class<?> clss;
            private final Bundle args;
            private BaseFragment fragment;

            TabInfo(String _tag, Class<?> _clss, Bundle _args) {
                tag = _tag;
                clss = _clss;
                args = _args;
            }
        }

        class TabFactory implements TabHost.TabContentFactory {
            private Context mContext;

            TabFactory(Context context) {
                mContext = context;
            }

            @Override
            public View createTabContent(String tag) {
                View v = new View(mContext);
                v.setMinimumHeight(0);
                v.setMinimumWidth(0);
                return v;
            }
        }

        // 加入tab
        public void addTab(TabHost.TabSpec tabSpec, Class<?> clss, Bundle args) {
            tabSpec.setContent(new TabFactory(mActivity));
            String tag = tabSpec.getTag();
            TabInfo info = new TabInfo(tag, clss, args);
            final FragmentManager fm = mActivity.getSupportFragmentManager();
            info.fragment = (BaseFragment) fm.findFragmentByTag(tag);
            // isDetached分离状态
            if (info.fragment != null && !info.fragment.isDetached()) {
                FragmentTransaction ft = fm.beginTransaction();
                ft.detach(info.fragment);
                info.fragment = null;
                ft.commit();
            }
            mTabs.put(tag, info);
            mTabHost.addTab(tabSpec);
        }

        @Override
        public void onTabChanged(String tabId) {
            TabInfo newTab = mTabs.get(tabId);
            if (mLastTab != newTab && newTab != null) {
                FragmentManager fragmentManager = mActivity
                        .getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager
                        .beginTransaction();
                // 脱离之前的tab
                if (mLastTab != null && mLastTab.fragment != null) {
                    fragmentTransaction.detach(mLastTab.fragment);
                }
                if (newTab != null) {
                    if (newTab.fragment == null) {
                        newTab.fragment = (BaseFragment) Fragment.instantiate(
                                mActivity, newTab.clss.getName(), newTab.args);
                        fragmentTransaction.add(mContainerID, newTab.fragment,
                                newTab.tag);
                    } else {// 激活
                        fragmentTransaction.attach(newTab.fragment);
                    }
                }


//				if (newTab.fragment == null) {
//					newTab.fragment = (BaseFragment) Fragment.instantiate(
//							mActivity, newTab.clss.getName(), newTab.args);
//					newTab.fragment.setRedPointListener(redPointListener);
//					if (mLastTab != null) {
//						fragmentTransaction.hide(mLastTab.fragment);
//						mLastTab.fragment.onPause();
//					}
//					fragmentTransaction.add(mContainerID, newTab.fragment,
//							newTab.tag);
//				} else {
//					if (!newTab.fragment.isAdded()) { // 先判断是否被add过
//						fragmentTransaction.hide(mLastTab.fragment).add(
//                                mContainerID, newTab.fragment, newTab.tag); // 隐藏当前的fragment，add下一个到Activity中
//						mLastTab.fragment.onPause();
//					} else {
//						if(mLastTab != null && mLastTab.fragment != null){
//							fragmentTransaction.hide(mLastTab.fragment);
//							mLastTab.fragment.onPause();
//						}
//						if(newTab.fragment != null){
//							fragmentTransaction.show(newTab.fragment); // 隐藏当前的fragment，显示下一个
//							newTab.fragment.onResume();
//						}
//					}
//				}
                mLastTab = newTab;
                fragmentTransaction.commitAllowingStateLoss();
                // 会在进程的主线程中，用异步的方式来执行,如果想要立即执行这个等待中的操作，就要调用这个方法
                // 所有的回调和相关的行为都会在这个调用中被执行完成，因此要仔细确认这个方法的调用位置。
                fragmentManager.executePendingTransactions();
            }
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if(mTabHost.getCurrentTab() != 0){
                mTabHost.setCurrentTab(0);
                return true;
            }else{
                if ((System.currentTimeMillis() - mExitTime) > 2000) {
                    showToast("再按一次退出程序");
                    mExitTime = System.currentTimeMillis();
                } else {
                    finish();
                }
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        FragmentManager fm = getSupportFragmentManager();
        String tag = mTabHost.getCurrentTabTag();
        ((BaseFragment) fm.findFragmentByTag(tag)).onClick(v);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
