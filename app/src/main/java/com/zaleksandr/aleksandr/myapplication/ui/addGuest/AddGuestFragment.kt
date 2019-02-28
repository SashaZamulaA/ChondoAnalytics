package com.zaleksandr.aleksandr.myapplication.ui.addGuest

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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.zaleksandr.aleksandr.myapplication.MainActivity
import com.zaleksandr.aleksandr.myapplication.MainActivity.Companion.SPINNER
import com.zaleksandr.aleksandr.myapplication.model.Guest
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_add_guest.*
import kotlinx.android.synthetic.main.fragment_add_guest.view.*
import kotlinx.android.synthetic.main.fragment_update_guest.*
import kotlinx.android.synthetic.main.fragment_update_guest.view.*
import java.text.DateFormat
import java.util.*


@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class AddGuestFragment : Fragment() {


    private var pictureJustChange = false
    val firestoreInstance: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }
    val currentUserDocRef: DocumentReference
        get() = firestoreInstance.document("City/${FirebaseAuth.getInstance().uid
                ?: throw NullPointerException("UID is null.")}")

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
            addNote()
        }
        return rootView
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


    private fun addNote() {


        val id = noteRefGuestCollection.id
        val name = guest_name.text.toString()
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