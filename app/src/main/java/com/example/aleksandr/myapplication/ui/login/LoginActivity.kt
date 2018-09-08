package com.example.aleksandr.myapplication.ui.login

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import com.example.aleksandr.myapplication.R
import com.example.aleksandr.myapplication.setSimpleTextWatcher
import com.example.aleksandr.myapplication.ui.login.presenter.LoginPresenter
import com.example.aleksandr.myapplication.ui.login.sql.DatabaseHelper
import com.example.aleksandr.myapplication.ui.main.MainActivity
import kotlinx.android.synthetic.main.view_login.*

class LoginActivity : AppCompatActivity(), ILoginActivity {

    private val activity = this@LoginActivity

    private lateinit var presenter: LoginPresenter

    private lateinit var databaseHelper: DatabaseHelper


    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_login)
        presenter = LoginPresenter(this, application)
        //        if (auth?.currentUser != null) {
//            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
//            finish()
//        }

        databaseHelper = DatabaseHelper(activity)

        input_email.setSimpleTextWatcher {
            presenter.onResetError()
            presenter.onUpdateOwnerEmail(it)
        }

        input_password.setSimpleTextWatcher {
            presenter.onResetError()
            presenter.onUpdateOwnerPassword(it)
        }

        btn_login.setOnClickListener { presenter.onValidateAndLogin() }

        link_signup.setOnClickListener {

            val intentRegister = Intent(applicationContext, RegistrationActivity::class.java)
            startActivity(intentRegister)
            finish()
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

    override fun onResetError() {
        text_InputLayoutEmail.error = ""
        text_InputLayoutPassword.error = ""
    }

    override fun setButtonLoginEnabled(isEnable: Boolean) {
        btn_login.isEnabled = isEnable
    }

    override fun showInvalidValue(errorField: ILoginActivity.InvalidValue) {
        when (errorField) {
            ILoginActivity.InvalidValue.NO_EMAIL -> text_InputLayoutEmail.error = resources.getString(R.string.no_email)
            ILoginActivity.InvalidValue.INVALID_EMAIL -> text_InputLayoutEmail.error = resources.getString(R.string.email_incorrect)
            ILoginActivity.InvalidValue.NO_PASSWORD -> text_InputLayoutPassword.error = resources.getString(R.string.no_password)
            ILoginActivity.InvalidValue.PASSWORD_TOO_SHORT -> text_InputLayoutPassword.error = resources.getString(R.string.password_too_short)
        }
    }

    override fun onLoginSuccess() {
        val progressDialog = ProgressDialog(this@LoginActivity)
        progressDialog.isIndeterminate = true
        progressDialog.setMessage("Login...")
        progressDialog.show()
        verifyFromSQLite()
    }

    override fun verifyFromSQLite() {
        val progressDialog = ProgressDialog(this@LoginActivity)
        progressDialog.isIndeterminate = true
        progressDialog.setMessage("Login...")
        progressDialog.show()

        if (databaseHelper.checkUser(input_email.text.toString().trim { it <= ' ' }, input_password.text.toString().trim { it <= ' ' })) {

            val accountsIntent = Intent(activity, MainActivity::class.java)
            accountsIntent.putExtra("EMAIL", input_email.text.toString().trim { it <= ' ' })
            progressDialog.dismiss()
            startActivity(accountsIntent)


        } else {
            progressDialog.dismiss()

            Snackbar.make(nestedScrollView!!, getString(R.string.error_valid_email_password), Snackbar.LENGTH_LONG).show()
        }
    }

    companion object {
        private val TAG = "LoginActivity"
        private val REQUEST_SIGNUP = 0
    }
 }