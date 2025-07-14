package com.example.eco.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.eco.ui.fragments.OrganizationRegistrationFragment
import com.example.eco.ui.fragments.VolunteerRegistrationFragment

class RegistrationPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> VolunteerRegistrationFragment()
            1 -> OrganizationRegistrationFragment()
            else -> VolunteerRegistrationFragment()
        }
    }
}