package com.aldreduser.belttools.specialtyorders

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.aldreduser.belttools.R
import com.aldreduser.belttools.extra.displayToastMessage
import kotlinx.android.synthetic.main.activity_specialty_orders.*
import kotlin.text.StringBuilder

//todo: show order numbers and notes available (in order of date added)

//todo: feature to delete orders (put order# and strngbuilder[order# & note] in keymap, then add keymap to string builder)
//      add the hashMap of string in the 'pastOrdersStrBuilder'
//      will remove order note and info from the key (order#) in shared preferences
//need to figure out how to put the 'pastOrderSBs' in order, when they come from the hashMap (maybe by the order number, organize it by the last 5 digits)

//scrollview for past orders doesn't seem to be working
//this activity is probably full of nullpointerexception errors from user input in text boxes

class SpecialtyOrdersActivity : AppCompatActivity() {

    private val pastOrdersSPKey = "past_orders"
    private var pastOrdersCount:Int = 0         //for user to keep track
    private var orderAndNoteMap = HashMap<String, String>()
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
        val textLine = ("$pastOrdersCount \t\t $orderNumber \t\t $note \n") //this (...) will be pastOrderSB will be appended after the hashmap
        orderAndNoteMap[orderNumber] = textLine

        //todo: put something in this method
        //sort them by the number in the first character (account for more than one character, ie. 9 or more)
        sortAndRenderOrders()

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
    private fun deleteOrder() {
        //todo:   v v v v
        //get order number from input box
        val orderNumber = orderNumText.text.toString()
        //delete instance of the keyMap of strings that is rendered to the stringBuilder (keymap has order# and string)
        orderAndNoteMap.remove(orderNumber) //todo: check if this works
        //rerender the string builder to the text box
        sortAndRenderOrders()
    }
    private fun displayOrder() {
        val orderNumber = orderNumText.text.toString()
        // get info from shared preferences using the order number

        val orderInfoSP = this.getPreferences(Context.MODE_PRIVATE) ?: return
        specialtyOrdersInfo.setText(orderInfoSP.getString(orderNumber, ""))

        val orderNoteSP = this.getPreferences(Context.MODE_PRIVATE) ?: return
        specialtyOrdersInfo.setText(orderNoteSP.getString("$orderNumber N", ""))
    }
    private fun loadPastOrders() {
        //load the past orders
        otherOrdersText.text = pastOrdersStrBuilder.toString()
    }
    private fun sortAndRenderOrders() {
        /*
        for(key in hashMap.keys){
            println("Element at key $key = ${hashMap[key]}")
        }*/
        //maybe fuse this function with loadPastOrders()
    }
    private fun callToast(message: String) {
        // well maybe one time it won't be 'saved'
        displayToastMessage(this, message)
    }
}
