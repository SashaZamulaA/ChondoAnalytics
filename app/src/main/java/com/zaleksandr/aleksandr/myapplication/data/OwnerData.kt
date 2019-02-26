package com.zaleksandr.aleksandr.myapplication.data

import com.google.gson.annotations.SerializedName

data class OwnerData(
        @SerializedName("userId") val userId: Int,
        @SerializedName("currentUserId") val id: Int
)