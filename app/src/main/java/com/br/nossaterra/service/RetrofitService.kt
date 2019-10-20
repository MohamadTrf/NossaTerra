package com.br.nossaterra.service
import android.content.Context
import android.telecom.Call
import com.br.nossaterra.config.RestConfig
import retrofit2.http.GET
import com.br.nossaterra.controller.Controller
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.br.nossaterra.models.Planta
import java.util.*
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitService{
    val gson = JsonObject()
    val strJson = Gson().toJson(gson)

    public fun getPlanta(vivo: Boolean){
        fun getUserPhoto(context: Context): Planta? {

            try {

                val aaa = Controller()
                val rest = RestConfig()
                val jsonObject = rest.getObjectJsonRequest("http://675f86f9.ngrok.io")

                if (!jsonObject.get("dados").isJsonNull && jsonObject.get("dados").asString != "") {
                    val checkin = jsonObject.get("dados").asJsonObject
                    val dadosCheck = Gson().fromJson(checkin, Planta::class.java)
                    aaa.clicaBotao(vivo,dadosCheck.percentual)
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }

            return null
        }
    }

}
