package com.example.aleksandr.myapplication.ui.login

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.aleksandr.myapplication.R
import com.example.aleksandr.myapplication.setSimpleTextWatcher
import com.example.aleksandr.myapplication.ui.login.model.User
import com.example.aleksandr.myapplication.ui.login.presenter.RegistrationPresenter
import com.example.aleksandr.myapplication.ui.login.sql.DatabaseHelper
import com.example.aleksandr.myapplication.ui.main.MainActivity
import kotlinx.android.synthetic.main.view_signup.*


class RegistrationActivity : AppCompatActivity(), IRegistrationActivity {

    private val activity = this@RegistrationActivity
    lateinit var presenter: RegistrationPresenter
    lateinit var databaseHelper: DatabaseHelper

    private val spinner_country = arrayOf(
            "Киев", "Харьков", "Днепропетровск", "Житомир", "Львов", "Одесса", "Чернигов"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        super.setContentView(R.layout.view_signup)
        presenter = RegistrationPresenter(this, application)
        databaseHelper = DatabaseHelper(activity)

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

        link_login.setOnClickListener {
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

        val user = User(name = input_name!!.text.toString().trim(),
                email = input_email!!.text.toString().trim(),
                password = input_password!!.text.toString().trim())

        databaseHelper.addUser(user)
        startActivity(Intent(this@RegistrationActivity, MainActivity::class.java))
        finish()
    }

    override fun setButtonCreateEnabled(isEnabled: Boolean) {
        btn_signup.isEnabled = isEnabled
    }

}


//    private val activity = this@RegistrationActivity
//    private lateinit var nestedScrollView: NestedScrollView
//    private lateinit var inputValidation: InputValidation
//    private lateinit var databaseHelper: DatabaseHelper
//
//
//        nestedScrollView = findViewById<View>(R.id.nestedScrollView) as NestedScrollView
//
//        initListeners()
//
//        // initializing the objects
//        initObjects()
//    }
//
//
//    private fun initListeners() {
//        btn_signup.setOnClickListener(this)
//        link_login.setOnClickListener(this)
//
//    }
//
//    /**
//     * This method is to initialize objects to be used
//     */
//    private fun initObjects() {
//        inputValidation = InputValidation(activity)
//        databaseHelper = DatabaseHelper(activity)
//
//
//    }
//
//
//    /**
//     * This implemented method is to listen the click on view
//     *
//     * @param v
//     */
//    override fun onClick(v: View) {
//        when (v.id) {
//
//            R.id.btn_signup -> postDataToSQLite()
//
//            R.id.link_login -> {  val intentRegister = Intent(applicationContext, LoginActivity::class.java)
//                startActivity(intentRegister)}
//        }
//    }
//
//    /**
//     * This method is to validate the input text fields and post data to SQLite
//     */
//    private fun postDataToSQLite() {
//        if (!inputValidation.isInputEditTextFilled(input_name, textInputLayoutName, getString(R.string.error_message_name))) {
//
//        }
//        if (!inputValidation.isInputEditTextFilled(editText_email, text_InputLayoutEmail, getString(R.string.error_message_email))) {
//
//        }
//        if (!inputValidation.isInputEditTextEmail(editText_email, text_InputLayoutEmail, getString(R.string.error_message_email))) {
//
//        }
//        if (!inputValidation.isInputEditTextFilled(input_password, textInputLayoutPassword, getString(R.string.error_message_password))) {
//
//        }
//        if (!inputValidation.isInputEditTextMatches(input_reEnterPassword, input_reEnterPassword,
//                        textInputLayoutConfirmPassword, getString(R.string.error_password_match))) {
//
//        }
//        if (!databaseHelper.checkUser(editText_email.text.toString().trim())) {
//
//            var user = User(name = input_name.text.toString().trim(),
//                    email = editText_email.text.toString().trim(),
//                    password = input_password.text.toString().trim())
//
//            databaseHelper.addUser(user)
//
//                      Snackbar.make(nestedScrollView, getString(R.string.success_message), Snackbar.LENGTH_LONG).show()
//            emptyInputEditText()
//
//
//        } else {
//            // Snack Bar to show error message that record already exists
//            Snackbar.make(nestedScrollView, getString(R.string.error_email_exists), Snackbar.LENGTH_LONG).show()
//        }
//
//
//    }
//
//    /**
//     * This method is to empty all input edit text
//     */
//    private fun emptyInputEditText() {
//        input_name.text = null
//        editText_email.text = null
//        input_password.text = null
//        input_reEnterPassword.text = null
//    }
//}
