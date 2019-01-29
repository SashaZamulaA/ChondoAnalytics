package com.zamulaaleksandr.aleksandr.myapplication.ui.goal

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
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
import kotlinx.android.synthetic.main.fragment_add_result.*
import kotlinx.android.synthetic.main.fragment_add_result.view.*
import java.util.*

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class GoalFragment : Fragment() {

    private var pictureJustChange = false
    val firestoreInstance: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }
    val currentUserDocRef: DocumentReference
        get() = firestoreInstance.document("City/${FirebaseAuth.getInstance().uid
                ?: throw NullPointerException("UID is null.")}")

    private val noteRefCollection = firestoreInstance.collection("NewCity").document()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(com.zamulaaleksandr.aleksandr.myapplication.R.layout.fragment_add_goal, container, false)

        rootView.result_fab_confirm_goal.setOnClickListener {
//            addNote()
        }
        return rootView
    }
//
//    private fun addNote() {
//        val centers = registration_city.selectedItem.toString()
//        val intro = introduction_edittext.text.toString()
//        val oneDayWS = one_day_seminar_edittext.text.toString()
//        val twoDayWS = two_day_seminar_edittext.text.toString()
//        val twOneDay = day21_seminar_edittext.text.toString()
//        val approach = result_time_street_edit_text.text.toString()
//        val timeStr = result_approach_edit_text.text.toString()
//        val lectOnStr = result_lectures_on_street_edittext.text.toString()
//        val lectCentr = result_lectures_in_center_edittext.text.toString()
//        var userPhotoPath = ""
//        var name = ""
//
//        val currentUserId = FirebaseAuth.getInstance().currentUser!!.uid
//        val dataToSave = HashMap<String, Any>()
//        dataToSave[SPINNER] = centers
//        val timestamp = System.currentTimeMillis()
//
//        val id = noteRefCollection.id
//
//        FirestoreUtil.currentUserDocRef.addSnapshotListener { documentSnapshot, _ ->
//            FirestoreUtil.getCurrentUser { user ->
//                if (documentSnapshot?.exists()!!) {
//                    name = documentSnapshot.getString(AUTHOR_KEY)?: ""
//                    if (!pictureJustChange && user.profilePicturePath != null) {
//                        userPhotoPath = user.profilePicturePath
//                    }
//                    noteRefCollection.set(City(id,currentUserId, intro, oneDayWS, twoDayWS, twOneDay, centers, approach, timeStr, lectOnStr, lectCentr, Date(),timestamp, userPhotoPath, name))
//                }
//            }
//        }
//        dataToSave[SPINNER] = centers
//        startActivity(Intent(context, MainActivity::class.java))
//    }
}
