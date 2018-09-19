package com.example.aleksandr.myapplication.ui.hdh

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.widget.*
import com.example.aleksandr.myapplication.BaseActivity
import com.example.aleksandr.myapplication.R
import com.example.aleksandr.myapplication.ui.hdh.model.HDHModel
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.view_hhw.*

class HDHView : BaseActivity(), IHDHView {


    private lateinit var presenter: HDHPresenter
    lateinit var databaseWord: DatabaseReference
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

//    override fun onStart() {
//        super.onStart()
//        //attaching value event listener
//        databaseWord.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//
//                //clearing the previous artist list
//                wordList.clear()
//
//                //iterating through all the nodes
//                for (postSnapshot in dataSnapshot.children) {
//                    //getting artist
//                    val word = postSnapshot.getValue<HDHModel>(HDHModel::class.java)
//                    //adding artist to the list
//                    if (word != null) {
//                        wordList.add(word)
//                    }
//                }
//            }
//            override fun onCancelled(databaseError: DatabaseError) {
//            }
//        })
//    }


    private fun addArtist() {
        //getting the values to save
        val name = editText_hhw.text.toString().trim()
        val category = add_category.selectedItem.toString()

        //checking if the value is provided
        if (!TextUtils.isEmpty(name)) {

            //getting a unique id using push().getKey() method
            //it will create a unique id and we will use it as the Primary Key for our Artist
            val id = databaseWord.push().key

            //creating an Artist Object
            val wordList = id?.let { HDHModel(it, name, category) }

            //Saving the Artist
            id?.let { databaseWord.child(it).setValue(wordList) }

            //setting edittext to blank again
            editText_hhw.setText("")

            //displaying a success toast
            Toast.makeText(this, "Artist added", Toast.LENGTH_LONG).show()
        } else {
            //if the value is not given displaying a toast
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