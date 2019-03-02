package com.zaleksandr.aleksandr.myapplication.ui.updateGuest

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.Query
import com.zaleksandr.aleksandr.myapplication.DialogCompositeDisposable
import com.zaleksandr.aleksandr.myapplication.MainActivity
import com.zaleksandr.aleksandr.myapplication.MainActivity.Companion.AUTHOR_KEY
import com.zaleksandr.aleksandr.myapplication.MainActivity.Companion.QUOTE_KEY
import com.zaleksandr.aleksandr.myapplication.addTo
import com.zaleksandr.aleksandr.myapplication.model.Guest
import com.zaleksandr.aleksandr.myapplication.showMaterialDialogCancelDelete
import com.zaleksandr.aleksandr.myapplication.ui.commonResult.adapter.MyGuestAdapter
import com.zaleksandr.aleksandr.myapplication.ui.settings.model.User
import com.zaleksandr.aleksandr.myapplication.util.FirestoreUtil
import com.zaleksandr.aleksandr.myapplication.util.FirestoreUtil.firestoreInstance
import com.zaleksandr.aleksandr.myapplication.util.StorageUtil
import com.zaleksandr.aleksandr.myapplication.util.StorageUtil.currentUserRef
import com.zaleksandr.aleksandr.tmbook.glade.GlideApp
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_settings.*
import kotlinx.android.synthetic.main.fragment_update_guest.*
import kotlinx.android.synthetic.main.fragment_update_guest.view.*
import java.io.ByteArrayOutputStream


class GuestEditFragment : Fragment(), MyGuestAdapter.FragmentCommunication {

    override fun respond(city: Guest) {
        context?.showMaterialDialogCancelDelete(
                title = resources.getText(com.zaleksandr.aleksandr.myapplication.R.string.delete_item_card_title).toString(),
                message = resources.getText(com.zaleksandr.aleksandr.myapplication.R.string.delete_select_item).toString(),
                onNoClick = {},
                onYesClick = {

                }
        )?.addTo(dialogDisposable)
    }

    companion object {
        val NAME = "name"
    }

    private val SELECT_IMAGE = 2
    var path = ""
    var name = ""
    var photo = ""
    var toolbar: Toolbar? = null
    val notesUpdateGuestRef = firestoreInstance.collection("Guest")
    var city: Guest? = null
    private val dialogDisposable = DialogCompositeDisposable()
    private var pictureJustChange = false
    private lateinit var selectImageBytes: ByteArray

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(com.zaleksandr.aleksandr.myapplication.R.layout.fragment_update_guest, container, false)

        rootView.update_profile_pocture.setOnClickListener {
            val intent = Intent().apply {
                type = "image/*"
                action = Intent.ACTION_GET_CONTENT
                putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("image/jpeg", "image/png", "image/jpg"))
            }
            startActivityForResult(Intent.createChooser(intent, " Select Image"), 2)
        }

        rootView.fab_update_guest.setOnClickListener {

            if (::selectImageBytes.isInitialized) {
                uploadGuestProfilePhoto(selectImageBytes) { profilePicturePath ->
                    updatePhoto(profilePicturePath)
                }
            }
            setUpRecyclerView(rootView)
            updateCurrentUser(

                    guest_name_edit.text.toString(),
                    guest_intro.isChecked,
                    guest_twoDay.isChecked,
                    guest_act.isChecked,
                    guest_21.isChecked,
                    guest_nwet.isChecked
            )

        }

        setUpRecyclerView(rootView)

//        bottomMenuInit(rootView)

        return rootView
    }

    fun uploadGuestProfilePhoto(imageBytes: ByteArray,
                                onSuccess: (imagePath: String) -> Unit) {
        val ref = currentUserRef.child("guestPictures/${(guest_name_edit.text.toString())}")
        ref.putBytes(imageBytes)
                .addOnSuccessListener {
                    onSuccess(ref.path)
                }
    }

    override fun onResume() {
        super.onResume()
        (this.activity!!.toolbar as Toolbar).title = "My guests"
    }

    private fun makeSnackBarMessage(message: String) {
        Snackbar.make(this.view!!, message, Snackbar.LENGTH_SHORT).show()
    }

//    fun updateNote(city: City?) {
//
//        val db = FirebaseFirestore.getInstance()
//
//        val noteRef = db
//                .collection("NewCity")
//                .document(city?.id.toString())
//
//        noteRef.update(
//                "intro", city?.intro
//        ).addOnCompleteListener { task ->
//            if (task.isSuccessful) {
//                makeSnackBarMessage("Updated note")
////                adapter?.updateNote(city!!)
//            } else {
//                makeSnackBarMessage("Failed. Check log.")
//            }
//        }
//    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == SELECT_IMAGE && resultCode == Activity.RESULT_OK &&
                data != null && data.data != null) {
            val selectedImagePath = data.data
            val selectedImageBmp = MediaStore.Images.Media
                    .getBitmap(context?.contentResolver, selectedImagePath)

            val outputStream = ByteArrayOutputStream()
            selectedImageBmp.compress(Bitmap.CompressFormat.JPEG, 20, outputStream)
            selectImageBytes = outputStream.toByteArray()

            GlideApp.with(this)
                    .load(selectImageBytes)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(update_profile_pocture)
            pictureJustChange = true
        }
    }


    private fun updatePhoto(profilePicturePath: String) {

        photo = profilePicturePath
        notesUpdateGuestRef.document(path).update("photo", photo)

    }
