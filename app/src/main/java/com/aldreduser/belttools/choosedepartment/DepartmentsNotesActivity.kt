package com.aldreduser.belttools.choosedepartment

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.aldreduser.belttools.R
import com.aldreduser.belttools.extra.displayToastMessage
import kotlinx.android.synthetic.main.activity_appliances_dept.*

// pass data to this app (string saying what department the user wants)

class DepartmentsNotesActivity : AppCompatActivity() {

    val departmentPassed = /* passed data */
    val savedNoteName = "$departmentPassed note"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_appliances_dept)
        depmntNoteTitleBox.text = departmentPassed

        getData()

        departmentsNotesSaveButton.setOnClickListener {
            saveData()
        }
    }

    private fun saveData() { //might have to add (view: View) parameter
        //pass the name of the note as a parameter
        val departmentsNotesSharedPref = this.getPreferences(Context.MODE_PRIVATE) ?: return
        with(departmentsNotesSharedPref.edit()) {
            putString(/*todo string passed as a parameter*/, departmentsNotesText.text.toString())
            commit()
            callToast("Saved")
        }
    }

    private fun getData() {
        //pass the name of the note as a parameter
        val departmentsNotesSharedPref = this.getPreferences(Context.MODE_PRIVATE) ?: return
        departmentsNotesText.setText(appliancesNotesSharedPref.getString(/*todo string passed as a parameter*/, ""))
    }

    private fun callToast(message: String) {
        displayToastMessage(this, message)
    }

}
