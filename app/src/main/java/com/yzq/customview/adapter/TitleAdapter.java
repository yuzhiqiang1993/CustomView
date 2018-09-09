package com.yzq.customview.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yzq.customview.R;

import java.util.List;

public class TitleAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public TitleAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

        helper.setText(R.id.tv, item);
    }
}