//    override fun onStart() {
//        super.onStart()
//        notesUpdateGuestRef
//                .whereEqualTo("currentUserId", if ("${FirebaseAuth.getInstance().uid}" == FirebaseAuth.getInstance().currentUser!!.uid) {
//                    FirebaseAuth.getInstance().uid
//                } else null).orderBy("time", Query.Direction.DESCENDING)
//                .whereEqualTo("name", name)
//
//                .get().addOnCompleteListener { querydocumentSnapshot ->
//                    if (querydocumentSnapshot.isSuccessful) {
//                        for (documentSnapshot in querydocumentSnapshot.result!!) {
//                            val note = documentSnapshot.toObject<Guest>(Guest::class.java)
//                            path = note.id.toString()
//
//                            if (note.photo != null) {
//                                GlideApp.with(this)
//                                        .load(StorageUtil.pathToReference(note.photo))
//                                        .into(update_profile_pocture)
//                            } else {
//                            }
//                        }
//
//                    }
//                }
//        }
    private fun updateCurrentUser(name: String = "", intro: Boolean, oneDay: Boolean, twoDay: Boolean, twOneDay: Boolean, nwet: Boolean) {

        val userFieldMap = mutableMapOf<String, Any>()

        notesUpdateGuestRef.document(path).update(userFieldMap)
        notesUpdateGuestRef.document(path).update("name", name,
                "intro", intro,
                "onedayWS", oneDay,
                "twoDayWS", twoDay,
                "twOneDay", twOneDay,
                "nwet", nwet)

    startActivity(Intent(context, MainActivity::class.java))
//        notesUpdateGuestRef.document(path).update("onedayWS", oneDay)
//        notesUpdateGuestRef.document(path).update("twoDayWS", twoDay)
//        notesUpdateGuestRef.document(path).update("twOneDay", twOneDay)
//        notesUpdateGuestRef.document(path).update("onedayWS", oneDay)
//        notesUpdateGuestRef.document(path).update("twoDayWS", twoDay)
    }

    private fun setUpRecyclerView(rootView: View) {

        val name = arguments?.getString("name")
        val intro = arguments?.getBoolean("intro", false)

        notesUpdateGuestRef
                .whereEqualTo("currentUserId", if ("${FirebaseAuth.getInstance().uid}" == FirebaseAuth.getInstance().currentUser!!.uid) {
                    FirebaseAuth.getInstance().uid
                } else null).orderBy("time", Query.Direction.DESCENDING)
                .whereEqualTo("name", name)

                .get().addOnCompleteListener { querydocumentSnapshot ->
                    if (querydocumentSnapshot.isSuccessful) {
                        for (documentSnapshot in querydocumentSnapshot.result!!) {
                            val note = documentSnapshot.toObject<Guest>(Guest::class.java)
                            path = note.id.toString()
                            guest_intro.isChecked = intro!!
                            guest_name_edit.setText(name.toString())

                                if (note.photo != null) {

                                    GlideApp.with(this)
                                            .load(StorageUtil.pathToReference(note.photo))
                                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                                            .skipMemoryCache(true)
                                            .into(update_profile_pocture)
                                } else {
                                }
                        }

                    }
                }
    }
}

//    private fun bottomMenuInit(rootView: View) {
//        val layoutParams = rootView.bottom_navigation_person.layoutParams as CoordinatorLayout.LayoutParams
//        layoutParams.behavior = BottomNavigationViewBehavior()
//        rootView.bottom_navigation_person.setOnNavigationItemSelectedListener { item ->
//            when (item.itemId) {
//                com.zaleksandr.aleksandr.myapplication.R.currentUserId.menu_year -> {
//                    adapter?.perioSelected(IndividualAdapter.ClickByFilter.YEAR)
//                }
//                com.zaleksandr.aleksandr.myapplication.R.currentUserId.menu_month -> {
//                    adapter?.perioSelected(IndividualAdapter.ClickByFilter.MONTH)
//                }
//                com.zaleksandr.aleksandr.myapplication.R.currentUserId.menu_week -> {
//                    adapter?.perioSelected(IndividualAdapter.ClickByFilter.WEEK)
//                }
//
//                else -> {
//                }
//            }
//            true
//        }
//    }
//
//}