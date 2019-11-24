package com.aldreduser.belttools

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_choose_department.*

class ChooseDepartmentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_department)

        appliancesChooseButton.setOnClickListener {
            val newIntent = Intent(this, AppliancesDeptActivity::class.java) // maybe change 'newIntent' name
            startActivity(newIntent)
        }

        flooringChooseButton.setOnClickListener {
            val newIntent = Intent(this, FlooringDeptActivity::class.java) // maybe change 'newIntent' name
            startActivity(newIntent)
        }

        proDeskChooseButton.setOnClickListener {
            val newIntent = Intent(this, ProDeskDeptActivity::class.java) // maybe change 'newIntent' name
            startActivity(newIntent)
        }
    }
}
