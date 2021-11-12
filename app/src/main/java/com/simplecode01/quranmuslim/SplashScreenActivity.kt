package com.simplecode01.quranmuslim

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import com.simplecode01.quranmuslim.firebase.Login
import com.simplecode01.quranmuslim.save.SaveSharedPreferences

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        //use for hide status bar and make full screen for splash screen
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        // we used the postDelayed(Runnable, time) method
        // to send a message with a delayed time.
        if(SaveSharedPreferences(this).onBoard == 0){
            Handler().postDelayed({
                val intent = Intent(this, OnBoarding::class.java)
                startActivity(intent)
                finish()
            }, 3000)
        }else{
            Handler().postDelayed({
                val intent = Intent(this, Login::class.java)
                startActivity(intent)
                finish()
            }, 3000)
        } // 3000 is the delayed time in milliseconds.
    }
}
