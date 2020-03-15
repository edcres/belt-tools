package com.aldreduser.belttools.specialtyorders

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.aldreduser.belttools.R
import com.aldreduser.belttools.extra.displayToastMessage
import kotlinx.android.synthetic.main.activity_specialty_orders.*

//todo show order numbers and notes available (in order of date added)

class SpecialtyOrdersActivity : AppCompatActivity() {
    private var savedNoteName = "SKU notes"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_specialty_orders)

        getData()

        specialtyOrdersSaveButton.setOnClickListener {
            //todo order number is saved with info and data
            //todo order number is the key and it will be connected to the info and data
            saveData()
        }
    }

    private fun saveData() { //might have to add (view: View) parameter
        //pass the name of the note as a parameter
        val ordersSharedPref = this.getPreferences(Context.MODE_PRIVATE) ?: return
        with(ordersSharedPref.edit()) {
            putString(savedNoteName, specialtyOrdersInfo.text.toString())
            commit()
            callToast("Saved")
        }
    }

    private fun getData() {
        //pass the name of the note as a parameter
        val ordersSharedPref = this.getPreferences(Context.MODE_PRIVATE) ?: return
        specialtyOrdersInfo.setText(ordersSharedPref.getString(savedNoteName, ""))
    }

    private fun callToast(message: String) {
        displayToastMessage(this, message)
    }
}
