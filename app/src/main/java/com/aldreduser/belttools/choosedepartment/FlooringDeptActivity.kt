package com.aldreduser.belttools.choosedepartment

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.aldreduser.belttools.R
import com.aldreduser.belttools.extra.displayToastMessage
import kotlinx.android.synthetic.main.activity_flooring_dept.*
import org.jetbrains.anko.toast

class FlooringDeptActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flooring_dept)

        getData()

        flooringNotesSaveButton.setOnClickListener {
            saveData()
        }
    }

    private fun saveData() { //might have to add (view: View) parameter
        val flooringNotesSharedPref = this.getPreferences(Context.MODE_PRIVATE) ?: return
        with(flooringNotesSharedPref.edit()) {
            putString("Notes", flooringNotesText.text.toString())
            commit()
            callToast("Saved")
        }
    }

    private fun getData() {
        val flooringNotesSharedPref = this.getPreferences(Context.MODE_PRIVATE) ?: return
        flooringNotesText.setText(flooringNotesSharedPref.getString("Notes", ""))
    }

    private fun callToast(message: String) {
        displayToastMessage(this, message)
    }
}
