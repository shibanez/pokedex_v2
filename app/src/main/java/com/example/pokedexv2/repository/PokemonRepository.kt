package com.example.pokedexv2.repository

import android.util.Log
import android.util.Log.d
import androidx.lifecycle.MutableLiveData
import com.example.pokedexv2.data.Pokemon
import com.example.pokedexv2.requests.response.PokemonPageResponse
import com.example.pokedexv2.requests.PokemonAPI
import com.example.pokedexv2.requests.response.PokemonResponse
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object PokemonRepository {

    private var pokemonList = mutableListOf<Pokemon>()
    private val pokemonListLiveData = MutableLiveData<List<Pokemon>>()
    private val pokemonPage = MutableLiveData<PokemonPageResponse>()

    private val pokemonAPI = Retrofit.Builder()
        .baseUrl("https://pokeapi.co/api/v2/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(OkHttpClient())
        .build().create(PokemonAPI::class.java)

    fun getPokemonPage(url: String  = "https://pokeapi.co/api/v2/pokemon" ): MutableLiveData<PokemonPageResponse> {
        pokemonAPI.getPokemonPage(url).enqueue(object: Callback<PokemonPageResponse> {
            override fun onFailure(call: Call<PokemonPageResponse>, t: Throwable) {
                d("onFailure", t.toString())
            }
            override fun onResponse(call: Call<PokemonPageResponse>, response: Response<PokemonPageResponse>) {
                addPokemonPageToPokemonList(response.body()!!.pokemonList)
                pokemonPage.value = response.body()
            }
        })
        return pokemonPage
    }
    fun getPokemon(name: String): MutableLiveData<PokemonResponse> {
        val pokemonResponse = MutableLiveData<PokemonResponse>()

        pokemonAPI.getPokemon(name).enqueue(object: Callback<PokemonResponse> {
            override fun onFailure(call: Call<PokemonResponse>, t: Throwable) {
                Log.d("onFailure", t.toString())
            }
            override fun onResponse(
                call: Call<PokemonResponse>,
                response: Response<PokemonResponse>
            ) {
                pokemonResponse.value = response.body()
            }

        })

        return pokemonResponse
    }

    fun getPokemonList(): MutableLiveData<List<Pokemon>> = pokemonListLiveData

    private fun addPokemonPageToPokemonList(newPokemonList: List<Pokemon>) {
        for ((index, pokemon) in newPokemonList.withIndex()) {
            pokemon.spriteUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${pokemonList.size + index + 1}.png"
        }
        pokemonList.addAll(newPokemonList)
        pokemonListLiveData.value = pokemonList
    }

}