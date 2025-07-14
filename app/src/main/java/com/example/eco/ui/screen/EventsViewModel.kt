package com.example.eco.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eco.api.dto.event.Event
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class EventsViewModel : ViewModel() {

    private val _events = MutableStateFlow<List<Event>>(emptyList())
    val events: StateFlow<List<Event>> get() = _events

    init {
        loadEvents()
    }

    private fun loadEvents() {
        viewModelScope.launch {
            _events.emit(listOf(
                Event(
                    id = 1,
                    title = "Технологическая конференция",
                    status = "ПРЕДСТОИТ",
                    date = "8 сент.",
                    time = "16:04",
                    location = "Сочи, Олимпийский парк",
                    description = "Выставка медицинского оборудования и технологий."
                ),
                Event(
                    id = 2,
                    title = "Экологический форум",
                    status = "ПРЕДСТОИТ",
                    date = "7 сент.",
                    time = "16:04",
                    location = "Екатеринбург, Выставочный комплекс",
                    description = "Конференция, посвященная вопросам экологии и устойчивого развития."
                ),
                Event(
                    id = 3,
                    title = "Фильм-фестиваль",
                    status = "ПРЕДСТОИТ",
                    date = "7 сент.",
                    time = "16:04",
                    location = "Нижний Новгород, Конференц-зал",
                    description = "Фестиваль, демонстрирующий лучшие фильмы года."
                )
            ))
        }
    }
}