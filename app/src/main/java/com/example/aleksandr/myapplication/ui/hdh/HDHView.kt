package com.example.aleksandr.myapplication.ui.hdh

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import com.example.aleksandr.myapplication.BaseActivity
import com.example.aleksandr.myapplication.R
import com.google.firebase.database.FirebaseDatabase

class HDHView : BaseActivity(), IHDHView {

    private lateinit var presenter: HDHPresenter

    var editTextName: EditText? = null
    var buttonSave: Button? = null
    var spinner: Spinner? = null

    override fun init(savedInstanceState: Bundle?) {
        super.setContentView(R.layout.view_hhw)
        presenter = HDHPresenter(this, application)
        editTextName = findViewById(R.id.editText_hhw)
        buttonSave = findViewById(R.id.btn_hdh)
        spinner = findViewById(R.id.add_category)
        buttonSave?.setOnClickListener {
            save()
        }
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