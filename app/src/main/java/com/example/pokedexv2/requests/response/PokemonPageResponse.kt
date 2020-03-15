package com.example.pokedexv2.requests.response

import com.example.pokedexv2.data.Pokemon
import com.google.gson.annotations.SerializedName

data class PokemonPageResponse(
    @SerializedName("count")
    val total: Int,
    @SerializedName("next")
    val nextPageUrl: String,
    @SerializedName("previous")
    val previousPageUrl: String,
    @SerializedName("results")
    val pokemonList: List<Pokemon>)