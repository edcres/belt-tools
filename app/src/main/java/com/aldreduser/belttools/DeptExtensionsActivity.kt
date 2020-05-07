package com.aldreduser.belttools

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_dept_extensions.*
import org.jetbrains.anko.toast
import android.content.Context
import android.support.v7.app.AlertDialog
import android.content.DialogInterface
import android.support.v7.widget.Toolbar
import kotlinx.android.synthetic.main.action_bar.*
import kotlinx.android.synthetic.main.activity_more_options_menu.*

// user can see phone extensions for different departments and is able to edit them
/** TODO:
 * Clean up code and do an array of objects for the ext boxes. Make displayExtensions() and updateExtensions() simple
 * UI: have the button below the scrollView
  */

//android:gravity="start|top"

class DeptExtensionsActivity : AppCompatActivity() {

    private val deptExtensions = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dept_extensions)

        setSupportActionBar(extensionsToolbar as Toolbar)
        toolbar_text.text = "Extensions"

        updateExtensions() //I need this here to give initial values to the array. Might replace later
        getData()
        displayExtensions()

        // update and save extensions
        saveExtensionsButton.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Are you sure?")
            builder.setPositiveButton("Yes") { _: DialogInterface?, _: Int ->
                deptExtensions.clear()
                updateExtensions()
                saveData()
            }
            builder.setNegativeButton("No") { _: DialogInterface?, _: Int -> }
            builder.show()
        }
    }

    private fun displayExtensions() {
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
    private fun updateExtensions() {
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

    private fun saveData() { //might have to add (view: View) parameter
        val deptExtensionsSharedPref = this.getPreferences(Context.MODE_PRIVATE) ?: return
        with(deptExtensionsSharedPref.edit()) {
            for(item in deptExtensions.indices) {   // might be a problem here
                putString("$item", deptExtensions[item])
            }
            commit()
            toast("Saved")
        }
    }

    private fun getData() {
        val deptExtensionsSharedPref = this.getPreferences(Context.MODE_PRIVATE) ?: return
        for (item in deptExtensions.indices) {
            try {
                deptExtensions[item] = deptExtensionsSharedPref.getString("$item", "")!!  //this is unwrapped
            } catch (e: NullPointerException) {toast("Something went wrong.")} //probably wrong exception
        }
    }
}
