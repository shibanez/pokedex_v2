package com.example.pokedexv2.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pokedexv2.data.Pokemon
import com.example.pokedexv2.requests.response.PokemonPageResponse
import com.example.pokedexv2.repository.PokemonRepository

class PokemonInfoActivityViewModel: ViewModel() {

    fun getPokemon(name: String): MutableLiveData<Pokemon> {
        return PokemonRepository.getPokemon(name)
    }
}