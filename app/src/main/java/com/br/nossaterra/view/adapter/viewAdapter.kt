package com.br.nossaterra.view.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class viewAdapter(fm: FragmentManager?) : FragmentStatePagerAdapter(fm!!) {
  private val fragmentList: ArrayList<Fragment> = ArrayList()

  override fun getItem(position: Int): Fragment {

    return this.fragmentList[position]

  }
  override fun getCount(): Int {

    return this.fragmentList.size
  }

  fun addFragment(fragment : Fragment){
    this.fragmentList.add(fragment)
  }
}
