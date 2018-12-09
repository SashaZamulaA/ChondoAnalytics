package com.example.aleksandr.myapplication.ui.main

import android.content.Intent
import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v7.app.AlertDialog
import android.util.Log
import com.example.aleksandr.myapplication.BaseActivity
import com.example.aleksandr.myapplication.model.City
import com.example.aleksandr.myapplication.ui.login.LoginActivity
import com.example.aleksandr.myapplication.util.FirestoreUtil.firestoreInstance
import com.google.firebase.firestore.FieldValue
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class MainActivity : BaseActivity() {
    private lateinit var presenter: MainPresenter
    private val noteRefCollection = firestoreInstance.collection("NewCity")
    private val noteRef = firestoreInstance.document("ResultNote/My First ResultNote")


    override fun init(savedInstanceState: Bundle?) {
        super.setContentView(com.example.aleksandr.myapplication.R.layout.activity_main)
        presenter = MainPresenter(this, application)
        loadKyivDay()
        loadKharkiv()

        val doc = HashMap<String, Any>()
        doc["timestamp"] = FieldValue.serverTimestamp()

        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                com.example.aleksandr.myapplication.R.id.menu_year -> {

                }
                com.example.aleksandr.myapplication.R.id.menu_month -> {

                }
                com.example.aleksandr.myapplication.R.id.menu_week -> {
                    loadKyivWeek()
                }
                com.example.aleksandr.myapplication.R.id.menu_day -> {
                    loadKyivDay()

                }
                else -> {
                }
            }
            true
        }
    }

//            val recyclerView = findViewById<RecyclerView>(R.id.result_recycler)
//        recyclerView.layoutManager = LinearLayoutManager(this)
//
//        val rootRef = FirestoreUtil.currentUserDocRef
//
//        val query = rootRef.collection("City")
//                .orderBy("City", Query.Direction.ASCENDING)
//
//        val options = FirestoreRecyclerOptions.Builder<CityData>()
//                .setQuery(query, CityData::class.java)
//                .build()
//
//        adapter = object : FirestoreRecyclerAdapter<CityData, ProductViewHolder>(options) {
//
//            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
//                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_main_list, parent, false)
//                return ProductViewHolder(view)
//            }
//
//            override fun onBindViewHolder(holder: ProductViewHolder, position: Int, model: CityData) {
//                holder.setProductName(model.name)
//            }
//        }
//        recyclerView.adapter = adapter
//    }
//
//    private inner class ProductViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {
//        internal fun setProductName(productName: String) {
//
//            result_city.text = productName
//        }
//    }
//        val rootRef = FirestoreUtil.currentUserDocRef
//        val query = rootRef.collection("City")
//        val options = FirestoreRecyclerOptions.Builder<CityData>()
//                .setQuery(query, CityData::class.java)
//                .build()
//
//        adapter = MainAdapter(options)
//        result_recycler.setHasFixedSize(true)
//        result_recycler.layoutManager = LinearLayoutManager(this)
//        result_recycler.adapter = adapter
//    }
//
//
//    override fun onStart() {
//        super.onStart()
//        adapter.startListening()
//        FirestoreUtil.currentUserDocRef.collection("City").get()
//                .addOnSuccessListener(OnSuccessListener<QuerySnapshot> { documentSnapshots ->
//                    if (documentSnapshots.isEmpty()) {
//                        return@OnSuccessListener
//                    } else {
//                        val types = documentSnapshots.toObjects(CityData::class.java)
//
//                        mArrayList.addAll(types)
//                    }
//                })
//
//        FirestoreUtil.currentUserDocRef.collection("City").document("Kyiv").addSnapshotListener { documentSnapshot, _ ->
//            FirestoreUtil.currentUserDocRef.collection("City")
//                    .document("Zhytomyr")
//
//            val quoteText = documentSnapshot?.getString("onedayWS")
//
//            main_summ.text = quoteText
//            FirestoreUtil.currentUserDocRef.collection("City").get()
//
//        }
//    }
//
////    override fun onStart() {
////        super.onStart()
////        adapter?.startListening()
////    }
//
//    override fun onStop() {
//        super.onStop()
//        adapter.stopListening()
//    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        presenter.bindView(this)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        presenter.unbindView(this)
    }


    override fun onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Closing Activity")
                    .setMessage("Are you sure you want to close this activity?")
                    .setPositiveButton("Yes") { _, _ ->
                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    .setNegativeButton("No", null)
                    .show()
        }

    }


//    private fun addNote() {
//
//        val title = edit_text_title.text.toString()
//        val description = edit_text_description.text.toString()
//
//        if (edit_text_priority.length() == 0) {
//            edit_text_priority.setText("0")
//        }
//        val priority = Integer.parseInt(edit_text_priority.text.toString())
//
//        val note = Note("", title, description, priority)
//        noteRefCollection.add(note)
//    }

