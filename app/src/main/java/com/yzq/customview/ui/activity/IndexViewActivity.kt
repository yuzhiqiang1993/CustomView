package com.yzq.customview.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.yzq.customview.R
import com.yzq.customview.widget.IndexView
import kotlinx.android.synthetic.main.activity_index_view.*

class IndexViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_index_view)


        indexView.setOnTouchLetterListener(object : IndexView.TouchLetterListener {
            override fun showLetter(touchLetter: String?) {

                letterTv.text = touchLetter
                letterTv.visibility = View.VISIBLE

            }

            override fun hideLetter() {
                letterTv.visibility = View.GONE
            }

        })
    }
}
