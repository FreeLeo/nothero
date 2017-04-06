package com.unbelievable.nothero.activity;

import android.os.Bundle;

import com.unbelievable.library.android.app.BaseActivity;
import com.unbelievable.library.android.constants.Constants;
import com.unbelievable.nothero.R;

public class WelcomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
    }

    @Override
    protected void onResume() {
        super.onResume();
        autoJump(MainActivity.class, Constants.DEFAULT_DELAY_WELCOME,true);
    }
}
