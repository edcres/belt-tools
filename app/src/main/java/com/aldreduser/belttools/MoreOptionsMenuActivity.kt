package com.aldreduser.belttools

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.aldreduser.belttools.choosedepartment.ChooseDepartmentActivity
import kotlinx.android.synthetic.main.activity_more_options_menu.*

/**
 * TODO:
 *
 *  SKUs to work on: phone can scan barcode and save item code (hopefully read the sku too). Can keep notes of skus to work and what to do
 *  Pallets to work on: phone can scan barcode and save pallet code. Can keep notes of pallets to work and what to do
 *  ^^^ copy to clipboard (so it can be pasted to the store app)
 *
 * make the home screen contact the more options screen through the button
 */

class MoreOptionsMenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_more_options_menu)

        extensionsButton.setOnClickListener {
            val newIntent = Intent(this, DeptExtensionsActivity::class.java) // maybe change 'newIntent' name
            startActivity(newIntent)
        }

        chooseDptButton.setOnClickListener {
            val newIntent = Intent(this, ChooseDepartmentActivity::class.java)
            startActivity(newIntent)
        }
    }
}