//    override fun onStart() {
//        super.onStart()
//        noteRefCollection.addSnapshotListener { queryDocumentSnapshots, _ ->
//
//            var data = ""
//
//            if (queryDocumentSnapshots != null) {
//
//                queryDocumentSnapshots.forEach { documentSnapshot ->
//
//                    val note = documentSnapshot.toObject(Note::class.java)
//
//                    val doc = documentSnapshot.id
//                    val title = note.title
//                    val description = note.description
//                    val priority = note.priority
//
//                    data += ("ID: $doc\nTitle: $title\nDescription: $description\nPriority:$priority\n\n")
//                }
//            }
//            text_view_data.text = data
//        }
//    }

    private fun getNowMinus24Hours(): Date {
        val calendar = Calendar.getInstance(Locale.getDefault())
        calendar.add(Calendar.DATE, -1)
        return calendar.time
    }

    fun startOfDay(date: Date): Date {
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 1)
        return calendar.time
    }

    fun atEndOfDay(date: Date): Date {
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.set(Calendar.HOUR_OF_DAY, 23)
        calendar.set(Calendar.MINUTE, 59)
        calendar.set(Calendar.SECOND, 59)
        calendar.set(Calendar.MILLISECOND, 999)
        return calendar.time
    }


    fun atEndOfWeek(date: Date): Date {
        val calendar = Calendar.getInstance()
        calendar.time = date

        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
        calendar.add(Calendar.DAY_OF_WEEK, 7)

        calendar.set(Calendar.HOUR_OF_DAY, 23)
        calendar.set(Calendar.MINUTE, 59)
        calendar.set(Calendar.SECOND, 59)
        calendar.set(Calendar.MILLISECOND, 999)
        return calendar.time
    }


    var c = GregorianCalendar.getInstance(Locale.UK)

    var onDayAgo = getNowMinus24Hours()
    val millis24Hours = (1000 * 60 * 60 * 24)

    private fun loadKyivWeek() {
        noteRefCollection
                .whereEqualTo("centers", "Kyiv")
//                .whereEqualTo("time", 1544028925821)
//                .orderBy("time")
//                .startAt(1543708800)
//                .endAt(1544227200)
                .whereLessThanOrEqualTo("time", atEndOfWeek(Date()))
                .get()
                .addOnSuccessListener { queryDocumentSnapshots ->
                    var sum = 0

                    queryDocumentSnapshots.forEach { documentSnapshot ->

                        val resultNote = documentSnapshot.toObject(City::class.java)
                        val intro = Integer.parseInt(resultNote.intro)
                        val oneD = resultNote.onedayWS
                        val twoD = resultNote.twoDayWS

                        sum += intro


                        if (sum == 0) {
                            main_intro_kyiv.text = "0"
                        } else {
                            main_intro_kyiv.text = sum.toString()
                        }

                        if (oneD.isNullOrEmpty()) {
                            main_one_day_kyiv.text = "0"

                        } else {
                            main_one_day_kyiv.text = oneD
                        }

                        if (twoD.isNullOrEmpty()) {
                            main_two_day_kyiv.text = "0"
                        } else {
                            main_two_day_kyiv.text = twoD
                        }
                    }

                }.addOnFailureListener { e ->
                    Log.d("What wrong", e.toString())
                }
    }

    fun loadKyivDay() {
        noteRefCollection
                .whereEqualTo("centers", "Kyiv")
//                .whereEqualTo("time", 1544028925821)
//                .orderBy("time")
//                .startAt(1543708800)
//                .endAt(1544227200)

                .whereLessThanOrEqualTo("time", atEndOfDay(Date()))
                .whereGreaterThanOrEqualTo("time", startOfDay(Date()))
                .get()
                .addOnSuccessListener { queryDocumentSnapshots ->
                    var sum = 0

                    queryDocumentSnapshots.forEach { documentSnapshot ->

                        val resultNote = documentSnapshot.toObject(City::class.java)
                        val intro = Integer.parseInt(resultNote.intro)
                        val oneD = resultNote.onedayWS
                        val twoD = resultNote.twoDayWS

                        sum += intro

                        if (sum == 0) {
                            main_intro_kyiv.text = "0"
                        } else {
                            main_intro_kyiv.text = sum.toString()
                        }

                        if (oneD.isNullOrEmpty()) {
                            main_one_day_kyiv.text = "0"

                        } else {
                            main_one_day_kyiv.text = oneD
                        }

                        if (twoD.isNullOrEmpty()) {
                            main_two_day_kyiv.text = "0"
                        } else {
                            main_two_day_kyiv.text = twoD
                        }
                    }

                }.addOnFailureListener { e ->
                    Log.d("What wrong", e.toString())

                }
    }

    private fun loadKharkiv() {
        noteRefCollection
                .whereEqualTo("centers", "Kharkiv")
                .get()
                .addOnSuccessListener { queryDocumentSnapshots ->

                    queryDocumentSnapshots.forEach { documentSnapshot ->

                        val resultNote = documentSnapshot.toObject(City::class.java)

                        val intro = resultNote.intro
                        val oneD = resultNote.onedayWS
                        val twoD = resultNote.twoDayWS

                        if (intro.isNullOrEmpty()) {
                            main_intro_kharkiv.text = "0"
                        } else {
                            main_intro_kharkiv.text = intro
                        }

                        if (oneD.isNullOrEmpty()) {
                            main_one_day_kharkiv.text = "0"

                        } else {
                            main_one_day_kharkiv.text = oneD
                        }

                        if (twoD.isNullOrEmpty()) {
                            main_two_day_kharkiv.text = "0"
                        } else {
                            main_two_day_kharkiv.text = twoD
                        }

                    }

                }.addOnFailureListener { e ->
                    Log.d("What wrong", e.toString())
                }
    }
}