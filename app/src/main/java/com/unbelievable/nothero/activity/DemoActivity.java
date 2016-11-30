package com.unbelievable.nothero.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.unbelievable.nothero.R;
public class DemoActivity extends BaseActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
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
