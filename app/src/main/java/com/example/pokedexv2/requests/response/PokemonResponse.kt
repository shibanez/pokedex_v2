package com.example.pokedexv2.requests.response

import com.google.gson.annotations.SerializedName

data class PokemonResponse(
//    @SerializedName("types")
    val types: List<Type>,
    val name: String,
    val id: Int,
    val height: Int,
    val weight: Int,
    val species: Species
)

data class Type(
    val slot: Int,
    @SerializedName("type")
    val typeObject: TypeObject
)

data class TypeObject(
    val name: String
)

data class Species(
    val name: String,
    val url: String
)