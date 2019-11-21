package com.aldreduser.belttools

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_dept_extensions.*
import org.jetbrains.anko.toast
import java.lang.StringBuilder

// user can see phone extensions for different departments and is able to edit them
/** TODO:
 * Make extension boxes not clickable until an edit button is pressed
 * Ask user if they're sure to update the extension (in a pop-up)
  */


class DeptExtensions : AppCompatActivity() {

    val deptExtensions = HashMap<String, String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dept_extensions)

        // save extensions
        saveExtensionsButton.setOnClickListener {
            updateExtensions()
            //save extensions function

            toast("Done")
        }
    }

    fun updateExtensions() {
        deptExtensions.clear()
        deptExtensions.put("Puller", pullerExtBox.text.toString())
        deptExtensions.put("Appliances", appliancesExtBox.text.toString())
        deptExtensions.put("ToolRental", toolRentalExtBox.text.toString())
        deptExtensions.put("Millwork", millworkExtBox.text.toString())
        deptExtensions.put("Receiving", receivingExtBox.text.toString())
        deptExtensions.put("ProDesk", proDeskExtBox.text.toString())
        deptExtensions.put("CustomerService", custServExtBox.text.toString())
        deptExtensions.put("Flooring", flooringExtBox.text.toString())
    }
}
