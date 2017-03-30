package com.unbelievable.nothero.activity;

import android.os.Bundle;
import android.view.View;

import com.unbelievable.nothero.R;
import com.unbelievable.nothero.testadapter.AdapterActivity;

public class DemoActivity extends BaseActivity implements View.OnClickListener{
    private String TAG = DemoActivity.class.getSimpleName();
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
    public void toBarChartActivity(View v){
        jump(BarChartActivity.class);
    }
    public void toAdapterActivity(View v){
        jump(AdapterActivity.class);
    }
}
