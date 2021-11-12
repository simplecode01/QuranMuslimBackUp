package com.simplecode01.quranmuslim

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.button.MaterialButton
import com.simplecode01.quranmuslim.firebase.Login
import com.simplecode01.quranmuslim.onboarding.OnboardingItem
import com.simplecode01.quranmuslim.onboarding.OnboardingItemsAdapter
import com.simplecode01.quranmuslim.save.SaveSharedPreferences

class OnBoarding : AppCompatActivity() {

    private lateinit var onboardingItemsAdapter: OnboardingItemsAdapter
    private lateinit var indicatorContainer: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding)
        setOnboardingItems()
        setupIndicators()
        setCurrentIndicator(0)
    }
    private fun setOnboardingItems(){
        onboardingItemsAdapter = OnboardingItemsAdapter(
            listOf(
                OnboardingItem(
                    onboardingImage = R.drawable.logo,
                    title = "Assalamualaikum",
                    descripstion = "Selamat datang di aplikasi Quran Muslim"
                ),
                OnboardingItem(
                    onboardingImage = R.drawable.image1,
                    title = "Random things",
                    descripstion = "Random things"
                ),
                OnboardingItem(
                    onboardingImage = R.drawable.image1,
                    title = "work things",
                    descripstion = "work things"
                ),
                OnboardingItem(
                    onboardingImage = R.drawable.image1,
                    title = "work things",
                    descripstion = "work things"
                )
            )
        )
        val onboardingViewPage = findViewById<ViewPager2>(R.id.onBoardingViewPager)
        onboardingViewPage.adapter = onboardingItemsAdapter
        onboardingViewPage.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentIndicator(position)
            }
        }
        )
        (onboardingViewPage.getChildAt(0) as RecyclerView).overScrollMode =
            RecyclerView.OVER_SCROLL_NEVER
        findViewById<ImageView>(R.id.img_next).setOnClickListener {
            if(onboardingViewPage.currentItem + 1 <onboardingItemsAdapter.itemCount){
                onboardingViewPage.currentItem +=1
            }else{
                navigateToMainActivity()
                SaveSharedPreferences(this).onBoard = 1
            }
        }
        findViewById<MaterialButton>(R.id.buttonGetStarted).setOnClickListener {
            navigateToMainActivity()
            SaveSharedPreferences(this).onBoard = 1
        }
    }
    private fun setupIndicators(){
        indicatorContainer = findViewById(R.id.idicatorContainer)
        val indicators = arrayOfNulls<ImageView>(onboardingItemsAdapter.itemCount)
        val layoutParams: LinearLayout.LayoutParams =
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        layoutParams.setMargins(8,0,8,0)
        for(i in indicators.indices){
            indicators[i] = ImageView(applicationContext)
            indicators[i]?.let {
                it.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_inactive_background
                    )
                )
                it.layoutParams = layoutParams
                indicatorContainer.addView(it)
            }
        }
    }
    private fun setCurrentIndicator(position: Int){
        val childCount = indicatorContainer.childCount
        for(i in 0 until childCount){
            val imageView = indicatorContainer.getChildAt(i) as ImageView
            if(i == position){
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_active_background
                    )
                )
            }else{
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_inactive_background
                    )
                )
            }
        }
    }
    private fun navigateToMainActivity(){
        startActivity(Intent(this, Login::class.java))
        finish()
    }
}