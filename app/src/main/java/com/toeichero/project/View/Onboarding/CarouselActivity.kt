package com.toeichero.project.View.Onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.viewpager.widget.ViewPager
import com.toeichero.project.R
import kotlinx.android.synthetic.main.activity_carousel.*
import com.toeichero.project.Service.BackgroundMusic


class CarouselActivity : AppCompatActivity() {

    override fun onPause() {
        super.onPause()
        val myService = Intent(this, BackgroundMusic::class.java)
        myService.setAction("PAUSE")
        startService(myService)
    }

    override fun onResume() {
        super.onResume()
        val myService = Intent(this, BackgroundMusic::class.java)
        myService.setAction("PLAY")
        startService(myService)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carousel)

        viewPager.adapter = ViewPagerOnboardingAdapter(supportFragmentManager)
        btn_next_onboarding.setOnClickListener { viewPager.arrowScroll(View.FOCUS_RIGHT) }

        val status = intent.getIntExtra("Status",0)
        if(status == 1){
            viewPager.setCurrentItem(2)
            indicator.setSelection(2)
            btn_next_onboarding.visibility = View.GONE
        }

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                when(position){
                    0 -> btn_next_onboarding.visibility = View.VISIBLE
                    1 -> btn_next_onboarding.visibility = View.VISIBLE
                    else -> btn_next_onboarding.visibility = View.GONE
                }
            }

            override fun onPageScrollStateChanged(state: Int) {

            }

        })
    }

    override fun onBackPressed() {
        val a = Intent(Intent.ACTION_MAIN)
        a.addCategory(Intent.CATEGORY_HOME)
        a.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(a)
    }
}