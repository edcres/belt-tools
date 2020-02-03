package com.aldreduser.belttools

import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.KeyEvent
import android.view.View
import android.widget.Button
import com.aldreduser.belttools.extra.displayToastMessage
import kotlinx.android.synthetic.main.activity_home_screen.*
import java.lang.NumberFormatException
import java.lang.StringBuilder
import kotlin.math.abs
import kotlin.math.floor
import kotlin.math.sqrt

// make sure no company private information is made public by the software developer's actions
// this app will have several calculating tools for work. Plus info guides
/**
 * TODO:
 *
 * features:
 * clean up code (including warnings)
 * price per sqrft of tile
 * how much sqr footage in each room
 * add an info icon explaining how to use each feature ***** (has pop up window the user can close)
 * each department in choose department should be an object and displayed in the same activity, (not each have its own activity)
 * put everything into a recyclerview
 * (flooring, appliances, pro desk exports) product info (info stored in phone) (get info from the work notebook)
 * fix warnings
 * box say where the magnet is (and where it was last seen)
 * choose by department button (in more options activity) should be a drop-down menu
 * how many louvers will a vertical blind need
 *
 * pt2 (features):
 * sqr a to sqr b (more measurement options than sqr foot to square in)
 *  * create function in another file to convert measurements (call them in this file)
 * make it so that there's a history of problems solved, and is deleted when the app is closed. (like the calculator app, restart when the app is closed)
 * virtual reality tape measurer
 * cut wire shelves with the least waste possible. Given the customer's measurements
 * user can pick different features from each department and choose what to display in the home screen (each feature would be in their respective department classes/files)
 *
 * ui:
 * make are you sure pop up darker
 * make toast edges round
 * organize and number the different features in the main page (eventually use picture icons instead of numbers)
 * make buttons look pretty
 * rounded edges in buttons, and smokey layouts
 *
 * optimization:
 * maybe make a function for try catch 'maybe fill both boxes with numbers' (probably delete almost all try catches)
 * count how many times i use each feature on my app, to what which feature to put where (make a radio button to check if eah count is real or for a test)
 *
 * skills:
 * learn to call code from other kotlin files in the project (like creating an object in java)
 * learn to change the color of a button without changing the borders (the gray part)
 * idk what ACTION_UP or ACTION_DOWN means
 *
 */

class HomeScreenActivity : AppCompatActivity() {

