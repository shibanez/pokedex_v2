package com.example.pokedexv2.repository

import com.example.pokedexv2.data.PokemonPage
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface PokemonAPI {

    @GET
    fun getPokemonPage(@Url url: String): Call<PokemonPage>
}