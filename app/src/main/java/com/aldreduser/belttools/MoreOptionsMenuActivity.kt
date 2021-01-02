package com.aldreduser.belttools

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.aldreduser.belttools.choosedepartment.DepartmentsNotesActivity
import com.aldreduser.belttools.palletstoworkon.PalletsToWorkOnActivity
import com.aldreduser.belttools.skustoworkon.SKUsToWorkOnActivity
import com.aldreduser.belttools.specialtyorders.SpecialtyOrdersActivity
import kotlinx.android.synthetic.main.action_bar.*
import kotlinx.android.synthetic.main.activity_more_options_menu.*

/**
 * TODO:
 *
 *  SKUs to work on: phone can scan barcode and save item code (hopefully read the sku too). Can keep notes of skus to work and what to do
 *  Pallets to work on: phone can scan barcode and save pallet code. Can keep notes of pallets to work and what to do
 *  ^^^ copy to clipboard (so it can be pasted to the store app)
 */

class MoreOptionsMenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_more_options_menu)

        setSupportActionBar(moreOptionsToolbar as Toolbar)
        toolbar_text.text = "Options"

        extensionsButton.setOnClickListener {
            val newIntent = Intent(this, DeptExtensionsActivity::class.java)
            startActivity(newIntent)
        }
        sKUsToWorkOnButton.setOnClickListener {
            val newIntent = Intent(this, SKUsToWorkOnActivity::class.java)
            startActivity(newIntent)
        }
        palletsToWorkOnButton.setOnClickListener {
            val newIntent = Intent(this, PalletsToWorkOnActivity::class.java)
            startActivity(newIntent)
        }
        specialtyOrdersButton.setOnClickListener {
            val newIntent = Intent(this, SpecialtyOrdersActivity::class.java)
            startActivity(newIntent)
        }


        // make drop down list (spinner)
        // warning. change a name in the array and saved data is lost
        val options = arrayOf("Choose Department", "Pro Desk", "Flooring", "Customer Service", "Appliances", "Millwork")
        chooseDepartmentSpinner.adapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,options)
        chooseDepartmentSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                //textForSpinner.text = "Choose Department"
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                // choose what happens when each option is clicked (position)
                when (options[position]) {
                    //each one should be a string (ie. "Flooring")
                    //options[0]  is the default text "Choose Department"    (quick fix)
                    options[1] -> sendDepartmentName(options[1])
                    options[2] -> sendDepartmentName(options[2])
                    options[3] -> sendDepartmentName(options[3])
                    options[4] -> sendDepartmentName(options[4])
                    options[5] -> sendDepartmentName(options[5])
                }
            }
        }
    }

    fun sendDepartmentName(departmentName: String) {
        val intentApp = Intent(this, DepartmentsNotesActivity::class.java)
        intentApp.putExtra("departmentName", departmentName)
        startActivity(intentApp)
    }
}