    // these are here bc there are 2 listener blocks using them
    private val resultsStringBuilder = StringBuilder()
    private var boxesResults = 0
    private val boxesResultsArray = mutableListOf<Double>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)

        //1 get the square feet
        // todo: add sqrft per room at the bottom of the layout
        // make it so when the box is clicked (with sqft per room), it's added to 'num of boxes',
        sqrtEqualsButton.setOnClickListener {
            if (sqrtBox1.text.isNotEmpty() && sqrtBox2.text.isNotEmpty() && (sqrtBoxResult.text != "0")) {
                sqrtBox1.setText(""); sqrtBox2.setText(""); sqrtBoxResult.text = "0"
            } else {
                try {
                    val num1 = sqrtBox1.text.toString().toDouble()
                    val num2 = sqrtBox2.text.toString().toDouble()
                    val result = num1*num2

                    sqrtBoxResult.text = offExtraZeros("%.3f", result)

                } catch (e: NumberFormatException) {
                    displayToastMessage(this, "Maybe fill both boxes with numbers.")
                }
            }
        }
        sqrtBoxResult.setOnClickListener{sqrtBoxResult.text = "0"}
        sqrtBox1.setOnClickListener{sqrtBox1.setText("")}
        sqrtBox2.setOnClickListener{sqrtBox2.setText("")}
        // click sqrtEqualsButton when user presses enter in box 1 or 2
        sqrtBox1.setOnKeyListener { _, keyCode, event -> pressedEnter(sqrtEqualsButton, keyCode, event) }
        sqrtBox2.setOnKeyListener { _, keyCode, event -> pressedEnter(sqrtEqualsButton, keyCode, event) }

        //2 get number of boxes to buy
        tileBoxResultsButton.setOnClickListener {
            if (homeSqrFt.text.isEmpty() && boxSqrFt.text.isEmpty() && tileBoxResultsButton.text != "boxes") {
                homeSqrFt.setText(""); boxSqrFt.setText(""); tileBoxResultsButton.text = "boxes"; boxesResults = 0; resultsStringBuilder.clear()
                boxesResultsArray.clear()
            } else {
                try {
                    val totalSqrFeet = homeSqrFt.text.toString().toDouble()
                    val boxSqrFeet = boxSqrFt.text.toString().toDouble()
                    val numOfBoxes = totalSqrFeet/boxSqrFeet

                    if (boxesResults < 6) {
                        resultsStringBuilder.append(offExtraZeros("%.2f", numOfBoxes))
                        boxesResultsArray.add(numOfBoxes)
                        if (boxesResults < 5) { resultsStringBuilder.append(" + ") }
                        boxesResults ++
                    } else {
                        displayToastMessage(this, "Limit reached.")
                    }

                    tileBoxResultsButton.text = resultsStringBuilder
                } catch (e: NumberFormatException) {
                    displayToastMessage(this, "Maybe fill both boxes with numbers.")
                }
            }
        }
        tileBoxResultsButton.setOnLongClickListener {
            val sumOfAllResults = offExtraZeros("%.2f", boxesResultsArray.sum())
            displayToastMessage(this, sumOfAllResults)
            return@setOnLongClickListener true
        }
        homeSqrFt.setOnClickListener { homeSqrFt.setText("") }
        boxSqrFt.setOnClickListener { boxSqrFt.setText("") }
        // click Button when user presses enter in box 1 or 2
        homeSqrFt.setOnKeyListener { _, keyCode, event -> pressedEnter(tileBoxResultsButton, keyCode, event) }
        boxSqrFt.setOnKeyListener { _, keyCode, event -> pressedEnter(tileBoxResultsButton, keyCode, event) }

        //3 sqr foot to sqr in sqrInBox
        sqrFootToSqrInButton.setOnClickListener {
            if (sqrFootBox.text.isNotEmpty() && sqrInBox.text.isNotEmpty() ) {
                sqrFootBox.setText("")
                sqrInBox.setText("")
            } else if (sqrFootBox.text.isNotEmpty()) {
                // converts it to sqr in
                val squareFt = sqrFootBox.text.toString().toDouble()
                val inches = sqrt(squareFt) * 12
                val sqrIn =  inches*inches  //this line is the only difference between this if brackets and the ones below (make it a function)
                sqrInBox.setText(offExtraZeros("%.3f", sqrIn))
            } else if (sqrInBox.text.isNotEmpty()) {
                // converts it to sqr ft
                val squareIn = sqrInBox.text.toString().toDouble()
                val feet = sqrt(squareIn) / 12
                val sqrFt = feet*feet
                sqrFootBox.setText(offExtraZeros("%.3f", sqrFt))
            } else {
                displayToastMessage(this, "Maybe fill a box with numbers.")
            }
        }
        sqrFootBox.setOnClickListener{sqrFootBox.setText("")}
        sqrInBox.setOnClickListener {sqrInBox.setText("")}
        // click Button when user presses enter in box 1 or 2
        sqrFootBox.setOnKeyListener { _, keyCode, event -> pressedEnter(sqrFootToSqrInButton, keyCode, event) }
        sqrInBox.setOnKeyListener { _, keyCode, event -> pressedEnter(sqrFootToSqrInButton, keyCode, event) }

        //4 get blind width
        blindWidthEqualsButton.setOnClickListener {
            if (windowWidthBox.text.isNotEmpty() && blindWidthBox.text.isNotEmpty() && blindWidthResult.text != "0"){
                windowWidthBox.setText(""); blindWidthBox.setText(""); blindWidthResult.text = "0"
            } else {
                try {
                    val window = windowWidthBox.text.toString().toDouble()
                    val blindPre = blindWidthBox.text.toString().toDouble()
                    val blindPro = (window - blindPre) / 2
                    blindWidthResult.text = offExtraZeros("%.3f", blindPro)
                } catch (e: NumberFormatException) {
                    displayToastMessage(this, "Maybe fill both boxes with numbers.")
                }
            }
        }
        blindWidthResult.setOnClickListener{blindWidthResult.text = "0"}
        // todo    displayToastMessage(this, "Cut on each side") } (when info button is made)
        windowWidthBox.setOnClickListener { windowWidthBox.setText("") }
        blindWidthBox.setOnClickListener { blindWidthBox.setText("") }
        // click Button when user presses enter in box 1 or 2
        windowWidthBox.setOnKeyListener { _, keyCode, event -> pressedEnter(blindWidthEqualsButton, keyCode, event) }
        blindWidthBox.setOnKeyListener { _, keyCode, event -> pressedEnter(blindWidthEqualsButton, keyCode, event) }

        //5 decimal to fraction
        decimalToFractionButton.setOnClickListener {
            val decimalNum:Double
            val completeFraction:String

            if (decimalBox.text.isNotEmpty() && fractionBox.text.isNotEmpty()) {
                decimalBox.setText(""); fractionBox.setText("")
            } else if (decimalBox.text.isNotEmpty() && fractionBox.text.isEmpty()) {
                decimalNum = decimalBox.text.toString().toDouble()
                completeFraction = decimalToFraction(decimalNum)
                fractionBox.setText(completeFraction)
            } else if (decimalBox.text.isEmpty() && fractionBox.text.isNotEmpty()) {
                if (fractionBox.text.contains("/")) {
                    completeFraction = fractionBox.text.toString()
                    val numerator = completeFraction.substringBeforeLast("/").toDouble()
                    val denominator = completeFraction.substringAfterLast("/").toDouble()
                    decimalBox.setText(offExtraZeros("%.3f", numerator/denominator))
                }else {displayToastMessage(this, "Write a fraction.")}
            }
        }
        decimalBox.setOnClickListener { decimalBox.setText("") }
        fractionBox.setOnClickListener { fractionBox.setText("") }
        // click button when user presses enter
        decimalBox.setOnKeyListener { _, keyCode, event -> pressedEnter(decimalToFractionButton, keyCode, event) }
        fractionBox.setOnKeyListener { _, keyCode, event -> pressedEnter(decimalToFractionButton, keyCode, event) }

        //6 lineal ft to square yard
        linealFtToSqrYardButton.setOnClickListener {
            //val widthFt = 12
            val widthYd = 4
            if (linealFtBox.text.isNotEmpty() && sqrYardBox.text.isNotEmpty()) {
                linealFtBox.setText(""); sqrYardBox.setText("")
            } else if (linealFtBox.text.isNotEmpty() && sqrYardBox.text.isEmpty()) {
                val linealFeet = linealFtBox.text.toString().toDouble()
                val linealYard = (linealFeet/3) //squared
                val sqrYard = linealYard * widthYd
                sqrYardBox.setText(offExtraZeros("%.3f", sqrYard))
            } else if (linealFtBox.text.isEmpty() && sqrYardBox.text.isNotEmpty()) {
                val sqrYard = sqrYardBox.text.toString().toDouble()
                val linealYard = sqrYard/widthYd
                val linealFeet = linealYard*3
                linealFtBox.setText(offExtraZeros("%.3f", linealFeet))
            }
        }
        linealFtBox.setOnClickListener { linealFtBox.setText("") }
        sqrYardBox.setOnClickListener { sqrYardBox.setText("") }
        // click button when user presses enter
        linealFtBox.setOnKeyListener { _, keyCode, event -> pressedEnter(linealFtToSqrYardButton, keyCode, event) }
        sqrYardBox.setOnKeyListener { _, keyCode, event -> pressedEnter(linealFtToSqrYardButton, keyCode, event) }

        //7 Lineal Backsplash
        // add functionality to ask if the given lineal length is ft or in
        bakShEqualsButton.setOnClickListener {
            if (linealSpaceBox.text.isNotEmpty() && cutOutsBox.text.isNotEmpty() && bakShResultsBox.text != "0") {
                bakShWidthBox.setText(""); linealSpaceBox.setText(""); cutOutsBox.setText(""); bakShResultsBox.text = "0"
            } else {
                try {
                    // width = 12 by default
                    val bakShWidth:Double = if (bakShWidthBox.text.isEmpty()) 12.0 else bakShWidthBox.text.toString().toDouble()

                    val linealSpace = getFeetToInch() // hopefully this is right
                    val cutOuts = cutOutsBox.text.toString().toDouble()
                    val bakShResults = linealSpace/(bakShWidth*cutOuts)
                    bakShResultsBox.text = offExtraZeros("%.3f", bakShResults)
                } catch (e: NumberFormatException) {
                    displayToastMessage(this, "Make sure the boxes are filled.") }
            }
        }
        bakShResultsBox.setOnClickListener{bakShResultsBox.text = "0"}
        bakShWidthBox.setOnClickListener { bakShWidthBox.setText("") }
        linealSpaceBox.setOnClickListener { linealSpaceBox.setText("") }
        cutOutsBox.setOnClickListener { cutOutsBox.setText("") }
        // click Button when user presses enter in box 1, 2, or 3
        bakShWidthBox.setOnKeyListener { _, keyCode, event -> pressedEnter(bakShEqualsButton, keyCode, event) }
        linealSpaceBox.setOnKeyListener { _, keyCode, event -> pressedEnter(bakShEqualsButton, keyCode, event) }
        cutOutsBox.setOnKeyListener { _, keyCode, event -> pressedEnter(bakShEqualsButton, keyCode, event) }
    }

    fun moreOptionsButtonClicked(view: View) {
        val newIntent = Intent(this, MoreOptionsMenuActivity::class.java)
        startActivity(newIntent)
    }
    fun resetAllButtonClicked(view: View) {
        // make each into a function and then call: reset.nameOfFunction
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Are you sure?")
        builder.setPositiveButton("Yes") { _: DialogInterface?, _: Int ->
            /*1*/    sqrtBox1.setText(""); sqrtBox2.setText(""); sqrtBoxResult.text = "0"
            /*2*/    homeSqrFt.setText(""); boxSqrFt.setText(""); tileBoxResultsButton.text = "Boxes";boxesResults = 0; resultsStringBuilder.clear(); boxesResultsArray.clear()
            /*3*/    sqrFootBox.setText(""); sqrInBox.setText("")
            /*4*/    windowWidthBox.setText(""); blindWidthBox.setText(""); blindWidthResult.text = "0"
            /*5*/    decimalBox.setText(""); fractionBox.setText("")
            /*6*/    linealFtBox.setText(""); sqrYardBox.setText("")
            /*7*/    bakShWidthBox.setText(""); linealSpaceBox.setText(""); cutOutsBox.setText(""); bakShResultsBox.text = "0"
        }
        builder.setNegativeButton("No") { _: DialogInterface?, _: Int ->}
        builder.show()
    }

    // enter presses = button
    fun pressedEnter(itemToClicked: Button, keycode: Int, theEvent: KeyEvent): Boolean {
        return if (keycode == KeyEvent.KEYCODE_ENTER && theEvent.action == KeyEvent.ACTION_UP){
            // idk what ACTION_UP or ACTION_DOWN means
            itemToClicked.performClick()
            true
        } else {
            false
        }
    }

    fun offExtraZeros(digits: String, resultNum: Double): String {
        // take out extra zeros after the decimal
        var answer = digits.format(resultNum).toDouble().toString()
        if (answer.last() == '0'){
            try {
                answer = answer.toDouble().toInt().toString()
                //var sds = answer
            } catch (e: NumberFormatException){
                displayToastMessage(this, "Something went wrong.")
            }
        }
        return answer
    }
    fun getFeetToInch(): Double {
        // function returns inches
        val num = linealSpaceBox.text.toString().toDouble()
        val result :Double

        // if the toggle button is feet, turn to in, else do nothing
        return if (!bakShToggleButton.isChecked) {
            //inout is feet
            result = num*12
            result
        } else if (bakShToggleButton.isChecked) {
            //input is inch
            num
        } else {
            displayToastMessage(this, "Something's wrong.")
            num
        }
    }
    fun decimalToFraction(num: Double): String {
        if (num < 0){
            return "-" + decimalToFraction(-num)
        }
        val tolerance:Double = 1.0E-6
        var h1:Double = 1.0; var h2:Double = 0.0
        var k1:Double = 0.0; var k2:Double = 1.0
        var b = num
        do {
            val a:Double = floor(b)
            var aux:Double = h1; h1 = a*h1+h2; h2 = aux
            aux = k1; k1 = a*k1+k2;  k2 = aux
            b = 1/(b-a)
        } while (abs(num-h1/k1) > num*tolerance)
        return "${h1.toInt()}/${k1.toInt()}"
    }
}
