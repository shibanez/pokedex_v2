package com.example.pokedexv2.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pokedexv2.data.Pokemon
import com.example.pokedexv2.data.PokemonPage
import com.example.pokedexv2.repository.PokemonRepository

class PokemonGridActivityViewModel: ViewModel() {

    private var _pokemonPage = PokemonRepository.getPokemonPage()
    private var _pokemonList = PokemonRepository.getPokemonList()

    val pokemonPage: LiveData<PokemonPage>
        get() = _pokemonPage
    val pokemonList: LiveData<List<Pokemon>>
        get() = _pokemonList

}