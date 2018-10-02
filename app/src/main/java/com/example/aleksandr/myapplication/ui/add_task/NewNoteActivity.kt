package com.example.aleksandr.myapplication.ui.add_task

import android.os.Bundle
import android.view.MenuItem
import com.example.aleksandr.myapplication.BaseActivity
import com.example.aleksandr.myapplication.R
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.aleksandr.myapplication.ui.add_task.note.model.Note
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_new_note.*
import kotlinx.android.synthetic.main.activity_new_note_copy.*
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CollapsingToolbarLayout
import android.support.v4.view.ViewCompat


class NewNoteActivity : BaseActivity() {

    override fun init(savedInstanceState: Bundle?) {
        super.setContentView(R.layout.activity_new_note)
//        val EXTRA_NAME = "cheese_name"
//        val intent = intent
//        val cheeseName = intent.getStringExtra(EXTRA_NAME)



        appbar.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
            var isShow = true
            var scrollRange = -1

            override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
                ViewCompat.setElevation(appBarLayout, 10F);
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
//        collapsing_toolbar.title = cheeseName
//
//        button_add_client?.setOnClickListener {  saveNote()}
    }



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
}