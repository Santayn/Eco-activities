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

class OrganizationRegistrationFragment : Fragment() {

    private lateinit var etOrgName: EditText
    private lateinit var etOrgLogin: EditText
    private lateinit var etOrgPassword: EditText
    private lateinit var etOrgPhone: EditText
    private lateinit var etOrgEmail: EditText
    private lateinit var btnOrgRegister: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.organization_registration_fragment, container, false)

        etOrgName = view.findViewById(R.id.et_organization_name)
        etOrgLogin = view.findViewById(R.id.et_organization_login)
        etOrgPassword = view.findViewById(R.id.et_organization_password)
        etOrgPhone = view.findViewById(R.id.et_organization_phone)
        etOrgEmail = view.findViewById(R.id.et_organization_email)
        btnOrgRegister = view.findViewById(R.id.btn_organization_register)

        btnOrgRegister.setOnClickListener {
            registerOrganization()
        }

        return view
    }

    private fun registerOrganization() {
        val fullName = etOrgName.text.toString().trim()
        val login = etOrgLogin.text.toString().trim()
        val password = etOrgPassword.text.toString().trim()
        val phone = etOrgPhone.text.toString().trim()
        val email = etOrgEmail.text.toString().trim()

        if (fullName.isEmpty() || login.isEmpty() || password.isEmpty()) {
            Toast.makeText(context, "Пожалуйста, заполните обязательные поля", Toast.LENGTH_SHORT).show()
            return
        }

        val request = UserRegistrationRequestDto(fullName, login, password, "ORGANIZATION", phone, email)

        ApiClient.registrationService.registerUser(request).enqueue(object : Callback<UserRegistrationResponseDto> {
            override fun onResponse(call: Call<UserRegistrationResponseDto>, response: Response<UserRegistrationResponseDto>) {
                if (response.isSuccessful) {
                    Toast.makeText(context, "Организация успешно зарегистрирована!", Toast.LENGTH_SHORT).show()
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