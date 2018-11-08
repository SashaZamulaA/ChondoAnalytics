package com.example.aleksandr.myapplication

import android.app.Activity
import android.app.AlertDialog
import android.app.ProgressDialog.show
import android.content.Context
import android.content.ContextWrapper
import android.content.DialogInterface
import android.support.design.widget.TextInputEditText
import android.text.Editable
import android.text.SpannableString
import android.text.TextWatcher
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import java.util.regex.Pattern

fun Activity.hideKeyboard() = currentFocus?.also { (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).apply { hideSoftInputFromWindow(it.windowToken, 0) } }

fun Boolean.toAndroidVisibility() = if (this) View.VISIBLE else View.GONE

fun EditText.setSimpleTextWatcher(watcher: (String) -> Unit): TextWatcher =
        object : TextWatcher {
            override fun afterTextChanged(s: Editable?) = watcher(s.toString())
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        }.also { addTextChangedListener(it) }


fun EditText.setTextSmartly(text: CharSequence) {
    if (getText().toString() != text) {
        text.length.also {
            val ss = selectionStart.run { if (this <= it) this else it }
            val se = selectionEnd.run { if (this <= it) this else it }
            setText(text)
            setSelection(ss, se)
        }
    }
}

fun Context.getActivity(): Activity? {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context
        context = context.baseContext
    }
    return null
}

class SimpleLock {
    var isLocked = false

    inline fun lockWhile(body: () -> Unit) {
        isLocked = true
        body()
        isLocked = false
    }
}

fun doNothing() {}

private val PATTERN_EMAIL_ADDRESS_VALIDATOR = Pattern.compile("""(?:[a-z0-9!#${'$'}%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#${'$'}%&'*+/=?^_`{|}~-]+)*|"(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21\x23-\x5b\x5d-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])*")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21-\x5a\x53-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])+)\])""")
fun String.isEmailAddressValid(): Boolean = PATTERN_EMAIL_ADDRESS_VALIDATOR.matcher(this.toLowerCase()).matches()

fun Context.spannableResString(stringId: Int) = SpannableString(getText(stringId))
fun Context.showMaterialDialogOk(title: String?, message: CharSequence, onOkClick: (DialogInterface) -> Unit) = showMaterialDialogPositiveButton(title, message, resources.getString(R.string.ok), onOkClick)
fun Context.showMaterialDialogOk(titleResId: Int?, messageResId: Int, onOkClick: (DialogInterface) -> Unit) = showMaterialDialogOk(titleResId?.let { resources.getString(it) }, spannableResString(messageResId), onOkClick)

