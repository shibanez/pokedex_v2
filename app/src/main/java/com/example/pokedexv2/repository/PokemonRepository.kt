package com.example.pokedexv2.repository

import android.util.Log
import android.util.Log.d
import androidx.lifecycle.MutableLiveData
import com.example.pokedexv2.data.Pokemon
import com.example.pokedexv2.requests.response.PokemonPageResponse
import com.example.pokedexv2.requests.PokemonAPI
import com.example.pokedexv2.requests.response.PokemonResponse
import com.example.pokedexv2.requests.response.PokemonSpeciesResponse
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object PokemonRepository {

    private var pokemonList = mutableListOf<Pokemon>()
    private val pokemonListLiveData = MutableLiveData<List<Pokemon>>()
    private val pokemonPage = MutableLiveData<PokemonPageResponse>()

    private val pokemonAPI = Retrofit.Builder()
        .baseUrl("https://pokeapi.co/api/v2/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(OkHttpClient())
        .build().create(PokemonAPI::class.java)

    fun getPokemonPage(url: String  = "https://pokeapi.co/api/v2/pokemon" ): MutableLiveData<PokemonPageResponse> {
        pokemonAPI.getPokemonPage(url).enqueue(object: Callback<PokemonPageResponse> {
            override fun onFailure(call: Call<PokemonPageResponse>, t: Throwable) {
                d("onFailure", t.toString())
            }
            override fun onResponse(call: Call<PokemonPageResponse>, response: Response<PokemonPageResponse>) {
                addPokemonPageToPokemonList(response.body()!!.pokemonList)
                pokemonPage.value = response.body()
            }
        })
        return pokemonPage
    }
    fun getPokemon(name: String): MutableLiveData<Pokemon> {
        val pokemonLiveData = MutableLiveData<Pokemon>()
        pokemonAPI.getPokemon(name).enqueue(object: Callback<PokemonResponse> {
            override fun onFailure(call: Call<PokemonResponse>, t: Throwable) {
                Log.d("onFailure", t.toString())
            }
            override fun onResponse(
                call: Call<PokemonResponse>, response: Response<PokemonResponse>) {
                val pokemonResponse = response.body()
                val pokemon = pokemonResponse?.let { parsePokemonResponse(it) }
                if (pokemonResponse!!.species != null) {
                    pokemon?.let {
                        getPokemonSpeciesFlavorText(pokemonResponse.species.url,
                            it, pokemonLiveData)
                    }
                } else {
                    pokemonLiveData.value = pokemon
                }
            }
        })
        return pokemonLiveData
    }

    fun getPokemonList(): MutableLiveData<List<Pokemon>> {
        return pokemonListLiveData
    }
    private fun getPokemonSpeciesFlavorText(url: String, pokemon: Pokemon, pokemonLiveData: MutableLiveData<Pokemon>) {
        pokemonAPI.getPokemonSpecies(url).enqueue(object: Callback<PokemonSpeciesResponse> {
            override fun onFailure(call: Call<PokemonSpeciesResponse>, t: Throwable) {
                Log.d("onFailure", t.toString())
            }
            override fun onResponse(
                call: Call<PokemonSpeciesResponse>,
                response: Response<PokemonSpeciesResponse>) {
                val pokemonSpeciesResponse = response.body()
                pokemon.description = getPokemonDescription(pokemonSpeciesResponse!!)
                pokemonLiveData.value = pokemon
            }
        })
    }
    private fun getPokemonDescription(pokemonSpeciesResponse: PokemonSpeciesResponse): String {
        for (flavorText in pokemonSpeciesResponse.flavorTexts) {
            if (flavorText.language.name == "en" &&
                flavorText.version.name in listOf("ultra-sun", "x")) {
                Log.d("flavor", flavorText.flavorText)
                return flavorText.flavorText
            }
        }
        return "Not available"
    }

    private fun parsePokemonResponse(pokemonResponse: PokemonResponse): Pokemon {
        val pokemon = Pokemon(pokemonResponse.name)

        pokemon.id = pokemonResponse.id
        val pokemonTypes = pokemonResponse.types
        for (type in pokemonTypes) {
            if (type.slot == 1) {
                pokemon.type1 = type.typeObject.name
            } else if (type.slot == 2) {
                pokemon.type2 = type.typeObject.name
            }
        }
        pokemon.weight = pokemonResponse.weight
        pokemon.height = pokemonResponse.height
        return pokemon
    }

    private fun addPokemonPageToPokemonList(newPokemonList: List<Pokemon>) {
        pokemonList.addAll(newPokemonList)
        pokemonListLiveData.value = pokemonList
    }

}