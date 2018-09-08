package com.example.aleksandr.myapplication.ui.login.presenter

import android.app.Application
import com.example.aleksandr.myapplication.AndroidApplication
import com.example.aleksandr.myapplication.BasePresenter
import com.example.aleksandr.myapplication.isEmailAddressValid
import com.example.aleksandr.myapplication.ui.login.ILoginActivity
import com.example.aleksandr.myapplication.ui.login.LoginActivity

class LoginPresenter(loginView: LoginActivity, applicationComponent: Application): BasePresenter<LoginActivity>(loginView){
    init { (applicationComponent as AndroidApplication).applicationComponent.inject(this)}

        private var email = ""
        private var password = ""
        private var isValidateSuccess = true

    override fun onBindView() {}
    override fun onUnbindView() {}

    fun onValidateAndLogin() {
        if (email.isEmpty()) {
            view?.showInvalidValue(ILoginActivity.InvalidValue.NO_EMAIL)
            view?.setButtonLoginEnabled(false)
            isValidateSuccess = false
        } else if (!email.isEmailAddressValid()) {
            view?.showInvalidValue(ILoginActivity.InvalidValue.INVALID_EMAIL)
            view?.setButtonLoginEnabled(false)
            isValidateSuccess = false
        }

        if (password.isEmpty()) {
            view?.showInvalidValue(ILoginActivity.InvalidValue.NO_PASSWORD)
            view?.setButtonLoginEnabled(false)
            isValidateSuccess = false
        } else if (password.length < 4) {
            view?.showInvalidValue(ILoginActivity.InvalidValue.PASSWORD_TOO_SHORT)
            view?.setButtonLoginEnabled(false)
            isValidateSuccess = false
        }
        if (isValidateSuccess) view?.onLoginSuccess()
    }

    fun onResetError() {
        isValidateSuccess = true
        view?.onResetError()
        view?.setButtonLoginEnabled(true)
    }

    fun onUpdateOwnerEmail(email: String) {
        this.email = email
    }
    fun onUpdateOwnerPassword(password: String) {
        this.password = password
    }
}