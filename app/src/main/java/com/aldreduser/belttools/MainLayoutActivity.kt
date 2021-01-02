package com.aldreduser.belttools

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.activity_main_layout.*


/*
TODO
 theres probably another way to make the launch screen. In my apps, a whit screen is shown first,
 in famous apps, the launch screen is shown first
 */

class MainLayoutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_layout)

//        supportActionBar!!.hide()

        ic_logo.startAnimation(AnimationUtils.loadAnimation(this, R.anim.splash_in))
        Handler().postDelayed({
            ic_logo.startAnimation(AnimationUtils.loadAnimation(this, R.anim.splash_out))
            Handler().postDelayed({
                ic_logo.visibility = View.GONE
                startActivity(Intent(this, HomeScreenActivity::class.java))
                finish()
            }, 500)
        }, 1500/*1.5 secs*/)
    }
}
