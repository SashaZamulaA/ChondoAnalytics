package com.example.aleksandr.myapplication.ui.settings

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.PorterDuff
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore

import com.example.aleksandr.myapplication.BaseActivity
import com.example.aleksandr.myapplication.R

import com.example.aleksandr.myapplication.model.User
import com.example.aleksandr.myapplication.util.FirestoreUtil
import com.example.aleksandr.myapplication.util.FirestoreUtil.currentUserDocRef
import com.example.aleksandr.myapplication.util.StorageUtil
import com.example.aleksandr.tmbook.glade.GlideApp
import com.vadym.adv.myhomepet.ui.settings.ISettingsView
import com.vadym.adv.myhomepet.ui.settings.SettingsPresenter
import kotlinx.android.synthetic.main.view_settings.*
import org.jetbrains.anko.toast
import java.io.ByteArrayOutputStream

class SettingsView : BaseActivity(), ISettingsView {

    private val RC_SELECT_IMAGE = 2
    private lateinit var selectImageBytes: ByteArray
    private var pictureJustChange = false

    companion object {
        val TAG = "Setting"
        val AUTHOR_KEY = "phone"
        val QUOTE_KEY = "name"
    }

    private lateinit var presenter: SettingsPresenter
    var selectedPhotoUri: Uri? = null
    override fun init(savedInstanceState: Bundle?) {
        super.setContentView(R.layout.view_settings)
        presenter = SettingsPresenter(this, application)
        settings_owner_name.background.mutate().setColorFilter(resources.getColor(R.color.white), PorterDuff.Mode.SRC_ATOP)
//        mMessageReference = FirebaseFirestore.getInstance().document("message/info")
        button_save_settings.setOnClickListener {
            if (::selectImageBytes.isInitialized)
                StorageUtil.uploadProfilePhoto(selectImageBytes) { imagePath ->
                    FirestoreUtil.updateCurrentUser(settings_owner_name.text.toString(),
                            settings_owner_phone.text.toString(), imagePath)

                    toast("Saving")
//                    saveQuote()
//                    fetchQuote()
                }
            else
                FirestoreUtil.updateCurrentUser(settings_owner_name.text.toString(),
                        settings_owner_phone.text.toString(), null)
        }
        imageView_profile_picture.setOnClickListener {
            val intent = Intent().apply {
                type = "image/*"
                action = Intent.ACTION_GET_CONTENT
                putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("image/jpeg", "image/png", "image/jpg"))
            }
            startActivityForResult(Intent.createChooser(intent, " Select Image"), RC_SELECT_IMAGE)
        }
    }

    override fun onStart() {
        super.onStart()
        FirestoreUtil.currentUserDocRef.addSnapshotListener { documentSnapshot, firebaseFirestoreException ->
            FirestoreUtil.getCurrentUser { user ->
            if (documentSnapshot?.exists()!!) {
                val myQuote = documentSnapshot.toObject(User::class.java)

                val quoteText = documentSnapshot.getString(QUOTE_KEY)
                val authorText = documentSnapshot.getString(AUTHOR_KEY)
                settings_owner_name.setText(quoteText)
                settings_owner_phone.setText(authorText)

                    if (!pictureJustChange && user.profilePicturePath != null)
                        GlideApp.with(this)
                                .load(StorageUtil.pathToReference(user.profilePicturePath))
                                .placeholder(R.drawable.ic_account_circle_black_24dp)
                                .circleCrop()
                                .into(imageView_profile_picture)
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

//    private fun fetchQuote() {
//        currentUserDocRef.get().addOnSuccessListener { documentSnapshot ->
//            if (!documentSnapshot.exists()) {
////                val myQuote = documentSnapshot.toObject(Settings::class.java)
//
//                val quoteText = documentSnapshot.getString(QUOTE_KEY)
//                val authorText = documentSnapshot.getString(AUTHOR_KEY)
//                settings_owner_name.setText(quoteText)
//                settings_owner_phone.setText(authorText)
//
//            }
//        }
//    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == RC_SELECT_IMAGE && resultCode == Activity.RESULT_OK &&
                data != null && data.data != null) {
            val selectedImagePath = data.data
            val selectedImageBmp = MediaStore.Images.Media
                    .getBitmap(contentResolver, selectedImagePath)

            val outputStream = ByteArrayOutputStream()
            selectedImageBmp.compress(Bitmap.CompressFormat.JPEG, 90, outputStream)
            selectImageBytes = outputStream.toByteArray()

            GlideApp.with(this)
                    .load(selectImageBytes)
                    .circleCrop()
                    .into(imageView_profile_picture)

            pictureJustChange = true
        }
    }

}

