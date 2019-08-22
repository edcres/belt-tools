package com.aldreduser.belttools

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main_layout.*

// this app will have several calculating tools for work. Plus info guides

class MainLayoutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_layout)

        // get the square feet
        sqrtEqualsButton.setOnClickListener {
            // check if user input is a number
            var num1 = sqrtBox1.text.toString().toDouble()      // try to convert it straight to double, by skipping string
            var num2 = sqrtBox2.text.toString().toDouble()

            sqrtBoxResult.text = (num1 * num2).toString()
        }

        // sqr foot to sqr in
        // have to figure out how to display text on the Plain Text object


        // get tax
        plusTaxButton.setOnClickListener {
            var taxAmount = amountBox.text.toString().toDouble() * 0.07

            afterTaxBox.text = (amountBox.text.toString().toDouble() + taxAmount).toString()
        }
    }
}
