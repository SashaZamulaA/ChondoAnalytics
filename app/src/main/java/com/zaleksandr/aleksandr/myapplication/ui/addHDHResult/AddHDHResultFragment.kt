package com.zaleksandr.aleksandr.myapplication.ui.addHDHResult

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.zaleksandr.aleksandr.myapplication.MainActivity
import com.zaleksandr.aleksandr.myapplication.MainActivity.Companion.AUTHOR_KEY
import com.zaleksandr.aleksandr.myapplication.MainActivity.Companion.SPINNER
import com.zaleksandr.aleksandr.myapplication.R
import com.zaleksandr.aleksandr.myapplication.model.CityHDH
import com.zaleksandr.aleksandr.myapplication.util.FirestoreUtil
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_add_week_result.*
import kotlinx.android.synthetic.main.fragment_add_week_result.view.*
import java.text.DateFormat
import java.util.*


@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class AddHDHResultFragment : Fragment() {


    private var pictureJustChange = false
    val firestoreInstance: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }
    val currentUserDocRef: DocumentReference
        get() = firestoreInstance.document("City/${FirebaseAuth.getInstance().uid
                ?: throw NullPointerException("UID is null.")}")

    var timestamp: Long = 0

    private val noteRefAddCollection = firestoreInstance.collection("ResultHDH").document()

    private val spinner_group = arrayOf(
            "Tolik", "Toms", "Igor", "Misha")

    private var category2: Array<String>? = null
    var calendarDate: Date? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_add_week_result, container, false)


        val c = Calendar.getInstance()
        val currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.time)
        rootView.textViewWeekDate.text = currentDateString


        rootView.button_back_from_week_result_to_common_res.setOnClickListener {
            Navigation.findNavController(this.view!!).navigate(R.id.action_addResultFragment_to_commonResultFragment)
        }
        category2 = resources.getStringArray(R.array.Group)
        @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
        val adapter2 = ArrayAdapter(context, android.R.layout.simple_dropdown_item_1line, category2)
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        val spinnerCountryAdapter = ArrayAdapter(context, R.layout.spinner_simple_item, spinner_group)
        spinnerCountryAdapter.setDropDownViewResource(R.layout.spinner_drop_down)
        rootView.registration_group.adapter = spinnerCountryAdapter
        rootView.registration_group.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            }
        }

        rootView.buttonWeekDate.setOnClickListener {
            showDatePickerDialog(this.context!!, String(), textViewWeekDate)
        }

        rootView.result_week_button.setOnClickListener {
            addNote()
        }
        return rootView
    }


    private fun showDatePickerDialog(mContext: Context, format: String, textViewDate: TextView) {

        val c = Calendar.getInstance()
        DatePickerDialog(mContext, OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            c.set(year, monthOfYear, dayOfMonth)

            calendarDate = Date(c.timeInMillis)
            val currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.time)
            textViewDate.text = currentDateString
            timestamp = c.timeInMillis

        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH)).show()


    }

    override fun onResume() {
        super.onResume()
        (this.activity!!.toolbar as Toolbar).visibility = View.GONE

    }

    private fun addNote() {

        val groups = registration_group.selectedItem.toString()
        val hdhZero = zero.isChecked
        val hdhOne = one.isChecked
        val hdhTwo =two.isChecked
        val hdhThree = three.isChecked
        val hdhFour = four.isChecked
        val hdhFive = five.isChecked
        val hdhSix =six.isChecked
        val hdhSeven =seven.isChecked
        val mobilisation = checkBox.isChecked
        val ss = checkBox2.isChecked
        val farm = checkBox3.isChecked



        val date: Date

        if (calendarDate == null) {
            val c = Calendar.getInstance()
            val currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.time)
            textViewWeekDate.text = currentDateString
            date = Date(c.timeInMillis)
            timestamp = System.currentTimeMillis()
        } else {
            date = calendarDate as Date
        }


        var userPhotoPath = ""
        var name = ""

        val id = FirebaseAuth.getInstance().currentUser!!.uid
        val dataToSave = HashMap<String, Any>()
        dataToSave[SPINNER] = groups
//        val timestamp = System.currentTimeMillis()

        val getId = noteRefAddCollection.id

        FirestoreUtil.currentUserDocRef.addSnapshotListener { documentSnapshot, _ ->
            FirestoreUtil.getCurrentUser { user ->
                if (documentSnapshot?.exists()!!) {
                    name = documentSnapshot.getString(AUTHOR_KEY) ?: ""
                    if (!pictureJustChange && user.profilePicturePath != null) {
                        userPhotoPath = user.profilePicturePath
                    }
                    noteRefAddCollection.set(CityHDH(getId, id, name, userPhotoPath, date, timestamp, hdhZero, hdhOne, hdhTwo, hdhThree, hdhFour, hdhFive, hdhSix, hdhSeven, mobilisation, ss, farm))
                }
            }
        }
        dataToSave[SPINNER] = groups
        startActivity(Intent(context, MainActivity::class.java))
    }
}