package com.aldreduser.belttools

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
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

    fun saveData() { //might have to add (view: View) parameter
        val proDeskNotesSharedPref = this.getPreferences(Context.MODE_PRIVATE) ?: return
        with(proDeskNotesSharedPref.edit()) {
            putString("Notes", proDeskNotesText.text.toString())
            commit()
            toast("Saved")
        }
    }

    fun getData() {
        val proDeskNotesSharedPref = this.getPreferences(Context.MODE_PRIVATE) ?: return
        proDeskNotesText.setText(proDeskNotesSharedPref.getString("Notes", ""))
    }
}
