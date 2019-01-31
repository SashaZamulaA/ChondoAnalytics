package com.zamulaaleksandr.aleksandr.myapplication.ui.addResult

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.widget.Toolbar
import com.zamulaaleksandr.aleksandr.myapplication.R
import androidx.fragment.app.Fragment
import com.zamulaaleksandr.aleksandr.myapplication.MainActivity
import com.zamulaaleksandr.aleksandr.myapplication.model.City
import com.zamulaaleksandr.aleksandr.myapplication.ui.settings.model.User
import com.zamulaaleksandr.aleksandr.myapplication.util.FirestoreUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.zamulaaleksandr.aleksandr.myapplication.MainActivity.Companion.AUTHOR_KEY
import com.zamulaaleksandr.aleksandr.myapplication.MainActivity.Companion.SPINNER
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_add_result.*
import kotlinx.android.synthetic.main.fragment_add_result.view.*
import kotlinx.android.synthetic.main.item_common_goal_and_result_list2.*
import org.jetbrains.anko.design.collapsingToolbarLayout
import java.util.*

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class AddResultFragment : Fragment() {

    private var pictureJustChange = false
    val firestoreInstance: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }
    val currentUserDocRef: DocumentReference
        get() = firestoreInstance.document("City/${FirebaseAuth.getInstance().uid
                ?: throw NullPointerException("UID is null.")}")

    private val noteRefCollection = firestoreInstance.collection("NewCity").document()
    var user : User? = null

    private val spinner_country = arrayOf(
            "Kyiv", "Kharkiv", "Dnepr", "Zhytomyr", "Lviv", "Odessa", "Chernigov"
    )

    private var category2: Array<String>? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(com.zamulaaleksandr.aleksandr.myapplication.R.layout.fragment_add_result, container, false)

        category2 = resources.getStringArray(R.array.City)
        @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
        val adapter2 = ArrayAdapter(context, android.R.layout.simple_dropdown_item_1line, category2)
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        val spinnerCountryAdapter = ArrayAdapter(context, R.layout.spinner_simple_item, spinner_country)
        spinnerCountryAdapter.setDropDownViewResource(R.layout.spinner_drop_down)
        rootView.registration_city.adapter = spinnerCountryAdapter
        rootView.registration_city.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            }
        }
        rootView.result_fab_confirm_goal.setOnClickListener {
            addNote()
        }
        return rootView
    }

    override fun onResume() {
        super.onResume()
        (this.activity!!.toolbar as Toolbar).title = "Add result"
    }


    private fun addNote() {
        val centers = registration_city.selectedItem.toString()
        val intro = introduction_edittext.text.toString()
        val oneDayWS = one_day_seminar_edittext.text.toString()
        val twoDayWS = two_day_seminar_edittext.text.toString()
        val twOneDay = day21_seminar_edittext.text.toString()
        val approach = result_time_street_edit_text.text.toString()
        val timeStr = result_approach_edit_text.text.toString()
        val lectOnStr = result_lectures_on_street_edittext.text.toString()
        val lectCentr = result_lectures_in_center_edittext.text.toString()
        val nwet = result_nwet_edittext.text.toString()
        val dp = result_DP_edittext.text.toString()

        var userPhotoPath = ""
        var name = ""

        val currentUserId = FirebaseAuth.getInstance().currentUser!!.uid
        val dataToSave = HashMap<String, Any>()
        dataToSave[SPINNER] = centers
        val timestamp = System.currentTimeMillis()

        val id = noteRefCollection.id

        FirestoreUtil.currentUserDocRef.addSnapshotListener { documentSnapshot, _ ->
            FirestoreUtil.getCurrentUser { user ->
                if (documentSnapshot?.exists()!!) {
                    name = documentSnapshot.getString(AUTHOR_KEY)?: ""
                    if (!pictureJustChange && user.profilePicturePath != null) {
                        userPhotoPath = user.profilePicturePath
                    }
                   noteRefCollection.set(City(id,currentUserId, intro, oneDayWS, twoDayWS, twOneDay, centers, approach, timeStr, lectOnStr, lectCentr, Date(),timestamp, userPhotoPath, name, nwet, dp))
                }
            }
        }
        dataToSave[SPINNER] = centers
        startActivity(Intent(context, MainActivity::class.java))
    }
}
