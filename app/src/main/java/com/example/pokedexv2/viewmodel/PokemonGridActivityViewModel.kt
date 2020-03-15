package com.example.pokedexv2.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.pokedexv2.data.Pokemon
import com.example.pokedexv2.requests.response.PokemonPageResponse
import com.example.pokedexv2.repository.PokemonRepository

class PokemonGridActivityViewModel: ViewModel() {

    private var _pokemonPage = PokemonRepository.getPokemonPage()
//    private var _pokemonList = PokemonRepository.getPokemonList()

    val pokemonPage: LiveData<PokemonPageResponse>
        get() = _pokemonPage
//    val pokemonList: LiveData<List<Pokemon>>
//        get() = _pokemonList

    fun getPokemonPage(url: String) {
        _pokemonPage = PokemonRepository.getPokemonPage(url)
    }

}