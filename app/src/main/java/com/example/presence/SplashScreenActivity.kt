package com.example.presence

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager

class SplashScreenActivity : AppCompatActivity() {

    private var SPLASH_SCREEN_TIME : Long = 6500

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_splash_screen)


        Handler(Looper.myLooper()!!).postDelayed({

            intent= Intent(this,LoginActivity::class.java)
            startActivity(intent)

        }, SPLASH_SCREEN_TIME)

    }
}