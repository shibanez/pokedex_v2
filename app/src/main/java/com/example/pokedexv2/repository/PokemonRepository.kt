package com.example.pokedexv2.repository

import android.util.Log
import android.util.Log.d
import androidx.lifecycle.MutableLiveData
import com.example.pokedexv2.data.PokemonPage
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object PokemonRepository {

    init {
        Log.d("MAMAMO", "YOW")
    }

//    private var pokemonPage = MutableLiveData<PokemonPage>()

    private val pokemonAPI = Retrofit.Builder()
        .baseUrl("https://pokeapi.co/api/v2/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(OkHttpClient())
        .build().create(PokemonAPI::class.java)

    fun getPokemonPage(url: String = "https://pokeapi.co/api/v2/pokemon"): MutableLiveData<PokemonPage> {
        val pokemonPage = MutableLiveData<PokemonPage>()

        pokemonAPI.getPokemonPage(url).enqueue(object: Callback<PokemonPage> {
            override fun onFailure(call: Call<PokemonPage>, t: Throwable) {
                d("onFailure", t.toString())
            }
            override fun onResponse(call: Call<PokemonPage>, response: Response<PokemonPage>) {
                pokemonPage.value = response.body()
            }
        })

        return pokemonPage
    }

}