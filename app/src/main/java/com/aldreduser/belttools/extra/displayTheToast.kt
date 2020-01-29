package com.aldreduser.belttools.extra

import android.content.Context
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.aldreduser.belttools.R
import kotlinx.android.synthetic.main.activity_home_screen.view.*


//todo make bacground round

fun displayToastMessage(context: Context, message: String) {

    var toast = Toast.makeText(context, message, Toast.LENGTH_SHORT)
    var view:View = toast.view

    var backgroundColor = ContextCompat.getColor(context, R.color.toastBackground)
    var textColor = ContextCompat.getColor(context, R.color.toastText)

    view.setBackgroundColor(backgroundColor)

    var text:TextView = view.findViewById(android.R.id.message)
    text.setTextColor(textColor)

    toast.show()
}