package com.diego.pokeapi.domain.service;

import com.diego.pokeapi.application.dto.DetailPokemonDto;
import com.diego.pokeapi.application.dto.PokemonDto;
import com.diego.pokeapi.domain.port.PokemonPort;

import java.util.List;


public class GetPokemonsService {

    private final PokemonPort pokemonPort;

    public GetPokemonsService(PokemonPort pokemonPort) {
        this.pokemonPort = pokemonPort;
    }

    public List<PokemonDto> getPokemons(int offSet, int limit){
        return   pokemonPort.getPokemons(offSet, limit);
    }

    public DetailPokemonDto getPokemon(String name){
        return pokemonPort.getPokemon(name);
    }
}
