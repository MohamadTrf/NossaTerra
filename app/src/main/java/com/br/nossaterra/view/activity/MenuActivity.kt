package com.br.nossaterra.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.br.nossaterra.R
import com.br.nossaterra.view.adapter.viewAdapter
import com.br.nossaterra.view.fragment.*

class MenuActivity : AppCompatActivity(){

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_menu)

    val viewPager = findViewById<ViewPager>(R.id.viewPagerMain)

    val adapter = viewAdapter(supportFragmentManager)
    adapter.addFragment(PrimeiroCapituloFragment())
    adapter.addFragment(SegundoCapituloFragment())
    adapter.addFragment(TerceiroCapituloFragment())
    adapter.addFragment(QuartoCapituloFragment())
    adapter.addFragment(QuintoCapituloFragment())
    adapter.addFragment(SextoCapituloFragment())
    adapter.addFragment(SetimoCapituloFragment())
    adapter.addFragment(OitavoCapituloFragment())


    viewPager.adapter = adapter

  }

}
