package com.example.aleksandr.myapplication.ui.login

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.aleksandr.myapplication.BaseActivity
import com.example.aleksandr.myapplication.R
import com.example.aleksandr.myapplication.ui.main.MainActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.view_login.*

class LoginActivity : AppCompatActivity() {
    private lateinit var presenter: LoginPresenter
    private var auth: FirebaseAuth? = null

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_login)
        presenter = LoginPresenter(this, application)
        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance()

        if (auth?.currentUser != null) {
            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
            finish()
        }
        link_signup.setOnClickListener {

            val intent = Intent(applicationContext, SignupActivity::class.java)
            startActivityForResult(intent, REQUEST_SIGNUP)
            finish()
        }

        btn_login.setOnClickListener(View.OnClickListener {
            val email = editText_email.text.toString()
            val password = input_password.text.toString()

            if (TextUtils.isEmpty(email)) {
                Toast.makeText(applicationContext, "Enter email address!", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }

            if (TextUtils.isEmpty(password)) {
                Toast.makeText(applicationContext, "Enter password!", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }

            btn_login.isEnabled = false

            val progressDialog = ProgressDialog(this@LoginActivity) // TODO: I can add style
            progressDialog.isIndeterminate = true
            progressDialog.setMessage("Login...")
            progressDialog.show()

            //authenticate user
            auth?.signInWithEmailAndPassword(email, password)
                    ?.addOnCompleteListener(this@LoginActivity) { task ->
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        progressDialog.dismiss()
                        if (!task.isSuccessful) {
                            // there was an error
                            if (password.length < 6) {
                                input_password.error = getString(R.string.minimum_password)
                            } else {
                                Toast.makeText(this@LoginActivity, getString(R.string.auth_failed), Toast.LENGTH_LONG).show()
                            }
                        } else {
                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    }
        })


    }

//    fun login() {
//        Log.d(TAG, "Login")
//
//        if (!validate()) {
//            onLoginFailed()
//            return
//        }
//
//        btn_login.isEnabled = false
//
//        val progressDialog = ProgressDialog(this@LoginActivity) // TODO: I can add style
//        progressDialog.isIndeterminate = true
//        progressDialog.setMessage("Login...")
//        progressDialog.show()
//
////        val email = input_email.text.toString()
//        val password = input_password.text.toString()
//
//        // TODO: Implement your own authentication logic here.
//
//        android.os.Handler().postDelayed(
//                {
//                    // On complete call either onLoginSuccess or onLoginFailed
//                    onLoginSuccess()
//                    // onLoginFailed();
//                    progressDialog.dismiss()
//                }, 3000)
//    }
//


//    override fun onBackPressed() {
//        // Disable going back to the MainActivity
////        moveTaskToBack(true)
//    }

    private fun onLoginSuccess() {
        btn_login.isEnabled = true
//        finish()
        startActivity(Intent(this, BaseActivity::class.java))
        finish()
    }

    private fun onLoginFailed() {
        Toast.makeText(baseContext, "Login failed", Toast.LENGTH_LONG).show()
        btn_login.isEnabled = true
    }

    private fun validate(): Boolean {
        var valid = true

//        val email = input_email.text.toString()
        val password = input_password.text.toString()

//        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
//            input_email.error = "enter a valid email address"
//            valid = false
//        } else input_email.error = null

        // TODO validate password
//        if (password.isEmpty() || password.length < 4 || password.length > 10) {
//            input_password.error = "between 4 and 10 alphanumeric characters"
//            valid = false
//        } else input_password.error = null

        return valid
    }

    companion object {
        private val TAG = "LoginActivity"
        private val REQUEST_SIGNUP = 0
    }
}