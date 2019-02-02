package com.zaleksandr.aleksandr.myapplication.util


import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.lang.NullPointerException
import java.util.*
import java.nio.ByteBuffer


object StorageUtil {
    private val storageInstance: FirebaseStorage by lazy { FirebaseStorage.getInstance() }
    var aString = "JUST_A_TEST_STRING"
    val currentUserRef: StorageReference
    get() = storageInstance.reference
            .child(FirebaseAuth.getInstance().currentUser?.uid
                    ?: throw NullPointerException("UID is null."))

    object UuidUtils {
        fun asUuid(bytes: ByteArray): UUID {
            val bb = ByteBuffer.wrap(bytes)
            val firstLong = bb.getLong()
            val secondLong = bb.getLong()
            return UUID(firstLong, secondLong)
        }

        fun asBytes(uuid: UUID): ByteArray {
            val bb = ByteBuffer.wrap(ByteArray(16))
            bb.putLong(uuid.mostSignificantBits)
            bb.putLong(uuid.leastSignificantBits)
            return bb.array()
        }
    }
    fun uploadProfilePhoto(imageBytes: ByteArray,
                           onSuccess: (imagePath: String) -> Unit){
        val ref = currentUserRef.child("profilePictures/${UUID.nameUUIDFromBytes(imageBytes)}")
        ref.putBytes(imageBytes)
                .addOnSuccessListener {
                    onSuccess(ref.path)
                }
    }

    fun uploadMessageImage(imageBytes: ByteArray,
                           onSuccess: (imagePath: String) -> Unit){
        val ref = currentUserRef.child("messages/${UUID.nameUUIDFromBytes(imageBytes)}")
        ref.putBytes(imageBytes)
                .addOnSuccessListener {
                    onSuccess(ref.path)
                }
    }
fun pathToReference(path: String) = storageInstance.getReference(path)
}