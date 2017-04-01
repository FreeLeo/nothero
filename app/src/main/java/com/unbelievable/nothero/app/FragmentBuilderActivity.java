package com.unbelievable.nothero.app;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.unbelievable.library.nothero.app.FragmentBuilder;
import com.unbelievable.nothero.R;
import com.unbelievable.nothero.activity.BaseActivity;
import com.unbelievable.nothero.fragment.TestFragment;

public class FragmentBuilderActivity extends BaseActivity implements TestFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_builder);

        replaceFragment(R.id.activity_fragment_builder,new FragmentBuilder(TestFragment.class).putString("content","测试FragmentBuilder").build());
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
