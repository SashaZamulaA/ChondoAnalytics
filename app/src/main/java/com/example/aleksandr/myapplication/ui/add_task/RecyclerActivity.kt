package com.example.aleksandr.myapplication.ui.add_task

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.example.aleksandr.myapplication.BaseActivity
import com.example.aleksandr.myapplication.R
import com.example.aleksandr.myapplication.ui.add_task.note.adapter.NoteAdapter
import com.example.aleksandr.myapplication.ui.add_task.note.model.Note
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.view_goal_list.*
import android.content.Intent
import android.view.View
import android.widget.Toast
import com.google.firebase.database.DatabaseError
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference



class RecyclerActivity : BaseActivity() {

    private val db = FirebaseFirestore.getInstance()
    private val notebookRef = db.collection("Notebook")
    private lateinit var adapter: NoteAdapter
    private var mStorageRef: StorageReference? = null
    override fun init(savedInstanceState: Bundle?) {
        setContentView(R.layout.view_goal_list)
        setUpRecyclerView()

        fab_add.setOnClickListener {
            startActivity(Intent(this@RecyclerActivity, NewNoteView::class.java)) }
           }

    private fun setUpRecyclerView() {



        val query = notebookRef.orderBy("goal", Query.Direction.ASCENDING)

        val options = FirestoreRecyclerOptions.Builder<Note>()
                .setQuery(query, Note::class.java)
                .build()



        adapter = NoteAdapter(options)
        recycler_view.setHasFixedSize(true)
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.adapter = adapter
        progress_circle.visibility = View.INVISIBLE
    }

    fun onCancelled(databaseError: DatabaseError) {
        Toast.makeText(this@RecyclerActivity, databaseError.message, Toast.LENGTH_SHORT).show()
        progress_circle.visibility = View.INVISIBLE
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }
}