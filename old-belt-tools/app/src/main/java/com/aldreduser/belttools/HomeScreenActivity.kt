package com.aldreduser.belttools

import android.content.Context
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
import kotlin.math.abs
import kotlin.math.floor
import kotlin.math.sqrt
import kotlin.text.StringBuilder

//todo: bug: In specialty order, when an order is overwritten, it shows up again with a +1 order count

/**
 * TODO:
 *
 * Features:
 *          pt1:
 * Add and look up different orders in 'specialty orders'
 * make top navbar thing look good
 * Manifest File warning: "App is not indexable by google search; consider adding one activity with ACTION_VIEW intent filter."
 * Choose department spinner: options need to be in a textview in order to be edited (to look pretty) ->https://stackoverflow.com/questions/9476665/how-to-change-spinner-text-size-and-text-color
 * add an info icon explaining how to use each feature ***** (has pop up window the user can close)
 * (flooring, appliances, pro desk exports) product info (info stored in phone) (get info from the work notebook)
 *          pt2:
 * sqr a to sqr b (more measurement options than sqr foot to square in)
 *  * create function in another file to convert measurements (call them in this file)
 * make it so that there's a history of problems solved, and is deleted when cleared. (like the calculator app, restart when the app is closed)
 * virtual reality tape measurer
 * in price per sqrft, different prices can be displayed and compared (like in boxes per room) and be under a name to identify them
 * cut wire shelves with the least waste possible. Given the customer's measurements     <<<- ***************************
 * user can pick different features from each department and choose what to display in the home screen (each feature would be in their respective department classes/files)
 *
 * ui:
 * make 'are you sure pop ups' darker
 * fix UI in xml code
 * make toast edges round
 * organize and number the different features in the main page (eventually use picture icons instead of numbers)
 * make buttons look pretty
 *  * rounded edges in buttons, and smokey layouts
 *
 * optimization:
 * count how many times i use each feature on my app, to what which feature to put where (make a radio button to check if eah count is real or for a test)
 */

class HomeScreenActivity : AppCompatActivity() {

