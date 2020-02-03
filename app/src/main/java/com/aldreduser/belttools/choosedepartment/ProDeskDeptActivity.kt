package com.aldreduser.belttools.choosedepartment

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.aldreduser.belttools.R
import com.aldreduser.belttools.extra.displayToastMessage
import kotlinx.android.synthetic.main.activity_pro_desk_dept.*
import org.jetbrains.anko.toast

class ProDeskDeptActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pro_desk_dept)

        getData()

        proDeskNotesSaveButton.setOnClickListener {
            saveData()
        }
    }

    private fun saveData() { //might have to add (view: View) parameter
        val proDeskNotesSharedPref = this.getPreferences(Context.MODE_PRIVATE) ?: return
        with(proDeskNotesSharedPref.edit()) {
            putString("Notes", proDeskNotesText.text.toString())
            commit()
            callToast("Saved")
        }
    }

    private fun getData() {
        val proDeskNotesSharedPref = this.getPreferences(Context.MODE_PRIVATE) ?: return
        proDeskNotesText.setText(proDeskNotesSharedPref.getString("Notes", ""))
    }

    private fun callToast(message: String) {
        displayToastMessage(this, message)
    }
}
