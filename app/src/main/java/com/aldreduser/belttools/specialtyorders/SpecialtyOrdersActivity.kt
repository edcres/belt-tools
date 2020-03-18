package com.aldreduser.belttools.specialtyorders

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.aldreduser.belttools.R
import com.aldreduser.belttools.extra.displayToastMessage
import kotlinx.android.synthetic.main.activity_specialty_orders.*
import java.lang.StringBuilder

//todo show order numbers and notes available (in order of date added)
//todo feature to delete orders

class SpecialtyOrdersActivity : AppCompatActivity() {

    private var pastOrdersStrBuilder = StringBuilder()
    private var savedInfoName = "the name"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_specialty_orders)

        loadPastOrders()

        allOrdersButton.setOnClickListener {
            if(otherOrdersText.visibility == View.INVISIBLE){
                otherOrdersText.visibility = View.VISIBLE
            } else {
                otherOrdersText.visibility = View.INVISIBLE
            }
        }
        specialtyOrdersSaveButton.setOnClickListener {
            //todo order number is saved with info and data
            //todo order number is the key and it will be connected to the info and data
            saveOrderInfo()
        }
    }

    private fun saveOrderInfo() {
        var orderNumber = orderNumText.text.toString()
        var orderInfo = specialtyOrdersInfo.text.toString()
        var note = orderNoteText.text.toString()
        // otherOrdersText = stringbuilder + \t new order# with the note
        pastOrdersStrBuilder.append("$orderNumber \t\t $note \n") //maybe check if user input is null. Also might have to initialise the array
        loadPastOrders()

        // the order number is the name of the shared preference that saves the info
        // the note is saved under 'order number $note'



        //pass the name of the note as a parameter
        val ordersSharedPref = this.getPreferences(Context.MODE_PRIVATE) ?: return
        with(ordersSharedPref.edit()) {
            putString(savedInfoName, specialtyOrdersInfo.text.toString())
            commit()
            callToast("Saved")
        }
    }

    private fun getOrder() {
        // user types in the order number
        // get info from shared preferences using the order number
        val ordersSharedPref = this.getPreferences(Context.MODE_PRIVATE) ?: return
        specialtyOrdersInfo.setText(ordersSharedPref.getString(savedInfoName, ""))
    }
    private fun loadPastOrders() {
        //load the past orders
        otherOrdersText.text = pastOrdersStrBuilder.toString()
    }

    private fun callToast(message: String) {
        displayToastMessage(this, message)
    }
}
