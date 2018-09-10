package com.yzq.customview.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yzq.customview.R;
import com.yzq.customview.adapter.TitleAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements BaseQuickAdapter.OnItemClickListener {

    List texts = new ArrayList();

    private TitleAdapter adapter;
    private RecyclerView mRecy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        initData();
    }


    private void initView() {
        mRecy = findViewById(R.id.recy);
    }

    private void initData() {
        texts.add("TextView");
        texts.add("ProgressView");
        texts.add("TextColorView");
        texts.add("RatingBar");
        texts.add("IndexView");

        adapter = new TitleAdapter(R.layout.item_layout, texts);

        mRecy.setAdapter(adapter);

        adapter.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

        Intent intent = new Intent();

        switch (position) {

            case 0:
                intent.setClass(this, TextActivity.class);

                break;
            case 1:
                intent.setClass(this, ProgressActivity.class);
                break;
            case 2:
                intent.setClass(this, ViewPagerActivity.class);
                break;
            case 3:
                intent.setClass(this, RatingActivity.class);
                break;

            case 4:
                intent.setClass(this, IndexViewActivity.class);
                break;


        }

        startActivity(intent);
    }
}
