package com.pns.podcastly.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.pns.podcastly.R
import com.pns.podcastly.ui.fragment.HomeFragment
import com.pns.podcastly.ui.fragment.AccountFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var isOptionVisible = false
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
                R.id.account->setCurrentFragment(AccountFragment())
            }
            true
        }
//      Option Listener
        micOption.setOnClickListener {
            if (!isOptionVisible) {
                options.visibility = View.VISIBLE
                isOptionVisible = true
                micOption.setImageResource(R.drawable.close_icon)
            } else {
                options.visibility = View.GONE
                isOptionVisible = false
                micOption.setImageResource(R.drawable.mic_icon)
            }
        }

        recordAudio.setOnClickListener { startActivity(Intent(this, RecordAudioActivity::class.java)) }

    }

    private fun setCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment)
            .commit()

    }
}