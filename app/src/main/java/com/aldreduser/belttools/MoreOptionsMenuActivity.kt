package com.aldreduser.belttools

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.aldreduser.belttools.choosedepartment.ChooseDepartmentActivity
import com.aldreduser.belttools.choosedepartment.DepartmentsNotesActivity
import kotlinx.android.synthetic.main.activity_more_options_menu.*
import org.jetbrains.anko.toast

/**
 * TODO:
 *
 *  SKUs to work on: phone can scan barcode and save item code (hopefully read the sku too). Can keep notes of skus to work and what to do
 *  Pallets to work on: phone can scan barcode and save pallet code. Can keep notes of pallets to work and what to do
 *  ^^^ copy to clipboard (so it can be pasted to the store app)
 *
 */

class MoreOptionsMenuActivity : AppCompatActivity() {

    //chooseDepartmentSpinner
    //textForSpinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_more_options_menu)

        extensionsButton.setOnClickListener {
            val newIntent = Intent(this, DeptExtensionsActivity::class.java) // maybe change 'newIntent' name
            startActivity(newIntent)
        }





        val adapter = ArrayAdapter.createFromResource(this, R.array.departMentsToChooseArray, android.R.layout.simple_list_item_1)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        chooseDepartmentSpinner.adapter = adapter

        // todo: when department is selected, it's not going to the notes screen. There's no .onItemSelectedListener
        val nameInArray = resources.getStringArray(R.array.departMentsToChooseArray)
        when (chooseDepartmentSpinner.selectedItem.toString()) {
            //each one should be a string (ie. "Flooring")
            //nameInArray[0]  is the default text "Choose Department"    (quick fix)
            nameInArray[1] -> sendDepartmentName(nameInArray[1])
            nameInArray[2] -> sendDepartmentName(nameInArray[2])
            nameInArray[3] -> sendDepartmentName(nameInArray[3])
            nameInArray[4] -> sendDepartmentName(nameInArray[4])
            nameInArray[5] -> sendDepartmentName(nameInArray[5])
        }

        /*
        // make drop down list (spinner)
        var options = arrayOf("Pro Desk", "Flooring", "Customer Service", "Appliances", "Millwork")
        chooseDepartmentSpinner.adapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,options)

        chooseDepartmentSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                //textForSpinner.text = "Select an Option"
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                // choose what happens when each option is clicked (position)



                textForSpinner.text = options.get(position)
            }
        }

         */







        chooseDptButton.setOnClickListener {
            val newIntent = Intent(this, ChooseDepartmentActivity::class.java)
            startActivity(newIntent)
        }
    }

    fun sendDepartmentName(departmentName: String) {
        val intentApp = Intent(this, DepartmentsNotesActivity::class.java)
        intentApp.putExtra("departmentName", departmentName)
        startActivity(intentApp)
    }
}
