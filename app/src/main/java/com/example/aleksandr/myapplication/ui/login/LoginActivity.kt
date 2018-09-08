package com.example.aleksandr.myapplication.ui.login

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.example.aleksandr.myapplication.R
import com.example.aleksandr.myapplication.setSimpleTextWatcher
import com.example.aleksandr.myapplication.ui.login.presenter.LoginPresenter
import com.example.aleksandr.myapplication.ui.main.MainActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.view_login.*
import kotlinx.android.synthetic.main.view_signup.*

class LoginActivity : AppCompatActivity(), ILoginActivity {

    private val activity = this@LoginActivity
    private var auth: FirebaseAuth? = null

    private lateinit var presenter: LoginPresenter

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_login)

        presenter = LoginPresenter(this, application)
        auth = FirebaseAuth.getInstance()

//        if (auth?.currentUser != null) {
//            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
//            finish()
//        }
        input_email_login.setSimpleTextWatcher {
            presenter.onResetError()
            presenter.onUpdateOwnerEmail(it)
        }

        input_password_login.setSimpleTextWatcher {
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
        text_InputLayoutEmail_login.error = ""
        text_InputLayoutPassword_login.error = ""
    }

    override fun setButtonLoginEnabled(isEnable: Boolean) {
        btn_login.isEnabled = isEnable
    }

    override fun showInvalidValue(errorField: ILoginActivity.InvalidValue) {
        when (errorField) {
            ILoginActivity.InvalidValue.NO_EMAIL -> text_InputLayoutEmail_login.error = resources.getString(R.string.no_email)
            ILoginActivity.InvalidValue.INVALID_EMAIL -> text_InputLayoutEmail_login.error = resources.getString(R.string.email_incorrect)
            ILoginActivity.InvalidValue.NO_PASSWORD -> text_InputLayoutPassword_login.error = resources.getString(R.string.no_password)
            ILoginActivity.InvalidValue.PASSWORD_TOO_SHORT -> text_InputLayoutPassword_login.error = resources.getString(R.string.password_too_short)
        }
    }

    override fun onLoginSuccess() {
        val progressDialog = ProgressDialog(this@LoginActivity)
        progressDialog.isIndeterminate = true
        progressDialog.setMessage("Login...")
        progressDialog.show()
        val email = input_email_login.text.toString()
        val password = input_password_login.text.toString()

        auth?.signInWithEmailAndPassword(email, password)
                ?.addOnCompleteListener(this@LoginActivity) { task ->
                    // If sign in fails, display a message to the user. If sign in succeeds
                    // the auth state listener will be notified and logic to handle the
                    // signed in user can be handled in the listener.
                    progressDialog.dismiss()
                    if (!task.isSuccessful) {
                        Toast.makeText(this@LoginActivity, "Authentication failed." + task.exception!!,
                                Toast.LENGTH_SHORT).show()

                    } else {
                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
    }

}
