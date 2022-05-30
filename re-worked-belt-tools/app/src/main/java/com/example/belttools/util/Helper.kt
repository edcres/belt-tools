package com.example.belttools.util

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.belttools.data.model.entities.Department
import com.example.belttools.data.model.entities.SKU
import com.example.belttools.data.model.entities.SpecialtyOrder
import kotlin.math.abs
import kotlin.math.floor

const val GLOBAL_TAG = "GLOBAL__TAG"
const val MAGNET_LOCATION_TAG = "Magnet Location"
const val MAGNET_LOCATION = "Magnet Location"
const val STORE_NUMBER_TAG = "Store Number"
const val STORE_NUMBER = "Store Number"
const val PLUS_JOIN = " + "
const val FLOOR_SKUS_LIST = "Floor SKUs"
const val PALLET_SKUS_LIST = "Pallet SKUs"

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

fun joinMaterialsList(decimalSpaces: String, materialsQty: List<String>?): String? {
    Log.d(GLOBAL_TAG, "HELPER: list = \n$materialsQty")
    if(materialsQty.isNullOrEmpty()) return null
    var joinedMaterials = materialsQty[0]
    for (i in materialsQty.indices) {
        if (i != 0) {
            joinedMaterials = "$joinedMaterials$PLUS_JOIN" +
                    offExtraZeros(decimalSpaces, materialsQty[i].toDouble())
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

fun getNewBlindWidth(windowWidth: Double, blindPre: Double) = (windowWidth - blindPre) / 2

fun linealFeetToSqrYard(linealFeet: Double) = (linealFeet/3) * 4

fun sqrYardToLinealFeet(sqrYard: Double) = (sqrYard / 4) * 3

fun getPricePerSqrFoot(boxPrice: Double, boxSqrFtNum: Double) = boxPrice/boxSqrFtNum

fun getNumberOfLouvers(blindWidth: Double) = blindWidth / 3

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

fun doesSKUContainId(list: List<SKU>, id: Long): Boolean {
    // todo: do this a more efficient way, instead of cycling through the entire list
    list.forEach {
        if (it.id == id) return true
    }
    return false
}

fun getDepartmentNames(departments: List<Department>): MutableList<String> {
    // This is used for the departments list in notes
    // todo: do this a more efficient way, instead of cycling through the entire list
    val departmentNames = mutableListOf<String>()
    departments.forEach {
        if (it.notes != null) departmentNames.add(it.name)
    }
    return departmentNames
}

fun findOrder(orderNum: String, orders: List<SpecialtyOrder>): SpecialtyOrder? {
    // todo: do this a more efficient way, instead of cycling through the entire list
    orders.forEach {
        if (it.orderNum == orderNum) {
            return it
        }
    }
    return null
}

fun filterExtensionNotNull(departments: List<Department>): List<Department> {
    // todo: do this a more efficient way, instead of cycling through the entire list
    val newDepartments = mutableListOf<Department>()
    departments.forEach {
        if (it.extensions != null) newDepartments.add(it)
    }
    return newDepartments.toList()
}