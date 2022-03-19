package com.example.belttools.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import com.example.belttools.R

/**
 *
 * - set up the viewModel
 * - make the functions in the StartFragment
 * - make the database
 * - make the navigation drawer
 * - Have the navigation drawer
 *      - Department Extensions
 *      - Specialty orders
 *      - Skus to work on
 *      - Pallets to work on
 *      - Notes (spinner in which user can choose each department)
 *
 *  In the beginning, user chooses which store code they are from (H6872)
 *  - that fist number is automatically added to the order number
 *
 *  Have the animation in the beginning
 *
 */

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
}