package com.example.pokedexv2.requests

import androidx.paging.ItemKeyedDataSource
import com.example.pokedexv2.data.Pokemon
import com.example.pokedexv2.repository.PokemonRepository
import com.example.pokedexv2.requests.response.PokemonPageResponse
import com.example.pokedexv2.requests.response.PokemonResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

interface PokemonAPI {

    @GET
    fun getPokemonPage(@Url url: String): Call<PokemonPageResponse>

    @GET("pokemon/{pokemon}")
    fun getPokemon(
        @Path("pokemon")
        pokemon: String
    ): Call<PokemonResponse>
}