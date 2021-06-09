package com.aldreduser.belttools.specialtyorders

import android.content.Context
import android.content.DialogInterface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.Toolbar
import android.view.View
import com.aldreduser.belttools.R
import com.aldreduser.belttools.extra.displayToastMessage
import kotlinx.android.synthetic.main.action_bar.*
import kotlinx.android.synthetic.main.activity_more_options_menu.*
import kotlinx.android.synthetic.main.activity_specialty_orders.*
import kotlin.text.StringBuilder

/**
 * In this activity:
 * -User can add OrderNumber, OrderInfo, and OrderNote to a list and save it in SharedPreferences.
 *      -also saved: numOfOrders, and orderNum under its unique numOfOrders
 * -User can LookUp, Add, Edit, or Delete an order by typing the order number.
 *
 * -Order numbers and notes are added to a hashMap in order to be displayed with a stringBuilder
 * -Orders are loaded up, saved and deleted directly from sharedPreferences
 */

/*
 *TODO: BUG: when deleting one of the orders that is not the last order displayed, the last order displayed disappears but is not deleted
 * the same order can be deleted more than once and the count decreases each time (not good)
 * when loading data, if one loop is added to the loops, the last order doesn't immediately disappear,
 * but it does once another one is deleted (i think)
*/

// todo: have a default store code in the begining of the order # (ie. h6872-) so the user doesn't have to type the whole thing
// todo: probably save order info and order note into a text file and not a shared preference
// todo: its better to display past orders in a 2 or 3 column recyclerview. Add delete features next to where it's displayed
// todo: show order numbers and notes available IN ORDER OF DATE ADDED

class SpecialtyOrdersActivity : AppCompatActivity() {

    private val orderNumSortedSPKey = "order#Sorted" //used to iterate through orders when loading orders and notes //this SPreference won't be removed
    private val orderNumSPKey = "order#"
    private val orderInfoSPKey = "info"
    private val noteSPKey = "note"                      //with the order#, I can find the note
    private val numOfOrdersSPKey = "numOfOrders"

