package com.example.pokedexv2.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.example.pokedexv2.data.PokemonPage
import com.example.pokedexv2.databinding.ActivityPokemonInfoBinding
import com.example.pokedexv2.repository.PokemonRepository

class PokemonInfoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPokemonInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPokemonInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pokemonPage =
            PokemonRepository.getPokemonPage() as LiveData<PokemonPage>

        pokemonPage.observe(this, Observer<PokemonPage> {
            pokemonSet -> binding.textMamamo.text = pokemonSet.pokemonList[0].name
        })



    }
}
