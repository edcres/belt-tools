package com.aldreduser.belttools.specialtyorders

import android.content.Context
import android.content.DialogInterface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
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
// asked user if they sure they wanna overwrite the other order
// called toast to save and delete orders
// todo: Scrollview for past orders doesn't seem to be working

// todo: probably save order info and order note into a text file and not a shared preference
// todo: its better to display past orders in a 2 or 3 column recyclerview. Add delete features next to where it's displayed
// todo: show order numbers and notes available IN ORDER OF DATE ADDED

class SpecialtyOrdersActivity : AppCompatActivity() {

    private val orderNumSortedSPKey = "order#Sorted" //used to iterate through orders when loading orders and notes //this SPreference won't be removed
    private val orderNumSPKey = "order#"
    private val orderInfoSPKey = "info"
    private val noteSPKey = "note"
    private val numOfOrdersSPKey = "numOfOrders"
    private val orderAndNoteSPKey = "order#AndNote"

    private var numOfOrders:Int = 0     //will be used to save and call order numbers from memory
    private var orderAndNoteMap = HashMap<String, String>()
    private var pastOrdersStrBuilder = StringBuilder()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_specialty_orders)

        loadPastData()

        lookUpOrderButton.setOnClickListener {
            displayOrder()
        }
        specialtyOrdersSaveButton.setOnClickListener {
            val orderNum = orderNumText.text.toString()
            val orderNumSP = this.getPreferences(Context.MODE_PRIVATE) ?: return@setOnClickListener
            //val orderNumb = orderNumbSP.getString("$orderNum $orderNumSPKey", orderNum)
            // if selected order already exists, ask user if they're sure, else saveOrder()
            if(orderNumSP.contains("$orderNum $orderNumSPKey")) {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Overwrite order?")
                builder.setPositiveButton("Yes") { _: DialogInterface?, _: Int ->
                    saveOrder()
                }
                builder.setNegativeButton("No") { _: DialogInterface?, _: Int -> }
                builder.show()
            } else {saveOrder()}
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
            loadPastData()
        }
    }

    private fun saveOrder(){
        // to replace saveOrderInfo
        val orderNumber = orderNumText.text.toString()

        saveOrderNumber(orderNumber)
        saveHashMapValue(orderNumber)

        // info
        val orderInfoSP = this.getPreferences(Context.MODE_PRIVATE) ?: return
        with(orderInfoSP.edit()) {
            putString("$orderNumber $orderInfoSPKey", specialtyOrdersInfo.text.toString())
            commit()
            callToast("Saved")
        }
        // note
        val orderNoteSP = this.getPreferences(Context.MODE_PRIVATE) ?: return
        with(orderNoteSP.edit()) {
            putString("$orderNumber $noteSPKey", orderNoteText.text.toString())
            commit()
        }
        loadPastData()
    }

    private fun displayOrder() {
        // get info and notes from memory
        val orderNumber = orderNumText.text.toString()

        // info
        val orderInfoSP = this.getPreferences(Context.MODE_PRIVATE) ?: return
        specialtyOrdersInfo.setText(orderInfoSP.getString("$orderNumber $orderInfoSPKey", ""))
        // note
        val orderNoteSP = this.getPreferences(Context.MODE_PRIVATE) ?: return
        orderNoteText.setText(orderNoteSP.getString("$orderNumber $noteSPKey", ""))
    }

    private fun deleteOrder(){
        //new deleteOrder function (orderNumSP, InfoSP, NoteSP, numOfOrdersSP - 1, orderAndNoteMap[order#])
        val orderNumber = orderNumText.text.toString()

        //delete order#
        val orderNumSP = this.getPreferences(Context.MODE_PRIVATE) ?: return
        orderNumSP.edit().remove("$orderNumber $orderNumSPKey").commit()

        //delete info
        val orderInfoSP = this.getPreferences(Context.MODE_PRIVATE) ?: return
        orderInfoSP.edit().remove("$orderNumber $orderInfoSPKey").commit()
        callToast("Deleted")

        //delete note
        val orderNoteSP = this.getPreferences(Context.MODE_PRIVATE) ?: return
        orderNoteSP.edit().remove("$orderNumber $noteSPKey").commit()

        //numOfOrders - 1 and save. Should have already been loaded when activity starts
        numOfOrders - 1
        val numOfOrdersSP = this.getPreferences(Context.MODE_PRIVATE) ?: return
        with(numOfOrdersSP.edit()) {
            putInt(numOfOrdersSPKey, numOfOrders)
            commit()
        }

        //delete orderAndNoteMap[order#]
        val orderAndNoteMapSP = this.getPreferences(Context.MODE_PRIVATE) ?: return
        orderAndNoteMapSP.edit().remove("$orderNumber $orderAndNoteSPKey").commit()
        if (orderAndNoteMap.containsKey(orderNumber)){ orderAndNoteMap.remove(orderNumber) }

        loadPastData()
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
            val orderNumber = pastOrderNumSP.getString("$num $orderNumSortedSPKey", "0") //returns (ie. h6872-25298)

            //gets the notes and adds them to the 'pastOrdersStrBuilder' with the order numbers
            val pastNoteSP = this.getPreferences(Context.MODE_PRIVATE) ?: return
            val orderNote = pastNoteSP.getString("$orderNumber $noteSPKey", "Order was deleted.")

            val pastOrderAndNote = "$num \t\t $orderNumber \t---\t $orderNote"
            if(orderNote != "Order was deleted."){pastOrdersStrBuilder.append("$pastOrderAndNote\n")}
        }
        otherOrdersText.text = pastOrdersStrBuilder.toString()
    }
    private fun saveOrderNumber(orderNum: String) {
        //called when order is saved
        numOfOrders++

        //save 'numOfOrders'
        val numOfOrdersSP = this.getPreferences(Context.MODE_PRIVATE) ?: return
        with(numOfOrdersSP.edit()) {
            putInt(numOfOrdersSPKey, numOfOrders)
            commit()
        }
        //save 'orderNum' under numOfOrders (so i can iterate through them when displaying them)
        //this will always be stored in the user's memory (cannot be deleted), maybe fix it
        val sortedOrderNumSP = this.getPreferences(Context.MODE_PRIVATE) ?: return
        with(sortedOrderNumSP.edit()) {
            putString("$numOfOrders $orderNumSortedSPKey", orderNum)
            commit()
        }
        //save 'orderNum' under order#
        val orderNumSP = this.getPreferences(Context.MODE_PRIVATE) ?: return
        with(orderNumSP.edit()) {
            putString("$orderNum $orderNumSPKey", orderNum)
            commit()
        }
    }
    private fun saveHashMapValue(orderNum: String) {
        //save order and note with "$orderNumber $orderAndNoteSPKey" as the key
        //will be called when user saves order

        val saveHashMapSP = this.getPreferences(Context.MODE_PRIVATE) ?: return
        with(saveHashMapSP.edit()) {
            putString("$orderNum $orderAndNoteSPKey", orderAndNoteMap[orderNum])
            commit()
        }
    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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
