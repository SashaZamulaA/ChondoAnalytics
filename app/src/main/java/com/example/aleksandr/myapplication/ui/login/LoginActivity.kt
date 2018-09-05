package com.example.aleksandr.myapplication.ui.login

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.TextInputEditText
import android.support.design.widget.TextInputLayout
import android.support.v4.widget.NestedScrollView
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.AppCompatButton
import android.support.v7.widget.AppCompatTextView
import android.view.View
import com.example.aleksandr.myapplication.R
import com.example.aleksandr.myapplication.ui.login.helpers.InputValidation
import com.example.aleksandr.myapplication.ui.login.presenter.LoginPresenter
import com.example.aleksandr.myapplication.ui.login.sql.DatabaseHelper
import com.example.aleksandr.myapplication.ui.main.MainActivity
import kotlinx.android.synthetic.main.view_login.*

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private val activity = this@LoginActivity

    private lateinit var presenter: LoginPresenter
    private lateinit var nestedScrollView: NestedScrollView

    private lateinit var textInputLayoutEmail: TextInputLayout
    private lateinit var textInputLayoutPassword: TextInputLayout

    private lateinit var textInputEditTextEmail: TextInputEditText
    private lateinit var textInputEditTextPassword: TextInputEditText

    private lateinit var appCompatButtonLogin: AppCompatButton

    private lateinit var textViewLinkRegister: AppCompatTextView

    private lateinit var inputValidation: InputValidation
    private lateinit var databaseHelper: DatabaseHelper


    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_login)
        presenter = LoginPresenter(this, application)
      //        if (auth?.currentUser != null) {
//            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
//            finish()
//        }
        initViews()

        // initializing the listeners
        initListeners()

        // initializing the objects
        initObjects()

        link_signup.setOnClickListener {

            val intent = Intent(applicationContext, RegistrationActivity::class.java)
            startActivityForResult(intent, REQUEST_SIGNUP)
            finish()
        }

    }

    private fun initViews() {

        nestedScrollView = findViewById<View>(R.id.nestedScrollView) as NestedScrollView

        textInputLayoutEmail = findViewById<View>(R.id.textInputLayoutEmail) as TextInputLayout
        textInputLayoutPassword = findViewById<View>(R.id.text_InputLayoutPassword) as TextInputLayout

        textInputEditTextEmail = findViewById<View>(R.id.input_email) as TextInputEditText
        textInputEditTextPassword = findViewById<View>(R.id.input_password) as TextInputEditText

        appCompatButtonLogin = findViewById<View>(R.id.btn_login) as AppCompatButton

        textViewLinkRegister = findViewById<View>(R.id.link_signup) as AppCompatTextView

    }

    private fun initListeners() {

        appCompatButtonLogin.setOnClickListener(this)
        textViewLinkRegister.setOnClickListener(this)
    }
    private fun initObjects() {

        databaseHelper = DatabaseHelper(activity)
        inputValidation = InputValidation(activity)

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_login -> verifyFromSQLite()
            R.id.link_signup -> {
                val intentRegister = Intent(applicationContext, RegistrationActivity::class.java)
                startActivity(intentRegister)
            }
        }
    }
    private fun verifyFromSQLite() {

        if (!inputValidation.isInputEditTextFilled(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_message_email))) {
            return
        }
        if (!inputValidation.isInputEditTextEmail(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_message_email))) {
            return
        }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextPassword, textInputLayoutPassword, getString(R.string.error_message_email))) {
            return
        }

        if (databaseHelper.checkUser(textInputEditTextEmail.text.toString().trim { it <= ' ' }, textInputEditTextPassword.text.toString().trim { it <= ' ' })) {


            val accountsIntent = Intent(activity, MainActivity::class.java)
            accountsIntent.putExtra("EMAIL", textInputEditTextEmail!!.text.toString().trim { it <= ' ' })
            emptyInputEditText()
            startActivity(accountsIntent)


        } else {

            // Snack Bar to show success message that record is wrong
            Snackbar.make(nestedScrollView!!, getString(R.string.error_valid_email_password), Snackbar.LENGTH_LONG).show()
        }
    }

    private fun emptyInputEditText() {
        textInputEditTextEmail!!.text = null
        textInputEditTextPassword!!.text = null
    }

    companion object {
        private val TAG = "LoginActivity"
        private val REQUEST_SIGNUP = 0
    }
}