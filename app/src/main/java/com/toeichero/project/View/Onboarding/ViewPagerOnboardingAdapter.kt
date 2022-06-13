package com.toeichero.project.View.Onboarding

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class ViewPagerOnboardingAdapter(fragmentManager: FragmentManager): FragmentPagerAdapter(fragmentManager) {
    private val fragments = listOf(
        FirstOnboardingFragment(),
        SecondOnboardingFragment(),
        ThirdOnboardingFragment()
    )

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }
}