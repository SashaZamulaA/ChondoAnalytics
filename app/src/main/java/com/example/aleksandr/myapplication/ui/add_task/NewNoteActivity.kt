package com.example.aleksandr.myapplication.ui.add_task

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.v4.view.ViewCompat
import com.example.aleksandr.myapplication.BaseActivity
import com.example.aleksandr.myapplication.R
import com.example.aleksandr.myapplication.getActivity
import kotlinx.android.synthetic.main.activity_new_note.*
import java.util.*


class NewNoteActivity : BaseActivity() {

    @SuppressLint("SetTextI18n")
    override fun init(savedInstanceState: Bundle?) {
        super.setContentView(R.layout.activity_new_note)
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

            val dpd = DatePickerDialog(getActivity(), DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                // Display Selected date in textbox
                total_field.text = "$dayOfMonth. $month. $year"
            }, year, month, day)
            dpd.show()
        }
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
