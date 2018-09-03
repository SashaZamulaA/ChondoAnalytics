package com.example.aleksandr.myapplication.ui.hdh

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.View
import android.widget.*
import com.example.aleksandr.myapplication.BaseActivity
import com.example.aleksandr.myapplication.R
import com.example.aleksandr.myapplication.hideKeyboard
import com.example.aleksandr.myapplication.ui.hdh.model.HDHModel
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.database.*
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.view_hhw.*


class HDHView : BaseActivity(), IHDHView {

    private lateinit var presenter: HDHPresenter
    private lateinit var databaseWord: DatabaseReference
    lateinit var wordList: MutableList<HDHModel>

    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private var notebookRef: CollectionReference = db.collection("word")
    lateinit var wordAdapter: WordAdapter

    override fun init(savedInstanceState: Bundle?) {
        super.setContentView(R.layout.view_hhw)
        presenter = HDHPresenter(this, application)
        databaseWord = FirebaseDatabase.getInstance().getReference("word")

        setUpRecyclerView()

//        listViewWord.onItemLongClickListener = OnItemLongClickListener { _, _, i, _ ->
//
//            val word = wordList[i]
//            showUpdateDialog(word.id, word.name)
//            true
//        }
//        wordList = ArrayList()
//        btn_hdh.setOnClickListener {
//            addArtist()
//        }
    }

    private fun setUpRecyclerView() {
        val query: Query = notebookRef.orderBy("priority", com.google.firebase.firestore.Query.Direction.DESCENDING)
        val options: FirestoreRecyclerOptions<HDHModel> = FirestoreRecyclerOptions.Builder<HDHModel>()
                .setQuery(query, HDHModel::class.java)
                .build()


        wordAdapter = WordAdapter(options)
        listViewWord.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        listViewWord.setHasFixedSize(true)

    }

    override fun onStart() {
        super.onStart()
        wordAdapter.startListening()
//        databaseWord.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//
//                wordList.clear()
//                for (postSnapshot in dataSnapshot.children) {
//                    val word = postSnapshot.getValue<HDHModel>(HDHModel::class.java)
//                    if (word != null) {
//                        wordList.add(word)
//                    }
//                }
//                val artistAdapter = ArtistList(this@HDHView, wordList)
//                listViewWord.adapter = artistAdapter
//            }
//
//            override fun onCancelled(databaseError: DatabaseError) {
//            }
//        })
    }

    override fun onStop() {
        super.onStop()
        wordAdapter.stopListening()
    }

    @SuppressLint("InflateParams")
    private fun showUpdateDialog(wordId: String, word: String) {

        val dialogBuilder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.update_dialog, null) as View
        dialogBuilder.setView(dialogView)

        val textViewName = dialogView.findViewById(R.id.editTextName) as EditText
        val buttonUpdate = dialogView.findViewById(R.id.buttonUpdate) as Button
        val buttonDelete = dialogView.findViewById(R.id.buttonDelete) as Button
        val spinner = dialogView.findViewById(R.id.spinner_categories_dialog) as Spinner

        dialogBuilder.setTitle("Update Word- $word")
        val alertDialog = dialogBuilder.create()
        alertDialog.show()

        buttonUpdate.setOnClickListener {
            val name: String = textViewName.text.toString().trim()
            val category: String = spinner.selectedItem.toString()

            if (TextUtils.isEmpty(name)) {
                editText_hhw.error = "Name required"
            }
//            updateArtist(wordId, name, category)
            alertDialog.dismiss()
            hideKeyboard()

        }

        buttonDelete.setOnClickListener {
//            deleteWord(wordId)
            alertDialog.dismiss()
            hideKeyboard()
        }
    }

//    private fun deleteWord(wordId: String) {
//        val dRDelete = FirebaseDatabase.getInstance().getReference("word").child(wordId)
//        dRDelete.removeValue()
//        Toast.makeText(this, "Word remove Successfully", Toast.LENGTH_LONG).show()
//    }

//    private fun updateArtist(id: String, name: String, category: String): Boolean {
//        val dR = FirebaseDatabase.getInstance().getReference("word").child(id)
//        val word = HDHModel(id, name, category)
//        dR.setValue(word)
//        Toast.makeText(this, "Word Update Successfully", Toast.LENGTH_LONG).show()
//        return true
//    }

//    private fun addArtist() {
//        val name = editText_hhw.text.toString().trim()
//        val category = add_category.selectedItem.toString()
//
//        if (!TextUtils.isEmpty(name)) {
//            val id = databaseWord.push().key
//            val wordList = id?.let { HDHModel(it, name, category) }
//
//            id?.let { databaseWord.child(it).setValue(wordList) }
//
//            editText_hhw.setText("")
//
//            Toast.makeText(this, "Artist added", Toast.LENGTH_LONG).show()
//        } else {
//            Toast.makeText(this, "Please enter a name", Toast.LENGTH_LONG).show()
//        }
//    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        presenter.bindView(this)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        presenter.unbindView(this)
    }

    override fun setButtonVisibility(isVisible: Boolean) {
//        btn_info.visibility = isVisible.toAndroidVisibility()
    }
}