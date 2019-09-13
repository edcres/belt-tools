package com.aldreduser.belttools

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main_layout.*
import org.jetbrains.anko.toast
import java.lang.NumberFormatException
import android.text.Editable as Editable

// this app will have several calculating tools for work. Plus info guides
/**
 * TODO:
 *
 * make it so numbers delete when calculation is done and the button is clicked
 * how many backsplash pieces for linear feet or inches
 * sqr a to sqr b (more options than sqr foot to square in)
 * change toast background color to dark
 * make it so that there's a history of problems solved, and is deleted when the app is closed. (like the calculator app)
 * maybe make a function for resetting individual features. Leaving the boxes at ""
 *
 * learn to call code from other kotlin files in the project
 * learn to go to another window in the app
 * learn to change the color of a button without changing the borders (the gray part)
 */

class MainLayoutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_layout)

        //reset all
        button.setOnClickListener {
            sqrtBox1.setText(""); sqrtBox2.setText(""); sqrtBoxResult.text = "0"
            sqrFootBox.setText(""); sqrInBox.setText("")
            amountBox.setText(""); afterTaxBox.text = "0"
            windowWidthBox.setText(""); blindWidthBox.setText(""); blindWidthResult.text = "0"
        }

        // get the square feet
        sqrtEqualsButton.setOnClickListener {
            if (sqrtBox1.text.isNotEmpty() && sqrtBox2.text.isNotEmpty() && sqrtBoxResult.text.isNotEmpty()) {
                sqrtBox1.setText(""); sqrtBox2.setText(""); sqrtBoxResult.text = "0"
            }
            try {
                var num1 = sqrtBox1.text.toString().toDouble()      // try to convert it straight to double, by skipping toString
                var num2 = sqrtBox2.text.toString().toDouble()
                sqrtBoxResult.text = (num1 * num2).toString()
            } catch (e: NumberFormatException) {    // might not be the right exception
                toast("Maybe fill both boxes with numbers.")
            }
        }

        // sqr foot to sqr in sqrInBox
        sqrFootToSqrInButton.setOnClickListener {
            if (sqrFootBox.text.isNotEmpty() && sqrInBox.text.isNotEmpty() ) {
                sqrFootBox.setText("")
                sqrInBox.setText("")
            } else if (sqrFootBox.text.isNotEmpty()) {
                // convert it to sqr in
                var squareFt = sqrFootBox.text.toString().toDouble()
                var inches = Math.sqrt(squareFt) * 12
                var sqrIn = ( inches*inches ).toString()
                sqrInBox.setText(sqrIn)
            } else if (sqrInBox.text.isNotEmpty()) {
                // convert it to sqr ft
                var squareIn = sqrInBox.text.toString().toDouble()
                var feet = Math.sqrt(squareIn) / 12
                var sqrFt = ( feet*feet ).toString()
                sqrFootBox.setText(sqrFt)
            } else {
                sqrFootToSqrInButton.setOnClickListener { toast("Something isn't right.") }
            }
        }

        // get tax
        plusTaxButton.setOnClickListener {
            if (amountBox.text.isNotEmpty() && afterTaxBox.text.isNotEmpty()){
                amountBox.setText(""); afterTaxBox.text = "0"
            }
            var taxAmount = amountBox.text.toString().toDouble() * 0.07
            afterTaxBox.text = (amountBox.text.toString().toDouble() + taxAmount).toString()
        }

        // get blind width
        blindWidthEqualsButton.setOnClickListener {
            // if clicked again, reset to 0
            if (windowWidthBox.text.isNotEmpty() && blindWidthBox.text.isNotEmpty() && blindWidthResult.text.isNotEmpty()){
                windowWidthBox.setText(""); blindWidthBox.setText(""); blindWidthResult.text = "0"
            }
            try {
                var window = windowWidthBox.text.toString().toDouble()
                var blindPre = blindWidthBox.text.toString().toDouble()
                var blindPro :Double = (window - blindPre) / 2

                blindWidthResult.text = blindPro.toString()
            } catch (e: NumberFormatException) { // might not be the right exception
                toast("Maybe fill both boxes with numbers.")
            }
        }
    }
}
