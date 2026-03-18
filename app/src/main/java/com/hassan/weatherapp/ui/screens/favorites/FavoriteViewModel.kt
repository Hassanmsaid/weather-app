package com.hassan.weatherapp.ui.screens.favorites

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hassan.weatherapp.models.Favorite
import com.hassan.weatherapp.repo.WeatherRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(private val repo: WeatherRepo) : ViewModel() {
    private val _favorites = MutableStateFlow<List<Favorite>>(emptyList())
    val favorites = _favorites.asStateFlow()
    val toastMessage = MutableStateFlow("")

    init {
        viewModelScope.launch {
            repo.getAllFavorites().distinctUntilChanged()
                .collect { favs ->
                    if (favs.isEmpty()) {
                        Log.d("", "favs is empty")
                    } else {
                        _favorites.value = favs
                        Log.d("Favs:", _favorites.value.toString())
                    }
                }
        }
    }

    fun isFavorite(city: String): Flow<Boolean> {
        return favorites.map { favs -> favs.any { it.city == city } }
    }

    fun toggleFavorite(favorite: Favorite) =
        viewModelScope.launch {
            if (_favorites.value.contains(favorite)) {
                repo.removeFavorite(favorite)
                toastMessage.value = "Removed from favorites"
            } else {
                repo.addFavorite(favorite)
                toastMessage.value = "Added to favorites"
            }
        }

    fun getFavoriteById(city: String) =
        viewModelScope.launch { repo.getFavoriteById(city) }

    fun updateFavorite(favorite: Favorite) =
        viewModelScope.launch { repo.updateFavorite(favorite) }

    fun removeAllFavorites() =
        viewModelScope.launch { repo.removeAllFavorites() }

    fun clearToast() {
        toastMessage.value = ""
    }
}