package com.hassan.weatherapp.ui.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hassan.weatherapp.repo.SettingsRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val repo: SettingsRepo) : ViewModel() {
    val unit = repo.tempUnitFlow.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        "C"
    )

    fun toggleTempUnit() {
        viewModelScope.launch { repo.toggleTempUnit() }
    }
}