package com.unbelievable.nothero.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * 界面跳转
     * @param cls
     */
    public void jump(Class cls){
        Intent intent = new Intent(this,cls);
        startActivity(intent);
    }

    /**
     * 延时自动跳转
     * @param cls
     * @param seconds
     */
    public void autoJump(final Class cls,int seconds){
        autoJump(cls,seconds,false);
    }

    /**
     * 延时自动跳转
     * @param cls        目标界面
     * @param seconds   延时时长
     * @param isFinish   跳转后是否结束当前界面
     */
    public void autoJump(final Class cls, int seconds, final boolean isFinish){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                jump(cls);
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
        Toast.makeText(this,str,Toast.LENGTH_SHORT).show();
    }

    public void replaceFragment(int containerViewId,Fragment fragment){
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(containerViewId,fragment).commit();
    }
}
