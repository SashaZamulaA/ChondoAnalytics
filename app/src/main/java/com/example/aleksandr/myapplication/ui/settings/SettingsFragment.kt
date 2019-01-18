package com.example.aleksandr.myapplication.ui.settings

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.PorterDuff
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.aleksandr.myapplication.MainActivity
import com.example.aleksandr.myapplication.R
import com.example.aleksandr.myapplication.ui.settings.model.User
import com.example.aleksandr.myapplication.ui.settings.SettingsView.Companion.AUTHOR_KEY
import com.example.aleksandr.myapplication.ui.settings.SettingsView.Companion.QUOTE_KEY
import com.example.aleksandr.myapplication.util.FirestoreUtil
import com.example.aleksandr.myapplication.util.StorageUtil
import com.example.aleksandr.tmbook.glade.GlideApp
import kotlinx.android.synthetic.main.fragment_settings.*
import kotlinx.android.synthetic.main.fragment_settings.view.*
import java.io.ByteArrayOutputStream

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class SettingsFragment : Fragment() {

    private val RC_SELECT_IMAGE = 2
    private lateinit var selectImageBytes: ByteArray
    private var pictureJustChange = false

    override fun onAttach(context: Context?) {
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(com.example.aleksandr.myapplication.R.layout.fragment_settings, container, false)

        rootView.settings_owner_name.background.mutate().setColorFilter(resources.getColor(R.color.white), PorterDuff.Mode.SRC_ATOP)
        rootView.fab_add.setOnClickListener {
            if (::selectImageBytes.isInitialized)
                StorageUtil.uploadProfilePhoto(selectImageBytes) { imagePath ->
                    FirestoreUtil.updateCurrentUser(settings_owner_name.text.toString(),
                            rootView.setting_e_mail.text.toString(), imagePath)
                    startActivity(Intent(context, MainActivity::class.java))
                }
            else
                FirestoreUtil.updateCurrentUser(settings_owner_name.text.toString(),
                        rootView.setting_e_mail.text.toString(), null)
            startActivity(Intent(context, MainActivity::class.java))
        }



        rootView.imageView_profile_picture.setOnClickListener {
            val intent = Intent().apply {
                type = "image/*"
                action = Intent.ACTION_GET_CONTENT
                putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("image/jpeg", "image/png", "image/jpg"))
            }
            startActivityForResult(Intent.createChooser(intent, " Select Image"), RC_SELECT_IMAGE)
        }

        return rootView
    }

    override fun onStart() {
        super.onStart()
        FirestoreUtil.currentUserDocRef.addSnapshotListener { documentSnapshot, _ ->
            FirestoreUtil.getCurrentUser { user ->
                if (documentSnapshot?.exists()!!) {
                    val myQuote = documentSnapshot.toObject(User::class.java)

                    val authorText = documentSnapshot.getString(AUTHOR_KEY)
                    val quoteText = documentSnapshot.getString(QUOTE_KEY)

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
                    .getBitmap(context?.contentResolver, selectedImagePath)

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

