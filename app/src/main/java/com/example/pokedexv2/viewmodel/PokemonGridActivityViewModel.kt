package com.example.pokedexv2.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pokedexv2.data.Pokemon
import com.example.pokedexv2.data.PokemonPage
import com.example.pokedexv2.repository.PokemonRepository

class PokemonGridActivityViewModel: ViewModel() {

    private var _pokemonPage = MutableLiveData<PokemonPage>()
    private var _pokemonList = MutableLiveData<List<Pokemon>>()

    val pokemonPage: LiveData<PokemonPage>
        get() = _pokemonPage
    val pokemonList: LiveData<List<Pokemon>>
        get() = _pokemonList

    init {
        _pokemonPage = PokemonRepository.getPokemonPage()
        _pokemonList.postValue(_pokemonPage.value?.pokemonList)
    }

}