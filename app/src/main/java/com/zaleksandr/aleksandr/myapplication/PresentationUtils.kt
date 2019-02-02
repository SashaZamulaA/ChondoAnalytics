package com.zaleksandr.aleksandr.myapplication

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.ContextWrapper
import android.content.DialogInterface
import android.text.Editable
import android.text.SpannableString
import android.text.TextWatcher
import android.text.method.LinkMovementMethod
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import java.lang.ref.WeakReference
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

fun Context.showMaterialDialogCancelDelete(title: String?, message: String, onNoClick: (DialogInterface) -> Unit, onYesClick: (DialogInterface) -> Unit) = showMaterialDialog2Button(title, message, resources.getString(R.string.cancel), onNoClick, resources.getString(R.string.delete), onYesClick)
fun Context.showMaterialDialogCancelContinueCustom(title: String?, message: CharSequence, continueText: String, onCancelClick: (DialogInterface) -> Unit, onContinueClick: (DialogInterface) -> Unit) = showMaterialDialog2Button(title, message, resources.getString(R.string.cancel), onCancelClick, continueText, onContinueClick)
fun Context.showMaterialDialogCancelContinueCustom(titleResId: Int?, messageResId: Int, continueTextResId: Int, onCancelClick: (DialogInterface) -> Unit, onContinueClick: (DialogInterface) -> Unit) = showMaterialDialogCancelContinueCustom(titleResId?.let { resources.getString(it) }, spannableResString(messageResId), resources.getString(continueTextResId), onCancelClick, onContinueClick)
fun Context.showMaterialDialog2Button(title: String?, message: CharSequence, buttonNeg: String, onClickNeg: (DialogInterface) -> Unit, buttonPos: String, onClickPos: (DialogInterface) -> Unit): AlertDialog =
        AlertDialog.Builder(this).run {
            setCancelable(false)
            title?.let { setTitle(it) }
            setMessage(message)
            setNegativeButton(buttonNeg) { dialog, _ -> onClickNeg(dialog) }
            setPositiveButton(buttonPos) { dialog, _ -> onClickPos(dialog) }
            show().apply {
                getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(resources.getColor(R.color.accent_darker))
                getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(resources.getColor(R.color.accent_darker))
                this.findViewById<TextView>(android.R.id.message)!!.run { movementMethod = LinkMovementMethod.getInstance(); setLinkTextColor(resources.getColor(R.color.blue)) }

            }
        }

fun Context.showMaterialDialogNoYes(title: String?, message: CharSequence, onNoClick: (DialogInterface) -> Unit, onYesClick: (DialogInterface) -> Unit) = showMaterialDialog2Button(title, message, resources.getString(R.string.no), onNoClick, resources.getString(R.string.yes), onYesClick)
fun Context.showMaterialDialogNoYes(titleResId: Int?, messageResId: Int, onNoClick: (DialogInterface) -> Unit, onYesClick: (DialogInterface) -> Unit) = showMaterialDialogNoYes(titleResId?.let { resources.getString(it) }, spannableResString(messageResId), onNoClick, onYesClick)



class DialogCompositeDisposable {

private val dialogs: MutableList<WeakReference<Dialog>> = mutableListOf()

fun addDialog(dialog: Dialog) {
    dialogs.add(WeakReference(dialog))
}
}

fun Dialog.addTo(disposable: DialogCompositeDisposable) = disposable.addDialog(this)