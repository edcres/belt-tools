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
//todo: change the names of shared preferences key

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

    private val orderNumSortedSPKey = "order#Sorted" //used to iterate through orders when loading orders and notes //this SPreference won't be removed
    private val orderNumSPKey = "order#"
    private val orderInfoSPKey = "info"
    private val noteSPKey = "note"
    private val numOfOrdersSPKey = "numOfOrders"
    private val orderAndNoteSPKey = "order#AndNote"

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
            putString("$orderNumber $orderInfoSPKey", specialtyOrdersInfo.text.toString())
            commit()
        }
        // the note is saved under 'order number $note'
        val orderNoteSP = this.getPreferences(Context.MODE_PRIVATE) ?: return
        with(orderNoteSP.edit()) {
            putString("$orderNumber $noteSPKey", orderNoteText.text.toString())
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
        orderInfoSP.edit().remove("$orderNumber $orderInfoSPKey").commit()
        //notes
        val orderNoteSP = this.getPreferences(Context.MODE_PRIVATE) ?: return
        orderNoteSP.edit().remove("$orderNumber $noteSPKey").commit()
    }
    private fun displayOrder() {
        val orderNumber = orderNumText.text.toString()
        // get info from shared preferences using the order number

        val orderInfoSP = this.getPreferences(Context.MODE_PRIVATE) ?: return
        specialtyOrdersInfo.setText(orderInfoSP.getString("$orderNumber $orderInfoSPKey", ""))

        val orderNoteSP = this.getPreferences(Context.MODE_PRIVATE) ?: return
        orderNoteText.setText(orderNoteSP.getString("$orderNumber $noteSPKey", ""))
    }
    private fun loadPastOrders() {
        //load the past orders
        pastOrdersStrBuilder.clear()
        val orderInfoSP = this.getPreferences(Context.MODE_PRIVATE) ?: return
        pastOrdersStrBuilder.append(orderInfoSP.getString(pastOrdersSPKey, ""))  //todo: eventually make defValue: "No Orders"
        otherOrdersText.text = pastOrdersStrBuilder.toString()
    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    private fun newDeleteOrder(){
        //todo: new deleteOrder function (orderNumSP, NoteSP, InfoSP, numOfOrdersSP - 1, orderAndNoteMap[order#], order number sorted)
        val orderNumber = orderNumText.text.toString()

        //delete order#
        val orderNumSP = this.getPreferences(Context.MODE_PRIVATE) ?: return
        orderNumSP.edit().remove("$orderNumber $orderNumSPKey").commit()

        //delete info
        val orderInfoSP = this.getPreferences(Context.MODE_PRIVATE) ?: return
        orderInfoSP.edit().remove("$orderNumber $orderInfoSPKey").commit()

        //delete note
        val orderNoteSP = this.getPreferences(Context.MODE_PRIVATE) ?: return
        orderNoteSP.edit().remove("$orderNumber $noteSPKey").commit()

        //todo: numOfOrdersSP - 1

        //todo: delete orderAndNoteMap[order#]
        val orderAndNoteMapSP = this.getPreferences(Context.MODE_PRIVATE) ?: return
        orderAndNoteMapSP.edit().remove("$orderNumber $orderAndNoteSPKey").commit()
    }

    private fun loadPastData(){
        //to replace loadPastOrders()
        //call when activity starts, and when order is saved and deleted
        pastOrdersStrBuilder.clear()

        //gets from memory the number of past orders and assign it to numOfOrders
        val numOfOrdersSP = this.getPreferences(Context.MODE_PRIVATE) ?: return
        numOfOrders = numOfOrdersSP.getInt(numOfOrdersSPKey, 0)

        //gets and displays all the order numbers with the notes
        for(num in 1..numOfOrders){

            val pastOrderNumSP = this.getPreferences(Context.MODE_PRIVATE) ?: return
            val orderNumber = pastOrderNumSP.getString("$orderNumSortedSPKey $num", "0") //returns (ie. h6872-25298)

            //gets the notes and adds them to the 'pastOrdersStrBuilder' with the order numbers
            val pastNoteSP = this.getPreferences(Context.MODE_PRIVATE) ?: return
            val orderNote = pastNoteSP.getString("$orderNumber $noteSPKey", "0")

            val pastOrderAndNote = "$num \t\t $orderNumber \t---\t $orderNote" //todo: bug: 'num' here might not work
            pastOrdersStrBuilder.append("$pastOrderAndNote\n")
        }
    }
    private fun saveOrderNumber(orderNum: String) {
        //called when order is saved
        numOfOrders++

        //save 'numOfOrders' todo: maybe don't save numOfOrders in this function
        val numOfOrdersSP = this.getPreferences(Context.MODE_PRIVATE) ?: return
        with(numOfOrdersSP.edit()) {
            putInt(numOfOrdersSPKey, numOfOrders)
            commit()
        }
        //save 'orderNum' under numOfOrders (so i can iterate through them when displaying them)
        val sortedOrderNumSP = this.getPreferences(Context.MODE_PRIVATE) ?: return
        with(sortedOrderNumSP.edit()) {
            putString("$orderNumSortedSPKey $numOfOrders", orderNum)
            commit()
        }
        //save 'orderNum' under order#
        val orderNumSP = this.getPreferences(Context.MODE_PRIVATE) ?: return
        with(orderNumSP.edit()) {
            putString("$orderNum $orderNumSPKey", orderNum)
            commit()
        }
    }
    private fun saveHashMapValue() {
        //save order and note with "HashMap $orderNumber" as the key
        //will be called when user saves order
        val orderNumber = orderNumText.text.toString()
        //https://stackoverflow.com/questions/7944601/how-to-save-hashmap-to-shared-preferences 'ut i need to save the hash map as itself like we adding vector'

        val saveHashMapSP = this.getPreferences(Context.MODE_PRIVATE) ?: return
        with(saveHashMapSP.edit()) {
            putString("$orderNumber $orderAndNoteSPKey", orderAndNoteMap[orderNumber])
            commit()
        }
    }

    private fun deleteAllPreferences(){
        // todo: get rid of this function when done debugging
        val otherOrdersSP = this.getPreferences(Context.MODE_PRIVATE) ?: return
        otherOrdersSP.edit().clear().commit()
    }
    private fun callToast(message: String) {
        //todo: call toast somewhere

        // well maybe one time it won't be 'saved'
        displayToastMessage(this, message)
    }
}
