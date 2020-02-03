package com.aldreduser.belttools.choosedepartment

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.aldreduser.belttools.R
import com.aldreduser.belttools.extra.displayToastMessage
import kotlinx.android.synthetic.main.activity_appliances_dept.*

class AppliancesDeptActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_appliances_dept)

        getData()

        appliancesNotesSaveButton.setOnClickListener {
            saveData()
        }
    }

    private fun saveData() { //might have to add (view: View) parameter
        val appliancesNotesSharedPref = this.getPreferences(Context.MODE_PRIVATE) ?: return
        with(appliancesNotesSharedPref.edit()) {
            putString("Notes", appliancesNotesText.text.toString())
            commit()
            callToast("Saved")
        }
    }

    private fun getData() {
        val appliancesNotesSharedPref = this.getPreferences(Context.MODE_PRIVATE) ?: return
        appliancesNotesText.setText(appliancesNotesSharedPref.getString("Notes", ""))
    }

    private fun callToast(message: String) {
        displayToastMessage(this, message)
    }
}
