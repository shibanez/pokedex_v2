package com.example.pokedexv2.ui

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.pokedexv2.R
import com.example.pokedexv2.data.Pokemon
import com.example.pokedexv2.databinding.ActivityPokemonInfoBinding
import com.example.pokedexv2.viewmodel.PokemonInfoActivityViewModel

class PokemonInfoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPokemonInfoBinding
    private lateinit var viewModel: PokemonInfoActivityViewModel
    private lateinit var pokemonName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPokemonInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        pokemonName = intent.getStringExtra("POKEMON_NAME")!!
        viewModel = ViewModelProvider(this).get(PokemonInfoActivityViewModel::class.java)
        binding.lifecycleOwner = this

        subscribeObservers()
    }

    private fun subscribeObservers() {
        viewModel.getPokemon(pokemonName).observe(this, Observer<Pokemon> {
            it -> setPokemonInfoViews(it)
        })
    }

    private fun setPokemonInfoViews(pokemon: Pokemon) {
        val pokemonId = "%03d".format(pokemon.id)
//TODO Background gradient
//        val bgDrawable = GradientDrawable(GradientDrawable.Orientation.BL_TR, []
//            resources.getIdentifier("${pokemon.type1}Type", "color", packageName))
//        binding.imagePokemonDetailBg.setImageDrawable(bgDrawable)
        Glide
            .with(this)
            .load("https://assets.pokemon.com/assets/cms2/img/pokedex/detail/${pokemonId}.png")
            .into(binding.imagePokemonInfoImage)
        binding.textPokemonInfoName.text = pokemon.name.capitalize()
        binding.chipType1.text = pokemon.type1.capitalize()
        binding.chipType1.setChipBackgroundColorResource(
            resources.getIdentifier("${pokemon.type1}Type", "color", packageName)
        )
        binding.chipType1.setChipIconResource(
            resources.getIdentifier("icon_${pokemon.type1}", "drawable", packageName)
        ) //TODO add other type icons
        binding.chipType1.visibility = View.VISIBLE

        if (pokemon.type2 != null) {
            binding.chipType2.text = pokemon.type2!!.capitalize()
            binding.chipType2.setChipBackgroundColorResource(
                resources.getIdentifier("${pokemon.type2}Type", "color", packageName)
            )
            binding.chipType2.setChipIconResource(
                resources.getIdentifier("icon_${pokemon.type2}", "drawable", packageName)
            )
            binding.chipType2.visibility = View.VISIBLE
        }
    }
}
