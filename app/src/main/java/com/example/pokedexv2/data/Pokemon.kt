package com.example.pokedexv2.data

data class Pokemon(val name: String,
                   var spriteUrl: String = "",
                   var type1: String = "",
                   var type2: String = "")