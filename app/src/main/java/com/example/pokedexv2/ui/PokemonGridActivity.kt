package com.example.pokedexv2.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedexv2.data.Pokemon
import com.example.pokedexv2.data.PokemonPage
import com.example.pokedexv2.databinding.ActivityPokemonGridBinding
import com.example.pokedexv2.viewmodel.PokemonGridActivityViewModel

class PokemonGridActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPokemonGridBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerViewAdapter: RecyclerView.Adapter<*>
    private lateinit var recyclerViewLayoutManager: GridLayoutManager
    private lateinit var pokemonList: List<Pokemon>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPokemonGridBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel = ViewModelProvider(this).get(PokemonGridActivityViewModel::class.java)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.button.setOnClickListener {
            val intent = Intent(this, PokemonInfoActivity::class.java)
            startActivity(intent)
        }



//        viewModel.pokemonPage.observe(this, Observer {
//            pokemonPage -> viewModel.pokemonList
//        })

//        viewModel.pokemonSet.observe(this, Observer<PokemonSet> {
//            pokemonSet -> binding.textSimple.text = pokemonSet.nextSetUrl
//        })

//        viewModel.pokemonSet.observe(this, Observer<PokemonSet> {
//            pokemonSet -> pokemonList = pokemonSet.pokemonSet
//        })

//        initializeRecyclerView()
    }

    private fun initializeRecyclerView() {
//        recyclerView = binding.recyclerViewPokemonGrid
//        recyclerViewLayoutManager = GridLayoutManager(this, 2)
//        recyclerViewAdapter = PokemonAdapter(pokemonList)
    }
}
