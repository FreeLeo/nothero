package com.unbelievable.nothero.testadapter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.unbelievable.nothero.R;
import com.unbelievable.nothero.activity.BaseActivity;
import com.unbelievable.nothero.testadapter.dummy.DummyContent;

public class AdapterActivity extends BaseActivity implements DummyFragment.OnListFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adapter);
        replaceFragment(R.id.activity_adapter,DummyFragment.newInstance(2));
    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {

    }
}
