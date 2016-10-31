package com.unbelievable.nothero.activity;

import android.os.Bundle;
import com.unbelievable.nothero.R;
import com.unbelievable.nothero.constants.Constants;

public class WelcomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
    }

    @Override
    protected void onResume() {
        super.onResume();
        autoJump(HomeActivity.class,Constants.DEFAULT_DELAY_WELCOME);
    }
}
