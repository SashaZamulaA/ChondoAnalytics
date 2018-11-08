package com.example.aleksandr.myapplication.util

import com.example.aleksandr.myapplication.ui.login.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

object FirebaseUtil {
    private val firestoreInstance: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }
    private var mDatabase: DatabaseReference? = null
    private val currentUserDocGef: DocumentReference

    get() = firestoreInstance.document("users/${FirebaseAuth.getInstance().uid
    ?: throw NullPointerException("UID is null.")}")
    private var user: FirebaseUser? = null
    fun initCurrentUserIfFirstTime(onComplete: () -> Unit){
        currentUserDocGef.get().addOnSuccessListener { documentSnapshot ->
            if (!documentSnapshot.exists()){
                val newUser = User(FirebaseAuth.getInstance().currentUser?.displayName?: "", "")
                currentUserDocGef.set(newUser).addOnSuccessListener {
                    onComplete()
                }
            }
            else
                onComplete()
        }
    }

    fun updateCurrentUser(name: String ="", email: String = ""){
        mDatabase = FirebaseDatabase.getInstance().reference

        val userFieldMap = mutableMapOf<String, Any>()
        if (name.isNotBlank()) userFieldMap["name"] = name
        if (email.isNotBlank()) userFieldMap["email"] = email
        mDatabase!!.updateChildren(userFieldMap)
     }
    fun getCurrentUser(onComplete: (User) -> Unit){
        currentUserDocGef.get()
                .addOnSuccessListener {
                    onComplete(it.toObject(User::class.java)!!)
                }
    }
}