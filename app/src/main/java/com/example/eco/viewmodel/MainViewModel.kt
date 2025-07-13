package com.example.eco.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eco.api.ApiClient
import com.example.eco.model.EventFilterDTO
import com.example.eco.model.EventResponseMediumDTO
import com.example.eco.model.UserRegistrationResponseDto
import com.example.eco.repository.AppRepository
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val repository = AppRepository(ApiClient.apiService)

    private val _eventList = MutableLiveData<List<EventResponseMediumDTO>>()
    val eventList: LiveData<List<EventResponseMediumDTO>> = _eventList

    private val _userList = MutableLiveData<List<UserRegistrationResponseDto>>()
    val userList: LiveData<List<UserRegistrationResponseDto>> = _userList

    fun fetchEvents(filter: EventFilterDTO) = viewModelScope.launch {
        try {
            _eventList.postValue(repository.searchEvents(filter))
        } catch (e: Exception) {
            // Обработка ошибки
        }
    }

    fun fetchUsers() = viewModelScope.launch {
        try {
            _userList.postValue(repository.getAllUsers())
        } catch (e: Exception) {
            // Обработка ошибки
        }
    }

    // И другие методы...
}