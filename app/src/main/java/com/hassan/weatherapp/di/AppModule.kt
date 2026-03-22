package com.hassan.weatherapp.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.room.Room
import com.hassan.weatherapp.data.WeatherDao
import com.hassan.weatherapp.data.WeatherDatabase
import com.hassan.weatherapp.network.WeatherApi
import com.hassan.weatherapp.repo.dataStore
import com.hassan.weatherapp.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideWeatherDao(weatherDb: WeatherDatabase): WeatherDao = weatherDb.provideWeatherDao()

    @Singleton
    @Provides
    fun provideWeatherDb(@ApplicationContext context: Context): WeatherDatabase =
        Room.databaseBuilder(
            context = context,
            klass = WeatherDatabase::class.java,
            name = "weather_db"
        ).fallbackToDestructiveMigration(dropAllTables = true).build()

    @Provides
    @Singleton
    fun provideWeatherApi(): WeatherApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApi::class.java)
    }


    @Provides
    @Singleton
    fun provideDataStore(
        @ApplicationContext context: Context
    ): DataStore<Preferences> = context.dataStore
}