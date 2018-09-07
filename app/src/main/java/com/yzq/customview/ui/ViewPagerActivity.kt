package com.yzq.customview.ui

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.ViewGroup
import android.widget.LinearLayout
import com.yzq.customview.R
import com.yzq.customview.R.id.viewPager
import com.yzq.customview.ui.fragment.PagerFragment
import com.yzq.customview.utils.LogUtils
import com.yzq.customview.widget.ColorText
import kotlinx.android.synthetic.main.activity_view_pager.*

class ViewPagerActivity : AppCompatActivity() {

    var titles = arrayListOf<String>("渭南新闻", "assdasda", "喻志强")

    var colorTexts= arrayListOf<ColorText>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_pager)


        initTablayout()
        initViewPager();

    }

    private fun initViewPager() {

        viewPager.adapter= object : FragmentPagerAdapter(supportFragmentManager) {
            override fun getItem(position: Int): Fragment {
                return PagerFragment.newInstance(titles[position])
            }

            override fun getCount(): Int {
               return titles.size
            }

        }

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

                LogUtils.i("position:${position},positionOffset：${positionOffset}")


                /*position 表示当前位置  默认是0  此时  应该是全红
                * 滑动时  左边慢慢变黑  直至全黑
                * */
                var left:ColorText = colorTexts[position]
                left.setDirection(ColorText.Direction.RIGHT_TO_LEFT)
                left.setProgress(1-positionOffset)

                try {
                    var right=colorTexts[position+1]
                    right.setDirection(ColorText.Direction.LEFT_TO_RIGHT)
                    right.setProgress(positionOffset)
                }catch (e:Exception){

                }



            }

            override fun onPageSelected(position: Int) {

            }

            override fun onPageScrollStateChanged(state: Int) {

            }

        })
    }

    private fun initTablayout() {

        for (title in titles) {

            LogUtils.i(title)
            var colorText = ColorText(this)
            var layParam = LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT)

            layParam.weight = 1f
            colorText.changeColor = Color.RED
            colorText.layoutParams = layParam
            colorText.setTextColor(Color.BLACK)
            colorText.textSize = 18f
            colorText.text = title

            colorText.gravity = Gravity.CENTER


            tableLayout.addView(colorText)
            colorTexts.add(colorText)

        }

    }
}
