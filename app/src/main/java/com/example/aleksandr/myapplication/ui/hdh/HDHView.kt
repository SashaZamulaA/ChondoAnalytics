package com.example.aleksandr.myapplication.ui.hdh

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.widget.LinearLayout
import android.widget.Toast
import com.example.aleksandr.myapplication.BaseActivity
import com.example.aleksandr.myapplication.R
import com.example.aleksandr.myapplication.ui.hdh.model.HDHModel
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.view_hhw.*

class HDHView : BaseActivity(), IHDHView {

    private lateinit var presenter: HDHPresenter
    lateinit var databaseWord: DatabaseReference
    lateinit var database: FirebaseDatabase
    var wordList: ArrayList<HDHModel> = ArrayList()
    lateinit var adapter: WordAdapter

    override fun init(savedInstanceState: Bundle?) {
        super.setContentView(R.layout.view_hhw)
        presenter = HDHPresenter(this, application)

        databaseWord = FirebaseDatabase.getInstance().getReference("word")

        listViewWord.setHasFixedSize(true)
        listViewWord.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)

        adapter = WordAdapter(wordList, this)
        listViewWord.adapter = adapter

        btn_hdh.setOnClickListener {
            addArtist()
        }
    }

    override fun onStart() {
        super.onStart()
        databaseWord.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                wordList.clear()

                for (postSnapshot in dataSnapshot.children) {
                    val word = postSnapshot.getValue<HDHModel>(HDHModel::class.java)
                    if (word != null) {
                        wordList.add(word)
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
            }
        })
    }

    fun updateList(){
        databaseWord.addChildEventListener(object : ChildEventListener{
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
            }

            override fun onChildRemoved(p0: DataSnapshot) {
            }
        })
    }

    private fun addArtist() {

        val name = editText_hhw.text.toString().trim()
        val category = add_category.selectedItem.toString()

        if (!TextUtils.isEmpty(name)) {

            val id = databaseWord.push().key
            val wordList = id?.let { HDHModel(it, name, category) }
            id?.let { databaseWord.child(it).setValue(wordList) }

            editText_hhw.setText("")

            Toast.makeText(this, "Artist added", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "Please enter a name", Toast.LENGTH_LONG).show()
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

    override fun setButtonVisibility(isVisible: Boolean) {
//        btn_info.visibility = isVisible.toAndroidVisibility()
    }
}