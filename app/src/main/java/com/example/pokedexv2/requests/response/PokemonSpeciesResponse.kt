package com.example.pokedexv2.requests.response

import com.google.gson.annotations.SerializedName

class PokemonSpeciesResponse (
    @SerializedName("flavor_text_entries")
    val flavorTexts: List<FlavorText>
)

class FlavorText(
    @SerializedName("flavor_text")
    val flavorText: String,
    val language: Language,
    val version: Version
)

class Language(
    val name: String
)

class Version(
    val name: String
)