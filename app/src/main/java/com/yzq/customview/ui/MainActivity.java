package com.yzq.customview.ui;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;

import com.yzq.customview.R;
import com.yzq.customview.widget.ColorText;
import com.yzq.customview.widget.QQStepView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private QQStepView qqStepView;
    private ColorText colorText;
    /**
     * 从左到右
     */
    private AppCompatButton mL2r;
    /**
     * 从右到左
     */
    private AppCompatButton mR2l;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        qqStepView = findViewById(R.id.qqStepView);

        colorText = findViewById(R.id.colorText);

        qqStepView.setMaxProgress(1000);
        qqStepView.setCurrentProgress(600);




    }


    private void initView() {
        mL2r = findViewById(R.id.l2r);
        mL2r.setOnClickListener(this);
        mR2l = findViewById(R.id.r2l);
        mR2l.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.l2r:
                startAni(ColorText.Direction.LEFT_TO_RIGHT);


                break;
            case R.id.r2l:
                startAni(ColorText.Direction.RIGHT_TO_LEFT);
                break;
        }
    }

    private void startAni(final ColorText.Direction direction) {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
        valueAnimator.setDuration(2000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float progress = (float) animation.getAnimatedValue();
                colorText.setDirection(direction);
                colorText.setProgress(progress);



            }
        });

        valueAnimator.start();
    }
}
