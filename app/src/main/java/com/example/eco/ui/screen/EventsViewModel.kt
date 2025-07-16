// File: EventsViewModel.kt

package com.example.eco.ui.screen

import EventRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eco.api.dto.event.EventResponseMediumDTO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class EventsViewModel(private val repository: EventRepository) : ViewModel() {

    private val _events = MutableStateFlow<List<EventResponseMediumDTO>>(emptyList())
    val events: StateFlow<List<EventResponseMediumDTO>> = _events

    init {
        viewModelScope.launch {
            _events.value = repository.getAllEvents()
        }
    }
}