package com.zaleksandr.aleksandr.myapplication.util

import com.zaleksandr.aleksandr.myapplication.ui.settings.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.zaleksandr.aleksandr.myapplication.MainActivity.Companion.AUTHOR_KEY
import com.zaleksandr.aleksandr.myapplication.MainActivity.Companion.QUOTE_KEY
import com.zaleksandr.aleksandr.myapplication.model.Guest

object FirestoreUtil {
    val firestoreInstance: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }
    val currentUserDocRef: DocumentReference
        get() = firestoreInstance.document("users/${FirebaseAuth.getInstance().uid
                ?: throw NullPointerException("UID is null.")}")

    private var user: FirebaseUser? = null
    fun initCurrentUserIfFirstTime(onComplete: () -> Unit) {
        currentUserDocRef.get().addOnSuccessListener { documentSnapshot ->
            if (!documentSnapshot.exists()) {
                val newUser = User(FirebaseAuth.getInstance().currentUser?.displayName
                        ?: "", "", "", null, mutableListOf())
                currentUserDocRef.set(newUser).addOnSuccessListener {
                    onComplete()
                }
            } else
                onComplete()
        }
    }

    fun getFCMRegistrationToken(onComplete: (tokens: MutableList<String>) -> Unit) {
        currentUserDocRef.get().addOnSuccessListener {
            val user = it.toObject(User::class.java)!!
            onComplete(user.registrationTokens)
        }
    }

    fun setFCMRegistrationToken(registrationTokens: MutableList<String>) {
        currentUserDocRef.update(mapOf("registrationTokens" to registrationTokens))
    }

    fun updateCurrentUser(name: String = "", email: String = "", profilePicturePath: String? = null) {

        val userFieldMap = mutableMapOf<String, Any>()
        if (name.isNotBlank()) userFieldMap[AUTHOR_KEY] = name
        if (email.isNotBlank()) userFieldMap[QUOTE_KEY] = email
        if (profilePicturePath != null)
            userFieldMap["profilePicturePath"] = profilePicturePath
        currentUserDocRef.update(userFieldMap)

    }

    fun updateCurrentUserDuringRegistration(name: String = "", email: String = "") {
        val userFieldMap = mutableMapOf<String, Any>()
        if (name.isNotBlank()) userFieldMap["name"] = name
        if (email.isNotBlank()) userFieldMap["email"] = email
        currentUserDocRef.update(userFieldMap)
    }

    fun getCurrentUser(onComplete: (User) -> Unit) {
        currentUserDocRef.get()
                .addOnSuccessListener {
                    onComplete(it.toObject(User::class.java)!!)
                }
    }

    fun getCurrentUserGuest(onComplete: (Guest) -> Unit) {
        val notesUpdateGuestRef = firestoreInstance.collection("Guest").document()
        notesUpdateGuestRef.get()
                .addOnSuccessListener {
                    onComplete(it.toObject(Guest::class.java)!!)
                }
    }
}

