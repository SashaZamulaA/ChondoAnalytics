package com.zaleksandr.aleksandr.myapplication.ui.updateGuest

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.Query
import com.zaleksandr.aleksandr.myapplication.DialogCompositeDisposable
import com.zaleksandr.aleksandr.myapplication.MainActivity
import com.zaleksandr.aleksandr.myapplication.addTo
import com.zaleksandr.aleksandr.myapplication.model.Guest
import com.zaleksandr.aleksandr.myapplication.showMaterialDialogCancelDelete
import com.zaleksandr.aleksandr.myapplication.util.FirestoreUtil.firestoreInstance
import com.zaleksandr.aleksandr.myapplication.util.StorageUtil
import com.zaleksandr.aleksandr.myapplication.util.StorageUtil.currentUserRef
import com.zaleksandr.aleksandr.tmbook.glade.GlideApp
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_update_guest.*
import kotlinx.android.synthetic.main.fragment_update_guest.view.*
import java.io.ByteArrayOutputStream
import java.text.DateFormat
import java.util.*
import android.view.MotionEvent
import android.view.View.OnTouchListener
import com.zaleksandr.aleksandr.myapplication.ui.updateGuest.adapter.MyGuestAdapter


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
    var updateDateBirthday: String? = null
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

        rootView.guest_edit_description.setOnTouchListener(OnTouchListener { v, event ->
            if (guest_edit_description.hasFocus()) {
                v.parent.requestDisallowInterceptTouchEvent(true)
                when (event.action and MotionEvent.ACTION_MASK) {
                    MotionEvent.ACTION_SCROLL -> {
                        v.parent.requestDisallowInterceptTouchEvent(false)
                        return@OnTouchListener true
                    }
                }
            }
            false
        })

        rootView.update_birthday_gues.setOnClickListener {
            showDatePickerBirthdayDialog(this.context!!, String(), birthday_edit)
        }

        rootView.guest_time_intro.setOnClickListener {
            showDatePickerIntroDialog(this.context!!, String(), guest_time_intro_edittext)
        }

        rootView.guest_time_one_day.setOnClickListener {
            showDatePickerOneDayDialog(this.context!!, String(), guest_time_1d)
        }

        rootView.guest_time_two_day.setOnClickListener {
            showDatePickerTwoDayDialog(this.context!!, String(), guest_time2d)
        }

        rootView.guest_time_act.setOnClickListener {
            showDatePickerActDialog(this.context!!, String(), guest_time_edittext_act)
        }

        rootView.guest_time_21_day.setOnClickListener {
            showDatePicker21Dialog(this.context!!, String(), guest_time_21d)
        }

        rootView.guest_time_nwet.setOnClickListener {
            showDatePickerNWETDialog(this.context!!, String(), guest_time_edittext_nwet)
        }
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
                    birthday_edit.text.toString(),
                    guest_intro.isChecked,
                    guest_oneDay.isChecked,
                    guest_twoDay.isChecked,
                    guest_act.isChecked,
                    guest_21.isChecked,
                    guest_nwet.isChecked,
                    guest_edit_phone.text.toString(),
                    guest_edit_description.text.toString(),
                    guest_time_intro_edittext.text.toString(),
                    guest_time_1d.text.toString(),
                    guest_time2d.text.toString(),
                    guest_time_edittext_act.text.toString(),
                    guest_time_21d.text.toString(),
                    guest_time_edittext_nwet.text.toString()
            )

        }

        setUpRecyclerView(rootView)

