package com.aldreduser.belttools.palletstoworkon

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import com.aldreduser.belttools.R
import com.aldreduser.belttools.extra.displayToastMessage
import kotlinx.android.synthetic.main.action_bar.*
import kotlinx.android.synthetic.main.activity_more_options_menu.*
import kotlinx.android.synthetic.main.activity_pallets_to_work_on.*

class PalletsToWorkOnActivity : AppCompatActivity() {
    private var savedNoteName = "SKU notes"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pallets_to_work_on)

        setSupportActionBar(palletsToolbar as Toolbar)
        toolbar_text.text = "Pallets"

        getData()

        palletsToWorkSaveButton.setOnClickListener {
            saveData()
        }
    }

    private fun saveData() { //might have to add (view: View) parameter
        //pass the name of the note as a parameter
        val palletsNotesSharedPref = this.getPreferences(Context.MODE_PRIVATE) ?: return
        with(palletsNotesSharedPref.edit()) {
            putString(savedNoteName, palletsNotesText.text.toString())
            commit()
            callToast("Saved")
        }
    }

    private fun getData() {
        //pass the name of the note as a parameter
        val palletsNotesSharedPref = this.getPreferences(Context.MODE_PRIVATE) ?: return
        palletsNotesText.setText(palletsNotesSharedPref.getString(savedNoteName, ""))
    }

    private fun callToast(message: String) {
        displayToastMessage(this, message)
    }
}
