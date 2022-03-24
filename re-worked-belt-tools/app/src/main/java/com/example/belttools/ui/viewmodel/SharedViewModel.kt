package com.example.belttools.ui.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.belttools.data.Repository
import com.example.belttools.data.model.MainRoomDatabase
import com.example.belttools.util.*
import kotlin.math.sqrt

class SharedViewModel: ViewModel() {

    private lateinit var roomDb: MainRoomDatabase
    private lateinit var repository: Repository

    // HELPERS //
    fun saveMagnetLocation(context: Context, location: String) {
        val magnetLocationSharedPref = context
            .getSharedPreferences(MAGNET_LOCATION_TAG, Context.MODE_PRIVATE) ?: return
        with(magnetLocationSharedPref.edit()) {
            putString(MAGNET_LOCATION, location)
            apply()
            displayToast(context, "Saved")
        }
    }
    fun getMagnetLocation(context: Context): String? {
        val magnetLocationSharedPref = context
            .getSharedPreferences(MAGNET_LOCATION_TAG, Context.MODE_PRIVATE)
        return magnetLocationSharedPref.getString(MAGNET_LOCATION, null)
    }
    fun getSqrPerRoom(width: Double, length: Double, btnTxt: String, btnDefault: String): String? {
        val result = width * length
        return if (btnTxt != btnDefault) {
            joinMaterialsList(
                "%.2f",
                addRoomSquaresOrBoxes(
                    btnTxt.split(PLUS_JOIN).toMutableList(),
                    offExtraZeros("%.2f", result)
                )
            )
        } else {
            offExtraZeros("%.2f", result)
        }
    }

    fun getNumberOfBoxes(
        width: Double, length: Double, btnTxt: String, btnDefault: String
    ): String? {
        val result = width / length
        return if (btnTxt != btnDefault) {
            joinMaterialsList(
                "%.4f",
                addRoomSquaresOrBoxes(
                    btnTxt.split(PLUS_JOIN).toMutableList(),
                    offExtraZeros("%.4f", result)
                )
            )
        } else {
            offExtraZeros("%.4f", result)
        }
    }

    fun getSqrFtOrSqrIn(
        sqrFt: String,
        sqrIn: String
    ): String {
        return when {
            sqrFt.isNotEmpty() -> {
                val inches = sqrt(sqrFt.toDouble()) * 12
                offExtraZeros("%.4f", inches * inches)
            }
            sqrIn.isNotEmpty() -> {
                val feet = sqrt(sqrIn.toDouble()) / 12
                offExtraZeros("%.4f", feet * feet)
            }
            else -> {
                return "0"
            }
        }
    }

    fun turnFractionToDecimal(fraction: String): Double? {
        return if (fraction.contains("/")) {
            val numerator = fraction.substringBeforeLast("/").toDouble()
            val denominator = fraction.substringAfterLast("/").toDouble()
            numerator / denominator
        } else null
    }

    fun getLinealBacksplash(
        widthInp: String, linealSpace: Double,
        inFeet: Boolean, cutOuts: Int
    ): Double {
        // If it's not in feet, it's in inches
        // width = 12 by default
        val bakShWidth: Double = if (widthInp.isEmpty()) 12.0 else widthInp.toDouble()
        val convertedLinealSpace = if (inFeet) linealSpace * 12 else linealSpace
        return convertedLinealSpace / (bakShWidth * cutOuts)
    }
    // HELPERS //

    // SET UP //
    fun setUpDatabase(application: Application) {
        // todo: call this function
        roomDb = MainRoomDatabase.getInstance(application)
        repository = Repository(roomDb)
    }
}























