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
 * limit the results at most a few decimal numbers
 *
 * optimization:
 * maybe make a function for resetting individual features. Leaving the boxes at ""
 * maybe make a function for try catch 'maybe fill both boxes with numbers'
 *
 * skills:
 * learn to call code from other kotlin files in the project
 * learn to go to another window in the app
 * learn to change the color of a button without changing the borders (the gray part)
 * learn about callin variables from function to function (is .setOnClickListener a function?)
 *
 */

class MainLayoutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_layout)

        // reset all
        resetAllButton.setOnClickListener {
            sqrtBox1.setText(""); sqrtBox2.setText(""); sqrtBoxResult.text = "0"
            sqrFootBox.setText(""); sqrInBox.setText("")
            amountBox.setText(""); afterTaxBox.text = "0"
            windowWidthBox.setText(""); blindWidthBox.setText(""); blindWidthResult.text = "0"
            homeSqrFt.setText(""); boxSqrFt.setText(""); tileBoxResultsButton.setText("Boxes")
            bakShWidthBox.setText(""); linealSpaceBox.setText(""); cutOutsBox.setText(""); bakShResultsBox.text = "0"
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
        // limit the result to 4 numbers after the decimal
        // has the last few results side to side  (house sqft, tileBox sqft, button-results are displayed in the button-)
        // hold boxes button to add up all boxes and display them
        // use an array for the number of results displayed in the button (or round it up)
        // try to decrease boxes button height
        tileBoxResultsButton.setOnClickListener {
            if (homeSqrFt.text.isNotEmpty() && boxSqrFt.text.isNotEmpty() && tileBoxResultsButton.text != "boxes") {
                homeSqrFt.setText(""); boxSqrFt.setText(""); tileBoxResultsButton.setText("boxes")
            } else {
                try {
                    homeSqrFt.setOnClickListener {homeSqrFt.setText("")}
                    boxSqrFt.setOnClickListener {boxSqrFt.setText("")}

                    var totalSqrFeet = homeSqrFt.text.toString().toDouble()
                    var boxSqrFeet = boxSqrFt.text.toString().toDouble()
                    var numOfBoxes = totalSqrFeet / boxSqrFeet

                    tileBoxResultsButton.text = numOfBoxes.toString()
                } catch (e: NumberFormatException) {
                    toast("Maybe fill both boxes with numbers.")
                }
            }
        }

        // Lineal Backsplash
        // add functionality to ask if the given lineal length is ft or in
        var bakShWidthBoxTouched = false
        bakShWidthBox.setOnClickListener { bakShWidthBoxTouched = true }
        bakShEqualsButton.setOnClickListener {
            if (linealSpaceBox.text.isNotEmpty() && cutOutsBox.text.isNotEmpty() && bakShResultsBox.text != "0") {
                // might be a bug here with bakShWidthBox. baksplashwidthBox can be full
                bakShWidthBox.setText(""); linealSpaceBox.setText(""); cutOutsBox.setText(""); bakShResultsBox.text = "0"
            } else {
                try {
                    var bakShWidth = 12.0
                    if (bakShWidthBoxTouched) {
                        var bakShWidth = bakShWidthBox.text.toString().toDouble()
                    }

                    var linealSpace = linealSpaceBox.text.toString().toDouble()
                    var cutOuts = cutOutsBox.text.toString().toDouble()
                    var bakShResults = bakShResultsBox.text.toString().toDouble()

                    bakShResults = linealSpace/(bakShWidth*cutOuts)
                    bakShResultsBox.text = bakShResults.toString()
                } catch (e: NumberFormatException) {
                    toast("Make sure the boxes are filled.")
                }
            }
        }
    }
}
