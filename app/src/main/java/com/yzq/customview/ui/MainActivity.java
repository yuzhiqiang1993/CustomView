package com.yzq.customview.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.yzq.customview.R;
import com.yzq.customview.widget.QQStepView;

public class MainActivity extends AppCompatActivity {

    private QQStepView qqStepView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        qqStepView = findViewById(R.id.qqStepView);

        qqStepView.setMaxProgress(1000);
        qqStepView.setCurrentProgress(600);

    }


}
