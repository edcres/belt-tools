package com.example.belttools.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatDelegate
import com.example.belttools.R

/** todo:
 *
 *  Have the animation in the beginning
 *
 *  Don't have an edit mode btn on specialty orders fragment.
 *
 *  Maybe change the edit mode icon color
 *
 *
 *
 *
 *  Add an info icon explaining how to use each feature ***** (has pop up window the user can close)
 *
 *  test:
 *      - StartFragment features
 *      - Specialty order fragment
 *      - Extensions fragment
 *      - Dept notes fragment
 *      - Skus to work on
 *      - Pallets to work on
 *
 *  Make sure 2 order don't have the same order_num, maybe make this the primary key
 *
 *  Ask user if they are sure when making major decisions (like removing specialty orders)
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