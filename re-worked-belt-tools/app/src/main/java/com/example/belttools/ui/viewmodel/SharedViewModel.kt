package com.example.belttools.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.belttools.util.*

class SharedViewModel: ViewModel() {

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
            return joinMaterialsList(
                addRoomSquaresOrBoxes(
                    btnTxt.split(PLUS_JOIN).toMutableList(),
                    offExtraZeros(result)
                )
            )
        } else {
            offExtraZeros(result)
        }
    }
    // HELPERS //
}