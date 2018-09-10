package com.example.aleksandr.myapplication.ui.login

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.aleksandr.myapplication.R
import com.example.aleksandr.myapplication.setSimpleTextWatcher
import com.example.aleksandr.myapplication.ui.login.presenter.RegistrationPresenter
import com.example.aleksandr.myapplication.ui.main.MainActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.view_signup.*


class RegistrationActivity : AppCompatActivity(), IRegistrationActivity {

    //    private val activity = this@RegistrationActivity
    lateinit var presenter: RegistrationPresenter
    private var auth: FirebaseAuth? = null

    private val spinner_country = arrayOf(
            "Киев", "Харьков", "Днепропетровск", "Житомир", "Львов", "Одесса", "Чернигов"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        super.setContentView(R.layout.view_signup)
        presenter = RegistrationPresenter(this, application)


        input_name.setSimpleTextWatcher {
            presenter.onResetError()
            presenter.onUpdateOwnerName(it)
        }
        input_email.setSimpleTextWatcher {
            presenter.onResetError()
            presenter.onUpdateOwnerEmail(it)
        }
        input_password.setSimpleTextWatcher {
            presenter.onResetError()
            presenter.onUpdateOwnerPassword(it)
        }
        input_reEnterPassword.setSimpleTextWatcher {
            presenter.onResetError()
            presenter.onUpdateOwnerRePassword(it)
        }
        checkbox_GDPR.setOnCheckedChangeListener { _, isChecked ->
            presenter.onResetError()
            presenter.onUpdateCheckboxGdpr(isChecked)
        }
        btn_signup.setOnClickListener {
            presenter.onValidateAndSave()
        }

        button_back.setOnClickListener {
            startActivity(Intent(applicationContext, LoginActivity::class.java))
            finish()
            overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out)
        }

        val spinnerCountryAdapter = ArrayAdapter(this, R.layout.spinner_simple_item, spinner_country)
        spinnerCountryAdapter.setDropDownViewResource(R.layout.spinner_drop_down)
        input_owner_city.adapter = spinnerCountryAdapter
        input_owner_city.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                presenter.onUpdateOwnerAddress(spinner_country[position])
            }
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        presenter.bindView(this)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        presenter.unbindView(this)
    }

    override fun showInvalidValue(errorField: IRegistrationActivity.InvalidValue) {
        when (errorField) {
            IRegistrationActivity.InvalidValue.NO_NAME -> text_InputLayoutName.error = resources.getString(R.string.no_name)
            IRegistrationActivity.InvalidValue.NO_EMAIL -> text_InputLayoutEmail.error = resources.getString(R.string.no_email)
            IRegistrationActivity.InvalidValue.INVALID_EMAIL -> text_InputLayoutEmail.error = resources.getString(R.string.email_incorrect)
            IRegistrationActivity.InvalidValue.EMAIL_ALREADY_EXIST -> text_InputLayoutEmail.error = resources.getString(R.string.email_exist)
            IRegistrationActivity.InvalidValue.NO_PASSWORD -> text_InputLayoutPassword.error = resources.getString(R.string.no_password)
            IRegistrationActivity.InvalidValue.PASSWORD_TOO_SHORT -> text_InputLayoutPassword.error = resources.getString(R.string.password_too_short)
            IRegistrationActivity.InvalidValue.REPASSWORD_WRONG -> text_InputLayoutPassword.error = resources.getString(R.string.repassword_wrong)
            IRegistrationActivity.InvalidValue.CHECKBOX_DISABLE -> checkbox_gdpr_error.text = resources.getString(R.string.checkbox_gdpr)
            IRegistrationActivity.InvalidValue.NO_INTERNET -> resources.getString(R.string.no_internet)
        }
    }

    override fun onResetError() {
        text_InputLayoutName.error = ""
        text_InputLayoutEmail.error = ""
        text_InputLayoutPassword.error = ""
        textInputLayoutConfirmPassword.error = ""
        checkbox_GDPR.text = ""
    }

    override fun onSignUpSuccess() {
        val progressDialog = ProgressDialog(this@RegistrationActivity)
        progressDialog.isIndeterminate = true
        progressDialog.setMessage(resources.getString(R.string.signup_successful))
        progressDialog.show()
        val email = input_email.text.toString().trim()
        val password = input_password.text.toString().trim()
        auth?.createUserWithEmailAndPassword(email, password)
                ?.addOnCompleteListener(this@RegistrationActivity) { task ->
                    Toast.makeText(this@RegistrationActivity, "createUserWithEmail:onComplete:" + task.isSuccessful, Toast.LENGTH_SHORT).show()
                    progressDialog.dismiss()

                    if (!task.isSuccessful) {
                        Toast.makeText(this@RegistrationActivity, "Authentication failed." + task.exception!!,
                                Toast.LENGTH_SHORT).show()
                    } else {
                        startActivity(Intent(this@RegistrationActivity, MainActivity::class.java))
                        finish()
                    }
                }
    }

    override fun setButtonCreateEnabled(isEnabled: Boolean) {
        btn_signup.isEnabled = isEnabled
    }
}
