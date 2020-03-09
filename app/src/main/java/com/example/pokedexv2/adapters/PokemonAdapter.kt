package com.example.pokedexv2.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedexv2.R
import com.example.pokedexv2.data.Pokemon
import com.example.pokedexv2.databinding.CardPokemonMiniBinding
import com.example.pokedexv2.ui.PokemonGridActivity
import com.example.pokedexv2.ui.PokemonInfoActivity
import kotlinx.android.synthetic.main.card_pokemon_mini.view.*

class PokemonAdapter(): RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>() {
    lateinit var context: Context
    lateinit var pokemonList: List<Pokemon>

    constructor(context: Context, pokemonList: List<Pokemon>) : this() {
        this.context = context
        this.pokemonList = pokemonList
    }

    class PokemonViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
//        private val binding = CardPokemonMiniBinding.inflate(layoutIn)
        val pokemonCard = itemView.card_pokemon
        val pokemonSprite = itemView.image_pokemon_sprite
        val pokemonName = itemView.text_pokemon_name
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_pokemon_mini, parent, false)
        return PokemonViewHolder(view)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val pokemon = pokemonList[position]
        holder.pokemonName.text = pokemon.name
    }
    override fun getItemCount() = pokemonList.size


}