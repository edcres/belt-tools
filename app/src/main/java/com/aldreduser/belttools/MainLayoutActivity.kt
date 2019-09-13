package com.aldreduser.belttools

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main_layout.*
import org.jetbrains.anko.toast
import android.text.Editable as Editable

// this app will have several calculating tools for work. Plus info guides

class MainLayoutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_layout)

        // get the square feet
        sqrtEqualsButton.setOnClickListener {
            // check if user input is a number
            var num1 = sqrtBox1.text.toString().toDouble()      // try to convert it straight to double, by skipping toString
            var num2 = sqrtBox2.text.toString().toDouble()

            sqrtBoxResult.text = (num1 * num2).toString()
        }

        // sqr foot to sqr in
        sqrFootToSqrInButton.setOnClickListener {
            // make sure user only types numbers
            if (sqrFootBox.text.isEmpty() && sqrInBox.text.isEmpty() ) {
                sqrFootBox.setText("")
                sqrInBox.setText("")
            } else if (sqrFootBox.text.isNotEmpty()) {
                // convert it to sqr in
            } else if (sqrInBox.text.isNotEmpty()) {
                // convert it to sqr ft
            } else {
                sqrFootToSqrInButton.setOnClickListener { toast("Something went wrong.") }
            }
        }


        // get tax
        plusTaxButton.setOnClickListener {
            var taxAmount = amountBox.text.toString().toDouble() * 0.07

            afterTaxBox.text = (amountBox.text.toString().toDouble() + taxAmount).toString()
        }

        // get blind width
        blindWidthEqualsButton.setOnClickListener {
            var window = windowWidthBox.text.toString().toDouble()
            var blindPre = blindWidthBox.text.toString().toDouble()
            var blindPro :Double = (window - blindPre) / 2

            blindWidthResult.text = blindPro.toString()
        }
    }
}
