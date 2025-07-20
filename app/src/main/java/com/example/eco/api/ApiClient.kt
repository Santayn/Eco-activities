// File: ApiClient.kt

package com.example.eco.api

import android.content.Context
import ApiService
import BonusApi
import com.example.eco.AuthInterceptor
import com.example.eco.api.service.AuthService
import com.example.eco.api.service.RegistrationService
import com.example.eco.api.service.UserService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {

    // 🔗 Базовый URL сервера
    private const val BASE_URL = "http://10.0.2.2:8080" // для эмулятора Android

    private var appContext: Context? = null

    fun init(context: Context) {
        appContext = context.applicationContext
    }

    // 🧪 Логгер для отладки HTTP-запросов
    private val loggingInterceptor by lazy {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    // 🔐 Интерсептор для добавления токена
    private val authInterceptor by lazy {
        AuthInterceptor(appContext ?: throw IllegalStateException("Context не инициализирован"))
    }

    // ⚙️ Настроенный OkHttpClient с таймаутами и логированием
    private val okHttpClient by lazy {
        OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authInterceptor)
            .build()
    }

    // 🛠 Фабрика для создания Retrofit сервисов
    private fun <T> createService(serviceClass: Class<T>): T {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(serviceClass)
    }

    // 🧾 API-сервисы
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
    val bonusApi: BonusApi by lazy {
        createService(BonusApi::class.java)
    }

}