package com.example.aleksandr.myapplication.ui.settings

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.PorterDuff
import android.os.Bundle
import android.provider.MediaStore
import com.example.aleksandr.myapplication.BaseActivity
import com.example.aleksandr.myapplication.R
import com.example.aleksandr.myapplication.model.User
import com.example.aleksandr.myapplication.util.FirestoreUtil
import com.example.aleksandr.myapplication.util.StorageUtil
import com.example.aleksandr.tmbook.glade.GlideApp
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.QuerySnapshot
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
        val AUTHOR_KEY = "name"
        val QUOTE_KEY = "e_mail"
        val SPINNER = "spinner"
    }

    private lateinit var presenter: SettingsPresenter

    override fun init(savedInstanceState: Bundle?) {
        super.setContentView(R.layout.view_settings)
        presenter = SettingsPresenter(this, application)
        settings_owner_name.background.mutate().setColorFilter(resources.getColor(R.color.white), PorterDuff.Mode.SRC_ATOP)
        button_save_settings.setOnClickListener {
            if (::selectImageBytes.isInitialized)
                StorageUtil.uploadProfilePhoto(selectImageBytes) { imagePath ->
                    FirestoreUtil.updateCurrentUser(settings_owner_name.text.toString(),
                            setting_e_mail.text.toString(), imagePath)

                    toast("Saving")
                }
            else
                FirestoreUtil.updateCurrentUser(settings_owner_name.text.toString(),
                        setting_e_mail.text.toString(), null)
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
        FirestoreUtil.currentUserDocRef.addSnapshotListener { documentSnapshot, _ ->
            FirestoreUtil.getCurrentUser { user ->
                if (documentSnapshot?.exists()!!) {
                    val myQuote = documentSnapshot.toObject(User::class.java)

                    val quoteText = documentSnapshot.getString(QUOTE_KEY)
                    val authorText = documentSnapshot.getString(AUTHOR_KEY)
                    settings_owner_name.setText(quoteText)
                    setting_e_mail.text = authorText

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

