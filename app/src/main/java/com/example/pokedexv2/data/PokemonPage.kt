package com.example.pokedexv2.data

import com.google.gson.annotations.SerializedName

data class PokemonPage(
    @SerializedName("count")
    val total: Int,
    @SerializedName("next")
    val nextSetUrl: String,
    @SerializedName("previous")
    val previousSetUrl: String,
    @SerializedName("results")
    val pokemonList: List<Pokemon>) {
}