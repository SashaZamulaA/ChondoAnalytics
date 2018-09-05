package com.example.aleksandr.myapplication.ui.login.helpers

import android.app.Activity
import android.content.Context
import android.support.design.widget.TextInputLayout
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

class InputValidation(private val context: Context) {


    fun isInputEditTextFilled(textInputEditText: EditText, textInputLayout: TextInputLayout, message: String): Boolean {
        val value = textInputEditText.text.toString().trim()
        if (value.isEmpty()) {
            textInputLayout.error = message
            hideKeyboardFrom(textInputEditText)
            return false
        } else {
            textInputLayout.isErrorEnabled = false
        }

        return true
    }

    fun isInputEditTextEmail(textInputEditText: EditText, textInputLayout: TextInputLayout, message: String): Boolean {
        val value = textInputEditText.text.toString().trim()
        if (value.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(value).matches()) {
            textInputLayout.error = message
            hideKeyboardFrom(textInputEditText)
            return false
        } else {
            textInputLayout.isErrorEnabled = false
        }
        return true
    }

    fun isInputEditTextMatches(textInputEditText1: EditText, textInputEditText2: EditText, textInputLayout: TextInputLayout, message: String): Boolean {
        val value1 = textInputEditText1.text.toString().trim()
        val value2 = textInputEditText2.text.toString().trim()
        if (!value1.contentEquals(value2)) {
            textInputLayout.error = message
            hideKeyboardFrom(textInputEditText2)
            return false
        } else {
            textInputLayout.isErrorEnabled = false
        }
        return true
    }

       private fun hideKeyboardFrom(view: View) {
        val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
    }
}