//        bottomMenuInit(rootView)

        return rootView
    }


    fun showDatePickerBirthdayDialog(mContext: Context, format: String, update_birthday_gues: TextView) {

        val c = Calendar.getInstance()
        DatePickerDialog(mContext, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            c.set(year, monthOfYear, dayOfMonth)

            val currentDateString = DateFormat.getDateInstance(DateFormat.DATE_FIELD).format(c.time)
            update_birthday_gues.text = currentDateString


        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH)).show()


    }

    fun showDatePickerIntroDialog(mContext: Context, format: String, guest_time_intro_edittext: TextView) {

        val c = Calendar.getInstance()
        DatePickerDialog(mContext, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            c.set(year, monthOfYear, dayOfMonth)

            val currentDateString = DateFormat.getDateInstance(DateFormat.DATE_FIELD).format(c.time)
            guest_time_intro_edittext.text = currentDateString

        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH)).show()


    }

    fun showDatePickerOneDayDialog(mContext: Context, format: String, guest_time_1d: TextView) {

        val c = Calendar.getInstance()
        DatePickerDialog(mContext, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            c.set(year, monthOfYear, dayOfMonth)

            val currentDateString = DateFormat.getDateInstance(DateFormat.DATE_FIELD).format(c.time)
            guest_time_1d.text = currentDateString

        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH)).show()


    }

    fun showDatePickerTwoDayDialog(mContext: Context, format: String, guest_time2d: TextView) {

        val c = Calendar.getInstance()
        DatePickerDialog(mContext, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            c.set(year, monthOfYear, dayOfMonth)

            val currentDateString = DateFormat.getDateInstance(DateFormat.DATE_FIELD).format(c.time)
            guest_time2d.text = currentDateString

        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH)).show()


    }

    fun showDatePickerActDialog(mContext: Context, format: String, guest_time_edittext_act: TextView) {

        val c = Calendar.getInstance()
        DatePickerDialog(mContext, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            c.set(year, monthOfYear, dayOfMonth)

            val currentDateString = DateFormat.getDateInstance(DateFormat.DATE_FIELD).format(c.time)
            guest_time_edittext_act.text = currentDateString

        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH)).show()


    }

    fun showDatePicker21Dialog(mContext: Context, format: String, guest_time_21d: TextView) {

        val c = Calendar.getInstance()
        DatePickerDialog(mContext, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            c.set(year, monthOfYear, dayOfMonth)

            val currentDateString = DateFormat.getDateInstance(DateFormat.DATE_FIELD).format(c.time)
            guest_time_21d.text = currentDateString

        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH)).show()


    }

    fun showDatePickerNWETDialog(mContext: Context, format: String, guest_time_edittext_nwet: TextView) {

        val c = Calendar.getInstance()
        DatePickerDialog(mContext, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            c.set(year, monthOfYear, dayOfMonth)

            val currentDateString = DateFormat.getDateInstance(DateFormat.DATE_FIELD).format(c.time)
            guest_time_edittext_nwet.text = currentDateString

        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH)).show()


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
    private fun updateCurrentUser(name: String = "",
                                  birthday: String,
                                  intro: Boolean,
                                  oneDay: Boolean,
                                  twoDay: Boolean,
                                  action: Boolean,
                                  twOneDay: Boolean,
                                  nwet: Boolean,
                                  phone: String,
                                  description: String,
                                  timeInt: String,
                                  timeOneD: String,
                                  timeTwoD: String,
                                  timeAct: String,
                                  time21: String,
                                  timeNwet: String) {

        val userFieldMap = mutableMapOf<String, Any>()

        notesUpdateGuestRef.document(path).update(userFieldMap)
        notesUpdateGuestRef.document(path).update("name", name,
                "birthday", birthday,
                "intro", intro,
                "onedayWS", oneDay,
                "twoDayWS", twoDay,
                "actionaiser", action,
                "twOneDay", twOneDay,
                "nwet", nwet,
                "phoneNum", phone,
                "description", description,
                "timeIntro", timeInt,
                "timeOneDay", timeOneD,
                "timeTwoDay", timeTwoD,
                "timeAct", timeAct,
                "time21Day", time21,
                "timeNwet", timeNwet
        )

        startActivity(Intent(context, MainActivity::class.java))
    }


    private fun setUpRecyclerView(rootView: View) {

        val name = arguments?.getString("name")
        val intro = arguments?.getBoolean("intro", false)
        val oneday = arguments?.getBoolean("oneDay", false)

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
                            guest_intro.isChecked = note.intro!!
                            guest_oneDay.isChecked = note.onedayWS!!
                            guest_twoDay.isChecked = note.twoDayWS!!
                            guest_act.isChecked = note.actionaiser!!
                            guest_21.isChecked = note.twOneDay!!
                            guest_nwet.isChecked = note.nwet!!
                            guest_edit_description.setText(note.description)
                            guest_edit_phone.setText(note.phoneNum)
                            birthday_edit.text = note.birthday
                            guest_time_intro_edittext.text = note.timeIntro
                            guest_time_1d.text = note.timeOneDay
                            guest_time2d.text = note.timeTwoDay
                            guest_time_edittext_act.text = note.timeAct
                            guest_time_21d.text = note.time21Day
                            guest_time_edittext_nwet.text = note.timeNwet


                            guest_name_edit.setText(name.toString())

                            if (!note.photo.isNullOrEmpty()) {

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