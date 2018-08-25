package com.example.aleksandr.myapplication.ui.hdh

import android.os.Bundle
import android.widget.*
import com.example.aleksandr.myapplication.BaseActivity
import com.example.aleksandr.myapplication.R
import com.google.firebase.database.*
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DataSnapshot
import com.example.aleksandr.myapplication.ui.hdh.model.HDHModel
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.view_hhw.*
import android.widget.Toast
import android.text.TextUtils
import com.example.aleksandr.myapplication.R.id.listViewArtists
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DatabaseReference
import java.util.ArrayList


class HDHView : BaseActivity(), IHDHView {


    private lateinit var presenter: HDHPresenter

    lateinit var editTextName: EditText
    lateinit var buttonSave: Button
    lateinit var spinner: Spinner
    lateinit var databaseWord : DatabaseReference
    lateinit var listViewArtists: ListView
    lateinit var wordList: MutableList<HDHModel>

//    lateinit var wordList: ArrayList<HDHModel>
    lateinit var adapter: WordAdapter

    override fun init(savedInstanceState: Bundle?) {
        super.setContentView(R.layout.view_hhw)
        presenter = HDHPresenter(this, application)
        editTextName = findViewById(R.id.editText_hhw)
        buttonSave = findViewById(R.id.btn_hdh)
        spinner = findViewById(R.id.add_category)
        listViewArtists = findViewById(R.id.listViewArtists)

        databaseWord = FirebaseDatabase.getInstance().getReference("word")

        wordList = ArrayList()
        buttonSave.setOnClickListener {
            addArtist()
        }
         }

    private fun save() {
        val name = editTextName.text.toString().trim()
        val category = spinner.selectedItem.toString()
//        if (name.isEmpty()) {
//            editTextName?.error = "Please enter a name"
//            return
//        }

        if (!TextUtils.isEmpty(name)) {

            //getting a unique id using push().getKey() method
            //it will create a unique id and we will use it as the Primary Key for our Artist
            val id = databaseWord.push().key

            //creating an Artist Object
            val word = HDHModel(id!!, name, category)

            //Saving the Artist
            databaseWord.child(id).setValue(word)

            //setting edittext to blank again
            editTextName.setText("")

            //displaying a success toast
            Toast.makeText(this, "Artist added", Toast.LENGTH_LONG).show()
        } else {
            //if the value is not given displaying a toast
            Toast.makeText(this, "Please enter a name", Toast.LENGTH_LONG).show()
        }

//        val ref = FirebaseDatabase.getInstance().getReference("word")
//
//        val wordId = ref.push().key
//        val word = HDHModel(wordId!!, category, name)
//        ref.child(wordId).setValue(word).addOnCanceledListener {
//            Toast.makeText(applicationContext, "Successfully", Toast.LENGTH_LONG).show()
//        }
    }

//

    override fun onStart() {
        super.onStart()
        //attaching value event listener
        databaseWord.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                //clearing the previous artist list
                wordList.clear()

                //iterating through all the nodes
                for (postSnapshot in dataSnapshot.children) {
                    //getting artist
                    val word = postSnapshot.getValue<HDHModel>(HDHModel::class.java)
                    //adding artist to the list
                    if (word != null) {
                        wordList.add(word)
                    }
                }

                //creating adapter
                val artistAdapter = ArtistList(this@HDHView, wordList)
                //attaching adapter to the listview
                listViewArtists.adapter = artistAdapter

                //attaching adapter to the listview

            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })
    }

    private fun addArtist() {
        //getting the values to save
        val name = editTextName.text.toString().trim()
        val category = spinner.selectedItem.toString()

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
            editTextName.setText("")

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