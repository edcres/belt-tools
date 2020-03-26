package com.aldreduser.belttools.specialtyorders

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.aldreduser.belttools.R
import com.aldreduser.belttools.extra.displayToastMessage
import kotlinx.android.synthetic.main.activity_specialty_orders.*
import kotlin.text.StringBuilder

/**
 * In this activity:
 * -User can add OrderNumber, OrderInfo, and OrderNote to a list and save it in SharedPreferences.
 * -User can LookUp or Delete and order by typing the order number.
 * -Orders are organized (todo: idk how yet)
 *
 * -Order numbers and notes are added to a hashMap
 * -Info and Notes are shared in SharedPreferences under the OrderNumber key
 * -To Delete an orders:
 *      -get the OrderNumber as input
 *      -delete Order from hashMap
 *      -refresh past orders textbox loadPastOrders()
 *      -delete Info and Notes from SharedPreferences
 */

//todo: ask user if they sure they wanna overwrite the other order
//todo: show order numbers and notes available IN ORDER OF DATE ADDED

//todo: feature to delete orders (put order# and strngbuilder[order# & note] in keymap, then add keymap to string builder)
//      add the hashMap of string in the 'pastOrdersStrBuilder'
//      will remove order note and info from the key (order#) in shared preferences

//This code is probably overcomplicated for no good reason
//Scrollview for past orders doesn't seem to be working
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
        deleteOrderButton.setOnClickListener {
            deleteOrder()
        }
    }
    private fun saveOrderInfo() {
        val orderNumber = orderNumText.text.toString()
        val note = orderNoteText.text.toString()

        pastOrdersCount++ //todo: this count restarts every time the app is closed. fix it
        val textLine = ("$pastOrdersCount \t\t $orderNumber \t\t $note \n") //this (...) will be pastOrderSB will be appended after the hashmap
        orderAndNoteMap[orderNumber] = textLine

        pastOrdersStrBuilder.append(orderAndNoteMap[orderNumber])

        val pastOrdersSP = this.getPreferences(Context.MODE_PRIVATE) ?: return
        with(pastOrdersSP.edit()) {
            putString(pastOrdersSPKey, pastOrdersStrBuilder.toString())
            commit()
        }
        loadPastOrders()

        // todo: probably save order info and order note into a text file and not a shared preference
        // the order number is the name of the shared preference that saves the info
        val orderInfoSP = this.getPreferences(Context.MODE_PRIVATE) ?: return
        with(orderInfoSP.edit()) {
            putString(orderNumber, specialtyOrdersInfo.text.toString())
            commit()
        }
        // the note is saved under 'order number $note'
        val orderNoteSP = this.getPreferences(Context.MODE_PRIVATE) ?: return
        with(orderNoteSP.edit()) {
            putString("$orderNumber N", orderNoteText.text.toString())
            commit()
        }
    }
    private fun deleteOrder() {
        val orderNumber = orderNumText.text.toString()
        //delete instance of the keyMap of strings that is rendered to the stringBuilder (keymap has order# and note)
        orderAndNoteMap.remove(orderNumber) //todo: check if this works
        //rerender the string builder to the text box
        //might need to clear the stringbuilder add all the values for the hasmap to the string builder
        loadPastOrders()

        //todo: delete from shared preferences
        //info
        val orderInfoSP = this.getPreferences(Context.MODE_PRIVATE) ?: return
        orderInfoSP.edit().remove(orderNumber).commit()
        //notes
        val orderNoteSP = this.getPreferences(Context.MODE_PRIVATE) ?: return
        orderNoteSP.edit().remove("$orderNumber N").commit()
    }
    private fun displayOrder() {
        val orderNumber = orderNumText.text.toString()
        // get info from shared preferences using the order number

        val orderInfoSP = this.getPreferences(Context.MODE_PRIVATE) ?: return
        specialtyOrdersInfo.setText(orderInfoSP.getString(orderNumber, ""))

        val orderNoteSP = this.getPreferences(Context.MODE_PRIVATE) ?: return
        orderNoteText.setText(orderNoteSP.getString("$orderNumber N", ""))
    }
    private fun loadPastOrders() {
        //load the past orders
        val orderInfoSP = this.getPreferences(Context.MODE_PRIVATE) ?: return
        otherOrdersText.text = orderInfoSP.getString(pastOrdersSPKey, "No Orders")
    }
    private fun callToast(message: String) {
        //todo: call toast somewhere

        // well maybe one time it won't be 'saved'
        displayToastMessage(this, message)
    }

    private fun sortAndRenderOrders() {
        //todo: maybe delete this function (is called 2 times: deleteOrder(), saveOrderInfo())

        for(key in orderAndNoteMap.keys){
            //add it to the string builder
            pastOrdersStrBuilder.append(orderAndNoteMap[key]) //might have to convert this toString()
        }
    }


    private fun deleteAllPreferences(){
        // get rid of this function when done debugging
        val otherOrdersSP = this.getPreferences(Context.MODE_PRIVATE) ?: return
        otherOrdersSP.edit().clear().commit()
    }
}
