package com.example.uascobacoba

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

// Kelas adapter untuk ViewPager2
class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    // Metode untuk membuat dan mengembalikan fragmen pada posisi tertentu
    override fun createFragment(position: Int): Fragment {
        return if (position == 1) {
            RegisterFragment()
        } else LoginFragment()
    }

    // Metode untuk mendapatkan jumlah total fragmen dalam ViewPager2
    override fun getItemCount(): Int {
        return 2
    }
}
