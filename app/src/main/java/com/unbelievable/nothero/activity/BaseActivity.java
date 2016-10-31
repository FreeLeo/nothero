package com.unbelievable.nothero.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
     * @param seconds
     */
    public void autoJump(final Class cls,int seconds){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                jump(cls);
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
}
