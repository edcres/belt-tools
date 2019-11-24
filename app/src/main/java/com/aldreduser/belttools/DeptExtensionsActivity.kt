package com.aldreduser.belttools

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_dept_extensions.*
import org.jetbrains.anko.toast
import android.content.Context
import kotlinx.android.synthetic.main.activity_main_layout.*

// user can see phone extensions for different departments and is able to edit them
/** TODO:
 * Make extension boxes not clickable until an edit button is pressed
 * Ask user if they're sure to update the extension (in a pop-up)
 * Clean up code and do an array of objects for the ext boxes. Make displayExtensions() and updateExtensions() simple
 * UI: have the button below the scrollView
  */

//android:gravity="start|top"

class DeptExtensionsActivity : AppCompatActivity() {

    val deptExtensions = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dept_extensions)

        updateExtensions() //I need this here to give values to the array. Might replace later
        getData()
        displayExtensions()

        // update and save extensions
        saveExtensionsButton.setOnClickListener {
            deptExtensions.clear()
            updateExtensions()
            saveData()
        }
    }

    fun displayExtensions() {
        pullerExtBox.setText(deptExtensions[0])
        appliancesExtBox.setText(deptExtensions[1])
        toolRentalExtBox.setText(deptExtensions[2])
        millworkExtBox.setText(deptExtensions[3])
        receivingExtBox.setText(deptExtensions[4])
        proDeskExtBox.setText(deptExtensions[5])
        custServExtBox.setText(deptExtensions[6])
        flooringExtBox.setText(deptExtensions[7])
        cashierExtBox.setText(deptExtensions[8])
        gardenExtBox.setText(deptExtensions[9])
        hardwareExtBox.setText(deptExtensions[10])
        plumbingExtBox.setText(deptExtensions[11])
        electricalExtBox.setText(deptExtensions[12])
        paintExtBox.setText(deptExtensions[13])
        lumberExtBox.setText(deptExtensions[14])
    }
    fun updateExtensions() {
        deptExtensions.add(pullerExtBox.text.toString())
        deptExtensions.add(appliancesExtBox.text.toString())
        deptExtensions.add(toolRentalExtBox.text.toString())
        deptExtensions.add(millworkExtBox.text.toString())
        deptExtensions.add(receivingExtBox.text.toString())
        deptExtensions.add(proDeskExtBox.text.toString())
        deptExtensions.add(custServExtBox.text.toString())
        deptExtensions.add(flooringExtBox.text.toString())
        deptExtensions.add(cashierExtBox.text.toString())
        deptExtensions.add(gardenExtBox.text.toString())
        deptExtensions.add(hardwareExtBox.text.toString())
        deptExtensions.add(plumbingExtBox.text.toString())
        deptExtensions.add(electricalExtBox.text.toString())
        deptExtensions.add(paintExtBox.text.toString())
        deptExtensions.add(lumberExtBox.text.toString())
    }

    fun saveData() { //might have to add (view: View) parameter
        val deptExtensionsSharedPref = this.getPreferences(Context.MODE_PRIVATE) ?: return
        with(deptExtensionsSharedPref.edit()) {
            for(item in deptExtensions.indices) {   // might be a problem here
                putString("$item", deptExtensions[item])
            }
            commit()
            toast("saved")
        }
    }

    fun getData() {
        val deptExtensionsSharedPref = this.getPreferences(Context.MODE_PRIVATE) ?: return
        for (item in deptExtensions.indices) {
            deptExtensions[item] = deptExtensionsSharedPref.getString("$item", "")
        }
    }
}
