package com.aldreduser.belttools

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import kotlinx.android.synthetic.main.activity_home_screen.*
import org.jetbrains.anko.toast
import java.lang.NumberFormatException
import java.lang.StringBuilder

// make sure no company private information is made public by the software developer's actions
// this app will have several calculating tools for work. Plus info guides
/**
 * TODO:
 *
 * features:
 * price per sqrft of tile
 * how many louvers will a vertical blind need
 * clean up code
 * put everything into a recyclerview
 * how much sqr footage in each room
 * user can choose the department that will show up in the homescreen (can also add features from other departments)
 * add an info icon explaining how to use each feature ***** (has pop up window the user can close)
 * (flooring, appliances, pro desk exports) product info (info stored in phone) (get info from the work notebook)
 * fix warnings
 * box say where the magnet is (and where it was last seen)
 * choose by department button (in more options activity) should be a drop-down menu
 * each department in choose department should be an object and displayed in the same activity, (not each have its own activity)
 *
 * pt2 (features):
 * sqr a to sqr b (more measurement options than sqr foot to square in)
 * make it so that there's a history of problems solved, and is deleted when the app is closed. (like the calculator app, restart when the app is closed)
 * virtual reality tape measurer
 * cut wire shelves with the least waste possible. Given the customer's measurements
 * user can pick different features from each department and choose what to display in the home screen
 *
 * ui:
 * change toast background color to dark.  Layout Inflater.  https://stackoverflow.com/questions/11288475/custom-toast-on-android-a-simple-example?noredirect=1&lq=1
 * organize and number the different features in the main page (eventually use picture icons instead of numbers)
 * make buttons look pretty
 *
 * optimization:
 * maybe make a function for try catch 'maybe fill both boxes with numbers'
 * create function in another file to convert measurements (call them in this file)
 * count how many times i use each feature on my app, to what which feature to put where (make a radio button to check if eah count is real or for a test)
 *
 * skills:
 * learn to call code from other kotlin files in the project (like creating an object in java)
 * learn to change the color of a button without changing the borders (the gray part)
 * idk what ACTION_UP or ACTION_DOWN means
 *
 */

// https://stackoverflow.com/questions/31175601/how-can-i-change-default-toast-message-color-and-background-color-in-android
// change toast background color (make it into a function and pass a string as a parameter)

//todo make new features resettable

class HomeScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)

        val resultsStringBuilder = StringBuilder()
        var boxesResults = 0
        val boxesResultsArray = mutableListOf<Double>()

        // reset all
        resetAllButton.setOnClickListener {
            // make each into a function and then call: reset.nameOfFunction
   /*1*/         sqrtBox1.setText(""); sqrtBox2.setText(""); sqrtBoxResult.text = "0"
   /*2*/         homeSqrFt.setText(""); boxSqrFt.setText(""); tileBoxResultsButton.text = "Boxes";boxesResults = 0; resultsStringBuilder.clear(); boxesResultsArray.clear()
   /*3*/         amountBox.setText(""); afterTaxBox.text = "0"
   /*4*/         sqrFootBox.setText(""); sqrInBox.setText("")
   /*5*/         windowWidthBox.setText(""); blindWidthBox.setText(""); blindWidthResult.text = "0"
   /*6*/         decimalBox.setText(""); fractionBox.setText("")
   /*7*/         linealFtBox.setText(""); sqrYardBox.setText("")
   /*8*/         bakShWidthBox.setText(""); linealSpaceBox.setText(""); cutOutsBox.setText(""); bakShResultsBox.text = "0"
        }

        // more options
        // open a new activity when this is clicked
        moreOptionsButton.setOnClickListener {
            val newIntent = Intent(this, MoreOptionsMenuActivity::class.java) // maybe change 'newIntent' name
            startActivity(newIntent)
        }

        //1 get the square feet
        // add sqrft per room at the bottom of the layout
        // m ake it so when the box is clicked (with sqft per room), it's added to 'num of boxes',
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
        // click sqrtEqualsButton when user presses enter in box 1
        sqrtBox1.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP){
                // idk what ACTION_UP or ACTION_DOWN means
                sqrtEqualsButton.performClick()
                return@OnKeyListener true
            } else false
        })
        // click sqrtEqualsButton when user presses enter in box 2
        sqrtBox2.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP){
                sqrtEqualsButton.performClick()
                return@OnKeyListener true
            } else false
        })

        //2 get number of boxes to buy
        tileBoxResultsButton.setOnClickListener {
            if (homeSqrFt.text.isEmpty() && boxSqrFt.text.isEmpty() && tileBoxResultsButton.text != "boxes") {
                homeSqrFt.setText(""); boxSqrFt.setText(""); tileBoxResultsButton.setText("boxes"); boxesResults = 0; resultsStringBuilder.clear();
                boxesResultsArray.clear();
            } else {
                try {
                    homeSqrFt.setOnClickListener { homeSqrFt.setText("") }
                    boxSqrFt.setOnClickListener { boxSqrFt.setText("") }

                    var totalSqrFeet = homeSqrFt.text.toString().toDouble()
                    var boxSqrFeet = boxSqrFt.text.toString().toDouble()
                    var numOfBoxes = totalSqrFeet/boxSqrFeet

                    if (boxesResults < 6) {
                        resultsStringBuilder.append("%.2f".format(numOfBoxes))
                        boxesResultsArray.add(numOfBoxes)
                        if (boxesResults < 5) { resultsStringBuilder.append(" + ") }
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
        // click Button when user presses enter in box 1
        homeSqrFt.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP){
                tileBoxResultsButton.performClick()
                return@OnKeyListener true
            } else false
        })
        // click Button when user presses enter in box 2
        boxSqrFt.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP){
                tileBoxResultsButton.performClick()
                return@OnKeyListener true
            } else false
        })

        //4 sqr foot to sqr in sqrInBox
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
        // click Button when user presses enter in box 1
        sqrFootBox.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP){
                sqrFootToSqrInButton.performClick()
                return@OnKeyListener true
            } else false
        })
        // click Button when user presses enter in box 2
        sqrInBox.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP){
                sqrFootToSqrInButton.performClick()
                return@OnKeyListener true
            } else false
        })

        //5 get blind width
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
        blindWidthResult.setOnClickListener{
            toast("Cut on each side")
        }
        // click Button when user presses enter in box 1
        windowWidthBox.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP){
                blindWidthEqualsButton.performClick()
                return@OnKeyListener true
            } else false
        })
        // click Button when user presses enter in box 2
        blindWidthBox.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP){
                blindWidthEqualsButton.performClick()
                return@OnKeyListener true
            } else false
        })





        //6 decimal to fraction
        decimalToFractionButton.setOnClickListener {
            var decimalNun:Double
            var completeFraction:String

            if (decimalBox.text.isNotEmpty() && fractionBox.text.isNotEmpty()) {
                decimalBox.setText(""); fractionBox.setText("")
            } else if (decimalBox.text.isNotEmpty() && fractionBox.text.isEmpty()) {
                decimalNun = decimalBox.text.toString().toDouble()
                completeFraction = decimalToFraction(decimalNun)
                fractionBox.setText(completeFraction)
            } else if (decimalBox.text.isEmpty() && fractionBox.text.isNotEmpty()) {
                completeFraction = fractionBox.text.toString()
                var numerator = completeFraction.substringBeforeLast("/").toDouble()
                var denominator = completeFraction.substringAfterLast("/").toDouble()
                decimalBox.setText( (numerator/denominator).toString() )
            } else {}
        }

        //7 lineal ft to square yard
        linealFtToSqrYardButton.setOnClickListener {
            //val widthFt = 12
            val widthYd = 4

            if (linealFtBox.text.isNotEmpty() && sqrYardBox.text.isNotEmpty()) {
                linealFtBox.setText(""); sqrYardBox.setText("")
            } else if (linealFtBox.text.isNotEmpty() && sqrYardBox.text.isEmpty()) {
                var linealFeet = linealFtBox.text.toString().toDouble()
                var linealYard = (linealFeet/3) //squared
                var sqrYard = linealYard * widthYd
                sqrYardBox.setText(sqrYard.toString())
            } else if (linealFtBox.text.isEmpty() && sqrYardBox.text.isNotEmpty()) {
                var sqrYard = sqrYardBox.text.toString().toDouble()
                var linealYard = sqrYard/widthYd
                var linealFeet = linealYard*3
                linealFtBox.setText(linealFeet.toString())
            } else {}
        }

        //3 get tax
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
        // click Button when user presses enter in the box
        amountBox.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP){
                plusTaxButton.performClick()
                return@OnKeyListener true
            } else false
        })

        //8 Lineal Backsplash
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

                    var linealSpace = getFeetToInch() // hopefully this is right
                    var cutOuts = cutOutsBox.text.toString().toDouble()
                    var bakShResults = linealSpace/(bakShWidth*cutOuts)
                    bakShResultsBox.text = "%.3f".format(bakShResults)
                } catch (e: NumberFormatException) {
                    toast("Make sure the boxes are filled.")
                }
            }
        }
        // click Button when user presses enter in box 1
        bakShWidthBox.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP){
                bakShEqualsButton.performClick()
                return@OnKeyListener true
            } else false
        })
        // click Button when user presses enter in box 2
        linealSpaceBox.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP){
                bakShEqualsButton.performClick()
                return@OnKeyListener true
            } else false
        })
        // click Button when user presses enter in box 3
        cutOutsBox.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP){
                bakShEqualsButton.performClick()
                return@OnKeyListener true
            } else false
        })
    }

    fun getFeetToInch(): Double {
        // function returns inches
        var num = linealSpaceBox.text.toString().toDouble()
        var result :Double

        // if the toggle button is feet, turn to in, else do nothing
        if (!bakShToggleButton.isChecked) {
            //inout is feet
            result = num*12
            return result
        } else if (bakShToggleButton.isChecked) {
            //input is inch
            return num
        } else {
            toast("Something's wrong.")
            return num
        }
    }

    fun decimalToFraction(num: Double): String {
        if (num < 0){
            return "-" + decimalToFraction(-num)
        }
        var tolerance:Double = 1.0E-6
        var h1:Double = 1.0; var h2:Double = 0.0
        var k1:Double = 0.0; var k2:Double = 1.0
        var b = num
        do {
            var a:Double = Math.floor(b)
            var aux:Double = h1; h1 = a*h1+h2; h2 = aux
            aux = k1; k1 = a*k1+k2;  k2 = aux
            b = 1/(b-a)
        } while (Math.abs(num-h1/k1) > num*tolerance)

        return "${h1.toInt()}/${k1.toInt()}"

        //takes in a decimal number and returns a string ('num' + '/' + 'num')
    }
}
