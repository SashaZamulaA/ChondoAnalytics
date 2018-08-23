package com.example.aleksandr.myapplication.ui.hdh

import android.os.Bundle
import android.widget.*
import com.example.aleksandr.myapplication.BaseActivity
import com.example.aleksandr.myapplication.R
import com.google.firebase.database.*
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DataSnapshot
import com.example.aleksandr.myapplication.ui.hdh.model.HDH
import com.google.firebase.database.ValueEventListener



class HDHView : BaseActivity(), IHDHView {



    private lateinit var presenter: HDHPresenter

    var editTextName: EditText? = null
    var buttonSave: Button? = null
    var spinner: Spinner? = null
    var databaseWord : DatabaseReference? = null
    var listViewHDH :ListView? = null
    var wordList: ArrayList<HDH>? = null


    override fun init(savedInstanceState: Bundle?) {
        super.setContentView(R.layout.view_hhw)
        presenter = HDHPresenter(this, application)
        editTextName = findViewById(R.id.editText_hhw)
        buttonSave = findViewById(R.id.btn_hdh)
        spinner = findViewById(R.id.add_category)
        listViewHDH = findViewById(R.id.listViewWord)
        buttonSave?.setOnClickListener {
            save()
        }
        wordList = ArrayList<HDH>()
    }

    private fun save() {
        val name = editTextName?.text.toString().trim()
        val category = spinner?.selectedItem.toString()
        if (name.isEmpty()) {
            editTextName?.error = "Please enter a name"
            return
        }

        val ref = FirebaseDatabase.getInstance().getReference("word")

        val wordId = ref.push().key
        val word = HDH(wordId!!, category, name)
        ref.child(wordId).setValue(word).addOnCanceledListener {
            Toast.makeText(applicationContext, "Successfully", Toast.LENGTH_LONG).show()
        }
    }

    override fun onStart() {
        super.onStart()
        //attaching value event listener
        databaseWord?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                //clearing the previous artist list
                wordList?.clear()


                //iterating through all the nodes
                for (postSnapshot in dataSnapshot.children) {
                    //getting artist
                    val artist = postSnapshot.getValue<HDH>(HDH::class.java)
                    //adding artist to the list
                    if (artist != null) {
                        wordList?.add(artist)
                    }
                }

                //creating adapter
                val artistAdapter = wordList?.let { WordList(this@HDHView, it) }
                //attaching adapter to the listview
                listViewHDH?.adapter = artistAdapter
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })
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