package com.example.pokedexv2.ui

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedexv2.R
import com.example.pokedexv2.adapters.PokemonGridAdapter
import com.example.pokedexv2.data.Pokemon
import com.example.pokedexv2.databinding.ActivityPokemonGridBinding
import com.example.pokedexv2.requests.response.PokemonPageResponse
import com.example.pokedexv2.viewmodel.PokemonGridActivityViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class PokemonGridActivity : AppCompatActivity(), PokemonGridAdapter.Interaction {

    private lateinit var binding: ActivityPokemonGridBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerViewAdapter: PokemonGridAdapter
    private lateinit var recyclerViewLayoutManager: GridLayoutManager
    private var nextPageUrl = ""
    private lateinit var viewModel: PokemonGridActivityViewModel
    private var isNextPageLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPokemonGridBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel =
            ViewModelProvider(this).get(PokemonGridActivityViewModel::class.java)
        binding.lifecycleOwner = this

        initializeRecyclerView()
        subscribeObservers()
        setUpSearchPokemonButton()
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
    private fun setUpSearchPokemonButton() {
        binding.buttonSearchPokemon.setOnClickListener(View.OnClickListener {
            val builder =
                AlertDialog.Builder(this)
            builder.setTitle("Search Pokemon")
            val input = EditText(this)
            input.inputType = InputType.TYPE_CLASS_TEXT
            builder.setView(input)
            builder.setPositiveButton(
                "GO"
            ) { dialog, which ->
                val pokemonName = input.text.toString().toLowerCase()
                val intent =
                    Intent(this, PokemonInfoActivity::class.java)
                intent.putExtra("POKEMON_NAME", pokemonName)
                startActivity(intent)
            }
            builder.setNegativeButton(
                "Cancel"
            ) { dialog, which -> dialog.cancel() }
            builder.show()
        })
    }

    private fun subscribeObservers() {
        viewModel.pokemonPage.observe(this, Observer<PokemonPageResponse> {
            nextPageUrl = it.nextPageUrl
        })
        viewModel.pokemonList.observe(this, Observer<List<Pokemon>> {
            recyclerViewAdapter.submitList(it)
            isNextPageLoading = false
            binding.progressBar.visibility = View.GONE
        })
    }

    override fun onItemSelected(position: Int, item: Pokemon) {
        Log.d("onItemSelected", "$position ${item.name}")
        val intent = Intent(this, PokemonInfoActivity::class.java)
        intent.putExtra("POKEMON_NAME", item.name)
        startActivity(intent)
    }
}
