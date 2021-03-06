package com.unbelievable.nothero.activity;

import android.os.Bundle;

import com.unbelievable.library.android.app.BaseActivity;
import com.unbelievable.library.android.constants.Constants;
import com.unbelievable.nothero.R;

public class WelcomeActivity extends BaseActivity {
    private final String TAG = WelcomeActivity.class.getSimpleName() + hashCode();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
    }

    @Override
    public String getVolleyTag() {
        return TAG;
    }

    @Override
    protected void onResume() {
        super.onResume();
        autoStartActivity(MainActivity.class, Constants.DEFAULT_DELAY_WELCOME,true);
    }
}