    private var numOfOrders:Int = 0     //will be used to save and call order numbers from memory
    private var orderAndNoteMap = LinkedHashMap<String, String>()
    private var pastOrdersStrBuilder = StringBuilder()      //used to display past orders in a multi-text box

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_specialty_orders)

        setSupportActionBar(specialtyOrdersToolbar as Toolbar)
        toolbar_text.text = "Orders"

        loadPastData()

        lookUpOrderButton.setOnClickListener {
            displayOrder()
        }

        //save order button
        specialtyOrdersSaveButton.setOnClickListener {
            var isOverwritten = false
            val orderNum = orderNumText.text.toString()
            val orderNumSP = this.getPreferences(Context.MODE_PRIVATE) ?: return@setOnClickListener
            //val orderNumb = orderNumbSP.getString("$orderNum $orderNumSPKey", orderNum)
            // if selected order already exists, ask user if they're sure, else saveOrder()
            if(orderNumSP.contains("$orderNum $orderNumSPKey")) {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Overwrite order?")
                builder.setPositiveButton("Yes") { _: DialogInterface?, _: Int ->
                    saveOrder(true)
                }
                builder.setNegativeButton("No") { _: DialogInterface?, _: Int -> }
                builder.show()
            } else {saveOrder(false)}
        }

        //delete one order
        deleteOrderButton.setOnClickListener {
            val orderNum = orderNumText.text.toString()
            //if the order inputted exists
            if (orderAndNoteMap.contains(orderNum)) {
                //ask user if they are sure?
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Delete order $orderNum?")
                builder.setPositiveButton("Yes") { _: DialogInterface?, _: Int ->
                    deleteOrder()
                }
                builder.setNegativeButton("No") { _: DialogInterface?, _: Int -> }
                builder.show()
            } else { callToast("Order doesn't exist.") }
        }
        //delete all orders
        deleteOrdersButton.setOnClickListener {
            //deletes all data from shared preferences in this activity
            //todo: get rid of this button eventually, when you can delete specific orders from the text box
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Delete all saved orders?")
            builder.setPositiveButton("Yes") { _: DialogInterface?, _: Int ->
                deleteAllPreferences()
                loadPastData()
            }
            builder.setNegativeButton("No") { _: DialogInterface?, _: Int -> }
            builder.show()
        }
    }

    private fun saveOrder(isOverwritten: Boolean){
        // to replace saveOrderInfo
        val orderNumber = orderNumText.text.toString()

        saveOrderNumber(orderNumber, isOverwritten)

        //save info
        val orderInfoSP = this.getPreferences(Context.MODE_PRIVATE) ?: return
        with(orderInfoSP.edit()) {
            putString("$orderNumber $orderInfoSPKey", specialtyOrdersInfo.text.toString())
            commit()
            callToast("Saved")
        }
        //save note
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

        //numOfOrders-- and save
        numOfOrders--
        val numOfOrdersSP = this.getPreferences(Context.MODE_PRIVATE) ?: return
        with(numOfOrdersSP.edit()) {
            putInt(numOfOrdersSPKey, numOfOrders)
            commit()
        }
        loadPastData()
    }

    private fun loadPastData(){
        //call when activity starts, and when order is saved and deleted
        pastOrdersStrBuilder.clear()

        //gets from memory the number of past orders and assigns it to numOfOrders
        val numOfOrdersSP = this.getPreferences(Context.MODE_PRIVATE) ?: return
        numOfOrders = numOfOrdersSP.getInt(numOfOrdersSPKey, 0)

        orderAndNoteMap.clear()
        //gets and displays all the order numbers with the notes
        for(num in 1..numOfOrders){

            val pastOrderNumSP = this.getPreferences(Context.MODE_PRIVATE) ?: return
            val orderNumber = pastOrderNumSP.getString("$num $orderNumSortedSPKey", "0") //returns (ie. h6872-25298)

            //if mutable set does NOT contain order#, add order#, and append to past orders
            //this is a quick fix, might not be necessary if underlying problems are fixed
            if (!orderAndNoteMap.contains(orderNumber)){
                //gets the notes and adds them to the 'pastOrdersStrBuilder' with the order numbers
                val pastNoteSP = this.getPreferences(Context.MODE_PRIVATE) ?: return
                val orderNote = pastNoteSP.getString("$orderNumber $noteSPKey", "Order was deleted.")

                //populate the orderAndNoteMap
                orderAndNoteMap[orderNumber] = orderNote       //might be wrong the syntax for this, its supposed to be a new key

                val pastOrderAndNote = "$num $numOfOrders \t\t $orderNumber \t---\t $orderNote"

                if(orderNote != "Order was deleted.") { pastOrdersStrBuilder.append("$pastOrderAndNote\n") }
            }
        }
        otherOrdersText.text = pastOrdersStrBuilder.toString()
    }

    //called from saveOrder()
    private fun saveOrderNumber(orderNum: String, isOverwritten: Boolean) {
        //increase numOfOrders if order saved is new, and not overwritten
        if(!isOverwritten) {
            numOfOrders++

            //save 'numOfOrders'
            val numOfOrdersSP = this.getPreferences(Context.MODE_PRIVATE) ?: return
            with(numOfOrdersSP.edit()) {
                putInt(numOfOrdersSPKey, numOfOrders)
                commit()
            }

            //save 'orderNum' under numOfOrders (so i can iterate through them when displaying them)
            val sortedOrderNumSP = this.getPreferences(Context.MODE_PRIVATE) ?: return
            with(sortedOrderNumSP.edit()) {
                putString("$numOfOrders $orderNumSortedSPKey", orderNum)
                commit()
            }
        }

        //save 'orderNum' under order# //why tho
        //using this to ask user if they want to overwrite older order
        val orderNumSP = this.getPreferences(Context.MODE_PRIVATE) ?: return
        with(orderNumSP.edit()) {
            putString("$orderNum $orderNumSPKey", orderNum)
            commit()
        }
    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private fun deleteAllPreferences(){
        val otherOrdersSP = this.getPreferences(Context.MODE_PRIVATE) ?: return
        otherOrdersSP.edit().clear().commit()
    }
    private fun callToast(message: String) {
        // well maybe one time it won't be 'saved'
        displayToastMessage(this, message)
    }
}
