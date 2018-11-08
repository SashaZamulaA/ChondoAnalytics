package com.example.aleksandr.myapplication.ui.settings

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.example.aleksandr.myapplication.BaseActivity
import com.example.aleksandr.myapplication.R
import com.example.aleksandr.myapplication.ui.login.model.Settings
import com.example.aleksandr.myapplication.ui.login.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.vadym.adv.myhomepet.ui.settings.ISettingsView
import com.vadym.adv.myhomepet.ui.settings.SettingsPresenter
import kotlinx.android.synthetic.main.activity_message.*
import kotlinx.android.synthetic.main.view_settings.*
import java.text.SimpleDateFormat
import java.util.*

class SettingsView : BaseActivity(), ISettingsView {
    var chatPartnerUser: User? = null

    private var mDatabase: DatabaseReference? = null
    private var mMessageReference: DatabaseReference? = null
    private var mMessageListener: ValueEventListener? = null
    private var user: FirebaseUser? = null

    companion object {
        val TAG = "Setting"
        val REQUIRED = "Required"
    }

    private lateinit var presenter: SettingsPresenter
    var selectedPhotoUri: Uri? = null
    override fun init(savedInstanceState: Bundle?) {
        super.setContentView(R.layout.view_settings)
        presenter = SettingsPresenter(this, application)

        mDatabase = FirebaseDatabase.getInstance().reference
        mMessageReference = FirebaseDatabase.getInstance().getReference("message")
        user = FirebaseAuth.getInstance().currentUser


        selectphoto_button_register.setOnClickListener {
            Log.d(TAG, "Try to show photo selector")

            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 0)
        }
        submitMessage()
        button_save_settings.setOnClickListener {
            submitMessage()
        }
    }

//    override fun onStart() {
//        super.onStart()
//        val messageListener = object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                if (dataSnapshot.exists()) {
//                    val message = dataSnapshot.getValue(Settings::class.java)
////                    val phone = dataSnapshot.child("phone").value as String
//                    Log.e(TAG, "onDataChange: Message data is updated: " + message!!.name + ", " + message.phone)
//                    settings_owner_name.setTextSmartly(message.name)
//                    settings_owner_phone.setTextSmartly(message.phone)
////                    tvTime.text = message.phone
//                }
//            }
//
//            override fun onCancelled(databaseError: DatabaseError) {
//                // Failed to read value
//                Log.e(TAG, "onCancelled: Failed to read message")
//                tvAuthor.text = ""
//                tvTime.text = ""
//            }
//        }
//        mMessageReference!!.addValueEventListener(messageListener)
//        mMessageListener = messageListener
//    }

    private fun submitMessage() {
        val phone = settings_owner_phone.text.toString()
        if (TextUtils.isEmpty(phone)) {
            edtSentText.error = REQUIRED
            return
        }
        // SettingsInfoData data change listener
        mDatabase!!.child("message").child(user!!.uid).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (user != null) {
                    Log.e(TAG, "onDataChange: SettingsInfoData data is null!")
                    Toast.makeText(this@SettingsView, "onDataChange: SettingsInfoData data is null!", Toast.LENGTH_SHORT).show()
                    return
                }
                val user = dataSnapshot.getValue(Settings::class.java)
                val phone = dataSnapshot.child("phone").value as String
                settings_owner_phone.setText(phone)
//                writeNewMessage(body, phone)
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.e(TAG, "onCancelled: Failed to read user!")
            }
        })
    }


    private fun writeNewMessage(name: String, phone: String) {
        val time = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().time)
        val message = Settings(user!!.email!!, name)
        val phone = Settings(user!!.email!!, phone)

        mMessageReference!!.setValue(message)
        mMessageReference!!.setValue(phone)
    }

    private fun getUsernameFromEmail(email: String?): String {
        return if (email!!.contains("@")) {
            email.split("@".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0]
        } else {
            email
        }
    }

    private fun uploadImageToFirebaseStorage() {
        if (selectedPhotoUri == null) return

        val filename = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("/images/$filename")

        ref.putFile(selectedPhotoUri!!)
                .addOnSuccessListener {
                    Log.d(TAG, "Successfully uploaded image: ${it.metadata?.path}")

                    ref.downloadUrl.addOnSuccessListener {
                        Log.d(TAG, "File Location: $it")


                    }
                }
                .addOnFailureListener {
                    Log.d(TAG, "Failed to upload image to storage: ${it.message}")
                }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null) {
            // proceed and check what the selected image was....
            Log.d(TAG, "Photo was selected")
            selectedPhotoUri = data.data

            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedPhotoUri)

            selectphoto_imageview_register.setImageBitmap(bitmap)

            selectphoto_button_register.alpha = 0f

//      val bitmapDrawable = BitmapDrawable(bitmap)
//      selectphoto_button_register.setBackgroundDrawable(bitmapDrawable)
        }
    }
}