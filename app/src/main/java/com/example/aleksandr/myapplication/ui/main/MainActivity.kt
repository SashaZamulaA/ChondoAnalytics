package com.example.aleksandr.myapplication.ui.main

import android.content.Intent
import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v7.app.AlertDialog
import android.util.Log
import com.example.aleksandr.myapplication.BaseActivity
import com.example.aleksandr.myapplication.R
import com.example.aleksandr.myapplication.R.string.description
import com.example.aleksandr.myapplication.model.City
import com.example.aleksandr.myapplication.ui.login.LoginActivity
import com.example.aleksandr.myapplication.ui.main.Note.Note
import com.example.aleksandr.myapplication.util.FirestoreUtil.firestoreInstance
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity() {
    private lateinit var presenter: MainPresenter
    private val KEY_TITLE = "title"
    private val KEY_DESCRIPTION = "description"


    private val noteRefCollection = firestoreInstance.collection("NewCity")
    private val noteRef = firestoreInstance.document("ResultNote/My First ResultNote")

    override fun init(savedInstanceState: Bundle?) {
        super.setContentView(R.layout.activity_main)
        presenter = MainPresenter(this, application)
        loadKyiv()
//        save_note.setOnClickListener { addNote() }
        load_note.setOnClickListener {
            loadKharkiv()
        }

//        val recyclerView = findViewById<RecyclerView>(R.id.result_recycler)
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
//
//    override fun onAttachedToWindow() {
//        super.onAttachedToWindow()
//        presenter.bindView(this)
//    }
//
//    override fun onDetachedFromWindow() {
//        super.onDetachedFromWindow()
//        presenter.unbindView(this)
//    }
//
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

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        presenter.bindView(this)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        presenter.unbindView(this)
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

    private fun loadKyiv() {
        noteRefCollection
                .whereEqualTo("centers", "Kyiv")
                .get()
                .addOnSuccessListener { queryDocumentSnapshots ->
                    var data = ""

                    queryDocumentSnapshots.forEach { documentSnapshot ->
                        val note = documentSnapshot.toObject(Note::class.java)

                        val resultNote = documentSnapshot.toObject(City::class.java)
                        val intro = resultNote.intro
                        val oneD = resultNote.onedayWS
                        val twoD = resultNote.twoDayWS



                        main_intro_kyiv.text = intro
                        main_one_day_kyiv.text = oneD
                        main_two_day_kyiv.text = twoD

                    }
                    text_view_data.text = data
                }.addOnFailureListener { e ->
                    Log.d("What wrong", e.toString())
                }
    }

    private fun loadKharkiv() {
        noteRefCollection
                .whereEqualTo("centers", "Kharkiv")
                .get()
                .addOnSuccessListener { queryDocumentSnapshots ->
                    var data = ""

                    queryDocumentSnapshots.forEach { documentSnapshot ->
                        val note = documentSnapshot.toObject(Note::class.java)

                        val resultNote = documentSnapshot.toObject(City::class.java)

                        val intro = resultNote.intro
                        val oneD = resultNote.onedayWS
                        val twoD = resultNote.twoDayWS



                        main_intro_kharkiv.text = intro
                        main_one_day_kharkiv.text = oneD
                        main_two_day_kharkiv.text = twoD

                    }
                    main_one_day_kyiv.text = data
                    main_intro_kyiv.text = data
                    main_two_day_kyiv.text = data

                }.addOnFailureListener { e ->
                    Log.d("What wrong", e.toString())
                }
    }
}