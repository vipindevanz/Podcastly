package com.pns.podcastly.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.pns.podcastly.R
import kotlinx.android.synthetic.main.fragment_account.*

class AccountFragment : Fragment(R.layout.fragment_account) {
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        firebaseAuth = FirebaseAuth.getInstance()
        if (firebaseAuth.currentUser != null) {
            Glide.with(profilePic).load(firebaseAuth?.currentUser?.photoUrl).into(profilePic)
            userName.text = firebaseAuth.currentUser?.displayName
        }
    }
}