package com.aldreduser.belttools.choosedepartment

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.aldreduser.belttools.R
import kotlinx.android.synthetic.main.activity_choose_department.*

class ChooseDepartmentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_department)

        appliancesChooseButton.setOnClickListener {
            var intentApp = Intent(this, DepartmentsNotesActivity::class.java)
            intentApp.putExtra("departmentName", "Appliances")
            startActivity(intentApp)

            // delete below
            /*val newIntent = Intent(this, AppliancesDeptActivity::class.java) // maybe change 'newIntent' name
            startActivity(newIntent)*/
        }

        flooringChooseButton.setOnClickListener {
            var intentFlr = Intent(this, DepartmentsNotesActivity::class.java)
            intentFlr.putExtra("departmentName", "Flooring")
            startActivity(intentFlr)

            // delete below
            /*val newIntent = Intent(this, FlooringDeptActivity::class.java) // maybe change 'newIntent' name
            startActivity(newIntent)*/
        }

        proDeskChooseButton.setOnClickListener {
            var intentPro = Intent(this, DepartmentsNotesActivity::class.java)
            intentPro.putExtra("departmentName", "Pro Desk")
            startActivity(intentPro)

            // delete below
            /*val newIntent = Intent(this, ProDeskDeptActivity::class.java) // maybe change 'newIntent' name
            startActivity(newIntent)*/
        }

    }
}
