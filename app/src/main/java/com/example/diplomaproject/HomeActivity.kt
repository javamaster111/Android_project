@file:Suppress("ktlint:standard:no-unused-imports")

package com.example.diplomaproject

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.diplomaproject.databinding.ActivityHomeBinding
import com.example.diplomaproject.mainpage.HomeFragment
import com.example.diplomaproject.mainpage.RoomsFragment
import com.example.diplomaproject.profile.ProfileFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

//    val locale = Locale(language)
//    Locale.setDefault(locale)
//
//    val resources = context.resources
//
//    val configuration = resources.configuration
//    configuration.locale = locale
//    configuration.setLayoutDirection(locale)
//
//    resources.updateConfiguration(configuration, resources.displayMetrics)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(HomeFragment())

        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home_id -> replaceFragment(HomeFragment())
                R.id.home1_id -> replaceFragment(RoomsFragment())
                R.id.profile_id -> replaceFragment(ProfileFragment())

                else -> {
                }
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.container, fragment)
        fragmentTransaction.commit()
    }
}
