package com.example.aleksandr.myapplication.ui.settings

import android.graphics.PorterDuff
import android.net.Uri
import android.os.Bundle
import com.example.aleksandr.myapplication.BaseActivity
import com.example.aleksandr.myapplication.R
import com.example.aleksandr.myapplication.R.id.settings_owner_name
import com.example.aleksandr.myapplication.R.id.settings_owner_phone
import com.example.aleksandr.myapplication.model.User
import com.example.aleksandr.myapplication.ui.settings.SettingsView.Companion.AUTHOR_KEY
import com.example.aleksandr.myapplication.ui.settings.SettingsView.Companion.QUOTE_KEY
import com.example.aleksandr.myapplication.util.FirestoreUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.vadym.adv.myhomepet.ui.settings.ISettingsView
import com.vadym.adv.myhomepet.ui.settings.SettingsPresenter
import kotlinx.android.synthetic.main.view_settings.*
import org.jetbrains.anko.toast

class SettingsView : BaseActivity(), ISettingsView {

    private val firestoreInstance: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }
    private var mDatabase: DatabaseReference? = null
    private val currentUserDocRef: DocumentReference
        get() = firestoreInstance.document("users/${FirebaseAuth.getInstance().uid
                ?: throw NullPointerException("UID is null.")}")
    private lateinit var selectImageBytes: ByteArray

    companion object {
        val TAG = "Setting"
        val AUTHOR_KEY = "author"
        val QUOTE_KEY = "quote"
    }

    private lateinit var presenter: SettingsPresenter
    var selectedPhotoUri: Uri? = null
    override fun init(savedInstanceState: Bundle?) {
        super.setContentView(R.layout.view_settings)
        presenter = SettingsPresenter(this, application)
        settings_owner_name.background.mutate().setColorFilter(resources.getColor(R.color.white), PorterDuff.Mode.SRC_ATOP)
//        mMessageReference = FirebaseFirestore.getInstance().document("message/info")
        button_save_settings.setOnClickListener {

                       FirestoreUtil.updateCurrentUser(settings_owner_name.text.toString(),
                       settings_owner_phone.text.toString())
                       toast("Saving")
            saveQuote()
            fetchQuote()
        }

    }

    override fun onStart() {
        super.onStart()
        currentUserDocRef.addSnapshotListener { documentSnapshot, firebaseFirestoreException ->

            FirestoreUtil.getCurrentUser { user ->
                if (documentSnapshot?.exists()!!) {
                    val myQuote = documentSnapshot.toObject(User::class.java)

                    val quoteText = documentSnapshot.getString(QUOTE_KEY)
                    val authorText = documentSnapshot.getString(AUTHOR_KEY)
                    settings_owner_name.setText(quoteText)
                    settings_owner_phone.setText(authorText)
//                } else if (firebaseFirestoreException != null) {
//                }
                }
            }
        }
    }
    private fun saveQuote() {
        val quoteText = settings_owner_name.text.toString()
        val authorText = settings_owner_phone.text.toString()

        if (quoteText.isEmpty() || authorText.isEmpty()) {
            return
        }
        val dataToSave = HashMap<String, Any>()
        dataToSave[QUOTE_KEY] = quoteText
        dataToSave[AUTHOR_KEY] = authorText
        currentUserDocRef.set(dataToSave).addOnSuccessListener {
        }
    }

    private fun fetchQuote() {
        currentUserDocRef.get().addOnSuccessListener { documentSnapshot ->
            if (!documentSnapshot.exists()) {
//                val myQuote = documentSnapshot.toObject(Settings::class.java)

                val quoteText = documentSnapshot.getString(QUOTE_KEY)
                val authorText = documentSnapshot.getString(AUTHOR_KEY)
                settings_owner_name.setText(quoteText)
                settings_owner_phone.setText(authorText)

            }
        }
    }
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null) {
//            // proceed and check what the selected image was....
//            Log.d(TAG, "Photo was selected")
//            selectedPhotoUri = data.data
//
//            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedPhotoUri)
//
//            selectphoto_imageview_register.setImageBitmap(bitmap)
//
//            selectphoto_button_register.alpha = 0f
//
////      val bitmapDrawable = BitmapDrawable(bitmap)
////      selectphoto_button_register.setBackgroundDrawable(bitmapDrawable)
//        }
//    }
}

