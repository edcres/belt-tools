package com.example.belttools.util

import android.content.Context
import android.widget.Button
import android.widget.Toast

const val MAGNET_LOCATION_TAG = "Magnet Location"
const val MAGNET_LOCATION = "Magnet Location"
const val PLUS_JOIN = " + "

fun offExtraZeros(resultNum: Double): String {
    // Take out extra zeros after the decimal.
    return if (getLastTwoChars(resultNum.toString()) == ".0") {
        resultNum.toInt().toString()
    } else {
        "%.2f".format(resultNum)
    }
}

fun getLastTwoChars(theString: String): String {
    val y = theString[theString.length - 2]
    val z = theString.last()
    return "$y$z"
}

fun addRoomSquaresOrBoxes(
    previousResults: MutableList<Double>,
    newResult: Double
): List<Double>? {
    //  todo: If returns null, display a toast "Limit reached."
   return if (previousResults.size < 6) {
       previousResults.add(newResult)
       previousResults
    } else {
        null
    }
}

fun joinMaterialsList(materialsQty: List<Double>): String {
    var joinedMaterials = ""
    materialsQty.forEach {
        joinedMaterials = "$joinedMaterials$PLUS_JOIN${offExtraZeros(it)}"
    }
    return joinedMaterials
//    return materialsQty.joinToString(PLUS_JOIN)
}

fun displayToast(context: Context, msg: String) {
    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
}