package com.example.aleksandr.myapplication.ui.login

interface ILoginActivity {
    fun onLoginSuccess()
    fun onResetError()
    fun setButtonLoginEnabled(isEnable: Boolean)
    fun showInvalidValue(errorField: InvalidValue)
    fun verifyFromSQLite()

    enum class InvalidValue {
        NO_EMAIL,
        INVALID_EMAIL,
        NO_PASSWORD,
        PASSWORD_TOO_SHORT
    }
}