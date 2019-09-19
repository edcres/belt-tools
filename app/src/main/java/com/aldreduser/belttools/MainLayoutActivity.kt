package com.aldreduser.belttools

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main_layout.*
import org.jetbrains.anko.toast
import java.lang.NumberFormatException

// this app will have several calculating tools for work. Plus info guides
/**
 * TODO:
 *
 * features:
 * sqr a to sqr b (more options than sqr foot to square in)
 * make it so that there's a history of problems solved, and is deleted when the app is closed. (like the calculator app)
 * make the entire screen slidable
 *
 * ui:
 * constraint to the top of the app (title bar), rn i only have a quick fix
 * change toast background color to dark
 *
 * optimization:
 * maybe make a function for resetting individual features. Leaving the boxes at ""
 * maybe make a function for try catch 'maybe fill both boxes with numbers'
 *
 * skills:
 * learn to call code from other kotlin files in the project
 * learn to go to another window in the app
 * learn to change the color of a button without changing the borders (the gray part)
 *
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
            if (sqrtBox1.text.isNotEmpty() && sqrtBox2.text.isNotEmpty() && (sqrtBoxResult.text != "0")) {
                sqrtBox1.setText(""); sqrtBox2.setText(""); sqrtBoxResult.text = "0"
            } else {
                try {
                    var num1 = sqrtBox1.text.toString().toDouble()      // try to convert it straight to double, by skipping toString
                    var num2 = sqrtBox2.text.toString().toDouble()
                    sqrtBoxResult.text = (num1 * num2).toString()
                } catch (e: NumberFormatException) {    // might not be the right exception
                    toast("Maybe fill both boxes with numbers.")
                }
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
                var sqrIn = ( inches*inches ).toString() //this line is the only difference between this if brackets and the ones below (make it a function)
                sqrInBox.setText(sqrIn)
            } else if (sqrInBox.text.isNotEmpty()) {
                // convert it to sqr ft
                var squareIn = sqrInBox.text.toString().toDouble()
                var feet = Math.sqrt(squareIn) / 12
                var sqrFt = ( feet*feet ).toString()
                sqrFootBox.setText(sqrFt)
            } else {
                toast("Maybe fill a box with numbers.")
            }
        }

        // get tax
        plusTaxButton.setOnClickListener {
            if (amountBox.text.isNotEmpty() && afterTaxBox.text != "0"){
                amountBox.setText(""); afterTaxBox.text = "0"
            } else {
                try {
                    var taxAmount = amountBox.text.toString().toDouble() * 0.07
                    afterTaxBox.text = (amountBox.text.toString().toDouble() + taxAmount).toString()
                } catch (e: NumberFormatException) {
                    toast("Maybe fill the box with numbers.")
                }
            }
        }

        // get blind width
        blindWidthResult.setOnClickListener{
            toast("Cut on each side")
        }
        blindWidthEqualsButton.setOnClickListener {
            if (windowWidthBox.text.isNotEmpty() && blindWidthBox.text.isNotEmpty() && blindWidthResult.text != "0"){
                windowWidthBox.setText(""); blindWidthBox.setText(""); blindWidthResult.text = "0"
            } else {
                try {
                    var window = windowWidthBox.text.toString().toDouble()
                    var blindPre = blindWidthBox.text.toString().toDouble()
                    var blindPro :Double = (window - blindPre) / 2

                    blindWidthResult.text = blindPro.toString()
                } catch (e: NumberFormatException) {
                    toast("Maybe fill both boxes with numbers.")
                }
            }
        }

        // get number of boxes to buy
        // has the last few results side to side  (house sqft, tileBox sqft, button-results are displayed in the button-)
        // use an array for the number of results displayed in the button
        // add error handling to make sure user has both boxes filled
        // reset when button is clicked again
        resultsButton.setOnClickListener {
            var totalSqrFeet = homeSqrFt.text.toString().toDouble()
            var boxSqrFeet = boxSqrFt.text.toString().toDouble()
            var numOfBoxes = totalSqrFeet / boxSqrFeet

            resultsButton.text = numOfBoxes.toString()
        }

        // Lineal Backsplash
        // how many backsplash pieces for linear feet or inches
        // add functionality to ask if the given lineal length is ft or in
        // backsplash width is 12 by deafult, unless user clicks on it
        // add error handling to make sure user has 3 boxes filled
        // reset when button is clicked again
        bakShEqualsButton.setOnClickListener{
            var linealSpace = linealSpaceBox.text.toString().toDouble()
            var bakShWidth = bakShWidthBox.text.toString().toDouble()
            var cutOuts = cutOutsBox.text.toString().toDouble()
            var bakShResults = bakShResultsBox.text.toString().toDouble()

            bakShResults = linealSpace/(bakShWidth*cutOuts)     // this might be wrong
            bakShResultsBox.text = bakShResults.toString()

        }
    }
}
