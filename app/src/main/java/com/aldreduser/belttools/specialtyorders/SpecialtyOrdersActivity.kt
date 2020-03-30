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
 * In this activity: (might not be accurate)
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

//todo: restructure: save hashmap key and values in shared preferences
//  have to save all the order numbers in different shared preferences (done), to then call them from memory (the keys for this will be ints and the biggest int will be saved in memory)
//  when loading, call them instead of pastOrdersStrBuilder from shared preferences

// todo bug: every time i restart the app and there was something saved in past orders text, when some new order is added, everything is deleted from the text box
//      prob bc the string builder is not given a value from SharedPreferences

//todo bug: feature to delete orders (put order# and strngbuilder[order# & note] in keymap, then add keymap to string builder)
//      add the hashMap of string in the 'pastOrdersStrBuilder'
//      will remove order note and info from the key (order#) in shared preferences
// is deleted but still shows up in the text (prob bc the hashmap is not saved in SharedPreferences)
//  save hashmap key and values in shared preferences

//todo: ask user if they sure they wanna overwrite the other order
//todo: show order numbers and notes available IN ORDER OF DATE ADDED

//This code is probably overcomplicated for no good reason
//Scrollview for past orders doesn't seem to be working
//this activity is probably full of nullpointerexception errors from user input in text boxes

class SpecialtyOrdersActivity : AppCompatActivity() {

    private var numOfOrders:Int = 0     //will be used to save and call order numbers from memory
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
        deleteOrdersButton.setOnClickListener {
            //deletes all data from shared preferences in this activity
            //todo: get rid of this button eventually, when you can delete specific orders from the text box
            deleteAllPreferences()
            loadPastOrders()
        }
    }
    private fun saveOrderInfo() {
        val orderNumber = orderNumText.text.toString()
        val note = orderNoteText.text.toString()

        pastOrdersCount++ //todo: this count restarts every time the app is closed. fix it
        //val textLine = ("$pastOrdersCount \t\t $orderNumber \t\t $note \n") //this (...) will be pastOrderSB will be appended after the hashmap
        val textLine = ("$orderNumber \t\t $note \n")
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
        orderAndNoteMap.remove(orderNumber)
        //rerender the string builder to the text box
        //might need to clear the stringbuilder add all the values for the hasmap to the string builder
        loadPastOrders()

        // deletes from shared preferences
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
        pastOrdersStrBuilder.clear()
        val orderInfoSP = this.getPreferences(Context.MODE_PRIVATE) ?: return
        pastOrdersStrBuilder.append(orderInfoSP.getString(pastOrdersSPKey, ""))  //todo: eventually make defValue: "No Orders"
        otherOrdersText.text = pastOrdersStrBuilder.toString()
    }
    private fun callToast(message: String) {
        //todo: call toast somewhere

        // well maybe one time it won't be 'saved'
        displayToastMessage(this, message)
    }



    private fun loadPastData(){
        //to replace loadPastOrders()
        pastOrdersStrBuilder.clear()

        //gets from memory the number of past orders and assign it to numOfOrders
        val numOfOrdersSP = this.getPreferences(Context.MODE_PRIVATE) ?: return
        numOfOrders = numOfOrdersSP.getInt("numOfOrders", 0)

        //displays all the order numbers with the notes
        for(num in 1..numOfOrders){

            val pastOrderNumsSP = this.getPreferences(Context.MODE_PRIVATE) ?: return
            val tempOrderNumber = pastOrderNumsSP.getInt("numOfOrder: $num", 0)

            //gets the notes and add them to the 'pastOrdersStrBuilder' with the order numbers
            val pastNotesSP = this.getPreferences(Context.MODE_PRIVATE) ?: return
            val tempOrderNote = pastNotesSP.getInt("$tempOrderNumber N", 0)

            val pastOrderAndNote = "$num \t\t $tempOrderNumber \t---\t $tempOrderNote" //todo: bug: 'num' here might not work
        }

    }
    private fun saveOrderNumber(orderNum: String) {
        numOfOrders++

        val numOfOrdersSP = this.getPreferences(Context.MODE_PRIVATE) ?: return
        with(numOfOrdersSP.edit()) {
            putInt("numOfOrders", numOfOrders)
            commit()
        }
        val pastOrderNumsSP = this.getPreferences(Context.MODE_PRIVATE) ?: return
        with(pastOrderNumsSP.edit()) {
            putString("numOfOrder: $numOfOrders", orderNum)
            commit()
        }
    }
    private fun saveHashMapValues() {
        //maybe just save one value
        val orderNumber = orderNumText.text.toString()
        //https://stackoverflow.com/questions/7944601/how-to-save-hashmap-to-shared-preferences 'ut i need to save the hash map as itself like we adding vector'

        for(key in orderAndNoteMap.keys){

            val saveHashMapSP = this.getPreferences(Context.MODE_PRIVATE) ?: return
            with(saveHashMapSP.edit()) {
                putString("HashMap $orderNumber", orderAndNoteMap[key])
                commit()
            }
        }
    }


    private fun deleteAllPreferences(){
        // get rid of this function when done debugging
        val otherOrdersSP = this.getPreferences(Context.MODE_PRIVATE) ?: return
        otherOrdersSP.edit().clear().commit()
    }
}
