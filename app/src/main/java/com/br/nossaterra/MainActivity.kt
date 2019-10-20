package com.br.nossaterra

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.br.nossaterra.config.RestConfig
import com.br.nossaterra.service.RetrofitService

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        RestConfig();
        RetrofitService().getPlanta(true)
    }
}
