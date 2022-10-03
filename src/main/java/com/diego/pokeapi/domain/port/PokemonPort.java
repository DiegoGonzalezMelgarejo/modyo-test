package com.diego.pokeapi.domain.port;

import com.diego.pokeapi.application.dto.DetailPokemonDto;
import com.diego.pokeapi.application.dto.PokemonDto;

import java.util.List;

public interface PokemonPort {

    List<PokemonDto> getPokemons(int offset, int lint);
    DetailPokemonDto getPokemon(String name);
}
