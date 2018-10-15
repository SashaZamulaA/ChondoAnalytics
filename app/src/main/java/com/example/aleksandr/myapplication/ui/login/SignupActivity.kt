package com.example.aleksandr.myapplication.ui.login

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.aleksandr.myapplication.R
import com.example.aleksandr.myapplication.R.string.email
import com.example.aleksandr.myapplication.ui.main.LoginActivity
import com.example.aleksandr.myapplication.ui.main.MainActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.view_signup.*


class SignupActivity : AppCompatActivity() {

    private var auth: FirebaseAuth? = null

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_signup)
        auth = FirebaseAuth.getInstance()
//        btn_signup.setOnClickListener { signup() }

        link_login.setOnClickListener {
            val intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)
            finish()
            overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out)
        }

        btn_signup.setOnClickListener(View.OnClickListener {
            val name = input_name.text.toString().trim()
            val address = input_address.text.toString().trim()
            val email = editText_email.text.toString().trim()
            val password = input_password.text.toString().trim()
            val reEnterPassword = input_reEnterPassword.text.toString().trim()

            if (TextUtils.isEmpty(email)) {
                Toast.makeText(applicationContext, "Enter email address!", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }

            if (TextUtils.isEmpty(password)) {
                Toast.makeText(applicationContext, "Enter password!", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }

            if (password.length < 6) {
                Toast.makeText(applicationContext, "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }

            val progressDialog = ProgressDialog(this@SignupActivity)
            progressDialog.isIndeterminate = true
            progressDialog.setMessage("Creating Account...")
            progressDialog.show()
            //create user
            auth?.createUserWithEmailAndPassword(email, password)
                    ?.addOnCompleteListener(this@SignupActivity) { task ->
                        Toast.makeText(this@SignupActivity, "createUserWithEmail:onComplete:" + task.isSuccessful, Toast.LENGTH_SHORT).show()
                        progressDialog.dismiss()
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful) {
                            Toast.makeText(this@SignupActivity, "Authentication failed." + task.exception!!,
                                    Toast.LENGTH_SHORT).show()
                        } else {
                            startActivity(Intent(this@SignupActivity, MainActivity::class.java))
                            finish()
                        }
                    }
        })
    }

    fun signup() {
        Log.d(TAG, "Signup")

        if (!validate()) {
            onSignupFailed()
            return
        }

        btn_signup.isEnabled = false

        val progressDialog = ProgressDialog(this@SignupActivity) // TODO: , com.vadym.adv.myhomepet.R.style.AppTheme_Dark_Dialog
        progressDialog.isIndeterminate = true
        progressDialog.setMessage("Creating Account...")
        progressDialog.show()

        val name = input_name.text.toString()
        val address = input_address.text.toString()
//        val email = input_email.text.toString()
        val mobile = editText_email.text.toString()
        val password = input_password.text.toString()
        val reEnterPassword = input_reEnterPassword.text.toString()

        // TODO: Implement your own signup logic here.

        android.os.Handler().postDelayed(
                {
                    // On complete call either onSignupSuccess or onSignupFailed
                    // depending on success
                    onSignupSuccess()
                    // onSignupFailed();
                    progressDialog.dismiss()
                }, 3000)
    }


    private fun onSignupSuccess() {
        btn_signup.isEnabled = true
//        setResult(Activity.RESULT_OK, null)
//        finish()
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun onSignupFailed() {
        Toast.makeText(baseContext, "Login failed", Toast.LENGTH_LONG).show()

        btn_signup.isEnabled = true
    }

    private fun validate(): Boolean {
        var valid = true

        val name = input_name.text.toString()
        val address = input_address.text.toString()
//        val email = input_email.text.toString()
        val mobile = editText_email.text.toString()
        val password = input_password.text.toString()
        val reEnterPassword = input_reEnterPassword.text.toString()

        if (name.isEmpty() || name.length < 3) {
            input_name.error = "at least 3 characters"
            valid = false
        } else {
            input_name.error = null
        }

        if (address.isEmpty()) {
            input_address.error = "Enter Valid Address"
            valid = false
        } else {
            input_address.error = null
        }


//        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
//            input_email.error = "enter a valid email address"
//            valid = false
//        } else {
//            input_email.error = null
//        }

        if (mobile.isEmpty() || mobile.length != 10) {
            editText_email.error = "Enter Valid Mobile Number"
            valid = false
        } else {
            editText_email.error = null
        }

        if (password.isEmpty() || password.length < 4 || password.length > 10) {
            input_password.error = "between 4 and 10 alphanumeric characters"
            valid = false
        } else {
            input_password.error = null
        }

        if (reEnterPassword.isEmpty() || reEnterPassword.length < 4 || reEnterPassword.length > 10 || reEnterPassword != password) {
            input_reEnterPassword.error = "Password Do not match"
            valid = false
        } else {
            input_reEnterPassword.error = null
        }

        return valid
    }

    override fun onResume() {
        super.onResume()
    }

    companion object {
        private val TAG = "SignupActivity"
    }
}