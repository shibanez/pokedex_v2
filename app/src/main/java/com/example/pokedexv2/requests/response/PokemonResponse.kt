package com.example.pokedexv2.requests.response

import com.google.gson.annotations.SerializedName

data class PokemonResponse (
//    @SerializedName("types")
    val types: List<Type>
)

data class Type(
    val slot: Int,
    @SerializedName("type")
    val typeObject: TypeObject
)

data class TypeObject(
    val name: String
)