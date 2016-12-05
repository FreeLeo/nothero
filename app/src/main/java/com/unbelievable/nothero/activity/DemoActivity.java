package com.unbelievable.nothero.activity;

import android.os.Bundle;
import android.view.View;

import com.android.open.utils.Logger;
import com.unbelievable.nothero.R;

public class DemoActivity extends BaseActivity implements View.OnClickListener{
    private String TAG = DemoActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        Logger.init(true,Logger.DEBUG);
        Logger.v(TAG,"vvvvv");
        Logger.d(TAG,"ddddd");
        Logger.i(TAG,"iiiii");
        Logger.w(TAG,"wwww");
        Logger.e(TAG,"eeeee");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_home_tab:
                break;
        }
    }

    public void toHomeTabActivity(View v){
        jump(HomeTabActivity.class);
    }
}
