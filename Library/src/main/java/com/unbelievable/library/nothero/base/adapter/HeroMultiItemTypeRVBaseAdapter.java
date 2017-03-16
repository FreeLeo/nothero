package com.unbelievable.library.nothero.base.adapter;

import android.content.Context;

import java.util.List;

/**
 * Created by _SOLID
 * Date:2016/4/5
 * Time:17:36
 * <p>
 * 支持多种ItemType的Adapter（适用于RecyclerView）
 */
public abstract class HeroMultiItemTypeRVBaseAdapter<T> extends HeroBaseAdapter<T> {


    public HeroMultiItemTypeRVBaseAdapter(Context context, List<T> beans) {
        super(context, beans);
    }

    @Override
    public abstract int getItemViewType(int position);


}
