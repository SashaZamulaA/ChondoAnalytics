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


class RecyclerActivity : BaseActivity() {

    private val db = FirebaseFirestore.getInstance()
    private val notebookRef = db.collection("Notebook")
    private lateinit var adapter: NoteAdapter

    override fun init(savedInstanceState: Bundle?) {
        setContentView(R.layout.view_goal_list)

        setUpRecyclerView()


        fab_add.setOnClickListener {
            startActivity(Intent(this@RecyclerActivity, NewNoteView::class.java)) }
           }

    private fun setUpRecyclerView() {
        val query = notebookRef.orderBy("goal", Query.Direction.DESCENDING)

        val options = FirestoreRecyclerOptions.Builder<Note>()
                .setQuery(query, Note::class.java)
                .build()

        adapter = NoteAdapter(options)
        recycler_view.setHasFixedSize(true)
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.adapter = adapter
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