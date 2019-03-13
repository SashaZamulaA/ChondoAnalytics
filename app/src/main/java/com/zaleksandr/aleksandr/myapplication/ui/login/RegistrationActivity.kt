package com.zaleksandr.aleksandr.myapplication.ui.login

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import com.zaleksandr.aleksandr.myapplication.R
import com.zaleksandr.aleksandr.myapplication.ui.settings.model.User
import com.zaleksandr.aleksandr.myapplication.service.MyFirebaseInstanceIDService
import com.zaleksandr.aleksandr.myapplication.setSimpleTextWatcher
import com.zaleksandr.aleksandr.myapplication.ui.login.presenter.RegistrationPresenter
import com.zaleksandr.aleksandr.myapplication.MainActivity
import com.zaleksandr.aleksandr.myapplication.util.FirestoreUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.iid.FirebaseInstanceId
import com.zaleksandr.aleksandr.myapplication.MainActivity.Companion.AUTHOR_KEY
import com.zaleksandr.aleksandr.myapplication.MainActivity.Companion.PASSWORDACCESS
import com.zaleksandr.aleksandr.myapplication.MainActivity.Companion.QUOTE_KEY
import kotlinx.android.synthetic.main.view_registration.*


class RegistrationActivity : AppCompatActivity(), IRegistrationActivity {

    lateinit var presenter: RegistrationPresenter
    private var auth: FirebaseAuth? = null
    var databaseReference: DatabaseReference? = null
    private val chatChannelsCollectionRef = FirestoreUtil.firestoreInstance.collection("users")
    private var result: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        super.setContentView(R.layout.view_registration)
        presenter = RegistrationPresenter(this, application)
        databaseReference = FirebaseDatabase.getInstance().getReference("Word")
        auth = FirebaseAuth.getInstance()

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
//        checkbox_GDPR.setOnCheckedChangeListener { _, isChecked ->
//            presenter.onResetError()
//            presenter.onUpdateCheckboxGdpr(isChecked)
//        }
        btn_signup.setOnClickListener {


            val b = AlertDialog.Builder(this@RegistrationActivity)
            b.setTitle("Please enter a password")
            val input = EditText(this@RegistrationActivity)
            b.setView(input)
            b.setPositiveButton("OK") { _, _ ->
                result = input.text.toString()
                if (result == PASSWORDACCESS) {
                    presenter.onValidateAndSave()
                } else {
                    val intentRegister = Intent(applicationContext, RegistrationActivity::class.java)
                    startActivity(intentRegister)
                    finish()
                }
            }
            b.setNegativeButton("CANCEL") { _, _ ->
                val intentRegister = Intent(applicationContext, RegistrationActivity::class.java)
                startActivity(intentRegister)
                finish()
            }
            b.show()


        }

                button_back_registration.setOnClickListener {
                    startActivity(Intent(applicationContext, LoginActivity::class.java))
                    finish()
                    overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out)
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
//            IRegistrationActivity.InvalidValue.CHECKBOX_DISABLE -> checkbox_gdpr_error.text = resources.getString(R.string.checkbox_gdpr)
            IRegistrationActivity.InvalidValue.NO_INTERNET -> resources.getString(R.string.no_internet)
        }
    }

    override fun onResetError() {
        text_InputLayoutName.error = ""
        text_InputLayoutEmail.error = ""
        text_InputLayoutPassword.error = ""
        textInputLayoutConfirmPassword.error = ""
//        checkbox_GDPR.text = ""
    }

    override fun onSignUpSuccess() {
        val progressDialog = ProgressDialog(this@RegistrationActivity)
        progressDialog.isIndeterminate = true
        progressDialog.setMessage(resources.getString(R.string.signup_successful))
        progressDialog.show()
        val email = input_email.text.toString().trim()
        val password = input_password.text.toString().trim()
        val name = input_name.text.toString().trim()
        val regId = mutableListOf<String>()
        auth?.createUserWithEmailAndPassword(email, password)
                ?.addOnCompleteListener(this@RegistrationActivity) { task ->
                    progressDialog.dismiss()
                    if (task.isSuccessful) {

                        FirestoreUtil.initCurrentUserIfFirstTime {
                            startActivity(Intent(this@RegistrationActivity, MainActivity::class.java))
                            finish()
                            val registrationToken = FirebaseInstanceId.getInstance().token
                            MyFirebaseInstanceIDService.addTokenToFirestore(registrationToken)
                        }
                        saveQuote()
                    }
                }
    }

    private fun saveQuote() {
        val authorText = input_name.text.toString()
        val quoteText = input_email.text.toString()

        if (quoteText.isEmpty() || authorText.isEmpty()) {
            return
        }
        val dataToSave = HashMap<String, Any>()
        dataToSave[AUTHOR_KEY] = authorText
        dataToSave[QUOTE_KEY] = quoteText

            FirestoreUtil.currentUserDocRef.set(dataToSave)
    }


    fun sendMessage(message: User, channelId: String) {
        chatChannelsCollectionRef.document(channelId)
                .collection("messages")
                .add(message)
    }
    private fun verifyEmail() {
        val mUser = auth!!.currentUser
        mUser!!.sendEmailVerification()
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this@RegistrationActivity,
                                "Verification email sent to " + mUser.email,
                                Toast.LENGTH_SHORT).show()
                    } else {
//                            Log.e(TAG, "sendEmailVerification", task.exception)
                        Toast.makeText(this@RegistrationActivity,
                                "Failed to send verification email.",
                                Toast.LENGTH_SHORT).show()
                    }
                }
    }

    override fun setButtonCreateEnabled(isEnabled: Boolean) {
        btn_signup.isEnabled = isEnabled
    }
}
