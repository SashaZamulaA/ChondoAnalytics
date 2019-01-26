package com.zamulaaleksandr.aleksandr.myapplication.network

import android.database.Observable
import com.zamulaaleksandr.aleksandr.myapplication.data.OwnerData
import retrofit2.http.GET

interface INetworkApi {
    @GET(Endpoints.owner)
fun getAllOwner(): Observable<List<OwnerData>>
}