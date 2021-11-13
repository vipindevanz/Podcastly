package com.pns.podcastly.ui.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.pns.podcastly.R
import com.pns.podcastly.remote.model.ListenNoteResponseDTO
import com.pns.podcastly.repo.ListenNotesApiRepo
import com.pns.podcastly.ui.fragment.HomeFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val podcastList = mutableListOf<ListenNoteResponseDTO>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomNav.background = null
        bottomNav.menu.getItem(2).isEnabled = false

        setCurrentFragment(HomeFragment())

        // Navigate to Fragment
        bottomNav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.home -> setCurrentFragment(HomeFragment())
            }
            true
        }


    }

    private fun setCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment)
            .commit()

    }
}