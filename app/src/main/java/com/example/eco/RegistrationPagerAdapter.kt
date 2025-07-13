import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.eco.OrganizationRegistrationFragment
import com.example.eco.VolunteerRegistrationFragment

class RegistrationPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> VolunteerRegistrationFragment()
            1 -> OrganizationRegistrationFragment()
            else -> VolunteerRegistrationFragment()
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "ВОЛОНТЕР"
            1 -> "ОРГАНИЗАЦИЯ"
            else -> null
        }
    }
}