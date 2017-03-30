package com.unbelievable.nothero.testadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.unbelievable.library.nothero.base.adapter.HeroBaseAdapter;
import com.unbelievable.nothero.R;
import com.unbelievable.nothero.testadapter.dummy.DummyContent;
import com.unbelievable.nothero.testadapter.dummy.DummyContent.DummyItem;

import java.util.List;

public class MyDummyRecyclerViewAdapter extends HeroBaseAdapter<DummyItem> {

    public MyDummyRecyclerViewAdapter(Context context, List<DummyItem> beans) {
        super(context,beans);
    }

    @Override
    protected void onBindDataToView(HeroCommonViewHolder holder, DummyItem bean, int position) {
        holder.setText(R.id.content,bean.content);
    }

    @Override
    public int getItemLayoutID(int viewType) {
        return R.layout.fragment_person;
    }
}
