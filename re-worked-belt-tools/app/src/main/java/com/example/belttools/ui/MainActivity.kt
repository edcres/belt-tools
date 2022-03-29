package com.example.belttools.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatDelegate
import com.example.belttools.R

/**
 * - Have in the navigation drawer
 *      - Specialty orders
 *
 *  In the beginning, user chooses which store code they are from (H6872)
 *  - that fist number is automatically added to the order number
 *
 *  Have the animation in the beginning
 *
 *  add an info icon explaining how to use each feature ***** (has pop up window the user can close)
 *
 * test the StartFragment features
 *
 * // make sure 2 order dont have the same order_num, maybe make this the primary key
 */

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        // I did this to fix a bug that makes the status bar grey.
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
    }
}