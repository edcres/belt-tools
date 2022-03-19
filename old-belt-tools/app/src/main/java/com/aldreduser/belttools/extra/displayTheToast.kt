package com.aldreduser.belttools.extra

import android.content.Context
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.aldreduser.belttools.R

//todo make background round

fun displayToastMessage(context: Context, message: String) {

    val toast = Toast.makeText(context, message, Toast.LENGTH_SHORT)
    val view: View = toast.view

    val backgroundColor = ContextCompat.getColor(context, R.color.toastBackground)
    val textColor = ContextCompat.getColor(context, R.color.toastText)

    view.setBackgroundColor(backgroundColor)

    val text:TextView = view.findViewById(android.R.id.message)
    text.setTextColor(textColor)

    toast.show()
}