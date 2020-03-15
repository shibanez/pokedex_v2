package com.example.pokedexv2.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.pokedexv2.data.Pokemon
import com.example.pokedexv2.requests.response.PokemonPageResponse
import com.example.pokedexv2.databinding.ActivityPokemonInfoBinding
import com.example.pokedexv2.repository.PokemonRepository
import com.example.pokedexv2.requests.response.PokemonResponse
import com.example.pokedexv2.viewmodel.PokemonInfoActivityViewModel

class PokemonInfoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPokemonInfoBinding
    private lateinit var viewModel: PokemonInfoActivityViewModel
    private lateinit var pokemonName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPokemonInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        pokemonName = intent.getStringExtra("POKEMON_NAME")
        viewModel = ViewModelProvider(this).get(PokemonInfoActivityViewModel::class.java)
        binding.lifecycleOwner = this

        subscribeObservers()
    }

    private fun subscribeObservers() {
        viewModel.getPokemon(pokemonName).observe(this, Observer<Pokemon> {
            it -> binding.pokemon = it
        })
    }
}
