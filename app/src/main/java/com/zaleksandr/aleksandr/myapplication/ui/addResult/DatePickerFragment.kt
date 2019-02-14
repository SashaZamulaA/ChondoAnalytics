package com.zaleksandr.aleksandr.myapplication.ui.addResult

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import java.util.*

class DatePickerFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val monthOfYear = c.get(Calendar.MONTH)
        val dayOfMonth = c.get(Calendar.DAY_OF_MONTH)
        return DatePickerDialog(this.context!!, activity as DatePickerDialog.OnDateSetListener, year, monthOfYear, dayOfMonth)
    }
}