package com.hassan.weatherapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.hassan.weatherapp.models.Favorite
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {
    @Query("SELECT * FROM favorites")
    fun getAllFavorites(): Flow<List<Favorite>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(favorite: Favorite)

    @Query("SELECT * from favorites where city = :city")
    suspend fun getFavoriteById(city: String)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateFavorite()

    @Query("DELETE from favorites")
    suspend fun removeAllFavorites()

    @Delete
    suspend fun removeFavorite(favorite: Favorite)
}