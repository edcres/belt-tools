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
//scrollview for past orders doesn't seem to be working
//this activity is probably full of nullpointerexception errors from user input in text boxes

class SpecialtyOrdersActivity : AppCompatActivity() {

    private val pastOrdersSPKey = "past_orders"
    private var pastOrdersCount:Int = 0
    private var pastOrdersStrBuilder = StringBuilder()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_specialty_orders)

        loadPastOrders()

        lookUpOrderButton.setOnClickListener {
            displayOrder()
        }
        specialtyOrdersSaveButton.setOnClickListener {
            saveOrderInfo()
        }
        allOrdersButton.setOnClickListener {
            if(otherOrdersText.visibility == View.INVISIBLE){
                otherOrdersText.visibility = View.VISIBLE
            } else {
                otherOrdersText.visibility = View.INVISIBLE
            }
        }
    }
    private fun saveOrderInfo() {
        val orderNumber = orderNumText.text.toString()
        val note = orderNoteText.text.toString()
        // otherOrdersText = stringbuilder + \t new order# with the note
        pastOrdersCount++
        pastOrdersStrBuilder.append("$pastOrdersCount \t\t $orderNumber \t\t $note \n") //maybe check if user input is null. Also might have to initialise the array
        //todo find out how to delete and order from paste orders (maybe number them)
        val pastOrdersSP = this.getPreferences(Context.MODE_PRIVATE) ?: return
        with(pastOrdersSP.edit()) {
            putString(pastOrdersSPKey, pastOrdersStrBuilder.toString())
            commit()
            callToast("Saved")
        }
        loadPastOrders()

        // todo: probably save order info and order note into a text file and not a shared preference
        // the order number is the name of the shared preference that saves the info
        val orderInfoSP = this.getPreferences(Context.MODE_PRIVATE) ?: return
        with(orderInfoSP.edit()) {
            putString(orderNumber, specialtyOrdersInfo.text.toString())
            commit()
            callToast("Saved")
        }
        // the note is saved under 'order number $note'
        val orderNoteSP = this.getPreferences(Context.MODE_PRIVATE) ?: return
        with(orderNoteSP.edit()) {
            putString("$orderNumber N", orderNoteText.text.toString())
            commit()
            callToast("Saved")
        }
    }

    private fun displayOrder() {
        val orderNumber = orderNumText.text.toString()
        // get info from shared preferences using the order number
        // todo: display order info and order note

        val orderInfoSP = this.getPreferences(Context.MODE_PRIVATE) ?: return
        specialtyOrdersInfo.setText(orderInfoSP.getString(orderNumber, ""))

        val orderNoteSP = this.getPreferences(Context.MODE_PRIVATE) ?: return
        specialtyOrdersInfo.setText(orderNoteSP.getString("$orderNumber N", ""))
    }
    private fun loadPastOrders() {
        //load the past orders
        otherOrdersText.text = pastOrdersStrBuilder.toString()
    }

    private fun callToast(message: String) {
        // well maybe one time it won't be 'saved'
        displayToastMessage(this, message)
    }
}
