package com.aldreduser.belttools.skustoworkon

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import com.aldreduser.belttools.R
import com.aldreduser.belttools.extra.displayToastMessage
import kotlinx.android.synthetic.main.action_bar.*
import kotlinx.android.synthetic.main.activity_more_options_menu.*
import kotlinx.android.synthetic.main.activity_skus_to_work_on.*

class SKUsToWorkOnActivity : AppCompatActivity() {
    private var savedNoteName = "SKU notes"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_skus_to_work_on)

        setSupportActionBar(SKUsToolbar as Toolbar)
        toolbar_text.text = "SKUs"

        getData()

        sKUsToWorkSaveButton.setOnClickListener {
            saveData()
        }
    }

    private fun saveData() { //might have to add (view: View) parameter
        //pass the name of the note as a parameter
        val sKUsNotesSharedPref = this.getPreferences(Context.MODE_PRIVATE) ?: return
        with(sKUsNotesSharedPref.edit()) {
            putString(savedNoteName, sKUsNotesText.text.toString())
            commit()
            callToast("Saved")
        }
    }

    private fun getData() {
        //pass the name of the note as a parameter
        val sKUsNotesSharedPref = this.getPreferences(Context.MODE_PRIVATE) ?: return
        sKUsNotesText.setText(sKUsNotesSharedPref.getString(savedNoteName, ""))
    }

    private fun callToast(message: String) {
        displayToastMessage(this, message)
    }
}
