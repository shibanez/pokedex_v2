package com.example.pokedexv2.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pokedexv2.R
import com.example.pokedexv2.data.Pokemon
import com.example.pokedexv2.databinding.CardPokemonMiniBinding
import com.example.pokedexv2.ui.PokemonGridActivity
import com.example.pokedexv2.ui.PokemonInfoActivity
import kotlinx.android.synthetic.main.card_pokemon_mini.view.*

class PokemonAdapter(): RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>() {
    lateinit var pokemonList: List<Pokemon>
    private lateinit var binding: CardPokemonMiniBinding

    constructor(pokemonList: List<Pokemon>) : this() {
        this.pokemonList = pokemonList
    }

    class PokemonViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val pokemonCard = itemView.card_pokemon
        val pokemonSprite = itemView.image_pokemon_sprite
        val pokemonName = itemView.text_pokemon_name
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        binding = CardPokemonMiniBinding.inflate(LayoutInflater.from(parent.context), parent, false)

//        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_pokemon_mini, parent, false)
        val view = binding.root
        return PokemonViewHolder(view)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val pokemon = pokemonList[position]
        binding.pokemon = pokemon
        holder.pokemonName.text = pokemon.name
        Glide
            .with(binding.root)
            .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${position + 1}.png") //TODO please refactor lol
            .into(binding.imagePokemonSprite)
    }
    override fun getItemCount() = pokemonList.size


}