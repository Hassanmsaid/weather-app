package com.hassan.weatherapp.repo

import android.util.Log
import com.hassan.weatherapp.data.DataOrException
import com.hassan.weatherapp.data.WeatherDao
import com.hassan.weatherapp.models.Favorite
import com.hassan.weatherapp.models.WeatherResponse
import com.hassan.weatherapp.network.WeatherApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WeatherRepo @Inject constructor(private val api: WeatherApi, val weatherDao: WeatherDao) {
    suspend fun getWeather(cityName: String): DataOrException<WeatherResponse, Boolean, Exception> {
        val response = try {
            api.getWeather(query = cityName)
        } catch (e: Exception) {
            Log.e("Weather repo:", "getWeather: ", e)
            return DataOrException(e = e)
        }
        Log.i("Weather repo:", "getWeather: ${response.city}")
        return DataOrException(data = response)
    }

    fun getAllFavorites(): Flow<List<Favorite>> = weatherDao.getAllFavorites()

    suspend fun addFavorite(favorite: Favorite) = weatherDao.addFavorite(favorite)

    suspend fun getFavoriteById(city: String) = weatherDao.getFavoriteById(city)

    suspend fun updateFavorite(favorite: Favorite) = weatherDao.updateFavorite(favorite)

    suspend fun removeAllFavorites() = weatherDao.removeAllFavorites()

    suspend fun removeFavorite(favorite: Favorite) = weatherDao.removeFavorite(favorite)
}