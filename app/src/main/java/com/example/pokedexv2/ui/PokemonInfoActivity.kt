package com.example.pokedexv2.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.example.pokedexv2.data.Pokemon
import com.example.pokedexv2.requests.response.PokemonPageResponse
import com.example.pokedexv2.databinding.ActivityPokemonInfoBinding
import com.example.pokedexv2.repository.PokemonRepository
import com.example.pokedexv2.requests.response.PokemonResponse

class PokemonInfoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPokemonInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPokemonInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pokemonName = intent.getStringExtra("POKEMON_NAME")

        val pokemonPage = PokemonRepository.getPokemon(pokemonName)

        pokemonPage.observe(this, Observer<PokemonResponse> {
            pokemonResponse ->
                binding.pokemon = Pokemon(name = pokemonName, type1 = pokemonResponse.types[0].typeObject.name)
        })



    }
}
