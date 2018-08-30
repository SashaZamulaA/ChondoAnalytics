package com.example.aleksandr.myapplication.ui.hdh

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemLongClickListener
import com.example.aleksandr.myapplication.BaseActivity
import com.example.aleksandr.myapplication.R
import com.example.aleksandr.myapplication.ui.hdh.model.HDHModel
import com.google.firebase.database.*
import android.widget.Toast
import com.example.aleksandr.myapplication.hideKeyboard
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DatabaseReference




class HDHView : BaseActivity(), IHDHView {


    private lateinit var presenter: HDHPresenter

    lateinit var editTextName: EditText
    lateinit var buttonSave: Button
    lateinit var spinner: Spinner
    lateinit var databaseWord: DatabaseReference
    lateinit var listViewArtists: ListView
    lateinit var wordList: MutableList<HDHModel>
    private lateinit var dialogBuilder: AlertDialog.Builder
    lateinit var inflater: LayoutInflater
    lateinit var dialogView: View
    private lateinit var textViewName: EditText
    lateinit var buttonUpdate: Button
    private lateinit var alertDialog: AlertDialog


    override fun init(savedInstanceState: Bundle?) {
        super.setContentView(R.layout.view_hhw)
        presenter = HDHPresenter(this, application)
        editTextName = findViewById(R.id.editText_hhw)
        buttonSave = findViewById(R.id.btn_hdh)
        spinner = findViewById(R.id.add_category)
        listViewArtists = findViewById(R.id.listViewWord)


        databaseWord = FirebaseDatabase.getInstance().getReference("word")

        listViewArtists.onItemLongClickListener = OnItemLongClickListener { _, _, i, _ ->

            val word = wordList[i]
            showUpdateDialog(word.id, word.name)
            true
        }

        wordList = ArrayList()
        buttonSave.setOnClickListener {
            addArtist()
        }
    }

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
            }

            override fun onCancelled(databaseError: DatabaseError) {
            }
        })
    }

    @SuppressLint("InflateParams")
    private fun showUpdateDialog(wordId: String, word: String) {
        dialogBuilder = AlertDialog.Builder(this)
        inflater = layoutInflater
        dialogView = inflater.inflate(R.layout.update_dialog, null)
        dialogBuilder.setView(dialogView)

        textViewName = dialogView.findViewById(R.id.editTextName)
        buttonUpdate = dialogView.findViewById(R.id.buttonUpdate)
        spinner = dialogView.findViewById(R.id.spinner_categories_dialog)

        dialogBuilder.setTitle("Update Word- $word")
        alertDialog = dialogBuilder.create()
        alertDialog.show()

        buttonUpdate.setOnClickListener {
            val name: String = textViewName.text.toString().trim()
            val category: String = spinner.selectedItem.toString()

            if (TextUtils.isEmpty(name)) {
                editTextName.setError("Name required")
            }
            updateArtist(wordId, name, category)
            alertDialog.dismiss()
            hideKeyboard()

        }
    }

    private fun updateArtist(id: String, name: String, category: String): Boolean {
        val dR = FirebaseDatabase.getInstance().getReference("word").child(id)
        val word = HDHModel(id, name, category)
        dR.setValue(word)
        Toast.makeText(this, "Word Update Successfully", Toast.LENGTH_LONG).show()
        return true
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