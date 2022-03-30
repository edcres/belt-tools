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
 *
 *
 *
 * test:
 *      - StartFragment features
 *      - Specialty order fragment
 *      - Extensions fragment
 *      - Dept notes fragment
 *      - Skus to work on
 *      - Pallets to work on
 *
 *  Add an info icon explaining how to use each feature ***** (has pop up window the user can close)
 *
 *  Make sure 2 orders don't have the same order_num, maybe make this the primary key
 *
 *  Ask user if they are sure when making major decisions (like removing specialty orders)
 *
 *  Make queries in Helper more efficient (check todos)
 *
 *  User can click accept on the keyboard to save
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