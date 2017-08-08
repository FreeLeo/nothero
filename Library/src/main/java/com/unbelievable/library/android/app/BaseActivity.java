package com.unbelievable.library.android.app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.unbelievable.library.android.utils.ToastUtils;
import com.unbelievable.library.android.volleyplus.VolleyPlus;

public abstract class BaseActivity extends AppCompatActivity {
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * 通过Class跳转界面
     **/
    public void startActivity(Class<?> cls) {
        startActivity(cls, null);
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * 通过Action跳转界面
     **/
    public void startActivity(String action) {
        startActivity(action, null);
    }

    /**
     * 含有Bundle通过Action跳转界面
     **/
    public void startActivity(String action, Bundle bundle) {
        Intent intent = new Intent();
        intent.setAction(action);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * 需要返回结果的跳转界面
     **/
    public void startActivityForResult(Class<?> cls, Bundle bundle,
                                       int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    /**
     * 延时自动跳转
     * @param cls
     * @param seconds
     */
    public void autoStartActivity(final Class cls,int seconds){
        autoStartActivity(cls,seconds,true);
    }

    /**
     * 延时自动跳转
     * @param cls        目标界面
     * @param seconds   延时时长
     * @param isFinish   跳转后是否结束当前界面
     */
    public void autoStartActivity(final Class cls, int seconds, final boolean isFinish){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(cls);
                if(isFinish){
                    BaseActivity.this.finish();
                }
            }
        },seconds);
    }

    /**
     * 显示普通toast
     * @param str
     */
    public void showToast(String str){
        ToastUtils.toastL(this,str);
    }

    /**
     * 创建Dialog对象
     *
     * @param msg
     *            提示字符串
     * @param cancelable
     *            是否可以取消
     */
    public void createProgressDialog(String msg, boolean cancelable) {
        progressDialog = null;
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage(msg);
        progressDialog.setCancelable(cancelable);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
    }
    /**
     * 创建Dialog对象
     *
     * @param msg
     *            提示字符串
     *            可以取消
     */
    public void createProgressDialog(String msg) {
        progressDialog = null;
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage(msg);
        progressDialog.setCancelable(true);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
    }

    /**
     * 关闭Dialog对象
     */
    public void destroyProgressDialog() {
        if (progressDialog != null)
            progressDialog.dismiss();
        progressDialog = null;
    }

    public void replaceFragment(int containerViewId,Fragment fragment){
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(containerViewId,fragment).commit();
    }

    /**
     * TAG + hashcode()
     * @return
     */
    public abstract String getVolleyTag();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            VolleyPlus.getRequestQueue().cancelAll(getVolleyTag());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
