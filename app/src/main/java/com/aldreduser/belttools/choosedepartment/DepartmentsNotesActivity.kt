package com.aldreduser.belttools.choosedepartment

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import com.aldreduser.belttools.R
import com.aldreduser.belttools.extra.displayToastMessage
import kotlinx.android.synthetic.main.action_bar.*
import kotlinx.android.synthetic.main.activity_dept_notes.*
import kotlinx.android.synthetic.main.activity_more_options_menu.*
import org.jetbrains.anko.toast

// pass data to this activity (string saying what department the user wants)

class DepartmentsNotesActivity : AppCompatActivity() {

    private lateinit var departmentPassed: String
    private lateinit var savedNoteName:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dept_notes)

        departmentPassed = intent.getStringExtra("departmentName")
        savedNoteName = "$departmentPassed note"

        setSupportActionBar(deptNotesToolbar as Toolbar)
        toolbar_text.text = departmentPassed

        getData()

        departmentsNotesSaveButton.setOnClickListener {
            saveData()
        }
    }

    private fun saveData() { //might have to add (view: View) parameter
        //pass the name of the note as a parameter
        val departmentsNotesSharedPref = this.getPreferences(Context.MODE_PRIVATE) ?: return
        with(departmentsNotesSharedPref.edit()) {
            putString(savedNoteName, departmentsNotesText.text.toString())
            commit()
            callToast("Saved")
        }
    }

    private fun getData() {
        //pass the name of the note as a parameter
        val departmentsNotesSharedPref = this.getPreferences(Context.MODE_PRIVATE) ?: return
        departmentsNotesText.setText(departmentsNotesSharedPref.getString(savedNoteName, ""))
    }

    private fun callToast(message: String) {
        displayToastMessage(this, message)
    }
}
