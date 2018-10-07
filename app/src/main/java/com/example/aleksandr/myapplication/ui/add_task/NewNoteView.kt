package com.example.aleksandr.myapplication.ui.add_task

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.PendingIntent.getActivity
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.v4.view.ViewCompat
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.example.aleksandr.myapplication.BaseActivity
import com.example.aleksandr.myapplication.R
import com.example.aleksandr.myapplication.R.id.*
import com.example.aleksandr.myapplication.getActivity
import com.example.aleksandr.myapplication.toAndroidVisibility
import kotlinx.android.synthetic.main.activity_new_note.*
import java.util.*


class NewNoteView : BaseActivity() , INewNote{

    override fun setVisibility(isVisible: Boolean) {
        container_quantity.visibility = isVisible.toAndroidVisibility()

    }

    private lateinit var presenter: NewNotePresenter

    @SuppressLint("SetTextI18n")
    override fun init(savedInstanceState: Bundle?) {
        super.setContentView(R.layout.activity_new_note)
        presenter = NewNotePresenter(this, application)
//        val EXTRA_NAME = "cheese_name"
//        val intent = intent
//        val cheeseName = intent.getStringExtra(EXTRA_NAME)


        appbar.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
            var isShow = true
            var scrollRange = -1

            override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
                ViewCompat.setElevation(appBarLayout, 10F)
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.totalScrollRange
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsing_toolbar.title = "Title"
                    isShow = true
                } else if (isShow) {
                    collapsing_toolbar.title = " "//carefull there should a space between double quote otherwise it wont work
                    isShow = false
                }

            }
        })

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        button_charge_start!!.setOnClickListener {

            val startTime = DatePickerDialog(getActivity(), DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

                total_field.text = "$dayOfMonth. $month. $year"
            }, year, month, day)
            startTime.show()
        }

        button_charge_end.setOnClickListener {

            val endTime = DatePickerDialog(getActivity(), DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

                total_field.text = "$dayOfMonth. $month. $year"
            }, year, month, day)
            endTime.show()
        }

        val spinner: Spinner = findViewById(R.id.spinner)
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
                this,
                R.array.time_period,
                android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter

            spinner.onItemSelectedListener = object : OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View,
                                            position: Int, id: Long) {
                    if (position == 1) {
                        presenter.updateInStockVisibility()
                    }

                }

                override fun onNothingSelected(arg0: AdapterView<*>) {}
            }

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
}

//        collapsing_toolbar.title = cheeseName
//
//        button_add_client?.setOnClickListener {  saveNote()}


//    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
//        return when (item?.itemId) {
//            R.id.save_note -> {
//                saveNote()
//                true
//            }
//            else -> super.onOptionsItemSelected(item)
//        }
//    }

//    private fun saveNote() {
//        val title = edit_text_title.text.toString()
//        val achiv = edit_text_achievement.text.toString()
//        val goal = edit_text_goal.text.toString()
//
//        if (title.trim { it <= ' ' }.isEmpty() || achiv.trim { it <= ' ' }.isEmpty()) {
//            Toast.makeText(this, "Please insert a title and description", Toast.LENGTH_SHORT).show()
//            return
//        }
//
//        val notebookRef = FirebaseFirestore.getInstance()
//                .collection("Notebook")
//        notebookRef.add(Note(title, achiv.toDouble(), goal.toDouble()))
//        Toast.makeText(this, "Note added", Toast.LENGTH_SHORT).show()
//        finish()
//    }
