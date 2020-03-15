package com.example.pokedexv2.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedexv2.adapters.PokemonGridAdapter
import com.example.pokedexv2.databinding.ActivityPokemonGridBinding
import com.example.pokedexv2.requests.response.PokemonPageResponse
import com.example.pokedexv2.viewmodel.PokemonGridActivityViewModel
import com.example.pokedexv2.data.Pokemon

class PokemonGridActivity : AppCompatActivity(), PokemonGridAdapter.Interaction {

    private lateinit var binding: ActivityPokemonGridBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerViewAdapter: PokemonGridAdapter
    private lateinit var recyclerViewLayoutManager: GridLayoutManager
    private var nextPageUrl = "";
    private lateinit var viewModel: PokemonGridActivityViewModel
    private var pokemonList = mutableListOf<Pokemon>()
    private var isNextPageLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPokemonGridBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel =
            ViewModelProvider(this).get(PokemonGridActivityViewModel::class.java)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        initializeRecyclerView()
        viewModel.pokemonPage.observe(this, Observer<PokemonPageResponse> {
            it ->
            nextPageUrl = it.nextPageUrl
            this.pokemonList.addAll(it.pokemonList)
            recyclerViewAdapter.submitList(pokemonList)
            isNextPageLoading = false
            binding.progressBar.visibility = View.GONE
        })
    }

    private fun initializeRecyclerView() {
        recyclerView = binding.recyclerViewPokemonGrid
        recyclerViewLayoutManager = GridLayoutManager(this, 2)
        recyclerViewAdapter = PokemonGridAdapter(this)
        recyclerView.adapter = recyclerViewAdapter
        recyclerView.layoutManager = recyclerViewLayoutManager

        recyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if(!recyclerView.canScrollVertically(1) && !isNextPageLoading && nextPageUrl != null) {
                    viewModel.getPokemonPage(nextPageUrl)
                    isNextPageLoading = true
                }
            }
        })
    }

    override fun onItemSelected(position: Int, item: Pokemon) {
        Log.d("onItemSelected", "$position ${item.name}")
    }
}
