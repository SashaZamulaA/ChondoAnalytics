//package com.example.aleksandr.myapplication.ui.hdh
//
//import android.content.Intent
//import android.support.v7.app.AppCompatActivity
//import android.os.Bundle
//import android.text.TextUtils
//import android.view.View
//import android.widget.Button
//import android.widget.EditText
//import android.widget.ListView
//import android.widget.SeekBar
//import android.widget.TextView
//import android.widget.Toast
//
//import com.example.aleksandr.myapplication.R
//import com.example.aleksandr.myapplication.ui.hdh.model.HDHModel
//import com.example.aleksandr.myapplication.ui.main.MainActivity
//import com.google.firebase.database.DataSnapshot
//import com.google.firebase.database.DatabaseError
//import com.google.firebase.database.DatabaseReference
//import com.google.firebase.database.FirebaseDatabase
//import com.google.firebase.database.ValueEventListener
//
//import java.util.ArrayList
//
//class ArtistActivity : AppCompatActivity() {
//
//    internal var buttonAddTrack: Button
//    internal var editTextTrackName: EditText
//    internal var seekBarRating: SeekBar
//    internal var textViewRating: TextView
//    internal var textViewArtist: TextView
//    internal var listViewTracks: ListView
//
//    internal var databaseTracks: DatabaseReference
//
//    internal var tracks: MutableList<Track>
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_artist)
//
//        val intent = intent
//
//        /*
//         * this line is important
//         * this time we are not getting the reference of a direct node
//         * but inside the node track we are creating a new child with the artist id
//         * and inside that node we will store all the tracks with unique ids
//         * */
//        databaseTracks = FirebaseDatabase.getInstance().getReference("tracks").child(intent.getStringExtra(MainActivity.ARTIST_ID))
//
//        buttonAddTrack = findViewById(R.id.buttonAddTrack) as Button
//        editTextTrackName = findViewById(R.id.editTextName) as EditText
//        seekBarRating = findViewById(R.id.seekBarRating) as SeekBar
//        textViewRating = findViewById(R.id.textViewRating) as TextView
//        textViewArtist = findViewById(R.id.textViewArtist) as TextView
//        listViewTracks = findViewById(R.id.listViewTracks) as ListView
//
//        tracks = ArrayList<HDHModel>()
//
//        textViewArtist.text = intent.getStringExtra(MainActivity.ARTIST_NAME)
//
//        seekBarRating.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
//            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
//                textViewRating.text = i.toString()
//            }
//
//            override fun onStartTrackingTouch(seekBar: SeekBar) {
//
//            }
//
//            override fun onStopTrackingTouch(seekBar: SeekBar) {
//
//            }
//        })
//
//        buttonAddTrack.setOnClickListener { saveTrack() }
//    }
//
//    override fun onStart() {
//        super.onStart()
//
//        databaseTracks.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                tracks.clear()
//                for (postSnapshot in dataSnapshot.children) {
//                    val track = postSnapshot.getValue<Track>(Track::class.java!!)
//                    tracks.add(track)
//                }
//                val trackListAdapter = TrackList(this@ArtistActivity, tracks)
//                listViewTracks.adapter = trackListAdapter
//            }
//
//            override fun onCancelled(databaseError: DatabaseError) {
//
//            }
//        })
//    }
//
//    private fun saveTrack() {
//        val trackName = editTextTrackName.text.toString().trim { it <= ' ' }
//        val rating = seekBarRating.progress
//        if (!TextUtils.isEmpty(trackName)) {
//            val id = databaseTracks.push().key
//            val track = Track(id, trackName, rating)
//            databaseTracks.child(id!!).setValue(track)
//            Toast.makeText(this, "Track saved", Toast.LENGTH_LONG).show()
//            editTextTrackName.setText("")
//        } else {
//            Toast.makeText(this, "Please enter track name", Toast.LENGTH_LONG).show()
//        }
//    }
//}