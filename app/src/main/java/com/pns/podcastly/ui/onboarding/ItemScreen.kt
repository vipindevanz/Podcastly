package com.pns.podcastly.ui.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.pns.podcastly.R

class ItemScreen : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_item_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}

// activity?.supportFragmentManager?.beginTransaction()?.setCustomAnimations(
//                    R.anim.slide_in,  // enter
//                    R.anim.fade_out,  // exit
//                    R.anim.fade_in,   // popEnter
//                    R.anim.slide_out  // popExit
//                )
//                    ?.add(
//                        R.id.createAccountFragmentContainer,
//                        GoalWeightFragment::class.java,
//                        bundle
//                    )
//                    ?.addToBackStack("")
//                    ?.commit()