package com.yzq.customview.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.yzq.customview.R
import kotlinx.android.synthetic.main.activity_progress.*

class ProgressActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_progress)

        qqStepView.setMaxProgress(1000)
        qqStepView.setCurrentProgress(600)

    }
}
