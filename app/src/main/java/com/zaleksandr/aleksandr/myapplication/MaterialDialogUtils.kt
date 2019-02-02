package com.zaleksandr.aleksandr.myapplication

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import android.text.method.LinkMovementMethod
import android.widget.TextView

fun Context.showMaterialDialogPositiveButton(title: String?, message: CharSequence, button: String, onClick: (DialogInterface) -> Unit): AlertDialog =
        AlertDialog.Builder(this).run {
            setCancelable(false)
            title?.let { setTitle(it) }
            setMessage(message)
            setPositiveButton(button) { dialog, _ -> onClick(dialog) }
            show().apply { this.findViewById<TextView>(android.R.id.message)!!.run { movementMethod = LinkMovementMethod.getInstance(); setLinkTextColor(resources.getColor(R.color.blue)) } }
        }