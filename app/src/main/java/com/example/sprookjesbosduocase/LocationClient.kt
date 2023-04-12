package com.example.sprookjesbosduocase

import android.location.Location

interface LocationClient {
    fun getLocationUpdates(interval: Long): kotlinx.coroutines.flow.Flow<Location>

    class LocationException(message: String): Exception()
}