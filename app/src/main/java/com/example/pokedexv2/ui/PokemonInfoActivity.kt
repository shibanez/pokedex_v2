package com.example.pokedexv2.ui

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.pokedexv2.R
import com.example.pokedexv2.data.Pokemon
import com.example.pokedexv2.databinding.ActivityPokemonInfoBinding
import com.example.pokedexv2.viewmodel.PokemonInfoActivityViewModel
import kotlin.math.roundToInt


class PokemonInfoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPokemonInfoBinding
    private lateinit var viewModel: PokemonInfoActivityViewModel
    private lateinit var pokemonName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        overridePendingTransition(R.anim.right_to_left, R.anim.exit)
        super.onCreate(savedInstanceState)
        binding = ActivityPokemonInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        pokemonName = intent.getStringExtra("POKEMON_NAME")!!
        viewModel = ViewModelProvider(this).get(PokemonInfoActivityViewModel::class.java)
        binding.lifecycleOwner = this

        subscribeObservers()
    }

    override fun onResume() {
        super.onResume()
        hideStatusBar()
    }

    private fun subscribeObservers() {
        viewModel.getPokemon(pokemonName).observe(this, Observer<Pokemon> { it ->
            setPokemonInfoViews(it)
        })
    }

    private fun setPokemonInfoViews(pokemon: Pokemon) {
        setBackgroundColor(pokemon.type1)
        showPokemonImage(pokemon.id)
        showPokemonTypes(pokemon.type1, pokemon.type2)
        setPokemonInfoTexts(pokemon)
        binding.layoutPokemonInfo.visibility = View.VISIBLE
    }

    private fun setPokemonInfoTexts(pokemon: Pokemon) {
        binding.textPokemonInfoName.text = pokemon.name.capitalize()
        binding.textPokemonWeight.text = convertWeight(pokemon.weight) + " lbs"
        binding.textPokemonHeight.text = convertHeight(pokemon.height)
    }

    private fun convertHeight(height: Int): String {
        val totalInches = (height * 3.937).roundToInt()
        val feet = totalInches / 12
        val remainingInches = totalInches % 12
        val formattedRemainingInches = "%02d".format(remainingInches)
        return "${feet}' ${formattedRemainingInches}\""
    }

    private fun convertWeight(weight: Int): String {
        val convertedWeight: Double = weight * 0.220462
        return String.format("%.1f", convertedWeight)
    }

    private fun showPokemonTypes(type1: String, type2: String?) {
        binding.chipType1.text = type1.capitalize()
        binding.chipType1.setChipBackgroundColorResource(
            resources.getIdentifier("${type1}Type", "color", packageName)
        )
        binding.chipType1.setChipIconResource(
            resources.getIdentifier("icon_${type1}", "drawable", packageName)
        )
        binding.chipType1.visibility = View.VISIBLE

        if (type2 != null) {
            binding.chipType2.text = type2!!.capitalize()
            binding.chipType2.setChipBackgroundColorResource(
                resources.getIdentifier("${type2}Type", "color", packageName)
            )
            binding.chipType2.setChipIconResource(
                resources.getIdentifier("icon_${type2}", "drawable", packageName)
            )
            binding.chipType2.visibility = View.VISIBLE
        }
    }

    private fun setBackgroundColor(mainType: String) {
        val typeColor = ContextCompat.getColor(this, resources
            .getIdentifier("${mainType}Type", "color", packageName))
        binding.layoutPokemonInfo.background.setTint(typeColor)
        binding.imagePokeball.setColorFilter(typeColor)
    }

    private fun showPokemonImage(id: Int) {
        val pokemonId = "%03d".format(id)
        Glide
            .with(this)
            .load("https://assets.pokemon.com/assets/cms2/img/pokedex/detail/${pokemonId}.png")
            .into(binding.imagePokemonInfoImage)
    }

    fun hideStatusBar() {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)

    }

}
