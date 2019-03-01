package com.zaleksandr.aleksandr.myapplication.ui.addGuest

import android.app.Activity
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.zaleksandr.aleksandr.myapplication.MainActivity
import com.zaleksandr.aleksandr.myapplication.MainActivity.Companion.SPINNER
import com.zaleksandr.aleksandr.myapplication.R
import com.zaleksandr.aleksandr.myapplication.model.Guest
import com.zaleksandr.aleksandr.myapplication.setSimpleTextWatcher
import com.zaleksandr.aleksandr.myapplication.util.StorageUtil.currentUserRef
import com.zaleksandr.aleksandr.tmbook.glade.GlideApp
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_add_guest.*
import kotlinx.android.synthetic.main.fragment_add_guest.view.*
import java.io.ByteArrayOutputStream
import java.text.DateFormat
import java.util.*

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class AddGuestFragment : Fragment() {

    enum class InvalidData {
        NO_NAME

    }


    private val SELECT_IMAGE = 2
    private lateinit var selectImageBytes: ByteArray
    private var pictureJustChange = false
    var photo = ""
    val firestoreInstance: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }
    val currentUserDocRef: DocumentReference
        get() = firestoreInstance.document("City/${FirebaseAuth.getInstance().uid
                ?: throw NullPointerException("UID is null.")}")

    var name = ""
    private val noteRefGuestCollection = firestoreInstance.collection("Guest").document()

    private val spinner_country = arrayOf(
            "Kyiv", "Kharkiv", "Dnepr", "Zhytomyr", "Lviv", "Odessa", "Chernigov", "Other"
    )

    private var category2: Array<String>? = null
    var calendarDate: Date? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(com.zaleksandr.aleksandr.myapplication.R.layout.fragment_add_guest, container, false)

        val c = Calendar.getInstance()
        val currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.time)
        rootView.date_intro_guest_textViewDate.text = currentDateString

        category2 = resources.getStringArray(com.zaleksandr.aleksandr.myapplication.R.array.City)
        @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
        val adapter2 = ArrayAdapter(context, android.R.layout.simple_dropdown_item_1line, category2)
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        rootView.add_guest_photo.setOnClickListener {
            val intent = Intent().apply {
                type = "image/*"
                action = Intent.ACTION_GET_CONTENT
                putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("image/jpeg", "image/png", "image/jpg"))
            }
            startActivityForResult(Intent.createChooser(intent, " Select Image"), SELECT_IMAGE)
        }


        val spinnerCountryAdapter = ArrayAdapter(context, com.zaleksandr.aleksandr.myapplication.R.layout.spinner_simple_item, spinner_country)
        spinnerCountryAdapter.setDropDownViewResource(com.zaleksandr.aleksandr.myapplication.R.layout.spinner_drop_down)
        rootView.add_guest_city.adapter = spinnerCountryAdapter
        rootView.add_guest_city.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            }
        }

        rootView.date_intro_guest_textViewDate.setOnClickListener {
            showDatePickerDialog(this.context!!, String(), date_intro_guest_textViewDate)
        }

        rootView.guest_fab_confirm_goal.setOnClickListener {
            guest_name_edittext.setSimpleTextWatcher {
                onResetError()
            }

            if (guest_name_edittext.text.toString().isBlank()) {
                showInvalidValue(InvalidData.NO_NAME)
            } else {
                if (::selectImageBytes.isInitialized) {
                    uploadGuestProfilePhoto(selectImageBytes) { imagePath ->
                        addNote(imagePath)
                    }
                }
                addNote()
            }
        }
        return rootView
    }

    private fun showInvalidValue(error: AddGuestFragment.InvalidData) {
        when (error) {
            AddGuestFragment.InvalidData.NO_NAME -> guest_name.error = resources.getString(R.string.no_email)
        }
    }


    fun onResetError() {
        guest_name.isErrorEnabled = false

    }

    fun uploadGuestProfilePhoto(imageBytes: ByteArray,
                                onSuccess: (imagePath: String) -> Unit) {
        val ref = currentUserRef.child("guestPictures/${(guest_name_edittext.text.toString())}")
        ref.putBytes(imageBytes)
                .addOnSuccessListener {
                    onSuccess(ref.path)
                }
    }

    fun showDatePickerDialog(mContext: Context, format: String, textViewDate: TextView) {

        val c = Calendar.getInstance()
        DatePickerDialog(mContext, OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            c.set(year, monthOfYear, dayOfMonth)

            calendarDate = Date(c.timeInMillis)
            val currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.time)
            textViewDate.text = currentDateString
//            val dateFormat = SimpleDateFormat("yyyy-MM-dd")
//            val parsedDate = dateFormat.parse(currentDateString).time


        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH)).show()


    }

    override fun onResume() {
        super.onResume()
        (this.activity!!.toolbar as Toolbar).title = "Add result"
    }

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
                    .into(add_guest_photo)

            pictureJustChange = true
        }
    }

    private fun addNote(profilePicturePath: String? = null) {

        if (profilePicturePath != null) {
            photo = profilePicturePath
        }

        val id = noteRefGuestCollection.id
        name = guest_name_edittext.text.toString()

        val center = add_guest_city.selectedItem.toString()
        val intro = add_guest_intro.isChecked
        val oneDayWS = add_guest_oneDay.isChecked
        val twoDayWS = add_guest_twoDay.isChecked
        val actionaiser = add_guest_act.isChecked
        val twOneDay = add_guest_21.isChecked
        val nwet = add_guest_nwet.isChecked
//        val birthday = guest_birthday.text.toString()
//        val phoneNum = guest_phone.text.toString()

        val dataToSave = HashMap<String, Any>()
        dataToSave[SPINNER] = center
        val timestamp = System.currentTimeMillis()


        val date: Date
        val currentUserId = FirebaseAuth.getInstance().currentUser!!.uid
        if (calendarDate == null) {
            val c = Calendar.getInstance()
            val currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.time)
            date_intro_guest_textViewDate.text = currentDateString
            date = Date(c.timeInMillis)
        } else {
            date = calendarDate as Date
        }

        noteRefGuestCollection.set(Guest(
                id,
                currentUserId,
                name,
                photo,
                center,
                intro,
                oneDayWS,
                twoDayWS,
                actionaiser,
                twOneDay,
                nwet,
//                            timeIntro,
//                            timeOneDay,
//                            timeTwoDay,
//                            timeAct,
//                            time21Day,
//                            timeNwet,
                Date(),
                timestamp
//                            birthday,
//                            phoneNum
        ))

        startActivity(Intent(context, MainActivity::class.java))
    }
}