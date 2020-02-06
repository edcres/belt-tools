package com.aldreduser.belttools

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.activity_main_layout.*

class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        /*Handler().postDelayed(Runnable {
            kotlin.run {
                var i = Intent(MainLayoutActivity.this, HomeScreenActivity.class)
            }
        })*/
    }
}
