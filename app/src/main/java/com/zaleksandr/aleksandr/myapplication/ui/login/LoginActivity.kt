package com.zaleksandr.aleksandr.myapplication.ui.login

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.zaleksandr.aleksandr.myapplication.MainActivity
import com.zaleksandr.aleksandr.myapplication.R
import com.zaleksandr.aleksandr.myapplication.setSimpleTextWatcher
import com.zaleksandr.aleksandr.myapplication.ui.login.presenter.LoginPresenter
import com.zaleksandr.aleksandr.myapplication.util.FirestoreUtil
import kotlinx.android.synthetic.main.view_login.*

class LoginActivity : AppCompatActivity(), ILoginActivity {

    private var auth: FirebaseAuth? = null
    private lateinit var presenter: LoginPresenter
    private var access = false
    private val chatChannelsCollectionRef = FirestoreUtil.firestoreInstance.collection("users")
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_login)

        presenter = LoginPresenter(this, application)
        auth = FirebaseAuth.getInstance()

        if (auth?.currentUser != null) {

            FirestoreUtil.currentUserDocRef.addSnapshotListener { documentSnapshot, _ ->
                if (documentSnapshot!!.exists()) {
                    FirestoreUtil.getCurrentUser { user ->
                        if (documentSnapshot.get("access") != null) {
                            this.access = documentSnapshot["access"] as Boolean
                            if (documentSnapshot.exists() && access) {

                                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                                startActivity(intent)
                                finish()
                            }
                        }
                    }
                }
            }
        }

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


    override fun onLoginSuccess(onOk: () -> Unit) {
        val progressDialog = ProgressDialog(this@LoginActivity)
        progressDialog.isIndeterminate = true
        progressDialog.setMessage("Login...")
        progressDialog.show()
        val email = input_email_login.text.toString()
        val password = input_password_login.text.toString()
        auth?.signInWithEmailAndPassword(email, password)
                ?.addOnCompleteListener(this@LoginActivity) { task ->
                    progressDialog.dismiss()
                    if (!task.isSuccessful) {
                        progressDialog.dismiss()
                        Toast.makeText(applicationContext, "Your password or email is not correct", Toast.LENGTH_LONG).show()

//                        progressDialog.isIndeterminate = true
//                        progressDialog.setMessage("Diney...")
//                        progressDialog.show()
                    } else {
                        FirestoreUtil.currentUserDocRef.addSnapshotListener { documentSnapshot, _ ->
                            if (documentSnapshot!!.exists()) {
                                FirestoreUtil.getCurrentUser { user ->

                                    if (documentSnapshot["access"] != null) {
                                        this.access = documentSnapshot["access"] as Boolean
                                        if (documentSnapshot.exists() && access) {

                                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                                            startActivity(intent)
                                            finish()
                                        }
                                    } else {
                                        Toast.makeText(applicationContext, "Please wait for confirmation of your account.", Toast.LENGTH_LONG).show()
                                    }
                                }
                            }
                        }
                    }
                }
    }
}
