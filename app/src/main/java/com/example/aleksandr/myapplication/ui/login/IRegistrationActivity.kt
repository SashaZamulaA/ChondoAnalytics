package com.example.aleksandr.myapplication.ui.login

interface IRegistrationActivity {

    fun onResetError()
    fun setButtonCreateEnabled(isEnabled: Boolean)
    fun showInvalidValue(errorField: InvalidValue)
    fun onSignUpSuccess()

    enum class InvalidValue {
        NO_NAME,
        NO_EMAIL,
        INVALID_EMAIL,
        EMAIL_ALREADY_EXIST,
        NO_PASSWORD,
        PASSWORD_TOO_SHORT,
        REPASSWORD_WRONG,
        NO_INTERNET,
        CHECKBOX_DISABLE
    }
}