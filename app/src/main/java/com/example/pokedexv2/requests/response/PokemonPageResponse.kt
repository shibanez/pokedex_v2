package com.example.pokedexv2.requests.response

import com.example.pokedexv2.data.Pokemon
import com.google.gson.annotations.SerializedName

data class PokemonPageResponse(
    @SerializedName("count")
    val total: Int,
    @SerializedName("next")
    val nextSetUrl: String,
    @SerializedName("previous")
    val previousSetUrl: String,
    @SerializedName("results")
    val pokemonList: List<Pokemon>)