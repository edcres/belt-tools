package com.example.belttools.util

import android.content.Context
import android.util.Log
import android.widget.Toast
import kotlin.math.abs
import kotlin.math.floor

const val GLOBAL_TAG = "GLOBAL_TAG"
const val MAGNET_LOCATION_TAG = "Magnet Location"
const val MAGNET_LOCATION = "Magnet Location"
const val PLUS_JOIN = " + "

fun displayToast(context: Context, msg: String) {
    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
}

fun offExtraZeros(decimalSpaces: String, resultNum: Double): String {
    // Take out extra zeros after the decimal.
    return if (getLastTwoChars(resultNum.toString()) == ".0") {
        resultNum.toInt().toString()
    } else {
        decimalSpaces.format(resultNum).toDouble().toString()
    }
}

fun getLastTwoChars(theString: String): String {
    val y = theString[theString.length - 2]
    val z = theString.last()
    return "$y$z"
}

fun addRoomSquaresOrBoxes(
    previousResults: MutableList<String>,
    newResult: String
): List<String>? {
    Log.d(GLOBAL_TAG, "HELPER: $previousResults")
   return if (previousResults.size < 5) {
       previousResults.add(newResult)
       previousResults
    } else {
        null
    }
}

fun joinMaterialsList(materialsQty: List<String>?): String? {
    Log.d(GLOBAL_TAG, "HELPER: list = \n$materialsQty")
    if(materialsQty.isNullOrEmpty()) return null
    var joinedMaterials = materialsQty[0]
    for (i in materialsQty.indices) {
        if (i != 0) {
            joinedMaterials =
                "$joinedMaterials$PLUS_JOIN${offExtraZeros(materialsQty[i].toDouble())}"
        }
    }
    return joinedMaterials
//    return materialsQty.joinToString(PLUS_JOIN)
}

fun sumListOfString(btnSquares: List<String>): Double {
    var squaresSummed = 0.0
    btnSquares.forEach {
        squaresSummed += it.toDouble()
    }
    return squaresSummed
}

fun getInches(num: Double, getFeet: Boolean): Double {
    // If it's not getFeet it's getInches
    return if (getFeet) {
        num*12
    } else {
        num
    }
}

fun decimalToFraction(num: Double): String {
    if (num < 0){
        return "-" + decimalToFraction(-num)
    }
    val tolerance = 1.0E-6
    var h1 = 1.0; var h2 = 0.0
    var k1 = 0.0; var k2 = 1.0
    var b = num
    do {
        val a:Double = floor(b)
        var aux:Double = h1; h1 = a*h1+h2; h2 = aux
        aux = k1; k1 = a*k1+k2;  k2 = aux
        b = 1/(b-a)
    } while (abs(num-h1/k1) > num*tolerance)
    return "${h1.toInt()}/${k1.toInt()}"
}