    // reset all is using these variables
    // sqr per room
    private val roomSqrStringBuilder = StringBuilder() //can probably send this inside the funtion
    private val roomSqrResultArray = mutableListOf<Double>() //can probably send this inside the funtion
    private var roomSqrResult = 0
    // boxes per room
    private val resultsStringBuilder = StringBuilder() //can probably send this inside the funtion
    private val boxesResultsArray = mutableListOf<Double>() //can probably send this inside the funtion
    private var boxesResults = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)
        getMagnetData()

        //1 get the square feet
        sqrPerRoomButton.setOnClickListener {
            val thisFeature = "sqrRoom"
            val buttonText = getString(R.string.sqrPerRoomButton_text) // should be a string
            if (sqrtBox1.text.isEmpty() && sqrtBox2.text.isEmpty() && (sqrPerRoomButton.text != buttonText)) {
                sqrtBox1.setText(""); sqrtBox2.setText(""); sqrPerRoomButton.text = buttonText; roomSqrResult = 0
                roomSqrStringBuilder.clear(); roomSqrResultArray.clear()
            } else {
                try { //if all input boxes are filled
                    val num1 = sqrtBox1.text.toString().toDouble()
                    val num2 = sqrtBox2.text.toString().toDouble()
                    val result = num1*num2 // this is the only thing preventing me from doing it all

                    //roomSqrResult
                    addAndDisplayRoomSqrsAndMaterials(thisFeature, roomSqrStringBuilder, result, roomSqrResultArray,
                        sqrPerRoomButton)

                } catch (e: NumberFormatException) { displayToastMessage(this, "Maybe fill both boxes with numbers.") }
            }
        }
        sqrPerRoomButton.setOnLongClickListener {
            val sumOfAllResults = offExtraZeros("%.3f", roomSqrResultArray.sum())
            homeSqrFt.setText(sumOfAllResults)
            return@setOnLongClickListener true
        }
        sqrtBox1.setOnClickListener{sqrtBox1.setText("")}
        sqrtBox2.setOnClickListener{sqrtBox2.setText("")}
        // click sqrtEqualsButton when user presses enter in box 1 or 2
        sqrtBox1.setOnKeyListener { _, keyCode, event -> pressedEnter(sqrPerRoomButton, keyCode, event) }
        sqrtBox2.setOnKeyListener { _, keyCode, event -> pressedEnter(sqrPerRoomButton, keyCode, event) }

        //2 get number of boxes to buy
        tileBoxResultsButton.setOnClickListener {
            val thisFeature = "numOfBoxes"
            val buttonText = getString(R.string.tileBoxResultsButton_text)
            if (homeSqrFt.text.isEmpty() && boxSqrFt.text.isEmpty() && tileBoxResultsButton.text != buttonText) {
                homeSqrFt.setText(""); boxSqrFt.setText(""); tileBoxResultsButton.text = buttonText; boxesResults = 0
                resultsStringBuilder.clear(); boxesResultsArray.clear()
            } else {
                try { //if all input boxes are filled
                    val totalSqrFeet = homeSqrFt.text.toString().toDouble()
                    val boxSqrFeet = boxSqrFt.text.toString().toDouble()
                    val numOfBoxes = totalSqrFeet/boxSqrFeet

                    addAndDisplayRoomSqrsAndMaterials(thisFeature, resultsStringBuilder, numOfBoxes, boxesResultsArray,
                        tileBoxResultsButton)

                } catch (e: NumberFormatException) { displayToastMessage(this, "Maybe fill both boxes with numbers.") }
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
                sqrFootBox.setText(""); sqrInBox.setText("")
            } else if (sqrFootBox.text.isNotEmpty()) {
                // converts it to sqr in
                val squareFt = sqrFootBox.text.toString().toDouble()
                val inches = sqrt(squareFt) * 12
                val sqrIn =  inches*inches
                sqrInBox.setText(offExtraZeros("%.4f", sqrIn))
            } else if (sqrInBox.text.isNotEmpty()) {
                // converts it to sqr ft
                val squareIn = sqrInBox.text.toString().toDouble()
                val feet = sqrt(squareIn) / 12
                val sqrFt = feet*feet
                sqrFootBox.setText(offExtraZeros("%.4f", sqrFt))
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
                    blindWidthResult.text = offExtraZeros("%.4f", blindPro)
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
                    decimalBox.setText(offExtraZeros("%.4f", numerator/denominator))
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
            val widthYd = 4
            if (linealFtBox.text.isNotEmpty() && sqrYardBox.text.isNotEmpty()) {
                linealFtBox.setText(""); sqrYardBox.setText("")
            } else if (linealFtBox.text.isNotEmpty() && sqrYardBox.text.isEmpty()) {
                val linealFeet = linealFtBox.text.toString().toDouble()
                val linealYard = (linealFeet/3) //squared
                val sqrYard = linealYard * widthYd
                sqrYardBox.setText(offExtraZeros("%.4f", sqrYard))
            } else if (linealFtBox.text.isEmpty() && sqrYardBox.text.isNotEmpty()) {
                val sqrYard = sqrYardBox.text.toString().toDouble()
                val linealYard = sqrYard/widthYd
                val linealFeet = linealYard*3
                linealFtBox.setText(offExtraZeros("%.4f", linealFeet))
            }
        }
        linealFtBox.setOnClickListener { linealFtBox.setText("") }
        sqrYardBox.setOnClickListener { sqrYardBox.setText("") }
        // click button when user presses enter
        linealFtBox.setOnKeyListener { _, keyCode, event -> pressedEnter(linealFtToSqrYardButton, keyCode, event) }
        sqrYardBox.setOnKeyListener { _, keyCode, event -> pressedEnter(linealFtToSqrYardButton, keyCode, event) }

        //7 Price per Sqr Ft
        sqrFtPriceButton.setOnClickListener{
            if (boxPriceBox.text.isNotEmpty() && boxSqrFtBox.text.isNotEmpty() && sqrFtPriceBox.text != "0"){
                boxPriceBox.setText(""); boxSqrFtBox.setText(""); sqrFtPriceBox.text = "0"
            } else {
                try {
                    val boxPrice = boxPriceBox.text.toString().toDouble()
                    val boxSqrFtNum = boxSqrFtBox.text.toString().toDouble()
                    val pricePerSqrFoot = boxPrice/boxSqrFtNum

                    sqrFtPriceBox.text = offExtraZeros("%.2f", pricePerSqrFoot)
                } catch (e: NumberFormatException) { displayToastMessage(this, "Make sure the boxes are filled.") }
            }
        }
        sqrFtPriceBox.setOnClickListener { sqrFtPriceBox.text = "0" }
        boxPriceBox.setOnClickListener { boxPriceBox.setText("") }
        boxSqrFtBox.setOnClickListener { boxSqrFtBox.setText("") }
        boxPriceBox.setOnKeyListener { _, keyCode, event -> pressedEnter(sqrFtPriceButton, keyCode, event) }
        boxSqrFtBox.setOnKeyListener { _, keyCode, event -> pressedEnter(sqrFtPriceButton, keyCode, event) }

        //8 number of vertical louvers
        numOfLouversEqualsButton.setOnClickListener {
            if (verticalBlindWidthBox.text.isNotEmpty() && numberOfLouversBox.text != "0") {
                verticalBlindWidthBox.setText(""); numberOfLouversBox.text = "0"
            } else {
                val blindWidth = verticalBlindWidthBox.text.toString().toDouble()
                val numOfLouvers = blindWidth/3
                numberOfLouversBox.text = offExtraZeros("%.1f", numOfLouvers)
            }
        }
        verticalBlindWidthBox.setOnClickListener { verticalBlindWidthBox.setText("") }
        numberOfLouversBox.setOnClickListener { numberOfLouversBox.text = "0" }
        verticalBlindWidthBox.setOnKeyListener { _, keyCode, event -> pressedEnter(numOfLouversEqualsButton, keyCode, event) }

        //9 Magnet location
        magnetLocationSaveButton.setOnClickListener{
            saveMagnetData()
            getMagnetData()
        }
        newMagnetLocationBox.setOnKeyListener { _, keyCode, event -> pressedEnter(magnetLocationSaveButton, keyCode, event) }

        //10 Lineal Backsplash
        bakShEqualsButton.setOnClickListener {
            if (linealSpaceBox.text.isNotEmpty() && cutOutsBox.text.isNotEmpty() && bakShResultsBox.text != "0") {
                bakShWidthBox.setText(""); linealSpaceBox.setText(""); cutOutsBox.setText(""); bakShResultsBox.text = "0"
            } else {
                try {
                    // width = 12 by default
                    val bakShWidth:Double = if (bakShWidthBox.text.isEmpty()) 12.0 else bakShWidthBox.text.toString().toDouble()
                    val linealSpace = getFeetOrInch()
                    val cutOuts = cutOutsBox.text.toString().toDouble()
                    val bakShResults = linealSpace/(bakShWidth*cutOuts)
                    bakShResultsBox.text = offExtraZeros("%.4f", bakShResults)
                } catch (e: NumberFormatException) { displayToastMessage(this, "Make sure the boxes are filled.") }
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
            /*1*/    sqrtBox1.setText(""); sqrtBox2.setText(""); sqrPerRoomButton.text =
            getString(R.string.sqrPerRoomButton_text); roomSqrResult =
            0; roomSqrStringBuilder.clear(); roomSqrResultArray.clear()
            /*2*/    homeSqrFt.setText(""); boxSqrFt.setText(""); tileBoxResultsButton.text =
            getString(R.string.tileBoxResultsButton_text);boxesResults = 0;
            resultsStringBuilder.clear(); boxesResultsArray.clear()
            /*3*/    sqrFootBox.setText(""); sqrInBox.setText("")
            /*4*/    windowWidthBox.setText(""); blindWidthBox.setText("");
            blindWidthResult.text = "0"
            /*5*/    decimalBox.setText(""); fractionBox.setText("")
            /*6*/    linealFtBox.setText(""); sqrYardBox.setText("")
            /*7*/    boxPriceBox.setText(""); boxSqrFtBox.setText(""); sqrFtPriceBox.text = "0"
            /*8*/    verticalBlindWidthBox.setText(""); numberOfLouversBox.text = "0"
            /*9*/    newMagnetLocationBox.setText("") //last location should not be erased from stored memory
            /*10*/   bakShWidthBox.setText(""); linealSpaceBox.setText(""); cutOutsBox.setText("");
            bakShResultsBox.text = "0"
        }
        builder.setNegativeButton("No") { _: DialogInterface?, _: Int ->}
        builder.show()
    }
    private fun saveMagnetData(){
        val magnetLocationSharedPref = this.getPreferences(Context.MODE_PRIVATE) ?: return
        with(magnetLocationSharedPref.edit()) {
            putString("Location", newMagnetLocationBox.text.toString())
            commit()
            displayToastMessage(this@HomeScreenActivity, "Saved")
        }
    }
    private fun getMagnetData(){
        val magnetLocationSharedPref = this.getPreferences(Context.MODE_PRIVATE) ?: return
        lastMagnetLocationBox.text = magnetLocationSharedPref.getString("Location", "")
    }

    // enter presses = button
    fun pressedEnter(itemToClick: Button, keycode: Int, theEvent: KeyEvent): Boolean {
        return if (keycode == KeyEvent.KEYCODE_ENTER && theEvent.action == KeyEvent.ACTION_UP){
            itemToClick.performClick()
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
    private fun addAndDisplayRoomSqrsAndMaterials(theFeature: String, stringBuilder: StringBuilder, result: Double,
                                          arrayToSum: MutableList<Double>, resultsButton: Button) {
        // this will be called by 'sqr per room' and 'box per room'
        val tempNumOfResults = if (theFeature == "sqrRoom") roomSqrResult else boxesResults

        if (tempNumOfResults < 6) {
            stringBuilder.append(offExtraZeros("%.3f", result))
            arrayToSum.add(result)
            if (tempNumOfResults < 5) {
                stringBuilder.append(" + ")
            }
            if (theFeature == "sqrRoom") roomSqrResult++ else boxesResults++
        } else {
            displayToastMessage(this, "Limit reached.")
        }
        resultsButton.text = stringBuilder
    }

    fun getFeetOrInch(): Double {
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
