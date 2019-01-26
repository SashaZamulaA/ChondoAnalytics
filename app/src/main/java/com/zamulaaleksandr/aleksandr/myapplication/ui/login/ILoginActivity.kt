package com.zamulaaleksandr.aleksandr.myapplication.ui.login

interface ILoginActivity {
    fun onLoginSuccess(onOk: () -> Unit)
    fun onResetError()
    fun setButtonLoginEnabled(isEnable: Boolean)
    fun showInvalidValue(errorField: InvalidValue)

    enum class InvalidValue {
        NO_EMAIL,
        INVALID_EMAIL,
        NO_PASSWORD,
        PASSWORD_TOO_SHORT
    }
}