package com.example.eco.api

import com.example.eco.api.dto.service.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {
    // Базовый URL сервера (для эмулятора)
    private const val BASE_URL = "http://10.0.2.2:8080"

    // Логгер для отладки запросов
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    // Клиент OkHttp с логированием
    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .addInterceptor(loggingInterceptor)
        .build()

    // Retrofit клиенты
    val authService: AuthService by lazy {
        createService(AuthService::class.java)
    }

    val registrationService: RegistrationService by lazy {
        createService(RegistrationService::class.java)
    }

    val userService: UserService by lazy {
        createService(UserService::class.java)
    }

    val eventService: ApiService by lazy {
        createService(ApiService::class.java)
    }

    // Вспомогательный метод для упрощения создания сервисов
    private fun <T> createService(serviceClass: Class<T>): T {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(serviceClass)
    }
}