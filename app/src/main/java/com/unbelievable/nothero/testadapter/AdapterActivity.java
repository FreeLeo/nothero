package com.unbelievable.nothero.testadapter;

import android.os.Bundle;

import com.unbelievable.nothero.R;
import com.unbelievable.library.android.app.BaseActivity;
import com.unbelievable.nothero.testadapter.dummy.DummyContent;

public class AdapterActivity extends BaseActivity implements DummyFragment.OnListFragmentInteractionListener {
    private final String TAG = AdapterActivity.class.getSimpleName() + hashCode();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adapter);
        replaceFragment(R.id.activity_adapter,DummyFragment.newInstance(2));
    }

    @Override
    public String getVolleyTag() {
        return TAG;
    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {

    }
}
