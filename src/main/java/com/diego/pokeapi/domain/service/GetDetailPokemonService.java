package com.diego.pokeapi.domain.service;

import com.diego.pokeapi.application.dto.DetailPokemonDto;
import com.diego.pokeapi.domain.port.PokemonPort;


public class GetDetailPokemonService {

    private final PokemonPort pokemonPort;

    public GetDetailPokemonService(PokemonPort pokemonPort) {
        this.pokemonPort = pokemonPort;
    }

    public DetailPokemonDto getPokemon(String name){
        return pokemonPort.getPokemon(name);
    }
}
