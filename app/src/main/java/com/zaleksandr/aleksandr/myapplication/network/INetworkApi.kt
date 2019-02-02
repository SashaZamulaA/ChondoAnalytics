package com.zaleksandr.aleksandr.myapplication.network

import android.database.Observable
import com.zaleksandr.aleksandr.myapplication.data.OwnerData
import retrofit2.http.GET

interface INetworkApi {
    @GET(Endpoints.owner)
fun getAllOwner(): Observable<List<OwnerData>>
}