package com.example.pokedexv2.data

data class Pokemon(var name: String,
                   var id: Int = 0,
                   var type1: String = "",
                   var type2: String? = null,
                   var weight: Int = 0,
                   var height: Int = 0,
                   var description: String = "") {

    override fun equals(other: Any?): Boolean {
        if (javaClass != other?.javaClass) return false

        other as Pokemon

        if (name != other.name ||
                type1 != other.type1 || type2 != other.type2) return false

        return true
    }
}