package com.example.aleksandr.myapplication.ui.login.presenter

import android.app.Application
import com.example.aleksandr.myapplication.AndroidApplication
import com.example.aleksandr.myapplication.BasePresenter
import com.example.aleksandr.myapplication.isEmailAddressValid
import com.example.aleksandr.myapplication.ui.login.IRegistrationActivity
import com.example.aleksandr.myapplication.ui.login.RegistrationActivity

class RegistrationPresenter (registrationActivity: RegistrationActivity, application: Application) : BasePresenter<RegistrationActivity>(registrationActivity) {
    init { (application as AndroidApplication).applicationComponent.ingect(this) }
    private var name = ""
    private var address = ""
    private var email = ""
    private var password = ""
    private var repassword = ""
    private var cb_gdpr = false
    private var isValidateSuccess = true

    override fun onBindView() {
        view?.setButtonCreateEnabled(isValidateSuccess)
    }
    override fun onUnbindView() {  }

    fun onResetError() {
        isValidateSuccess = true
        view?.onResetError()
        view?.setButtonCreateEnabled(true)
    }

    fun onUpdateOwnerName(name: String) {
        this.name = name
    }
    fun onUpdateOwnerAddress(address: String) {
        this.address = address
    }
    fun onUpdateOwnerEmail(email: String) {
        this.email = email
    }
    fun onUpdateOwnerPassword(password: String) {
        this.password = password
    }
    fun onUpdateOwnerRePassword(repassword: String) {
        this.repassword = repassword
    }
    fun onUpdateCheckboxGdpr(isChecked: Boolean) {
        this.cb_gdpr = isChecked
    }

    fun onValidateAndSave() {
        if (name.isEmpty()) {
            view?.showInvalidValue(IRegistrationActivity.InvalidValue.NO_NAME)
            view?.setButtonCreateEnabled(false)
            isValidateSuccess = false
        }

        if (email.isEmpty()) {
            view?.showInvalidValue(IRegistrationActivity.InvalidValue.NO_EMAIL)
            view?.setButtonCreateEnabled(false)
            isValidateSuccess = false
        } else if (!email.isEmailAddressValid()) {
            view?.showInvalidValue(IRegistrationActivity.InvalidValue.INVALID_EMAIL)
            view?.setButtonCreateEnabled(false)
            isValidateSuccess = false
        }

        if (password.isEmpty()) {
            view?.showInvalidValue(IRegistrationActivity.InvalidValue.NO_PASSWORD)
            view?.setButtonCreateEnabled(false)
            isValidateSuccess = false
        } else if (password.length < 4) {
            view?.showInvalidValue(IRegistrationActivity.InvalidValue.PASSWORD_TOO_SHORT)
            view?.setButtonCreateEnabled(false)
            isValidateSuccess = false
        }

        if (repassword.length < 4 || repassword != password) {
            view?.showInvalidValue(IRegistrationActivity.InvalidValue.REPASSWORD_WRONG)
            view?.setButtonCreateEnabled(false)
            isValidateSuccess = false
        }

        if (!cb_gdpr) {
            view?.showInvalidValue(IRegistrationActivity.InvalidValue.CHECKBOX_DISABLE)
            view?.setButtonCreateEnabled(false)
            isValidateSuccess = false
        }
        if (isValidateSuccess) view?.onSignUpSuccess()
    }
}
