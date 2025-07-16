package com.example.eco.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.eco.R
import com.example.eco.api.ApiClient

import com.example.eco.api.dto.registration.UserRegistrationRequestDto
import com.example.eco.api.dto.registration.UserRegistrationResponseDto
import com.example.eco.ui.screen.LoginActivity


import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VolunteerRegistrationFragment : Fragment() {

    private lateinit var etFullName: EditText
    private lateinit var etLogin: EditText
    private lateinit var etPassword: EditText
    private lateinit var etPhone: EditText
    private lateinit var etEmail: EditText
    private lateinit var btnRegister: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.volunteer_registration_fragment, container, false)

        etFullName = view.findViewById(R.id.et_volunteer_full_name)
        etLogin = view.findViewById(R.id.et_volunteer_login)
        etPassword = view.findViewById(R.id.et_volunteer_password)
        etPhone = view.findViewById(R.id.et_volunteer_phone)
        etEmail = view.findViewById(R.id.et_volunteer_email)
        btnRegister = view.findViewById(R.id.btn_volunteer_register)

        btnRegister.setOnClickListener {
            registerVolunteer()
        }

        return view
    }

    private fun registerVolunteer() {
        val fullName = etFullName.text.toString().trim()
        val login = etLogin.text.toString().trim()
        val password = etPassword.text.toString().trim()
        val phone = etPhone.text.toString().trim()
        val email = etEmail.text.toString().trim()

        if (fullName.isEmpty() || login.isEmpty() || password.isEmpty()) {
            Toast.makeText(context, "Пожалуйста, заполните обязательные поля", Toast.LENGTH_SHORT).show()
            return
        }

        val request = UserRegistrationRequestDto(fullName, login, password, "USER", phone, email)

        ApiClient.registrationService.registerUser(request).enqueue(object : Callback<UserRegistrationResponseDto> {
            override fun onResponse(call: Call<UserRegistrationResponseDto>, response: Response<UserRegistrationResponseDto>) {
                if (response.isSuccessful) {
                    Toast.makeText(context, "Регистрация успешна!", Toast.LENGTH_SHORT).show()
                    activity?.startActivity(Intent(context, LoginActivity::class.java))
                    activity?.finish()
                } else {
                    Toast.makeText(context, "Ошибка сервера: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<UserRegistrationResponseDto>, t: Throwable) {
                Toast.makeText(context, "Ошибка подключения: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }
}