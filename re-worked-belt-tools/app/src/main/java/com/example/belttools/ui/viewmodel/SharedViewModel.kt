package com.example.belttools.ui.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.belttools.util.MAGNET_LOCATION
import com.example.belttools.util.MAGNET_LOCATION_TAG

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
    // HELPERS //
}