package com.hassan.weatherapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class Favorite(
    @PrimaryKey
    val city: String,
    val country: String
)
