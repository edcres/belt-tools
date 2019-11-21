package com.aldreduser.belttools

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_more_options_menu.*

/**
 * TODO:
 *
 * make phone extensions editable by the user
 * choose by departmetn button should be a drop-down menu
 *
 *  SKUs to work on: phone can scan barcode and save item code (hopefully read the sku too). Can keep notes of skus to work and what to do
 *  Pallets to work on: phone can scan barcode and save pallet code. Can keep notes of pallets to work and what to do
 *
 * make the home screen contact the more options screen through the button
 */

class MoreOptionsMenu : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_more_options_menu)

        extensionsButton.setOnClickListener {
            val newIntent = Intent(this, DeptExtensions::class.java) // maybe change 'newIntent' name
            startActivity(newIntent)
        }
    }
}
