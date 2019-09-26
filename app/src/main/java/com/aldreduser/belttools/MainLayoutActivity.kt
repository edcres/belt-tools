package com.aldreduser.belttools

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main_layout.*
import org.jetbrains.anko.toast
import java.lang.NumberFormatException
import java.lang.StringBuilder

// make sure no company private information is made public by the software developer's actions
// this app will have several calculating tools for work. Plus info guides
/**
 * TODO: make the app icon **********
 *
 * features:
 * lineal feet to square yard (price of carpet calculator)
 * sqr a to sqr b (more measurement options than sqr foot to square in)
 * make it so that there's a history of problems solved, and is deleted when the app is closed. (like the calculator app)
 * add an info icon explaining how to use each feature
 * pt2:
 * (flooring, appliances, pro desk exports) product info (info stored in phone) (get info from the work notebook)
 * phone extensions of other stores
 * click button when enter is pressed on the phone keyboard
 * virtual reality tape measurer
 * image recognition of items (or integrate it with the home depot's app)
 *
 * ui:
 * change toast background color to dark
 * organize and number the different features in the main page (eventually use picture icons instead of numbers)
 *
 *
 * optimization:
 * maybe make a function for resetting individual features. Leaving the boxes at ""
 * maybe make a function for try catch 'maybe fill both boxes with numbers'
 * create function in another file to convert measurements (call them in this file)
 * count how many time i use each feature on my app, to what which feature to put where (make a radio to check if eah count is real or for a test)
 *
 * skills:
 * learn to call code from other kotlin files in the project (like creating an object in jave)
 * learn to change the color of a button without changing the borders (the gray part)
 * learn about calling variables from function to function (is .setOnClickListener a function?)
 *
 */

class MainLayoutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_layout)

        val resultsStringBuilder = StringBuilder()
        var boxesResults = 0
        val boxesResultsArray = mutableListOf<Double>()

        // reset all
        resetAllButton.setOnClickListener {
            // make each into a function and then call: reset.nameOfFunction
            sqrtBox1.setText(""); sqrtBox2.setText(""); sqrtBoxResult.text = "0"
            sqrFootBox.setText(""); sqrInBox.setText("")
            amountBox.setText(""); afterTaxBox.text = "0"
            windowWidthBox.setText(""); blindWidthBox.setText(""); blindWidthResult.text = "0"
            homeSqrFt.setText(""); boxSqrFt.setText(""); tileBoxResultsButton.text = "Boxes"; boxesResults = 0; resultsStringBuilder.clear()
            bakShWidthBox.setText(""); linealSpaceBox.setText(""); cutOutsBox.setText(""); bakShResultsBox.text = "0"
        }

        // more options
        // open a new activity when this is clicked
        moreOptionsButton.setOnClickListener {
            toast("It's not ready yet.")
        }

        // get the square feet
        sqrtEqualsButton.setOnClickListener {
            if (sqrtBox1.text.isNotEmpty() && sqrtBox2.text.isNotEmpty() && (sqrtBoxResult.text != "0")) {
                sqrtBox1.setText(""); sqrtBox2.setText(""); sqrtBoxResult.text = "0"
            } else {
                try {
                    var num1 = sqrtBox1.text.toString().toDouble()
                    var num2 = sqrtBox2.text.toString().toDouble()
                    var result = num1 * num2
                    sqrtBoxResult.text = "%.3f".format(result)
                } catch (e: NumberFormatException) {
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
                // converts it to sqr in
                var squareFt = sqrFootBox.text.toString().toDouble()
                var inches = Math.sqrt(squareFt) * 12
                var sqrIn =  inches*inches  //this line is the only difference between this if brackets and the ones below (make it a function)
                sqrInBox.setText("%.3f".format(sqrIn))
            } else if (sqrInBox.text.isNotEmpty()) {
                // converts it to sqr ft
                var squareIn = sqrInBox.text.toString().toDouble()
                var feet = Math.sqrt(squareIn) / 12
                var sqrFt = feet*feet
                sqrFootBox.setText("%.3f".format(sqrFt))
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
                    var result = (amountBox.text.toString().toDouble() + taxAmount)
                    afterTaxBox.text = "%.3f".format(result)
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
                    var blindPro = (window - blindPre) / 2
                    blindWidthResult.text = "%.3f".format(blindPro)
                } catch (e: NumberFormatException) {
                    toast("Maybe fill both boxes with numbers.")
                }
            }
        }

        // get number of boxes to buy
        tileBoxResultsButton.setOnClickListener {
            if (homeSqrFt.text.isEmpty() && boxSqrFt.text.isEmpty() && tileBoxResultsButton.text != "boxes") {
                homeSqrFt.setText(""); boxSqrFt.setText(""); tileBoxResultsButton.setText("boxes"); boxesResults = 0; resultsStringBuilder.clear()
            } else {
                try {
                    homeSqrFt.setOnClickListener { homeSqrFt.setText("") }
                    boxSqrFt.setOnClickListener { boxSqrFt.setText("") }

                    var totalSqrFeet = homeSqrFt.text.toString().toDouble()
                    var boxSqrFeet = boxSqrFt.text.toString().toDouble()
                    var numOfBoxes = totalSqrFeet/boxSqrFeet

                    if (boxesResults < 7) {
                        resultsStringBuilder.append("%.2f".format(numOfBoxes))
                        boxesResultsArray.add(numOfBoxes)
                        if (boxesResults < 6) { resultsStringBuilder.append(" + ") }
                        boxesResults ++
                    } else {
                        toast("Limit reached.")
                    }

                    tileBoxResultsButton.text = resultsStringBuilder
                } catch (e: NumberFormatException) {
                    toast("Maybe fill both boxes with numbers.")
                }
            }
        }
        tileBoxResultsButton.setOnLongClickListener {
            val sumOfAllResults = "%.2f".format(boxesResultsArray.sum())
            toast(sumOfAllResults)
            return@setOnLongClickListener true
        }

        // Lineal Backsplash
        // add functionality to ask if the given lineal length is ft or in
        var bakShWidthBoxTouched = false
        bakShWidthBox.setOnClickListener { bakShWidthBoxTouched = true }
        bakShEqualsButton.setOnClickListener {
            if (linealSpaceBox.text.isNotEmpty() && cutOutsBox.text.isNotEmpty() && bakShResultsBox.text != "0") {
                bakShWidthBox.setText(""); linealSpaceBox.setText(""); cutOutsBox.setText(""); bakShResultsBox.text = "0"
            } else {
                try {
                    // width = 12 by default
                    var bakShWidth = 12.0
                    if (bakShWidthBoxTouched) {
                        var bakShWidth = bakShWidthBox.text.toString().toDouble()
                    }

                    var linealSpace = linealSpaceBox.text.toString().toDouble()
                    var cutOuts = cutOutsBox.text.toString().toDouble()
                    var bakShResults = linealSpace/(bakShWidth*cutOuts)
                    bakShResultsBox.text = "%.3f".format(bakShResults)
                } catch (e: NumberFormatException) {
                    toast("Make sure the boxes are filled.")
                }
            }
        }
    }
}


//func to turn ft. to in., and vice versa
/*
ask if var comming is in ft or in. (from the drop down menu)
take it and turn it into the other
    multiply or divide by 12
return the result
 */