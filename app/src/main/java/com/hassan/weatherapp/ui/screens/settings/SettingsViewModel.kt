package com.hassan.weatherapp.ui.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hassan.weatherapp.repo.MeasureUnit
import com.hassan.weatherapp.repo.SettingsRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val repo: SettingsRepo) : ViewModel() {
    val unit = repo.measureUnitFlow.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), MeasureUnit.Metric.name
    )

    fun toggleMeasureUnit() {
        viewModelScope.launch { repo.toggleMeasureUnit() }
    }

    val isImperial = unit
        .map { it == MeasureUnit.Imperial.name }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)
}